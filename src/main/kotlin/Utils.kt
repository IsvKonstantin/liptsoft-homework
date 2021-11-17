fun String.parse(): MutableList<String> {
    val parsed: MutableList<String> = mutableListOf()
    val builder = StringBuilder().append(this.first())
    for (char in this.drop(1)) {
        if (char.isUpperCase()) {
            parsed.add(builder.toString())
            builder.clear()
        }
        builder.append(char)
    }
    parsed.add(builder.toString())
    return parsed
}