package advent.of.code.utils

fun List<String>.toIntList(): List<Int> {
    return this.map { Integer.parseInt(it) }
}

fun readInput(path: String): List<String> {
    val resource = object {}.javaClass.getResource(path) ?: return emptyList()
    return resource.readText().split("\n")
}