package ru.justmc.api.auth

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insertIgnore
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.update
import ru.justmc.api.sql
import ru.justmc.api.user.User
import ru.justmc.api.user.UsersTable

object AuthTypeTable : Table("auth_type") {
    val user = reference("user", UsersTable)
    val type = customEnumeration("type", fromDb = { AuthType[it as Int] }, toDb = { it.id })

    suspend fun get(user: User): AuthType? = sql {
        val resultRow = select { AuthTypeTable.user eq UsersTable.toEntityId(user) }.singleOrNull()
        resultRow?.get(type)
    }

    suspend fun set(user: User, authType: AuthType) = sql {
        val update = insertIgnore {
            it[AuthTypeTable.user] = UsersTable.toEntityId(user)
            it[type] = authType
        }.resultedValues.isNullOrEmpty()
        if (update) {
            update({ AuthTypeTable.user eq UsersTable.toEntityId(user) }) {
                it[type] = authType
            }
        }
    }
}