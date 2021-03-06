package controllers

interface ContextWrapper {
	fun json(theObject: Any)
	fun <T> bodyAsClass(theClass: Class<T>): T
	fun status(code: Int)
	fun param(name: String): String?
	fun queryParam(name: String): String?
	fun result(result: String)
}