plugins {
    id("java")
    id("application")
}

group = "org.example"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jboss.weld.se:weld-se-core:5.1.2.Final")
    implementation("com.h2database:h2:2.2.224")
    implementation("org.hibernate:hibernate-core:6.5.2.Final")

    implementation("com.sparkjava:spark-core:2.9.4")

    implementation("com.google.code.gson:gson:2.11.0")

}
sourceSets{
    main{
        output.setResourcesDir(file("${buildDir}/classes/java/main"))
    }
}

tasks.test {
    useJUnitPlatform()
}