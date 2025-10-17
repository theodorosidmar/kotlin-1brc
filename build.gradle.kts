@file:OptIn(ExperimentalKotlinGradlePluginApi::class)

import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
  kotlin("multiplatform") version "2.3.0"
}

kotlin {
  jvm {
    binaries {
      executable {
        mainClass = "MainKt"
      }
    }
  }

  listOf(
    linuxX64(),
    //macosArm64(),
    //mingwX64(),
  ).forEach {
    it.binaries {
      executable {
        baseName = "onebrc"
      }
    }
  }
}

tasks {
  withType<Jar> {
    manifest {
      attributes["Main-Class"] = "MainKt"
    }
    archiveBaseName.set("onebrc")
  }
}
