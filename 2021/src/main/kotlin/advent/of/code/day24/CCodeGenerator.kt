package advent.of.code.day24

import advent.of.code.utils.readInputLines
import java.lang.RuntimeException

class CCodeGenerator {
    var code = ""
    var indent = ""
    var inputCount = -1

    fun generate(input: List<String>): String {
        input.forEach { instruction ->
            val parts = instruction.split(" ")
            val opcode = parts[0]

            when (opcode) {
                "inp" -> {
                    inputCount++
                    addLine("")
                    addLine("for (int ${operator("w")} = 9; ${operator("w")} >= 1; ${operator("w")}--) {")
                    indent += "  "
                    if (inputCount == 0) {
                        addLine("int x0 = 0;")
                        addLine("int y0 = 0;")
                        addLine("int z0 = 0;")
                    } else {
                        addLine("int x$inputCount = x${inputCount - 1};")
                        addLine("int y$inputCount = y${inputCount - 1};")
                        addLine("int z$inputCount = z${inputCount - 1};")
                    }
                }
                "add" -> addLine(operation(parts, "+"))
                "mul" -> addLine(operation(parts, "*"))
                "div" -> addLine(operation(parts, "/"))
                "mod" -> addLine(operation(parts, "%"))
                "eql" -> addLine("${operator(parts[1])} = ${operator(parts[1])} == ${operator(parts[2])} ? 1 : 0;")
                else -> throw RuntimeException("Unknown opcode: $instruction")
            }
        }

        while (indent.isNotEmpty()) {
            indent = indent.substring(2)
            addLine("}")
        }

        return code
    }

    private fun addLine(line: String) {
        code += "$indent$line\n"
    }

    private fun operation(instructionParts: List<String>, operator: String): String {
        return "${operator(instructionParts[1])} = ${operator(instructionParts[1])} $operator ${operator(instructionParts[2])};"
    }

    private fun operator(name: String): String {
        if (listOf("w", "x", "y", "z").contains(name)) {
            return "$name$inputCount"
        }
        return name
    }
}

fun main() {
    val input = readInputLines("/day24/input.txt")
    println(CCodeGenerator().generate(input))
}