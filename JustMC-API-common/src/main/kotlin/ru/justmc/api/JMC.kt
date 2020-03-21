@file:JvmName("JMC")

package ru.justmc.api

import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import ru.justmc.api.auth.AuthManager
import ru.justmc.api.database.DatabaseManager
import ru.justmc.api.user.UserManager
import java.util.logging.Logger

var log: Logger = Logger.getLogger("JustMC")

lateinit var authManager: AuthManager
lateinit var userManager: UserManager
lateinit var databaseManager: DatabaseManager

suspend fun <T> sql(statement: suspend Transaction.() -> T) =
    newSuspendedTransaction(db = databaseManager.database, statement = statement)

fun getUser(name: String) = runBlocking { userManager.getUser(name) }
fun getUser(id: Int) = runBlocking { userManager.getUser(id) }
fun getUsers() = runBlocking { userManager.getUsers() }