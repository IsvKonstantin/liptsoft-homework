import java.io.FileNotFoundException

fun main(args: Array<String>) {
    if (args.size != 2) {
        println("Usage: class_finder filename \'pattern\'")
    } else {
        val filepath: String = args[0]
        val inputPattern: String = args[1]

        try {
            val classNameReader = ClassNameReader()
            val pattern = Pattern(inputPattern)
            val classNames = classNameReader.readFromFile(filepath) as MutableList

            println("Class names matched by '$inputPattern', sorted in alphabetical order:")
            classNames
                .filter { pattern.matches(it) }
                .sortedBy { it.name }
                .forEach { println(it.nameFull) }
        } catch (e: FileNotFoundException) {
            println("File $filepath was not found")
        } catch (e: IllegalArgumentException) {
            println(e.message)
        }
    }
}
