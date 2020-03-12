package ru.justmc.api.auth

import ru.justmc.api.sql
import ru.justmc.api.user.User

data class AuthData<T>(
    val user: User,
    val lastIp: String,
    val authType: AuthType,
    val data: T
) {
    companion object {
        suspend fun loadAuthData(user: User) = sql {

        }
    }
}