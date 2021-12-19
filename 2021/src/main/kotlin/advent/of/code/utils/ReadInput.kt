package advent.of.code.utils

import java.lang.RuntimeException

fun readInput(path: String): String {
    val resource = object {}.javaClass.getResource(path) ?: throw RuntimeException("Could not read file at $path")
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