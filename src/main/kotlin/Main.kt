fun main(args: Array<String>) {
    val filepath: String = args[0]
    val inputPattern: String = args[1].drop(1).dropLast(1)

    val classNameReader = ClassNameReader()
    val pattern = Pattern(inputPattern)
    val classNames = classNameReader.readFromFile(filepath) as MutableList

    println("Class names matched by '$inputPattern', sorted in alphabetical order:")
    classNames
        .filter { pattern.matches(it) }
        .sortedBy { it.name }
        .forEach { println(it.nameFull) }
}