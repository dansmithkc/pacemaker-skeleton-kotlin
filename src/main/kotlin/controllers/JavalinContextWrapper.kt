package controllers

import io.javalin.Context

class JavalinContextWrapper(val context: Context) : ContextWrapper {

	override fun json(theObject: Any) {
		context.json(theObject)
	}

	override fun <T> bodyAsClass(theClass: Class<T>): T {
		return context.bodyAsClass(theClass)
	}

}