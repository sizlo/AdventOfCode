package advent.of.code.day02

import advent.of.code.utils.Part
import advent.of.code.utils.Part.PART_1
import advent.of.code.utils.Part.PART_2
import advent.of.code.utils.readInputLines

class Dive(private val part: Part) {
    val position = Position(depth = 0, horizontal = 0)
    private var aim = 0

    fun applyCommands(commands: List<String>) {
        commands
                .map(::splitCommand)
                .forEach { (direction, distance) ->
                    when(part) {
                        PART_1 -> move(direction, distance)
                        PART_2 -> moveWithAim(direction, distance)
                    }
                }
    }

    private fun move(direction: String, distance: Int) {
        when(direction) {
            "forward" -> position.horizontal += distance
            "down" -> position.depth += distance
            "up" -> position.depth -= distance
        }
    }

    private fun moveWithAim(direction: String, distance: Int) {
        when(direction) {
            "down" -> aim += distance
            "up" -> aim -= distance
            "forward" -> {
                position.horizontal += distance
                position.depth += aim * distance
            }
        }
    }

    private fun splitCommand(command: String): Pair<String, Int> {
        val parts = command.split(" ")
        val direction = parts[0]
        val distance = parts[1].toInt()
        return Pair(direction, distance)
    }
}

data class Position(var depth: Int, var horizontal: Int) {
    fun multiply() = depth * horizontal
}

fun main() {
    val input = readInputLines("/day02/input.txt")

    val diveForPart1 = Dive(PART_1)
    diveForPart1.applyCommands(input)
    println(diveForPart1.position.multiply()) // 1524750

    val diveForPart2 = Dive(PART_2)
    diveForPart2.applyCommands(input)
    println(diveForPart2.position.multiply())// 1592426537
}