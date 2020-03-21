package ru.justmc.api.vk

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.insertIgnore
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.update
import ru.justmc.api.getUser
import ru.justmc.api.sql
import ru.justmc.api.user.User
import ru.justmc.api.user.UsersTable

object VkUsersTable : IntIdTable("vk_users") {
    var userId = reference("user_id", UsersTable)

    suspend fun getUserByVkUser(vkId: Int): User? = sql {
        val result = select { VkUsersTable.id eq EntityID(vkId, VkUsersTable) }.singleOrNull()
        if (result != null) {
            getUser(result[userId].value)
        } else {
            null
        }
    }

    suspend fun getVkUser(user: User): VkUser? = sql {
        val result = select { userId eq user.id }.singleOrNull()
        if (result != null) {
            VkUser(result[VkUsersTable.id].value)
        } else {
            null
        }
    }

    suspend fun setVkUser(user: User, vkUser: VkUser) = sql {
        val update = insertIgnore {
            it[id] = EntityID(vkUser.id, VkUsersTable)
            it[userId] = EntityID(user.id, UsersTable)
        }.resultedValues.isNullOrEmpty()
        if (update) {
            update({ VkUsersTable.id eq EntityID(vkUser.id, VkUsersTable) }) {
                it[userId] = EntityID(user.id, UsersTable)
            }
        }
    }
}
