plugins {
    id("java")
    id("maven-publish")
}

group = "com.driftkiller"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "com.driftkiller"
            artifactId = "EasyHttp"
            version = "1.0"

            from(components["java"])
        }
    }
}

dependencies {
    //testing
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    //lombok
    compileOnly("org.projectlombok:lombok:1.18.28")
    annotationProcessor("org.projectlombok:lombok:1.18.28")
    testCompileOnly("org.projectlombok:lombok:1.18.28")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.28")
    //http client for performing test
    // https://mvnrepository.com/artifact/com.squareup.okhttp3/okhttp
    testImplementation("com.squareup.okhttp3:okhttp:4.11.0")
    // https://mvnrepository.com/artifact/com.google.code.gson/gson
    implementation("com.google.code.gson:gson:2.10.1")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}