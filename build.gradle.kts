plugins {
    `java-library`
    `maven-publish`
}

group = "com.pixerena.firework"
version = "0.1.0-SNAPSHOT"

java {
    withJavadocJar()
    withSourcesJar()
}

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    api("com.google.inject:guice:7.0.0")

    implementation("io.github.classgraph:classgraph:4.8.161")

    compileOnly("io.papermc.paper:paper-api:1.20.1-R0.1-SNAPSHOT")

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            versionMapping {
                usage("java-api") {
                    fromResolutionOf("runtimeClasspath")
                }
                usage("java-runtime") {
                    fromResolutionResult()
                }
            }
            pom {
                name.set("Firework")
                description.set("The missing PaperMC plugin framework")
                url.set("https://github.com/pixerena/firework")
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        id.set("danny900714")
                        name.set("Chao Tzu-Hsien")
                        email.set("danny900714@gmail.com")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/pixerena/firework.git")
                    developerConnection.set("scm:git:ssh://github.com/pixerena/firework.git")
                    url.set("https://github.com/pixerena/firework")
                }
            }
        }
    }
    repositories {
        maven {
            val isSnapshot = version.toString().endsWith("SNAPSHOT")
            val releaseRepoUrl = uri("https://oss.sonatype.org/service/local/staging/deploy/maven2/")
            val snapshotRepoUrl = uri("https://maven.pkg.github.com/pixerena/firework")

            url = if (isSnapshot) snapshotRepoUrl else releaseRepoUrl
            credentials {
                username = System.getenv(if (isSnapshot) "GITHUB_ACTOR" else "MAVEN_USERNAME")
                password = System.getenv(if (isSnapshot) "GITHUB_TOKEN" else "MAVEN_PASSWORD")
            }
        }
    }
}

tasks.test {
    useJUnitPlatform()
}

tasks.javadoc {
    if (JavaVersion.current().isJava9Compatible) {
        (options as StandardJavadocDocletOptions).addBooleanOption("html5", true)
    }
}
