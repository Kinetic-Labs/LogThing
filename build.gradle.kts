plugins {
    id("java")
    id("application")
    id("org.jetbrains.kotlin.jvm") version "2.3.0-Beta1"
}

group = "com.github.kinetic.logthing"
version = "1.0-DEV"

val logThingMainClass = "com.github.kinetic.logthing.Start"
val kotlinVersion = "2.3.0-Beta1"
val jvmArgsList = listOf(
    "-XX:+IgnoreUnrecognizedVMOptions",
    "--add-opens", "java.base/java.lang=ALL-UNNAMED",
    "--add-opens", "java.base/sun.nio.ch=ALL-UNNAMED",
    "--add-opens", "java.base/jdk.internal.misc=ALL-UNNAMED",
    "--add-exports", "java.base/jdk.internal.misc=ALL-UNNAMED",
    "--sun-misc-unsafe-memory-access=allow"
)

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("org.jetbrains.kotlin:kotlin-scripting-jsr223:$kotlinVersion")
    implementation("org.jetbrains.kotlin:kotlin-scripting-common:$kotlinVersion")
    implementation("org.jetbrains.kotlin:kotlin-scripting-jvm:$kotlinVersion")
    implementation("org.jetbrains.kotlin:kotlin-scripting-jvm-host:$kotlinVersion")
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
    jvmArgs(jvmArgsList)
}

application {
    mainClass.set(logThingMainClass)
    applicationDefaultJvmArgs = jvmArgsList
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(25))
    }
}

kotlin {
    jvmToolchain(25)
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_24)
    }
}

tasks.withType<JavaCompile> {
    options.release.set(24)
}

tasks.withType<JavaExec> {
    jvmArgs(jvmArgsList)
}

tasks.register<Copy>("copyDependencies") {
    from(configurations.runtimeClasspath)
    into("${layout.buildDirectory.get()}/libs/libraries")
}

tasks.named("build") {
    dependsOn("copyDependencies")
}

tasks.register<JavaExec>("logThingRun") {
    group = "application"
    description = "Run LogThing with env as working directory"
    mainClass.set(logThingMainClass)
    classpath = sourceSets["main"].runtimeClasspath
    workingDir = file("env")
    jvmArgs(jvmArgsList)
}
