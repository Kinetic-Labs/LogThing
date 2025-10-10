plugins {
    id("java")
    id("application")
    id("org.jetbrains.kotlin.jvm") version "2.3.0-Beta1"
}

group = "com.github.kinetic.logthing"
version = "1.0-DEV"

val logThingMainClass = "com.github.kinetic.logthing.Start"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("org.jetbrains.kotlin:kotlin-scripting-jsr223:2.2.0")
    implementation("org.jetbrains.kotlin:kotlin-scripting-common:2.2.0")
    implementation("org.jetbrains.kotlin:kotlin-scripting-jvm:2.2.0")
    implementation("org.jetbrains.kotlin:kotlin-scripting-jvm-host:2.2.0")
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}

application {
    mainClass.set(logThingMainClass)
    applicationDefaultJvmArgs = listOf(
        "--add-opens", "java.base/sun.misc=ALL-UNNAMED",
        "--add-opens", "java.base/java.lang=ALL-UNNAMED"
    )
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
    jvmArgs(
        "--add-opens", "java.base/sun.misc=ALL-UNNAMED",
        "--add-opens", "java.base/java.lang=ALL-UNNAMED"
    )
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
    jvmArgs(
        "--add-opens", "java.base/sun.misc=ALL-UNNAMED",
        "--add-opens", "java.base/java.lang=ALL-UNNAMED"
    )
}
