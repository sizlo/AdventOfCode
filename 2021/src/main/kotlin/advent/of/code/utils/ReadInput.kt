package advent.of.code.utils

fun readInput(path: String): String {
    val resource = object {}.javaClass.getResource(path) ?: return ""
    return resource.readText()
}

fun readInputLines(path: String): List<String> {
    return readInput(path).split("\n")
}

fun readInputAsOneListOfIntegers(path: String): List<Int> {
    return readInputLines(path)[0].split(",").toIntList()
}

fun readInputAsIntGrid(path: String): IntGrid {
    return readInputLines(path).toIntGrid()
}