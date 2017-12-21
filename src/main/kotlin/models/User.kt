package models

import java.util.UUID

data class User(
		var firstname: String = "",
		var lastname: String = "",
		var email: String = "",
		var password: String = "",
		var id: String = UUID.randomUUID().toString(),
		val activities: MutableMap<String, Activity> = hashMapOf<String, Activity>())