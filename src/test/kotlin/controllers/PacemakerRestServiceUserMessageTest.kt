package controllers

import models.Fixtures
import models.Message
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test

class PacemakerRestServiceUserMessageTest {
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
	fun testMessageFriend() {
		// Setup
		utility.setupCreateFriend()
		var message = Message(text = "Hello World")
		context.returnedMessage = message
		// Exercise
		service.messageFriend(context)
		// Verify
		assertEquals(200, context.status)
	}

	@Test
	fun testListMessages() {
		// Setup
		utility.setupCreateFriend()
		var message = Message(text = "Hello World")
		context.returnedMessage = message
		service.messageFriend(context)

		context.params.put("id", context.params.get("friendId")!!)
		// Exercise
		service.listMessages(context)
		// Verify
		assertEquals("[Message(text=Hello World)]", context.json)
	}

	@Test
	fun testListMessagesTwo() {
		// Setup
		utility.setupCreateFriend()
		var message = Message(text = "Hello World")
		context.returnedMessage = message
		service.messageFriend(context)
		message = Message(text = "Also")
		context.returnedMessage = message
		service.messageFriend(context)

		context.params.put("id", context.params.get("friendId")!!)
		// Exercise
		service.listMessages(context)
		// Verify
		assertEquals("[Message(text=Hello World), Message(text=Also)]", context.json)
	}

	@Test
	fun testListMessagesNone() {
		// Setup
		utility.setupCreateFriend()
		context.params.put("id", context.params.get("friendId")!!)
		// Exercise
		service.listMessages(context)
		// Verify
		assertEquals("[]", context.json)
	}

	@Test
	fun testListMessagesNull() {
		// Exercise
		try {
			service.listMessages(context)
			fail("Did not throw an exception on a null user")
		} catch (e: NullPointerException) {
			// Success because it threw an exception
		}
		assertTrue(true)
	}

	@Test
	fun testListMessagesNotExisting() {
		// Setup
		context.params.put("id", "nobody")
		// Exercise
		service.listMessages(context)
		// Verify
		assertEquals("[]", context.json)
	}

	@Test
	fun testMessageFriendNull() {
		// Exercise
		try {
			service.messageFriend(context)
			fail("Did not throw an exception on a null user")
		} catch (e: NullPointerException) {
			// Success because it threw an exception
		}
		assertTrue(true)
	}

	@Test
	fun testMessageFriendNotExisting() {
		// Setup
		context.params.put("id", "nobody")
		context.params.put("friendId", "nobody")
		// Exercise
		service.messageFriend(context)
		// Verify
		assertEquals(200, context.status)
	}

	@Test
	fun testMessageFriendFriendNull() {
		// Setup
		context.params.put("id", "nobody")
		// Exercise
		try {
			service.messageFriend(context)
			fail("Did not throw an exception on a null user")
		} catch (e: NullPointerException) {
			// Success because it threw an exception
		}
		assertTrue(true)
	}

	@Test
	fun testMessageFriendFriendNotExisting() {
		// Setup
		var user1 = fixtures.users[0]
		context.returnedUser = user1
		service.createUser(context)
		var user1Id = StringTestUtilities.findIdInResult(context.json)
		context.params.put("id", user1Id)
		context.params.put("friendId", "nobody")
		// Exercise
		service.messageFriend(context)
		// Verify
		assertEquals(200, context.status)
	}


}
