import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.PrintStream
import kotlin.test.assertEquals

internal class MainKtTest {
    private val inputPath: String = "src/test/resources/input_sample.txt"
    private val standardOut = System.out
    private val outputStreamCaptor = ByteArrayOutputStream()

    @BeforeEach
    fun setUp() {
        System.setOut(PrintStream(outputStreamCaptor))
    }

    @AfterEach
    fun tearDown() {
        System.setOut(standardOut)
    }

    @Test
    fun `test too many arguments`() {
        main(arrayOf("a", "b", "c"))
        assertEquals("Usage: class_finder filename \'pattern\'", outputStreamCaptor.toString().trim())
    }

    @Test
    fun `test not enough arguments`() {
        main(arrayOf("a"))
        assertEquals("Usage: class_finder filename \'pattern\'", outputStreamCaptor.toString().trim())
    }

    @Test
    fun `test invalid pattern`() {
        main(arrayOf(inputPath, "B1rB2z"))
        assertEquals("Pattern should consist of letters or wildcard characters ('*') only: B1rB2z", outputStreamCaptor.toString().trim())
    }

    @Test
    fun `test blank pattern`() {
        main(arrayOf(inputPath, ""))
        assertEquals("Pattern is blank", outputStreamCaptor.toString().trim())
    }

    @Test
    fun `test file not found`() {
        main(arrayOf("notfound.txt", "Bar"))
        assertEquals("File notfound.txt was not found", outputStreamCaptor.toString().trim())
    }

    @Test
    fun `test blank class name`() {
        val tempInputPath = "src/test/resources/temp_test.txt"
        File(tempInputPath).writeText("a.b.c.")
        main(arrayOf(tempInputPath, "Bar"))
        assertEquals("Class name is blank", outputStreamCaptor.toString().trim())
        File(tempInputPath).delete()
    }

    @Test
    fun `test invalid class name`() {
        val tempInputPath = "src/test/resources/temp_test.txt"
        File(tempInputPath).writeText("a.b.c.FooB1rBa*z")
        main(arrayOf(tempInputPath, "Bar"))
        assertEquals("Class name should consist of letters only: a.b.c.FooB1rBa*z", outputStreamCaptor.toString().trim())
        File(tempInputPath).delete()
    }

    @Test
    fun `test camel case class name`() {
        val tempInputPath = "src/test/resources/temp_test.txt"
        File(tempInputPath).writeText("a.b.c.fooBar")
        main(arrayOf(tempInputPath, "Bar"))
        assertEquals("Class name should be in CamelCase: a.b.c.fooBar", outputStreamCaptor.toString().trim())
        File(tempInputPath).delete()
    }
}
