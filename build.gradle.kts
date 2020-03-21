plugins {
    kotlin("jvm") version "1.3.70"
}

group = "ru.justmc"
version = "1.0-SNAPSHOT"

allprojects {
    apply(plugin = "kotlin")

    repositories {
        jcenter()
        maven { setUrl("https://jitpack.io") }
    }

    dependencies {
        api(kotlin("stdlib-jdk8"))
        api(kotlin("reflect"))
        api(project(":JustMC-SDK"))
        api("org.jetbrains.kotlinx", "kotlinx-coroutines-core", "1.3.5")
        api("org.jetbrains.exposed", "exposed-core", "0.22.1")
        api("org.jetbrains.exposed", "exposed-java-time", "0.22.1")
        api("com.github.coffeeinjected", "kextensions", "0.6.0")
        api("it.unimi.dsi", "fastutil", "8.3.1")
        api("com.zaxxer", "HikariCP", "3.4.2")
        api("com.google.code.gson", "gson", "2.8.6")
    }
}

val common = subprojects.find { it.name == "JustMC-API-common" }!!

subprojects {
    dependencies {
        if (name != "JustMC-API-common") {
            api(common)
        }
    }
}

dependencies {
    subprojects {
        api(this)
    }
}

tasks {
    compileKotlin { kotlinOptions.jvmTarget = "1.8" }
    compileJava { options.encoding = "UTF-8" }
}