package controllers

import io.javalin.Context

class TestContextWrapper() : ContextWrapper {
	var result = "";

	override fun json(theObject: Any) {
		result = theObject.toString()
	}

}