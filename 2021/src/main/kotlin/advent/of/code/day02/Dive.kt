package advent.of.code.day02

import advent.of.code.utils.readInput

class Dive {
    val position = Position(depth = 0, horizontal = 0)
    private var aim = 0

    fun applyCommandsForPart1(commands: List<String>) {
        commands.forEach { command ->
            val (direction, distance) = splitCommand(command)
            when(direction) {
                "forward" -> position.horizontal += distance
                "down" -> position.depth += distance
                "up" -> position.depth -= distance
            }
        }
    }

    fun applyCommandsForPart2(commands: List<String>) {
        commands.forEach { command ->
            val (direction,  distance) = splitCommand(command)
            when(direction) {
                "down" -> aim += distance
                "up" -> aim -= distance
                "forward" -> {
                    position.horizontal += distance
                    position.depth += aim * distance
                }
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

data class Position(var depth:Int, var horizontal:Int) {
    fun multiply() = depth * horizontal
}

fun main() {
    val input = readInput("/day02/input.txt")

    val diveForPart1 = Dive()
    diveForPart1.applyCommandsForPart1(input)
    println(diveForPart1.position.multiply()) // 1524750

    val diveForPart2 = Dive()
    diveForPart2.applyCommandsForPart2(input)
    println(diveForPart2.position.multiply())// 1592426537
}