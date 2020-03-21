repositories {
    maven { setUrl("https://repo.velocitypowered.com/snapshots") }
    maven { setUrl("https://repo.spongepowered.org/maven") }
}

dependencies {
    compileOnly("com.velocitypowered", "velocity-api", "1.1.0-SNAPSHOT")
}
