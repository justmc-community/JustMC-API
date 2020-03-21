package ru.justmc.api.sessions

import ru.justmc.api.user.User
import java.time.Instant

data class Session(
    var id: Int = -1,
    var user: User?,
    val ip: String,
    var authorized: Boolean = false,
    var startTime: Instant = Instant.now(),
    var endTime: Instant? = null
)