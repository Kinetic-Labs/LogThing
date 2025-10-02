plugins {
    id("java")
    id("application")
}


group = "com.github.kinetic.logthing"
version = "1.0-SNAPSHOT"

var logThingMainClass = "com.github.kinetic.logthing.Start"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}

application {
    mainClass.set(logThingMainClass)
}
