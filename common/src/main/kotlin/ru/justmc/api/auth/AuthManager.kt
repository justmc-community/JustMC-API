package ru.justmc.api.auth

import ru.justmc.api.user.User

interface AuthManager {
    suspend fun getAuthData(user: User): AuthData<*>?

    fun setCurrentAuthStage(user: User, authStage: AuthStage)

    fun getCurrentAuthStage(user: User): AuthStage?

    fun removeAuthStage(user: User)

    fun authorization(user: User)
}