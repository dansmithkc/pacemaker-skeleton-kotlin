package test.models

import models.User
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class LocationTest {
	var user = User()
	var user2 = User()

	@Before
	fun setup() {
		user = User()
		user2 = User()
	}

	@After
	fun tearDown() {
	}

	@Test
	fun constructorDefault() {
		val user3 = User()
		user3.id = "123"
		assertEquals(user3.id, "123")

		assertEquals(user.firstname, "")
		assertEquals(user.lastname, "")
		assertEquals(user.email, "")
		assertEquals(user.password, "")

		// just exercise the accessor for id
		val id = user.id
		val activities = user.activities
	}

	@Test
	fun testToString() {
		assertEquals("User(firstname=, lastname=, email=, password=, id=" + user.id + ", activities={})", user.toString())
	}

	@Test
	fun useEquals() {
		assertEquals(user, user)
		assertNotEquals(user, user2)
		assertFalse(user.equals(user2))
		assertTrue(user.equals(user))
		val wrongObjectType = Any()
		assertFalse(user.equals(wrongObjectType))
	}

	@Test
	fun useCopy() {
		val user3 = user.copy()
	}

	@Test
	fun useHashCode() {
		val code = user.hashCode()
	}
}