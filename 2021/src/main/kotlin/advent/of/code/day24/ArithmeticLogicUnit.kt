package advent.of.code.day24

import advent.of.code.day24.ArithmeticLogicUnit.InputInstruction
import advent.of.code.day24.ArithmeticLogicUnit.Instruction
import advent.of.code.utils.ProgressPrinter
import advent.of.code.utils.readInputLines
import java.lang.RuntimeException
import kotlin.system.measureTimeMillis

typealias Program = List<Instruction>

fun Program.splitOnInputs(): List<Program> {
    var currentSubProgram = mutableListOf<Instruction>()
    val result = mutableListOf(currentSubProgram)

    this.forEach { instruction ->
        if (instruction is InputInstruction) {
            currentSubProgram = mutableListOf()
            result += currentSubProgram
        }
        currentSubProgram += instruction
    }

    return result.filter { it.isNotEmpty() }.map { it.toList() }
}

fun readProgram(input: List<String>): Program {
    return input.map { Instruction.fromString(it) }
}

class ArithmeticLogicUnit {

    private val progressPrinter = ProgressPrinter()

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

    class ALU(w: Int = 0, x: Int = 0, y: Int = 0, z: Int = 0) {
        val variables = mutableMapOf(
            "w" to w,
            "x" to x,
            "y" to y,
            "z" to z,
        )

        private val inputNumberQueue = mutableListOf<Int>()

        fun run(program: Program, inputNumbers: List<Int>) {
            inputNumberQueue += inputNumbers
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
        val subPrograms = program.splitOnInputs()

        return tryAllValues(subPrograms, ALU(), 0)!!
    }

    private fun tryAllValues(subPrograms: List<Program>, state: ALU, value: Long): Long? {
        if (subPrograms.isEmpty()) {
            progressPrinter.printPercent("Value: $value") { ((99999999999999 - value) / 88888888888888).toInt() * 100 }
            return if (valueWasValid(state)) value else null
        }

        (9 downTo 1).forEach { digit ->
            val alu = ALU(
                state.variables.getValue("w"),
                state.variables.getValue("x"),
                state.variables.getValue("y"),
                state.variables.getValue("z")
            )
            alu.run(subPrograms.first(), listOf(digit))
            val result = tryAllValues(subPrograms.slice(1 until subPrograms.size), alu, value * 10 + digit)
            if (result != null) {
                return result
            }
        }

        return null
    }

    private fun valueWasValid(state: ALU): Boolean {
        return state.variables["z"] == 0
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