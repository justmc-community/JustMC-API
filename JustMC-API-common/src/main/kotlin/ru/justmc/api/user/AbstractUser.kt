package ru.justmc.api.user

import one.cyanpowered.common.terminable.composite.CompositeTerminable
import ru.justmc.api.sessions.Session
import ru.justmc.sdk.localization.Text
import java.util.*
import kotlin.collections.HashMap

@Suppress("UNCHECKED_CAST")
abstract class AbstractUser(
    override var id: Int = -1,
    override var name: String = ""
) : User {
    val terminableRegistry = CompositeTerminable.create()

    val metadata: MutableMap<String, Any> = HashMap()

    override lateinit var lastIp: String
    override var locale: Locale = Locale("ru", "RU")
    override lateinit var session: Session
    override var authorized: Boolean = false

    override fun sendTitle(text: Text, fadeIn: Int, stay: Int, fadeOut: Int) {}
    override fun sendMessage(text: Text) {}
    override fun sendActionBar(text: Text) {}
    override fun kick(text: Text) {}

    override fun <T : AutoCloseable> bind(terminable: T): T = terminableRegistry.bind(terminable)

    override fun toString(): String {
        return "AbstractUser(id=$id, name='$name', lastIp='$lastIp', locale=$locale, session=$session, authorized=$authorized)"
    }

    fun setMetadata(key: String, value: Any) {
        metadata[key] = value
    }

    fun <T> getMetadata(key: String): T {
        return metadata[key] as T
    }

    fun hasMetadata(key: String): Boolean {
        return metadata.containsKey(key)
    }

    fun removeMetadata(key: String) {
        metadata.remove(key)
    }
}