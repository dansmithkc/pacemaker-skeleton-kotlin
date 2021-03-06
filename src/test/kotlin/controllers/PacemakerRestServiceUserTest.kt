package controllers

import models.Fixtures
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test

class PacemakerRestServiceUserTest {
	lateinit var service: PacemakerRestService
	lateinit var context: TestContextWrapper
	lateinit var fixtures: Fixtures
	lateinit var utility: PacemakerRestServiceTestUtilities

	@Before
	fun setup() {
		service = PacemakerRestService()
		context = TestContextWrapper()
		fixtures = Fixtures()
		utility = PacemakerRestServiceTestUtilities(service, context, fixtures)
	}

	@After
	fun tearDown() {
	}

	@Test
	fun testListUsers() {
		// Exercise
		service.listUsers(context)
		// Verify
		assertEquals("[]", context.json)
	}

	@Test
	fun testCreateUser() {
		// Setup
		context.returnedUser = fixtures.users[0]
		// Exercise
		service.createUser(context)
		// Verify
		// remove the ID since we cannot match on that
		var truncatedResult = context.json.substring(0, context.json.indexOf(", id"))
		assertEquals("User(firstname=marge, lastname=simpson, email=marge@simpson.com, password=secret", truncatedResult)
	}

	@Test
	fun testListShowsCreatedUser() {
		// Setup
		context.returnedUser = fixtures.users[1]
		service.createUser(context)
		// Exercise
		service.listUsers(context)
		// Verify
		var truncatedResult = context.json.substring(0, context.json.indexOf(", id"))
		assertEquals("[User(firstname=lisa, lastname=simpson, email=lisa@simpson.com, password=secret", truncatedResult)
	}

	@Test
	fun testDeleteUsers() {
		// Exercise
		service.deleteUsers(context)
		// Verify
		assertEquals(204, context.status)
	}

	@Test
	fun testCreateThenDeleteUsers() {
		// Setup
		context.returnedUser = fixtures.users[2]
		service.createUser(context)
		// Exercise
		service.deleteUsers(context)
		// Verify
		assertEquals(204, context.status)
		// Exercise
		service.listUsers(context)
		// Verify
		assertEquals("[]", context.json)
	}

	@Test
	fun testFollowFriend() {
		// Setup
		var user1 = fixtures.users[0]
		context.returnedUser = user1
		service.createUser(context)
		var user1Id = StringTestUtilities.findIdInResult(context.json)

		var user2 = fixtures.users[2]
		context.returnedUser = user2
		service.createUser(context)
		var user2Id = StringTestUtilities.findIdInResult(context.json)

		context.params.put("id", user1Id)
		context.params.put("friendId", user2Id)
		// Exercise
		service.followFriend(context)
		// Verify
		assertEquals(200, context.status)
	}

	@Test
	fun testListFriendsNone() {
		// Setup
		var user1 = fixtures.users[0]
		context.returnedUser = user1
		service.createUser(context)
		context.params.put("id", user1.id)
		// Exercise
		service.listFriends(context)
		// Verify
		assertEquals("[]", context.json)
	}

	@Test
	fun testListFriendsOne() {
		// Setup
		var user1 = fixtures.users[0]
		context.returnedUser = user1
		service.createUser(context)
		var user1Id = StringTestUtilities.findIdInResult(context.json)

		var user2 = fixtures.users[2]
		context.returnedUser = user2
		service.createUser(context)
		var user2Id = StringTestUtilities.findIdInResult(context.json)

		context.params.put("id", user1Id)
		context.params.put("friendId", user2Id)
		service.followFriend(context)
		// Exercise
		service.listFriends(context)
		// Verify
		var truncatedResult = StringTestUtilities.removeIdFromResult(context.json)
		assertEquals("[User(firstname=bart, lastname=simpson, email=bart@simpson.com, password=secret, id=, activities={})]", truncatedResult)
	}

	@Test
	fun testUnfollowFriendsNone() {
		// Setup
		var user1 = fixtures.users[0]
		context.returnedUser = user1
		service.createUser(context)
		var user1Id = StringTestUtilities.findIdInResult(context.json)
		context.params.put("id", user1Id)

		// Exercise
		service.unfollowFriends(context)

		// Verify
		assertEquals(204, context.status)
		service.listFriends(context)
		var truncatedResult = StringTestUtilities.removeIdFromResult(context.json)
		assertEquals("[]", truncatedResult)
	}

	@Test
	fun testUnfollowFriendsOne() {
		// Setup
		var user1 = fixtures.users[0]
		context.returnedUser = user1
		service.createUser(context)
		var user1Id = StringTestUtilities.findIdInResult(context.json)

		var user2 = fixtures.users[2]
		context.returnedUser = user2
		service.createUser(context)
		var user2Id = StringTestUtilities.findIdInResult(context.json)

		context.params.put("id", user1Id)
		context.params.put("friendId", user2Id)
		service.followFriend(context)

		// Verify Setup
		service.listFriends(context)
		var truncatedResult = StringTestUtilities.removeIdFromResult(context.json)
		assertEquals("[User(firstname=bart, lastname=simpson, email=bart@simpson.com, password=secret, id=, activities={})]", truncatedResult)

		// Exercise
		service.unfollowFriends(context)

		// Verify
		assertEquals(204, context.status)
		service.listFriends(context)
		truncatedResult = StringTestUtilities.removeIdFromResult(context.json)
		assertEquals("[]", truncatedResult)
	}

	@Test
	fun testFollowFriendNull() {
		try {
			service.followFriend(context)
			fail("Did not throw an exception on a null user")
		} catch (e: NullPointerException) {
			// Success because it threw an exception
		}
		assertTrue(true)
	}

	@Test
	fun testFollowFriendNotExisting() {
		// Setup
		context.params.put("id", "nobody")
		context.params.put("friendId", "nobody")
		// Exercise
		service.followFriend(context)
		// Verify
		assertEquals(200, context.status)
	}

	@Test
	fun testFollowFriendNullFriend() {
		// Setup
		var user1 = fixtures.users[0]
		context.returnedUser = user1
		service.createUser(context)
		var user1Id = StringTestUtilities.findIdInResult(context.json)
		context.params.put("id", user1Id)
		// Exercise
		try {
			service.followFriend(context)
			fail("Did not throw an exception on a null user")
		} catch (e: NullPointerException) {
			// Success because it threw an exception
		}
		assertTrue(true)
	}

	@Test
	fun testFollowFriendFriendNotExisting() {
		// Setup
		var user1 = fixtures.users[0]
		context.returnedUser = user1
		service.createUser(context)
		var user1Id = StringTestUtilities.findIdInResult(context.json)
		context.params.put("id", user1Id)
		context.params.put("friendId", "nobody")
		// Exercise
		service.followFriend(context)
		// Verify
		assertEquals(200, context.status)
	}

	@Test
	fun testUnfollowFriendsNull() {
		try {
			service.unfollowFriends(context)
			fail("Did not throw an exception on a null user")
		} catch (e: NullPointerException) {
			// Success because it threw an exception
		}
		assertTrue(true)
	}

	@Test
	fun testUnfollowFriendsNotExisting() {
		// Setup
		context.params.put("id", "nobody")
		// Exercise
		service.unfollowFriends(context)
		// Verify
		assertEquals(204, context.status)
	}

}
