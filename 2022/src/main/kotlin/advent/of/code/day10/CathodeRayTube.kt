package advent.of.code.day10

import advent.of.code.utils.readInputLines
import kotlin.math.absoluteValue

class CPU {
    private var x = 1
    private var currentCycle = 1
    private val cycleXs = mutableMapOf<Int, Int>()

    fun runProgram(program: List<String>) = program.forEach { runInstruction(it) }

    private fun runInstruction(instruction: String) {
        if (instruction == "noop") {
            bumpCycle()
            return
        }

        val amount = instruction.split(" ").last().toInt()
        repeat(2) { bumpCycle() }
        x += amount
    }

    fun getXAtCycle(cycle: Int) = cycleXs.getValue(cycle)

    fun getSignalStrengthAtCycle(cycle: Int) = cycle * cycleXs.getValue(cycle)

    private fun bumpCycle() {
        cycleXs[currentCycle] = x
        currentCycle++
    }
}

class CathodeRayTube {

    private val width = 40
    private val height = 6
    private val cpu = CPU()

    fun getSumOfInterestingSignalStrengths(program: List<String>): Int {
        cpu.runProgram(program)
        val interestingCycles = listOf(20, 60, 100, 140, 180, 220)
        return interestingCycles.sumOf { cpu.getSignalStrengthAtCycle(it) }
    }

    fun render(program: List<String>): String {
        cpu.runProgram(program)
        return List(height) { renderLine(it) }.joinToString("\n")
    }

    private fun renderLine(lineIndex: Int): String {
        val startCycle = lineIndex * width + 1
        return (0 until width).joinToString("") { getPixelValue(it, startCycle + it) }
    }

    private fun getPixelValue(horizontalPosition: Int, cycle: Int): String {
        val spritePosition = cpu.getXAtCycle(cycle)
        return if ((spritePosition - horizontalPosition).absoluteValue <= 1) "#" else "."
    }
}

fun main() {
    val input = readInputLines("/day10/input.txt")
    println(CathodeRayTube().getSumOfInterestingSignalStrengths(input)) // 12560
    println(CathodeRayTube().render(input)) // PLPAFBCL
}
