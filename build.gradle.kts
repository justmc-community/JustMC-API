plugins {
    kotlin("jvm") version "1.3.70"
}

group = "com.xjcyan1de.spigotkt"
version = "1.0-SNAPSHOT"

allprojects {
    apply(plugin = "kotlin")

    repositories {
        jcenter()
        maven { setUrl("https://jitpack.io") }
        maven { setUrl("https://papermc.io/repo/repository/maven-public/") }
    }

    dependencies {
        api(kotlin("stdlib-jdk8"))
        api(kotlin("reflect"))
        api("org.jetbrains.kotlinx", "kotlinx-coroutines-core", "1.3.4")
        api("org.jetbrains.kotlinx", "kotlinx-io-jvm", "0.1.16")
        api("org.jetbrains.exposed", "exposed-core", "0.21.1")
        api("org.jetbrains.exposed", "exposed-java-time", "0.21.1")
        api("com.github.coffeeinjected", "kextensions", "0.2")
        api("it.unimi.dsi", "fastutil", "8.3.1")
        api("com.zaxxer", "HikariCP", "3.4.2")
        api("com.google.code.gson", "gson", "2.8.6")
        api("com.github.xjcyan1de.cyanlibz", "CyanLibZ-Bukkit", "1.5.4")
        api("com.github.xjcyan1de.cyanlibz", "CyanLibZ-ChatComponent", "1.5.4")
        api("com.github.xjcyan1de.cyanlibz", "CyanLibZ-Mojang", "1.5.4")
        api("com.github.xjcyan1de.cyanlibz", "CyanLibZ-GSON", "1.5.4")
        api("com.github.xjcyan1de.cyanlibz", "CyanLibZ-Reflection", "1.5.4")
        api("com.github.xjcyan1de.cyanlibz", "CyanLibZ-Math", "1.5.4")
        api("com.github.xjcyan1de.cyanlibz", "CyanLibZ-Metadata", "1.5.4")
        api("com.github.xjcyan1de.cyanlibz", "CyanLibZ-Localization", "1.5.4")
        api("com.github.xjcyan1de.cyanlibz", "CyanLibZ-Loader", "1.5.4")
        api("com.github.xjcyan1de.cyanlibz", "CyanLibZ-Messenger", "1.5.4")
        api("com.github.xjcyan1de.cyanlibz", "CyanLibZ-Redis", "1.5.4")
        api("com.github.xjcyan1de.cyanlibz", "CyanLibZ-Terminable", "1.5.4")
        compileOnly("com.destroystokyo.paper", "paper-api", "1.15.2-R0.1-SNAPSHOT")
    }
}