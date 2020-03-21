package ru.justmc.api.database

import org.jetbrains.exposed.sql.Database

interface DatabaseManager {
    val database: Database
}