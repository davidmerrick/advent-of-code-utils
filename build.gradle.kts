group = "io.github.davidmerrick.aoc"

repositories {
    gradlePluginPortal()
    mavenCentral()
}

plugins {
    `maven-publish`
    kotlin("jvm") version "1.7.20"
    id("org.ajoberstar.reckon") version "0.16.1"
}

reckon {
    snapshots()
    setStageCalc(calcStageFromProp())
    setScopeCalc(
        calcScopeFromProp()
            .or(calcScopeFromCommitMessages())
    )
}

dependencies {
    testImplementation("org.spekframework.spek2:spek-runner-junit5:2.0.18")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.0")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.9.0")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.9.0")
    testImplementation("io.kotlintest:kotlintest-runner-junit5:3.4.2")
}

tasks {
    compileKotlin {
        kotlinOptions {
            jvmTarget = "11"
        }
    }

    compileTestKotlin {
        kotlinOptions {
            jvmTarget = "11"
        }
    }

    test {
        useJUnitPlatform()
    }

    build {
        dependsOn(buildJar)
    }
}

val buildJar by tasks.creating(Jar::class) {
    from(tasks.compileKotlin)
}

publishing {
    publications {
        create<MavenPublication>("default") {
            artifact(buildJar)
        }
    }

    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/davidmerrick/advent-of-code-helpers")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
}
