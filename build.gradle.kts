import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import io.gitlab.arturbosch.detekt.Detekt

plugins {
	id("org.springframework.boot") version "2.3.4.RELEASE"
	id("io.spring.dependency-management") version "1.0.10.RELEASE"
	kotlin("jvm") version "1.4.10"
	kotlin("plugin.spring") version "1.3.72"
	kotlin("plugin.jpa") version "1.3.72"

    id("org.jmailen.kotlinter") version "2.4.1"
	id("io.gitlab.arturbosch.detekt").version("1.14.1")
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
	jcenter()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-mustache")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

	// Internet Connection with OkHttp3 retrofit2 OAuth2
	implementation("com.squareup.okhttp3:okhttp:3.8.1")
    implementation("com.squareup.okhttp3:logging-interceptor:3.8.1")
    implementation("com.squareup.retrofit2:retrofit:2.3.0")
    implementation("com.squareup.retrofit2:adapter-rxjava:2.0.2")
    implementation("com.squareup.retrofit2:converter-jackson:2.0.2")

	developmentOnly("org.springframework.boot:spring-boot-devtools")
	runtimeOnly("com.h2database:h2")
	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
	}
	testImplementation("io.mockk:mockk:1.10.2")
}

tasks.withType<Test> {
	useJUnitPlatform()
	logging.captureStandardOutput(LogLevel.INFO)
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Detekt>().configureEach {
    // Target version of the generated JVM bytecode. It is used for type resolution.
    this.jvmTarget = "11"
}

tasks.register("testEnv") {
	doFirst {
		if(System.getenv("AUTH_TOKEN").isNullOrEmpty()) {
				throw RuntimeException("You should set environtment variable \${AUTH_TOKEN} for spotify API.")
		}
	}
}

tasks.test {
	dependsOn("testEnv")
}
