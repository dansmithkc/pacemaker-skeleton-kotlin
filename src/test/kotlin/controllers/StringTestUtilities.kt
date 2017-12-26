package controllers

object StringTestUtilities {

	fun findIdInResult(result: String): String {
		var id = result.substring(result.indexOf(", id=") + 5)
		return id.substring(0, id.indexOf(","))
	}

	fun removeIdFromResult(result: String): String {
		return result.replace(Regex(", id=.+?,"), ", id=,")
	}

	fun listOfActivityFields(result: String, fieldName: String): List<String> {
		return result.split(fieldName)
				.filterIndexed { index, value -> index > 0 }
				.map { it.replace(Regex(",.*"), "") }
	}

}
