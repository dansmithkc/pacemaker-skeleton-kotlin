package models

class Fixtures {
	var users = arrayOf(User("marge", "simpson", "marge@simpson.com", "secret"),
			User("lisa", "simpson", "lisa@simpson.com", "secret"),
			User("bart", "simpson", "bart@simpson.com", "secret"),
			User("maggie", "simpson", "maggie@simpson.com", "secret"))

	var activities = arrayOf(
			Activity("walk", "fridge", 0.001f),
			Activity("walk", "bar", 1.0f),
			Activity("run", "work", 2.2f),
			Activity("walk", "shop", 2.5f),
			Activity("cycle", "school", 4.5f))
}