package ru.justmc.api.sessions

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.`java-time`.timestamp
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.select
import ru.justmc.api.sql
import ru.justmc.api.user.User
import ru.justmc.api.user.UsersTable
import ru.justmc.api.userManager
import java.time.Instant

object SessionsTable : IntIdTable("sessions") {
    val user = entityId("user", UsersTable).nullable()
    val ip = varchar("ip", 50)
    val authorized = bool("authorized")
    val startTime = timestamp("start_time")
    val endTime = timestamp("end_time").nullable()

    suspend fun get(id: Int) = sql {
        val result = select { SessionsTable.id eq EntityID(id, SessionsTable) }.singleOrNull()
        if (result == null) {
            null
        } else {
            val user = result[user]?.value?.let { userManager.getUser(it) }
            Session(
                id, user, result[ip], result[authorized], result[startTime], result[endTime]
            )
        }
    }

    suspend fun create(user: User?, ip: String, authorized: Boolean, startTime: Instant) = sql {
        val id = insertAndGetId {
            if (user != null) {
                it[SessionsTable.user] = EntityID(user.id, UsersTable)
            }
            it[SessionsTable.ip] = ip
            it[SessionsTable.authorized] = authorized
            it[SessionsTable.startTime] = startTime
        }
        Session(id.value, user, ip, authorized, startTime)
    }
}