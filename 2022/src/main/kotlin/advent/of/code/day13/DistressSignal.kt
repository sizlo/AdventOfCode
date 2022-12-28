package advent.of.code.day13

import advent.of.code.utils.productOf
import advent.of.code.utils.readInputLines
import advent.of.code.utils.splitOnBlankLines

open class PacketData
class IntPacketData(val value: Int): PacketData() { override fun toString() = value.toString() }
class ListPacketData : PacketData() {
    val children = mutableListOf<PacketData>()
    override fun toString() = "[${children.joinToString(",")}]"
}

enum class ComparisonResult(val sortValue: Int) { UNKNOWN(0), CORRECT(-1), INCORRECT(1) }

private fun String.toPacketData(): PacketData {
    val root = ListPacketData()
    val stack = mutableListOf(root)

    var index = 1
    while (index < length) {
        when(this[index]) {
            '[' -> {
                val newList = ListPacketData()
                stack.last().children.add(newList)
                stack.add(newList)
            }
            ']' -> {
                stack.removeLast()
            }
            ',' -> { /* do nothing */ }
            else -> {
                var buffer = this[index].toString()
                while(this[index + 1].isDigit()) {
                    index++
                    buffer += this[index]
                }
                stack.last().children.add(IntPacketData(buffer.toInt()))
            }
        }

        index++
    }

    return root
}

class DistressSignal {
    fun getSumOfIndexesOfPairsInCorrectOrder(input: List<String>): Int {
        val pairs = input.splitOnBlankLines()
        return pairs.mapIndexed { index, pair -> if (isPairInCorrectOrder(pair)) index + 1 else 0 }.sum()
    }

    fun findDecoderKey(input: List<String>): Int {
        val dividerPackets = listOf("[[2]]", "[[6]]")

        val packets = (input + dividerPackets)
            .filter { it.isNotEmpty() }
            .map { it.toPacketData() }

        return packets
            .sortedWith { left, right -> compare(left, right).sortValue }
            .mapIndexed { index, packet -> if (dividerPackets.contains(packet.toString())) index + 1 else 0 }
            .filterNot { it == 0 }
            .productOf { it.toLong() }.toInt()
    }

    private fun isPairInCorrectOrder(pair: List<String>): Boolean {
        val (left, right) = pair.map { it.toPacketData() }
        return compare(left, right) == ComparisonResult.CORRECT
    }

    private fun compare(left: PacketData, right: PacketData): ComparisonResult {

        if (left is IntPacketData && right is IntPacketData) {
            if (left.value < right.value) return ComparisonResult.CORRECT
            if (left.value == right.value) return ComparisonResult.UNKNOWN
            return ComparisonResult.INCORRECT
        }

        if (left is IntPacketData) {
            val nested = ListPacketData().also { it.children.add(left) }
            return compare(nested, right)
        }

        if (right is IntPacketData) {
            val nested = ListPacketData().also { it.children.add(right) }
            return compare(left, nested)
        }

        if (left is ListPacketData  && right is ListPacketData) {
            var index = 0

            while (true) {
                val leftSideRanOut = left.children.size == index
                val rightSideRanOut = right.children.size == index

                if (leftSideRanOut && rightSideRanOut) return ComparisonResult.UNKNOWN
                if (leftSideRanOut) return ComparisonResult.CORRECT
                if (rightSideRanOut) return ComparisonResult.INCORRECT

                val comparisonResult = compare(left.children[index], right.children[index])
                if (comparisonResult != ComparisonResult.UNKNOWN) return comparisonResult

                index++
            }
        }

        throw RuntimeException("Bad things have happened")
    }
}

fun main() {
    val input = readInputLines("/day13/input.txt")
    println(DistressSignal().getSumOfIndexesOfPairsInCorrectOrder(input)) // 4821
    println(DistressSignal().findDecoderKey(input)) // 21890
}
