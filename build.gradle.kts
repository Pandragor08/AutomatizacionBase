plugins {
    id("java")
    id("io.qameta.allure") version "2.11.2"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val seleniumVersion = "4.18.1"
val cucumberVersion = "7.15.0"
val allureVersion = "2.24.0"
val log4jVersion = "2.22.1"

dependencies {

    implementation(platform("org.junit:junit-bom:5.10.0"))
    implementation("org.junit.jupiter:junit-jupiter")
    implementation("org.junit.platform:junit-platform-suite:1.10.0")

    implementation("org.seleniumhq.selenium:selenium-java:$seleniumVersion")

    implementation("io.cucumber:cucumber-java:$cucumberVersion")
    implementation("io.cucumber:cucumber-junit-platform-engine:$cucumberVersion")

    implementation("io.qameta.allure:allure-cucumber7-jvm:$allureVersion")

    implementation(platform("org.apache.logging.log4j:log4j-bom:$log4jVersion"))
    implementation("org.apache.logging.log4j:log4j-api")
    implementation("org.apache.logging.log4j:log4j-core")
}

tasks.test {
    useJUnitPlatform()
}

allure {
    version.set(allureVersion)
}
tasks.test {
    systemProperty("headless", System.getProperty("headless"))
}