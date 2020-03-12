package ru.justmc.api.sessions

import ru.justmc.api.user.User
import java.time.Instant

interface SessionManager {
    suspend fun create(
        user: User?,
        ip: String,
        authorized: Boolean = false,
        startTime: Instant = Instant.now()
    ): Session = SessionsTable.create(user, ip, authorized, startTime)
}