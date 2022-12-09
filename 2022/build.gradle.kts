plugins {
    id("org.jetbrains.kotlin.jvm") version "1.7.20"
    id("org.jetbrains.compose") version "1.2.1"
}

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation(compose.desktop.currentOs)

    testImplementation("org.junit.jupiter:junit-jupiter:5.9.1")
    testImplementation("com.willowtreeapps.assertk:assertk-jvm:0.25")
}

tasks.withType<Test> {
    jvmArgs = listOf("-Xmx8g")
    useJUnitPlatform()
}
