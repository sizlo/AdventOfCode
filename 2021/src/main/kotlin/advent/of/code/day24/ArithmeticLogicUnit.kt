package advent.of.code.day24

import advent.of.code.day24.ArithmeticLogicUnit.Instruction
import advent.of.code.utils.ProgressPrinter
import advent.of.code.utils.readInputLines
import java.lang.RuntimeException
import kotlin.system.measureTimeMillis

typealias Program = List<Instruction>

fun readProgram(input: List<String>): Program {
    return input.map { Instruction.fromString(it) }
}

class ArithmeticLogicUnit {

    interface Instruction {
        companion object {
            fun fromString(input: String): Instruction {
                val parts = input.split(" ")
                return when (parts[0]) {
                    "inp" -> InputInstruction(parts[1])
                    "add" -> AddInstruction(parts[1], parts[2])
                    "mul" -> MultiplyInstruction(parts[1], parts[2])
                    "div" -> DivideInstruction(parts[1], parts[2])
                    "mod" -> ModInstruction(parts[1], parts[2])
                    "eql" -> EqualsInstruction(parts[1], parts[2])
                    else -> throw RuntimeException("Unknown instruction: $input")
                }
            }
        }

        fun apply(alu: ALU)
    }

    class InputInstruction(private val variableName: String): Instruction {
        override fun apply(alu: ALU) {
            alu.variables[variableName] = alu.getNextInput()
        }
    }

    class AddInstruction(private val lhs: String, private val rhs: String): Instruction {
        override fun apply(alu: ALU) {
            alu.variables[lhs] = alu.variables[lhs]!! + alu.variableOrValue(rhs)
        }
    }

    class MultiplyInstruction(private val lhs: String, private val rhs: String): Instruction {
        override fun apply(alu: ALU) {
            alu.variables[lhs] = alu.variables[lhs]!! * alu.variableOrValue(rhs)
        }
    }

    class DivideInstruction(private val lhs: String, private val rhs: String): Instruction {
        override fun apply(alu: ALU) {
            alu.variables[lhs] = alu.variables[lhs]!! / alu.variableOrValue(rhs)
        }
    }

    class ModInstruction(private val lhs: String, private val rhs: String): Instruction {
        override fun apply(alu: ALU) {
            alu.variables[lhs] = alu.variables[lhs]!! % alu.variableOrValue(rhs)
        }
    }

    class EqualsInstruction(private val lhs: String, private val rhs: String): Instruction {
        override fun apply(alu: ALU) {
            alu.variables[lhs] = if (alu.variables[lhs]!! == alu.variableOrValue(rhs)) 1 else 0
        }
    }

    class ALU(inputNumbers: List<Int>) {
        private val inputNumberQueue = ArrayDeque(inputNumbers)

        val variables = mutableMapOf(
            "w" to 0,
            "x" to 0,
            "y" to 0,
            "z" to 0,
        )

        fun run(program: Program) {
            program.forEach { it.apply(this) }
        }

        fun getNextInput(): Int {
            return inputNumberQueue.removeFirst()
        }

        fun variableOrValue(operand: String): Int {
            return if (variables.contains(operand)) {
                variables.getValue(operand)
            } else {
                operand.toInt()
            }
        }
    }

    fun getMaximumValidValue(input: List<String>): Long {
        val program = readProgram(input)

        val maxPossibleValue = 99999999999999
        val minPossibleValue = 11111111111111

        val progressPrinter = ProgressPrinter()

        (maxPossibleValue downTo minPossibleValue)
            .forEach { number ->
                progressPrinter.printPercent("Current value: $number") {
                    ((maxPossibleValue - number) / (maxPossibleValue - minPossibleValue)).toInt() * 100
                }
                val digits = number.toString().toList().map { it.digitToInt() }
                if (!digits.contains(0)) {
                    val alu = ALU(digits)
                    alu.run(program)
                    if (alu.variables["z"] == 0) {
                        return number
                    }
                }
        }

        throw RuntimeException("Could not find valid value")
    }
}

fun main() {
    val input = readInputLines("/day24/input.txt")

    val timeForPart1 = measureTimeMillis {
        val part1 = ArithmeticLogicUnit().getMaximumValidValue(input)
        println("Part 1 result: $part1")
    }
    println("Part 1 took ${timeForPart1}ms")


    // TODO
    // Split the program into sections, the start of each section is when it asks for the next input
    // For every number 1-9 get the state of running the first section on that
    // For every resulting state from the first section run every number 1-9 on that state, and so on
}