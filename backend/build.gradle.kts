val versionKtor: String by project
val versionKotlin: String by project
val versionLogging: String by project
val versionExposed: String by project
val versionH2: String by project

plugins {
    kotlin("jvm") version "1.9.10"
    id("io.ktor.plugin") version "2.3.4"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.10"
}

group = "com.bs14"
version = "0.0.1"

application {
    mainClass.set("com.bs14.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.github.oshai:kotlin-logging-jvm:$versionLogging")

    implementation("io.ktor:ktor-server-core-jvm:2.3.5")
    implementation("io.ktor:ktor-server-auth-jvm:2.3.5")
    implementation("io.ktor:ktor-server-sessions-jvm:2.3.5")
    implementation("io.ktor:ktor-server-auto-head-response-jvm:2.3.5")
    implementation("io.ktor:ktor-server-host-common-jvm:2.3.5")
    implementation("io.ktor:ktor-server-call-logging-jvm:2.3.5")
    implementation("io.ktor:ktor-server-content-negotiation-jvm:2.3.5")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:2.3.5")
    implementation("io.ktor:ktor-server-netty-jvm:2.3.5")
    implementation("io.ktor:ktor-server-auth-jwt-jvm:2.3.5")
    testImplementation("io.ktor:ktor-server-tests-jvm:2.3.5")

    implementation("org.jetbrains.exposed:exposed-core:$versionExposed")
    implementation("org.jetbrains.exposed:exposed-jdbc:$versionExposed")
    implementation("org.jetbrains.exposed:exposed-dao:$versionExposed")
    implementation("org.jetbrains.exposed:exposed-kotlin-datetime:$versionExposed")

    implementation("org.postgresql:postgresql:42.3.8")

    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$versionKotlin")

}
