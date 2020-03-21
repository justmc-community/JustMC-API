package ru.justmc.api.libs

import ru.justmc.api.log
import java.io.File
import java.io.IOException
import java.net.URL
import java.net.URLClassLoader
import java.nio.file.Files
import java.util.*
import java.util.concurrent.Callable
import java.util.concurrent.Executors

object LibraryLoader {
    private val ADD_URL_METHOD = URLClassLoader::class.java.getDeclaredMethod("addURL", URL::class.java).apply {
        isAccessible = true
    }
    const val MAVEN_REPO = "https://repo1.maven.org/maven2"
    val librariesFolder = File(System.getProperty("user.home"), ".m2/repository").apply {
        mkdirs()
    }
    val executor = Executors.newWorkStealingPool()

    @Suppress("ReplaceCallWithBinaryOperator")
    fun loadLibraries() {
        val packages = Package.getPackages()
        val libraryLoader = LibraryLoaderContext()
        libraryLoader.load("org/jetbrains/kotlin", "kotlin-stdlib", "1.3.61")
        libraryLoader.load("org/jetbrains/kotlin", "kotlin-stdlib-jdk7", "1.3.61")
        libraryLoader.load("org/jetbrains/kotlin", "kotlin-stdlib-jdk8", "1.3.61")
        libraryLoader.load("org/jetbrains/kotlin", "kotlin-reflect", "1.3.61")
        libraryLoader.load("org/jetbrains/kotlinx", "kotlinx-coroutines-core", "1.3.3")
        libraryLoader.load("org/jetbrains/kotlinx", "kotlinx-coroutines-jdk8", "1.3.3")
        libraryLoader.load("it/unimi/dsi", "fastutil", "8.3.0")
        libraryLoader.load("org/jetbrains/exposed", "exposed-core", "0.20.3", "https://dl.bintray.com/kotlin/exposed/")
        libraryLoader.load("org/jetbrains/exposed", "exposed-jdbc", "0.20.3", "https://dl.bintray.com/kotlin/exposed/")
        libraryLoader.load(
            "org/jetbrains/exposed",
            "exposed-java-time",
            "0.20.3",
            "https://dl.bintray.com/kotlin/exposed/"
        )
        libraryLoader.load("org/apache/commons", "commons-lang3", "3.9")
        libraryLoader.load("org/apache/commons", "commons-math3", "3.6.1")
        libraryLoader.load("org/apache/commons", "commons-pool2", "2.8.0")
        libraryLoader.load("org/apache/httpcomponents", "httpclient", "4.5.11")
        libraryLoader.load("org/slf4j", "slf4j-api", "1.7.30")
        libraryLoader.load("joda-time", "joda-time", "2.10.5")
        libraryLoader.load("mysql", "mysql-connector-java", "8.0.19")
        libraryLoader.load("p6spy", "p6spy", "3.8.7")
        libraryLoader.load("org/yaml", "snakeyaml", "1.25")
        libraryLoader.load("com/github/games647", "craftapi", "0.2", "https://repo.codemc.org/repository/maven-public/")
        libraryLoader.load("com/mashape/unirest", "unirest-java", "1.4.9")
        libraryLoader.load("com/google/code/gson", "gson", "2.8.6")
        libraryLoader.load("commons-io", "commons-io", "2.6")
        libraryLoader.load("redis/clients", "jedis", "3.2.0")
        libraryLoader.load("com/zaxxer", "HikariCP", "3.4.2")
        libraryLoader.load("org/flywaydb", "flyway-core", "6.2.2")
        libraryLoader.load("fr/minuskube", "jpastee", "1.0.1")
        libraryLoader.load("com/lambdaworks", "scrypt", "1.4.0")
        libraryLoader.load("org/jooq", "jooq", "3.12.4")
        libraryLoader.load("org/jooq", "jooq-meta", "3.12.4")
        libraryLoader.load("org/jooq", "jooq-codegen", "3.12.4")
        libraryLoader.load("org/reactivestreams", "reactive-streams", "1.0.0")
        executor.invokeAll(libraryLoader.tasks).forEach {
            println("Loaded: ${it.get()}")
        }
    }

    fun loadLibraries(loader: LibraryLoaderContext.() -> Unit) {
        val libraryLoaderContext = LibraryLoaderContext()
        libraryLoaderContext.apply(loader)
        executor.invokeAll(libraryLoaderContext.tasks).forEach {
            it.get()
        }
    }

    class LibraryLoaderContext {
        val tasks = LinkedList<Callable<String>>()

        @Suppress("ConvertTryFinallyToUseCall")
        fun load(groupId: String, artifactId: String, version: String, repoUrl: String = MAVEN_REPO) = Callable {
            try {
                val name = "$artifactId-$version"
                val path = "$groupId/$artifactId/$version"
                val dirLocation = File(librariesFolder, path)
                val fileLocation = File(dirLocation, "$name.jar")
                if (fileLocation.isDirectory) {
                    fileLocation.delete()
                }
                if (!fileLocation.exists()) {
                    log.info("$name is not found at runtime classpath. Attempting to download...")
                    val url = URL("$repoUrl/$path/$name.jar")
                    val inputStream = url.openStream()
                    try {
                        dirLocation.mkdirs()
                        Files.copy(inputStream, fileLocation.toPath())
                    } catch (e: IOException) {
                        e.printStackTrace()
                    } finally {
                        inputStream.close()
                    }
                }
                val classLoader = javaClass.classLoader
                if (classLoader is URLClassLoader) {
                    ADD_URL_METHOD(classLoader, fileLocation.toURI().toURL())
                }
                name
            } catch (e: Throwable) {
                e.printStackTrace()
                ""
            }
        }.also {
            tasks.add(it)
        }
    }
}