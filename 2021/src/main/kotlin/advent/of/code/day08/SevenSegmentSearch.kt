package advent.of.code.day08

import advent.of.code.utils.readInput

class SevenSegmentSearch(input: List<String>) {

    class Entry(private val signalPatterns: List<String>, val outputSignalPatterns: List<String>) {
        companion object {
            fun fromInputString(inputString: String): Entry {
                val parts = inputString.split(" | ")
                val signalPatterns = sortAllEntries(parts[0].split(" "))
                val outputSignalPatterns = sortAllEntries(parts[1].split(" "))
                return Entry(signalPatterns, outputSignalPatterns)
            }

            private fun sortAllEntries(list: List<String>): List<String> {
                return list.map { it.toList().sorted().joinToString("") }
            }
        }

        fun decodeOutputValue() : Int {
            val incorrectSignalPatternToValue = decodeSignalPatterns()
            return outputSignalPatterns
                .map { incorrectSignalPatternToValue[it] }
                .joinToString("")
                .toInt()
        }

        private fun decodeSignalPatterns(): Map<String, String> {
            // 1 is the only 2 letter signal pattern
            val incorrect1SignalPattern = signalPatterns.ofLength(2)[0]

            // 4 is the only 4 letter signal pattern
            val incorrect4SignalPattern = signalPatterns.ofLength(4)[0]

            // 7 is the only 3 letter signal pattern
            val incorrect7SignalPattern = signalPatterns.ofLength(3)[0]

            // 8 is the only 7 letter signal pattern
            val incorrect8SignalPattern = signalPatterns.ofLength(7)[0]

            // a is the only segment in 7 but not 1
            val incorrectA = incorrect7SignalPattern.minusAllCharsFrom(incorrect1SignalPattern)[0]

            // f is the only segment that is present in 9 segments
            val incorrectF = signalPatterns
                .flatMap { it.toList() }
                .groupBy { it }
                .filter { it.value.size == 9 }
                .map { it.key }
                .first()

            // c is the only other segment in 1 that is not f
            val incorrectC = incorrect1SignalPattern.minusChar(incorrectF).first()

            // 2 is the only 5 letter signal pattern without an f
            val incorrect2SignalPattern = signalPatterns.ofLength(5).notContaining(incorrectF)[0]

            // 5 is the only 5 letter signal pattern without a c
            val incorrect5SignalPattern = signalPatterns.ofLength(5).notContaining(incorrectC)[0]

            // 3 is the only 5 letter signal pattern that contains a c and f
            val incorrect3SignalPattern = signalPatterns.ofLength(5).containingAll(incorrectA, incorrectC, incorrectF)[0]

            // 6 is the only 6 letter signal pattern without a c
            val incorrect6SignalPattern = signalPatterns.ofLength(6).notContaining(incorrectC)[0]

            // e is the only segment in 2 but not in 3
            val incorrectE = incorrect2SignalPattern.minusAllCharsFrom(incorrect3SignalPattern)[0]

            // 9 is the only 6 letter signal pattern without an e
            val incorrect9SignalPattern = signalPatterns.ofLength(6).notContaining(incorrectE)[0]

            // 0 is the only 6 letter signal pattern left
            val incorrect0SignalPattern = signalPatterns
                .ofLength(6)
                .first { it != incorrect6SignalPattern && it != incorrect9SignalPattern }

            return mapOf(
                incorrect0SignalPattern to "0",
                incorrect1SignalPattern to "1",
                incorrect2SignalPattern to "2",
                incorrect3SignalPattern to "3",
                incorrect4SignalPattern to "4",
                incorrect5SignalPattern to "5",
                incorrect6SignalPattern to "6",
                incorrect7SignalPattern to "7",
                incorrect8SignalPattern to "8",
                incorrect9SignalPattern to "9",
            )
        }

        private fun String.minusAllCharsFrom(otherString: String): String {
            return this.toList().minus(otherString.toList().toSet()).joinToString("")
        }

        private fun String.minusChar(char: Char): String {
            return this.toList().minus(char).joinToString("")
        }

        private fun List<String>.ofLength(targetLength: Int): List<String> {
            return this.filter { it.length == targetLength }
        }

        private fun List<String>.notContaining(char: Char): List<String> {
            return this.filter { !it.contains(char) }
        }

        private fun List<String>.containingAll(vararg chars: Char): List<String> {
            return this.filter { it.toList().containsAll(chars.toList()) }

        }
    }

    private val entries = input.map(Entry::fromInputString)

    fun countOutputSignalPatternsWhichUseAUniqueNumberOfSegments(): Int {
        // Signal patterns with unique lengths are 1, 4, 7, and 8. Their respective pattern lengths are 2, 4, 3 and 7
        val segmentCountsToLookFor = listOf(2, 4, 3, 7)

        return entries
            .flatMap { it.outputSignalPatterns }
            .count { segmentCountsToLookFor.contains(it.length) }
    }

    fun decodeAndSumAllOutputValues(): Int {
        return entries.sumOf { it.decodeOutputValue() }
    }
}

fun main() {
    val input = readInput("/day08/input.txt")
    val sevenSegmentSearch = SevenSegmentSearch(input)
    println(sevenSegmentSearch.countOutputSignalPatternsWhichUseAUniqueNumberOfSegments()) // 310
    println(sevenSegmentSearch.decodeAndSumAllOutputValues()) // 915941
}