package advent.of.code.day06

import advent.of.code.utils.readInput
import advent.of.code.utils.toIntList

class Lanternfish(input: List<Int>) {

    private val fishCounts = (0..8)
        .map { fishTimer ->
            input.count { it == fishTimer }.toLong()
        }.toMutableList()

    fun simulate(days: Int) {
        repeat(days) {
            val fishReadyToMultiply = fishCounts[0]
            for (index in 0..7) {
                fishCounts[index] = fishCounts[index + 1]
            }
            fishCounts[8] = fishReadyToMultiply
            fishCounts[6] += fishReadyToMultiply
        }
    }

    fun countFish(): Long {
        return fishCounts.sum()
    }
}

fun main() {
    val input = readInput("/day06/input.txt")[0].split(",").toIntList()

    val lanternfishForPart1 = Lanternfish(input)
    lanternfishForPart1.simulate(days = 80)

    val lanternfishForPart2 = Lanternfish(input)
    lanternfishForPart2.simulate(days = 256)

    println(lanternfishForPart1.countFish()) // 349549
    println(lanternfishForPart2.countFish()) // 1589590444365
}