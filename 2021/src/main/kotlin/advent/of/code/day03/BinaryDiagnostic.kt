package advent.of.code.day03

import advent.of.code.utils.readInput

class BinaryDiagnostic(private val diagnosticBinaryNumbers: List<String>) {

    companion object {
        private const val ONE = '1'
        private const val ZERO = '0'
    }

    enum class BitCriteria { MOST_COMMON, LEAST_COMMON }

    class BitsAtIndexCounter(binaryNumbers: List<String>, private val index: Int) {

        private var numOnes: Int = binaryNumbers.map { it[index] }.count { it == ONE }
        private var numZeros = binaryNumbers.size - numOnes

        fun getBitForCriteria(bitCriteria: BitCriteria): Char {
            return when(bitCriteria) {
                BitCriteria.MOST_COMMON -> if (numOnes >= numZeros) ONE else ZERO
                BitCriteria.LEAST_COMMON -> if (numZeros <= numOnes) ZERO else ONE
            }
        }
    }
    
    fun getPowerConsumption(): Int {
        val (gammaRate, epsilonRate) = generateGammaAndEpsilon(diagnosticBinaryNumbers)
        return gammaRate * epsilonRate
    }

    fun getLifeSupportRating(): Int {
        return generateOxygenGeneratorRating() * generateCo2ScrubberRating()
    }

    private fun generateGammaAndEpsilon(diagnosticBinaryNumbers: List<String>): Pair<Int, Int> {
        var gammaRateBinary = ""
        var epsilonRateBinary = ""

        for (index in 0 until diagnosticBinaryNumbers.first().length) {
            val bitsAtIndexCounter = BitsAtIndexCounter(diagnosticBinaryNumbers, index)
            gammaRateBinary += bitsAtIndexCounter.getBitForCriteria(BitCriteria.MOST_COMMON)
            epsilonRateBinary += bitsAtIndexCounter.getBitForCriteria(BitCriteria.LEAST_COMMON)
        }

        return Pair(gammaRateBinary.toInt(2), epsilonRateBinary.toInt(2))
    }

    private fun generateOxygenGeneratorRating(): Int {
        return filterBasedOnBitCriteria(diagnosticBinaryNumbers, BitCriteria.MOST_COMMON)
    }

    private fun generateCo2ScrubberRating(): Int {
        return filterBasedOnBitCriteria(diagnosticBinaryNumbers, BitCriteria.LEAST_COMMON)
    }

    private tailrec fun filterBasedOnBitCriteria(binaryNumbers: List<String>, bitCriteria: BitCriteria, index: Int = 0): Int {
        if (binaryNumbers.size == 1) return binaryNumbers.first().toInt(2)

        val bitToKeep = BitsAtIndexCounter(binaryNumbers, index).getBitForCriteria(bitCriteria)
        val filteredNumbers = binaryNumbers.filter { it[index] == bitToKeep }
        return filterBasedOnBitCriteria(filteredNumbers, bitCriteria, index + 1)
    }
}

fun main() {
    val input = readInput("/day03/input.txt")
    val binaryDiagnostic = BinaryDiagnostic(input)
    println(binaryDiagnostic.getPowerConsumption()) // 1092896
    println(binaryDiagnostic.getLifeSupportRating()) // 4672151
}