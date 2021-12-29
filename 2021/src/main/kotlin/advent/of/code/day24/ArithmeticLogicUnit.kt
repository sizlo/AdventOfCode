package advent.of.code.day24

import advent.of.code.day24.ArithmeticLogicUnit.Instruction
import advent.of.code.utils.readInputLines
import java.lang.RuntimeException

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

    fun validateMonadValue(value: String, programInput: List<String>) {
        val program = readProgram(programInput)
        val alu = ALU()

        alu.run(program, value.toList().map { it.digitToInt() })

        val isValid = alu.variables["z"] == 0
        println("Validate MONAD value: $value: $isValid")
    }
}

fun main() {
    val input = readInputLines("/day24/input.txt")
    ArithmeticLogicUnit().validateMonadValue("92967699949891", input)
    ArithmeticLogicUnit().validateMonadValue("91411143612181", input)
}