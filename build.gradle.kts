plugins {
    java
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.yaml:snakeyaml:1.33")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(project.extra["java_version"].toString()))
}

// Configure the shadowJar task
tasks.shadowJar {
    archiveFileName.set("PteroVM-1.0.1-SNAPSHOT.jar")
    destinationDirectory.set(file("$buildDir/libs"))
    mergeServiceFiles()
    manifest {
        attributes("Main-Class" to "com.triassic.pterovm.Main")
    }
}

// Add a task to merge dependencies into the existing JAR
task mergeDependencies(type: ShadowJar) {
    from configurations.runtimeClasspath.get().filter { it.name.endsWith(".jar") }.map { zipTree(it) }
    configurations = listOf(project.configurations.shadow)
    archiveFileName.set("PteroVM-1.0.1-SNAPSHOT.jar")
    destinationDirectory.set(file("$buildDir/libs"))
}

// Make the mergeDependencies task depend on shadowJar task
mergeDependencies.dependsOn(tasks.shadowJar)
