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

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.compilerArgs.addAll(listOf("-Xlint:unchecked", "-Xlint:deprecation"))
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "com.triassic.pterovm.Main"
    }
}

tasks.create("fatJar", ShadowJar::class) {
    archiveBaseName.set("PteroVM")
    archiveVersion.set("1.0.1-SNAPSHOT")
    archiveClassifier.set("")
    from(sourceSets.main.get().output)
    configurations = listOf(project.configurations.runtimeClasspath)
    mergeServiceFiles()
}
