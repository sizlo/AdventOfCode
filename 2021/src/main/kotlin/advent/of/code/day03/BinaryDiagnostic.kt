package advent.of.code.day03

import advent.of.code.utils.readInput

class BinaryDiagnostic(private val diagnosticBinaryNumbers: List<String>) {

    companion object {
        private const val ONE = '1'
        private const val ZERO = '0'
    }

    enum class BitCriteria { MOST_COMMON, LEAST_COMMON }
    
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
            gammaRateBinary += diagnosticBinaryNumbers.findMostCommonBitAtIndex(index)
            epsilonRateBinary += diagnosticBinaryNumbers.findLeastCommonBitAtIndex(index)
        }

        return Pair(gammaRateBinary.toInt(2), epsilonRateBinary.toInt(2))
    }

    private fun generateOxygenGeneratorRating(): Int {
        return filterBasedOnBitCriteria(diagnosticBinaryNumbers, BitCriteria.MOST_COMMON).first().toInt(2)
    }

    private fun generateCo2ScrubberRating(): Int {
        return filterBasedOnBitCriteria(diagnosticBinaryNumbers, BitCriteria.LEAST_COMMON).first().toInt(2)
    }

    private fun filterBasedOnBitCriteria(binaryNumbers: List<String>, bitCriteria: BitCriteria, index: Int = 0): List<String> {
        if (binaryNumbers.size == 1) return binaryNumbers

        val bitToKeep = if (bitCriteria == BitCriteria.MOST_COMMON) {
            binaryNumbers.findMostCommonBitAtIndex(index)
        } else {
            binaryNumbers.findLeastCommonBitAtIndex(index)
        }

        val filteredNumbers = binaryNumbers.filter { it[index] == bitToKeep }
        return filterBasedOnBitCriteria(filteredNumbers, bitCriteria, index + 1)
    }

    private fun List<String>.findMostCommonBitAtIndex(index: Int): Char {
        val numOnes = this.countOnesAtIndex(index)
        val numZeros = this.size - numOnes
        if (numOnes > numZeros) return ONE
        if (numZeros > numOnes) return ZERO
        return ONE
    }

    private fun List<String>.findLeastCommonBitAtIndex(index: Int): Char {
        val numOnes = this.countOnesAtIndex(index)
        val numZeros = this.size - numOnes
        if (numOnes < numZeros) return ONE
        if (numZeros < numOnes) return ZERO
        return ZERO
    }

    private fun List<String>.countOnesAtIndex(index: Int): Int {
        return this.map { it[index] }.count { it == ONE }
    }
}

fun main() {
    val input = readInput("/day03/input.txt")
    val binaryDiagnostic = BinaryDiagnostic(input)
    println(binaryDiagnostic.getPowerConsumption()) // 1092896
    println(binaryDiagnostic.getLifeSupportRating()) // 4672151
}