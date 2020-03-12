package ru.justmc.api.auth

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap

enum class AuthType(val id: Int) {
    VK(0),
    MOJANG(1),
    TELEGRAM(3);

    companion object {
        val values = values()
        private val byId = values.map { it.id to it }.toMap(Int2ObjectOpenHashMap())

        @JvmStatic
        operator fun get(id: Number) = byId[id.toInt()] ?: error("Unknown AuthType with id: $id")
    }
}