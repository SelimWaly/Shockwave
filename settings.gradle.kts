rootProject.name = "shockwave"

val modules = listOf(
	"src",
)

modules.forEach {
	include(it)
	project(":$it").buildFileName = "$it.gradle.kts"
}

dependencyResolutionManagement {
	repositories {
		mavenCentral()
	}
}
