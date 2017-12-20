package utils

class HerokuUtils {

	fun getHerokuAssignedPort(processBuilder: ProcessBuilder): Int {
		return if (processBuilder.environment()["PORT"] != null) {
			Integer.parseInt(processBuilder.environment()["PORT"])
		} else 7000
	}

}