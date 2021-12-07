package advent.of.code.utils

fun List<String>.toIntList(): List<Int> {
    return this.map { it.toInt() }
}

fun readInput(path: String): List<String> {
    val resource = object {}.javaClass.getResource(path) ?: return emptyList()
    return resource.readText().split("\n")
}

fun readInputAsOneListOfIntegers(path: String): List<Int> {
    return readInput(path)[0].split(",").toIntList()
}