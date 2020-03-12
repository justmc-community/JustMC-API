package ru.justmc.api.telegram

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.insertIgnore
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.update
import ru.justmc.api.getUser
import ru.justmc.api.sql
import ru.justmc.api.user.User
import ru.justmc.api.user.UsersTable

object TelegramUsersTable : IntIdTable("telegram_users") {
    var userId = reference("user_id", UsersTable)

    suspend fun getUserByVkUser(vkId: Int): User? = sql {
        val result = select { TelegramUsersTable.id eq EntityID(vkId, TelegramUsersTable) }.singleOrNull()
        if (result != null) {
            getUser(result[userId].value)
        } else {
            null
        }
    }

    suspend fun getTelegramUser(user: User): TelegramUser? = sql {
        val result = select { userId eq user.id }.singleOrNull()
        if (result != null) {
            TelegramUser(result[TelegramUsersTable.id].value)
        } else {
            null
        }
    }

    suspend fun setTelegramUser(user: User, telegramUser: TelegramUser) = sql {
        val update = insertIgnore {
            it[id] = EntityID(telegramUser.id, TelegramUsersTable)
            it[userId] = EntityID(user.id, UsersTable)
        }.resultedValues.isNullOrEmpty()
        if (update) {
            update({ TelegramUsersTable.id eq EntityID(telegramUser.id, TelegramUsersTable) }) {
                it[userId] = EntityID(user.id, UsersTable)
            }
        }
    }
}
