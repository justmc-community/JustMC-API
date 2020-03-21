package ru.justmc.api.velocity

import com.velocitypowered.api.event.player.*
import com.velocitypowered.api.proxy.Player
import ru.justmc.api.getUser
import ru.justmc.api.user.User

val Player.user: User get() = getUser(username)
val KickedFromServerEvent.user get() = player.user
val PlayerChatEvent.user get() = player.user
val PlayerChooseInitialServerEvent.user get() = player.user
val PlayerModInfoEvent.user get() = player.user
val PlayerResourcePackStatusEvent.user get() = player.user
val PlayerSettingsChangedEvent.user get() = player.user
val ServerConnectedEvent.user get() = player.user
val ServerPreConnectEvent.user get() = player.user
val TabCompleteEvent.user get() = player.user