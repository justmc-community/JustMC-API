package ru.justmc.api.user

interface
UserManager {
    val userMap: Map<String, User>

    suspend fun getUser(name: String): User =
        userMap[name] ?: UsersTable.get(name)

    suspend fun getUser(id: Int): User =
        userMap.values.find { it.id == id }
            ?: UsersTable.get(id)

    fun getUsers() = userMap.map { it.value }

    fun createUser(id: Int = -1, name: String = ""): User
}