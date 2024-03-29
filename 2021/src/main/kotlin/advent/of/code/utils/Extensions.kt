package advent.of.code.utils

import kotlin.math.sqrt

typealias IntGrid = List<List<Int>>

fun List<String>.toIntList(): List<Int> {
    return this.map { it.toInt() }
}

fun List<String>.toIntGrid(transform: (Char) -> Int = {it.digitToInt()} ): IntGrid {
    return this.map { row ->
        row.toList().map { item ->
            transform.invoke(item)
        }
    }
}

fun List<String>.splitOnBlankLines(): List<List<String>> {
    return this
        .joinToString("\n")
        .split("\n\n")
        .map { it.split("\n") }
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

fun IntRange.containsOther(other: IntRange): Boolean = this.contains(other.first) && this.contains(other.last)

fun IntRange.getIntersectWithOther(other: IntRange): IntRange? {
    val intersectStart = maxOf(this.first, other.first)
    val intersectEnd = minOf(this.last, other.last)
    return if (intersectStart > intersectEnd) null else intersectStart .. intersectEnd
}

fun IntRange.splitBasedOnOverlapWith(other: IntRange): List<IntRange> {
    return this.getIntersectWithOther(other)?.let { intersect ->

        val possibleParts = setOf(
            this.first .. minOf(this.last, intersect.first - 1),
            intersect,
            maxOf(this.first, intersect.last + 1) .. this.last
        )

        return possibleParts.filter { this.containsOther(it) }

    } ?: listOf(this)
}
