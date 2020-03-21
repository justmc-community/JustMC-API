package ru.justmc.api.auth

import one.cyanpowered.common.terminable.Terminable
import ru.justmc.api.authManager
import ru.justmc.api.user.User
import ru.justmc.sdk.localization.textOf

abstract class AuthStage(
    var user: User
) : Terminable, Runnable {
    var nextStage: AuthStage? = null
        set(value) {
            val previously = field
            field = value
            if (previously != null) {
                value?.setLast(previously)
            }
        }

    open fun start() {}

    override fun close() {}

    /**
     * Что делать, если требование выполнено
     */
    open fun complete() {}

    /**
     * Что делать, если требование выполнено, вариант с callback
     */
    open fun complete(done: () -> Unit) {
        done()
    }

    /**
     * Что делать, елси требование не выполнено
     */
    open fun failure() {}

    /**
     * Начать выполнять следующее требование от игрока
     * А если требований нету, то отправить игрока на сервер
     */
    open fun next() {
        user.sendMessage(textOf("\n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n"))
        user.sendTitle(textOf(" \n "))
        complete()
        complete {
            val next = nextStage
            if (next == null) {
                authManager.removeAuthStage(user)
                authManager.authorization(user)
                close()

                println(user.name + " прошел, авторизацию, перемещаем в хаб")
            } else {
                authManager.setCurrentAuthStage(user, next)

                println(user.name + " еще не прошел авторизацию, ставим следующее требование")
            }
        }
    }

    /**
     * Установить стадию авторизации игрока в конец очереди.
     * Таким образом стадия начнется после текущей стадии, а так же все запланнированных.
     */
    fun setLast(last: AuthStage) {
        var currentLast: AuthStage = this
        while (currentLast.nextStage != null) {
            currentLast = currentLast.nextStage!!
        }
        currentLast.nextStage = last
    }
}