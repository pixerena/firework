plugins {
    `java-library`
    `maven-publish`
    signing
    id("io.github.gradle-nexus.publish-plugin") version "1.3.0"
}

group = "io.github.pixerena"
version = "0.6.0-SNAPSHOT"

java {
    withJavadocJar()
    withSourcesJar()
}

configurations {
    configurations.testImplementation.get().apply {
        extendsFrom(configurations.compileOnly.get())
    }
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

    // Add dependency constraints to fix security vulnerabilities
    constraints {
        implementation("com.google.guava:guava:32.0.+") {
            because("versions below 32.0.0 have security vulnerabilities (CVE-2023-2976)")
        }
    }
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
                        url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
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
}

nexusPublishing.repositories {
    sonatype {
        nexusUrl = uri("https://s01.oss.sonatype.org/service/local/")
        snapshotRepositoryUrl = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
        username = System.getenv("MAVEN_USERNAME")
        password = System.getenv("MAVEN_PASSWORD")
    }
}

signing {
    useInMemoryPgpKeys(System.getenv("GPG_PRIVATE_KEY"), System.getenv("GPG_PASSPHRASE"))
    sign(publishing.publications["mavenJava"])
}

tasks.test {
    useJUnitPlatform()
}

tasks.javadoc {
    if (JavaVersion.current().isJava9Compatible) {
        (options as StandardJavadocDocletOptions).addBooleanOption("html5", true)
    }

    exclude("io/github/pixerena/firework/internal/**")

    // Link external library
    (options as StandardJavadocDocletOptions).links("https://jd.papermc.io/paper/1.20/", "https://google.github.io/guice/api-docs/latest/javadoc/")

    // The following lines will workaround the problem with the exclude
    val sourceSetDirectories = sourceSets.main.get().java.srcDirs.joinToString(":")
    (options as StandardJavadocDocletOptions).addStringOption("-source-path", sourceSetDirectories)
}

tasks.register<Jar>("packageTests") {
    from(sourceSets.main.get().output, sourceSets.test.get().output, configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    archiveClassifier.set("test")
}
