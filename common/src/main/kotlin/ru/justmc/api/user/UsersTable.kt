package ru.justmc.api.user

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.select
import ru.justmc.api.sql
import ru.justmc.api.userManager

object UsersTable : IntIdTable("users") {
    var name = varchar("name", 16)
    val ip = varchar("ip", 50)

    suspend fun get(name: String) = sql {
        val result = select { UsersTable.name like name }.singleOrNull()
        if (result == null) {
            userManager.createUser(name = name)
        } else {
            userManager.createUser(id = result[UsersTable.id].value, name = result[UsersTable.name]).apply {
                lastIp = result[ip]
            }
        }
    }

    suspend fun get(id: Int) = sql {
        val result = select { UsersTable.id eq EntityID(id, UsersTable) }.singleOrNull()
        if (result == null) {
            userManager.createUser(id = id)
        } else {
            userManager.createUser(id = id, name = result[name]).apply {
                lastIp = result[ip]
            }
        }
    }

    suspend fun add(user: User): Int = sql {
        println("user name: ${user.name}")
        val newId = insertAndGetId {
            it[name] = user.name
        }.value
        user.id = newId
        println("user: [${user.id}] ${user.name} ")
        newId
    }

    fun toEntityId(user: User) = EntityID(user.id, this)
}