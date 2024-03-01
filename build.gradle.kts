import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import java.lang.System.out

plugins {
    kotlin("jvm")
    id("java")
    id("org.jetbrains.compose")
}

group = "com.xingray"
version = "1.0.0"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

dependencies {
    // Note, if you develop a library, you should use compose.desktop.common.
    // compose.desktop.currentOs should be used in launcher-sourceSet
    // (in a separate module for demo project and in testMain).
    // With compose.desktop.common you will also lose @Preview functionality
    implementation(compose.desktop.currentOs)
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "compose-opengl"
            packageVersion = "1.0.0"
            windows.shortcut = true

            appResourcesRootDir.set(project.layout.projectDirectory.dir("resources"))
        }
    }
}

tasks.named("jar") {
    println("========= assemble =============")
    dependsOn("nativeBuild")
}

tasks.register("nativeBuild") {
    println("========= nativeBuild =============")
    val nativeBuild = project.layout.buildDirectory.get().dir("native")

    doLast {
        exec {
            println("========= nativeBuild exec 1 =============")
            mkdir(nativeBuild)
            workingDir(nativeBuild)
            commandLine("pwsh", "/c", "ls")

            commandLine(
                "cmake", "../../src/main/cpp", "-G",
                "\"Visual Studio 17 2022\"", "-A", "x64",
                "-DCMAKE_BUILD_TYPE=Release"
            )
//        commandLine(
//            "cmake", "../../src/main/cpp", "-G",
//            "\"MinGW Makefiles\"",
//            "-DCMAKE_BUILD_TYPE=Release"
//        )

            standardOutput = out
        }
    }


    doLast {
        exec {
            println("========= nativeBuild exec 2 =============")
            workingDir(nativeBuild)
            // cmake --build . --config Debug
            // cmake --build . --config Release
            commandLine("cmake", "--build", ".", "--config Release")
            standardOutput = out
        }
    }

    doLast {
        // 复制 MyWindow.dll 到指定目录
        val sourceFile = file("${nativeBuild.asFile.absolutePath}/Release/MyWindow.dll")
        val destDir = file("${project.layout.buildDirectory.get().asFile.absolutePath}/resources/main/lib")
        if(!destDir.exists()){
            destDir.mkdirs()
        }
        sourceFile.copyTo(destDir.resolve("MyWindow.dll"), true)
    }
}