package ru.justmc.api

import com.github.xjcyan1de.cyanlibz.bukkit.BukkitPlugin
import com.github.xjcyan1de.cyanlibz.bukkit.util.initialize

class BukkitPlugin : BukkitPlugin() {
    override fun load() {
        initialize()
    }

    override fun enable() {

    }
}