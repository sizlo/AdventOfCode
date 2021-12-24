package advent.of.code.day24

import advent.of.code.day24.ArithmeticLogicUnit.ALU
import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class ArithmeticLogicUnitTest {

    @Test
    fun `test negating a number`() {
        val alu = ALU(inputNumbers = listOf(5))

        val program = readProgram(listOf(
            "inp x",
            "mul x -1",
        ))

        alu.run(program)
        val result = alu.variables["x"]

        assertThat(result).isEqualTo(-5)
    }

    @ParameterizedTest
    @CsvSource(
        "3,9,1",
        "3,8,0"
    )
    fun `test checking if one number is 3 times another`(firstInput: Int, secondInput: Int, expectedResult: Int) {
        val alu = ALU(inputNumbers = listOf(firstInput, secondInput))

        val program = readProgram(listOf(
            "inp z",
            "inp x",
            "mul z 3",
            "eql z x",
        ))

        alu.run(program)
        val result = alu.variables["z"]

        assertThat(result).isEqualTo(expectedResult)
    }

    @ParameterizedTest
    @CsvSource(
        "5,0,1,0,1",
        "13,1,1,0,1",
        "6,0,1,1,0",
    )
    fun `test binary converter`(input: Int, expectedEights: Int, expectedFours: Int, expectedTwos: Int, expectedOnes: Int) {
        val alu = ALU(inputNumbers = listOf(input))

        val program = readProgram(listOf(
            "inp w",
            "add z w",
            "mod z 2",
            "div w 2",
            "add y w",
            "mod y 2",
            "div w 2",
            "add x w",
            "mod x 2",
            "div w 2",
            "mod w 2",
        ))

        alu.run(program)

        assertThat(alu.variables["z"]).isEqualTo(expectedOnes)
        assertThat(alu.variables["y"]).isEqualTo(expectedTwos)
        assertThat(alu.variables["x"]).isEqualTo(expectedFours)
        assertThat(alu.variables["w"]).isEqualTo(expectedEights)
    }
}