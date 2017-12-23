package models

import models.User

class Fixtures {
	var users = arrayOf(User("marge", "simpson", "marge@simpson.com", "secret"),
			User("lisa", "simpson", "lisa@simpson.com", "secret"),
			User("bart", "simpson", "bart@simpson.com", "secret"),
			User("maggie", "simpson", "maggie@simpson.com", "secret"))


}