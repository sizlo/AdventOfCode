package advent.of.code.day13

import advent.of.code.day13.TransparentOrigami.Axis.*
import advent.of.code.utils.Coordinate
import advent.of.code.utils.readInputLines
import kotlin.math.absoluteValue

class TransparentOrigami(input: List<String>) {

    enum class Axis { X, Y }

    class FoldInstruction(instructionString: String) {

        private val axis: Axis
        private val pivot: Int

        init {
            val parts = instructionString
                .removePrefix("fold along ")
                .split("=")

            axis = values().first { it.name.equals(parts[0], ignoreCase = true) }
            pivot = parts[1].toInt()
        }

        fun apply(coordinate: Coordinate): Coordinate {
            return when (axis) {
                X -> coordinate.copy(x = pivot(coordinate.x))
                Y -> coordinate.copy(y = pivot(coordinate.y))
            }
        }

        private fun pivot(value: Int): Int {
            val difference = (value - pivot).absoluteValue
            return pivot - difference
        }
    }

    private val startingDotLocations = input
        .filter { it.isNotBlank() }
        .filter { !it.startsWith("fold") }
        .map { Coordinate(it) }

    private val foldInstructions = input
        .filter { it.startsWith("fold") }
        .map { FoldInstruction(it) }

    fun countDotsAfter1Fold(): Int {
        return startingDotLocations
            .map { foldInstructions[0].apply(it) }
            .distinct()
            .size
    }

    fun getImageProducedAfterApplyingAllFolds(): String {
        val finalDotLocations = foldInstructions.fold(startingDotLocations) { currentDotLocations, instruction ->
            currentDotLocations.map { instruction.apply(it) }.distinct()
        }

        return createImage(finalDotLocations)
    }

    private fun createImage(dotLocations: List<Coordinate>): String {
        var result = ""
        for (y in dotLocations.minOf { it.y } .. dotLocations.maxOf { it.y }) {
            for (x in dotLocations.minOf { it.x } .. dotLocations.maxOf { it.x }) {
                result += if (Coordinate(x, y) in dotLocations) "#" else "."
            }
            result += "\n"
        }
        return result
    }
}

fun main() {
    val input = readInputLines("/day13/input.txt")
    val transparentOrigami = TransparentOrigami(input)
    println(transparentOrigami.countDotsAfter1Fold()) // 701
    println(transparentOrigami.getImageProducedAfterApplyingAllFolds()) // FPEKBEJL
}