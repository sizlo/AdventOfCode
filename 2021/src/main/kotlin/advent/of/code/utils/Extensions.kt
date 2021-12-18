package advent.of.code.utils

import kotlin.math.sqrt

typealias IntGrid = List<List<Int>>

fun List<String>.toIntList(): List<Int> {
    return this.map { it.toInt() }
}

fun List<String>.toIntGrid(): IntGrid {
    return this.map { row ->
        row.toList().map { item ->
            item.digitToInt()
        }
    }
}

fun <T> Collection<T>.productOf(selector: (T) -> Long): Long {
    return this.fold(1L) { product, item -> product * selector(item) }
}

fun Int.triangle(): Int = (this * (this + 1)) / 2

fun Int.isTriangle(): Boolean {
    // n is a triangle if 8n+1 is a perfect square - https://stackoverflow.com/a/2913319
    return sqrt(8 * this + 1.0) % 1 == 0.0
}

fun String.isAllDigits(): Boolean = this.toList().all { it.isDigit() }