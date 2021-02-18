plugins {
    kotlin("multiplatform") version "1.4.10"
    id("dev.fritz2.fritz2-gradle") version "0.8"
}

repositories {
    maven("https://oss.jfrog.org/artifactory/jfrog-dependencies")
    jcenter()
    maven("https://dl.bintray.com/jwstegemann/fritz2")
}

kotlin {
    kotlin {
        jvm()
        js(LEGACY).browser()

        sourceSets {
            val commonMain by getting {
                dependencies {
                    implementation(kotlin("stdlib"))
                }
            }
            val jvmMain by getting {
                dependencies {
                }
            }
            val jsMain by getting {
                dependencies {
                    implementation("dev.fritz2:components:0.9-SNAPSHOT")
                }
            }
        }
    }
}