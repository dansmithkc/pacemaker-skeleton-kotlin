package models

data class Friend(
		var following: User = User(),
		var followedBy: User = User())