package advent.of.code.day06

import advent.of.code.utils.hasOnlyUniqueContents
import advent.of.code.utils.readInput

class TuningTrouble {
    fun getEndPositionOfFirstStartOfPacketMarker(datastream: String): Int {
        return getEndPositionOfFirstWindowWithNUniqueCharacters(datastream, 4)
    }

    fun getEndPositionOfFirstStartOfMessageMarker(datastream: String): Int {
        return getEndPositionOfFirstWindowWithNUniqueCharacters(datastream, 14)
    }

    private fun getEndPositionOfFirstWindowWithNUniqueCharacters(datastream: String, n: Int): Int {
        val indexOfStartOfWindow = datastream
            .windowed(n)
            .indexOfFirst { it.toList().hasOnlyUniqueContents() }
        return indexOfStartOfWindow + n
    }
}

fun main() {
    val input = readInput("/day06/input.txt")
    println(TuningTrouble().getEndPositionOfFirstStartOfPacketMarker(input)) // 1080
    println(TuningTrouble().getEndPositionOfFirstStartOfMessageMarker(input)) // 3645
}
