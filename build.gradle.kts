import org.gradle.nativeplatform.platform.internal.DefaultNativePlatform

plugins {
  id("java")
  id("java-library")
  id("com.github.johnrengelman.shadow") version "7.1.2"
  id("org.openjfx.javafxplugin") version "0.1.0"
}

group = "stardust-indicators"
version = "1.0-SNAPSHOT"

java {
  sourceCompatibility = JavaVersion.VERSION_21
  targetCompatibility = JavaVersion.VERSION_21
}

javafx {
  modules("javafx.controls", "javafx.fxml")
}

// This is so it picks up new builds on jitpack
configurations.all {
  resolutionStrategy.cacheChangingModulesFor(0, "seconds")
}

repositories {
  mavenCentral()
  mavenLocal() // Uncomment to use mavenLocal version of LoD engine
  maven { url = uri("https://jitpack.io") }
  maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots/") }
}

dependencies {
  implementation("legend:lod:snapshot") // Uncomment to use mavenLocal version of LoD engine (also comment out next line)
//  implementation("com.github.Legend-of-Dragoon-Modding:Legend-of-Dragoon-Java:main-SNAPSHOT")
  api("org.legendofdragoon:mod-loader:4.2.0")
  api("org.legendofdragoon:script-recompiler:0.5.6")
}

sourceSets {
  main {
    java {
      srcDirs("src/main/java")
      exclude(".gradle", "build", "files")
    }
  }
}

buildscript {
  repositories {
    gradlePluginPortal()
  }
  /*dependencies {
    implementation("com.github.johnrengelman.shadow:7.1.2")
  }*/
}

apply(plugin = "com.github.johnrengelman.shadow")
apply(plugin = "java")
apply(plugin = "org.openjfx.javafxplugin")

tasks.jar {
  exclude("*.jar")
}