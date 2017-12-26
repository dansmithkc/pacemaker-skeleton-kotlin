package models

import controllers.PacemakerRestServiceTestUtilities
import controllers.StringTestUtilities
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class FriendTest {
	lateinit var friend: Friend
	lateinit var friend2: Friend
	lateinit var utility: PacemakerRestServiceTestUtilities

	@Before
	fun setup() {
		friend = Friend()
		friend2 = Friend()
	}

	@After
	fun tearDown() {
	}

	@Test
	fun constructorDefault() {
		val friend3 = Friend()

		var followingWithoutID = StringTestUtilities.removeIdFromResult(friend3.following.toString())
		assertEquals("User(firstname=, lastname=, email=, password=, id=, activities={})", followingWithoutID)
		var followedByWithoutID = StringTestUtilities.removeIdFromResult(friend3.followedBy.toString())
		assertEquals("User(firstname=, lastname=, email=, password=, id=, activities={})", followedByWithoutID)
	}

	@Test
	fun testSetters() {
		var user = User()
		friend.followedBy = user
		friend.following = user

		user = friend.component1()
		user = friend.component2()
	}

	@Test
	fun testToString() {
		var friendWithoutID = StringTestUtilities.removeIdFromResult(friend.toString())
		assertEquals("Friend(following=User(firstname=, lastname=, email=, password=, id=, activities={}), followedBy=User(firstname=, lastname=, email=, password=, id=, activities={}))", friendWithoutID)
	}

	@Test
	fun useEquals() {
		assertEquals(friend, friend)
		assertNotEquals(friend, friend2)
		assertFalse(friend.equals(friend2))
		assertTrue(friend.equals(friend))
		val wrongObjectType = Any()
		assertFalse(friend.equals(wrongObjectType))
	}

	@Test
	fun useCopy() {
		val friend3 = friend.copy()
	}

	@Test
	fun useHashCode() {
		val code = friend.hashCode()
	}
}