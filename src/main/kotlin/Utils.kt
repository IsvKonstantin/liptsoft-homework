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

fun String.matchesPrefix(prefix: String): Boolean {
    if (prefix.length > this.length) {
        return false
    } else {
        if (prefix.isEmpty()) {
            return true
        }
        return if (prefix.first() == WILDCARD_CHAR) {
            (1..this.length).any { this.drop(it).matchesPrefix(prefix.drop(1)) }
        } else {
            if (prefix.first() == this.first()) {
                this.drop(1).matchesPrefix(prefix.drop(1))
            } else {
                false
            }
        }
    }
}

const val WILDCARD_CHAR: Char = '*'
