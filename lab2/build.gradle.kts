plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:6.0.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    // Source: https://mvnrepository.com/artifact/org.springframework/spring-context
    implementation("org.springframework:spring-context:4.1.6.RELEASE")
    implementation("org.springframework:spring-context:6.1.6")

    implementation("org.springframework:spring-aop:6.1.6")

    implementation("org.aspectj:aspectjweaver:1.9.22")
}

tasks.test {
    useJUnitPlatform()
}