package advent.of.code.utils

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

fun IntGrid.getRows() = this

fun IntGrid.getColumns(): IntGrid {
    val width = this.first().size
    return (0 until width).map { columnIndex -> this.map { row -> row[columnIndex] } }
}

fun List<String>.splitOnBlankLines(): List<List<String>> {
    return this
        .joinToString("\n")
        .split("\n\n")
        .map { it.split("\n") }
}

fun <T> List<T>.splitInHalf(): Pair<List<T>, List<T>> {
    if (this.size % 2 == 1) {
        throw RuntimeException("Cannot split a list of an odd size in half, size: ${this.size}")
    }

    val parts = this.chunked(this.size / 2)
    return Pair(parts[0], parts[1])
}

fun <T> Collection<T>.productOf(selector: (T) -> Long): Long {
    return this.fold(1L) { product, item -> product * selector(item) }
}

fun <K, V> Map<K, V>.invert(): Map<V, K> {
    return this.entries.associate { it.value to it.key }
}

fun String.toIntRange(): IntRange {
    if (this.toList().count { it == '-' } != 1) {
        throw RuntimeException("Negative numbers are not supported")
    }

    val ints =  this.split("-").toIntList()
    return ints[0]..ints[1]
}

fun IntRange.fullyContains(other: IntRange): Boolean {
    return this.contains(other.first) && this.contains(other.last)
}

fun IntRange.overlaps(other: IntRange): Boolean {
    return this.contains(other.first) || this.contains(other.last) || other.contains(this.first) || other.contains(this.last)
}

fun <T> Collection<T>.hasOnlyUniqueContents(): Boolean {
    return this.distinct().size == this.size
}
