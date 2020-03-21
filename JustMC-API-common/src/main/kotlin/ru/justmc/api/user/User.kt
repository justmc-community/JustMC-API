package ru.justmc.api.user

import one.cyanpowered.common.terminable.TerminableConsumer
import ru.justmc.api.sessions.Session
import ru.justmc.sdk.localization.Text
import java.util.*

interface User : TerminableConsumer {
    var id: Int
    var name: String
    var lastIp: String
    var locale: Locale
    var session: Session
    var authorized: Boolean
    val displayName: String get() = name // TODO: Сделать префиксы

    fun sendTitle(text: Text, fadeIn: Int = 20, stay: Int = 20, fadeOut: Int = 20)
    fun sendMessage(text: Text)
    fun sendActionBar(text: Text)
    fun kick(text: Text)
}