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