package models

import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class MessageTest {
	lateinit var message: Message
	lateinit var message2: Message

	@Before
	fun setup() {
		message = Message()
		message2 = Message()
	}

	@After
	fun tearDown() {
	}

	@Test
	fun constructorDefault() {
		val message3 = Message()

		assertEquals("", message3.text)
	}

	@Test
	fun testToString() {
		assertEquals("Message(text=)", message.toString())
	}

	@Test
	fun testSetterAndGetter() {
		message.text = "another"
		message.component1()
	}

	@Test
	fun useEquals() {
		assertEquals(message, message)
		assertEquals(message, message2)
		assertTrue(message.equals(message2))
		assertTrue(message.equals(message))
		val wrongObjectType = Any()
		assertFalse(message.equals(wrongObjectType))
	}

	@Test
	fun useCopy() {
		val message3 = message.copy()
	}

	@Test
	fun useHashCode() {
		val code = message.hashCode()
	}
}