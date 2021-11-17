import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

internal class UtilsTest {

    @Test
    fun parse() {
        assertEquals(listOf("Foo", "Bar", "Baz"), "FooBarBaz".parse())
        assertEquals(listOf("Wish", "Maker"), "WishMaker".parse())
        assertEquals(listOf("Fo*", "Ba"), "Fo*Ba".parse())
        assertEquals(listOf("F", "B", "Z"), "FBZ".parse())
        assertEquals(listOf("F*", "B*", "B*"), "F*B*B*".parse())
        assertEquals(listOf("****"), "****".parse())
    }

    @Test
    fun `prefix matching tests`() {
        assertTrue("For".matchesPrefix("Fo*"))
        assertTrue("Forr".matchesPrefix("Fo**"))
        assertTrue("Foo".matchesPrefix("F"))
        assertTrue("Foobar".matchesPrefix("Foob"))
        assertTrue("Foobar".matchesPrefix("Foobar"))
        assertTrue("Foo".matchesPrefix("F*o"))
        assertTrue("Fooobaaaaara".matchesPrefix("F*ob*r*"))
        assertTrue("Foobar".matchesPrefix("F*r"))
        assertTrue("Foob".matchesPrefix("F**b"))
        assertTrue("Foooob".matchesPrefix("F**b"))
        assertTrue("Foooob".matchesPrefix("F*o*b"))
        assertTrue("Fxxxx".matchesPrefix("F****"))
        assertTrue("Fxxxx".matchesPrefix("F*"))

        assertFalse("Foo".matchesPrefix("Bar"))
        assertFalse("Foo".matchesPrefix("Fob"))
        assertFalse("Foo".matchesPrefix("F*r"))
        assertFalse("Foo".matchesPrefix("F**o"))
        assertFalse("Foo".matchesPrefix("F*ob"))
        assertFalse("Foo".matchesPrefix("F***"))
        assertFalse("Foobar".matchesPrefix("F**oba*"))
    }
}