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
    testImplementation("com.squareup.okhttp3:okhttp:4.11.0")
    //lombok
    compileOnly("org.projectlombok:lombok:1.18.28")
    annotationProcessor("org.projectlombok:lombok:1.18.28")
    testCompileOnly("org.projectlombok:lombok:1.18.28")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.28")
    // https://mvnrepository.com/artifact/com.google.code.gson/gson
    implementation("com.google.code.gson:gson:2.10.1")
    // https://mvnrepository.com/artifact/commons-fileupload/commons-fileupload
    implementation("commons-fileupload:commons-fileupload:1.5")
    // javax servlet
    implementation("javax.servlet:javax.servlet-api:4.0.1")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}