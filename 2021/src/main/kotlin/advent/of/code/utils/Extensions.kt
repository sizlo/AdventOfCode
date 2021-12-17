package advent.of.code.utils

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