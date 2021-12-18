package advent.of.code.day18

import advent.of.code.day18.Snailfish.SnailfishNumber
import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

internal class SnailfishTest {

    private val exampleInput = listOf(
        "[[[0,[5,8]],[[1,7],[9,6]]],[[4,[1,2]],[[1,4],2]]]",
        "[[[5,[2,8]],4],[5,[[9,9],0]]]",
        "[6,[[[6,2],[5,6]],[[7,6],[4,7]]]]",
        "[[[6,[0,7]],[0,9]],[4,[9,[9,0]]]]",
        "[[[7,[6,4]],[3,[1,3]]],[[[5,5],1],9]]",
        "[[6,[[7,3],[3,2]]],[[[3,8],[5,7]],4]]",
        "[[[[5,4],[7,7]],8],[[8,3],8]]",
        "[[9,3],[[9,9],[6,[4,9]]]]",
        "[[2,[[7,7],7]],[[5,8],[[9,3],[0,2]]]]",
        "[[[[5,2],5],[8,[3,7]]],[[5,[7,5]],[4,4]]]",
    )

    private val testSubject = Snailfish()

    @Test
    fun `test example reductions`() {
        testReduction("[[[[[9,8],1],2],3],4]", "[[[[0,9],2],3],4]")
        testReduction("[7,[6,[5,[4,[3,2]]]]]", "[7,[6,[5,[7,0]]]]")
        testReduction("[[6,[5,[4,[3,2]]]],1]", "[[6,[5,[7,0]]],3]")
        testReduction("[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]", "[[3,[2,[8,0]]],[9,[5,[7,0]]]]")

    }

    @Test
    fun `test simple example additions`() {
        assertThat((sf("[1,2]") + sf("[[3,4],5]")).toString()).isEqualTo("[[1,2],[[3,4],5]]")
        assertThat((sf("[[[[4,3],4],4],[7,[[8,4],9]]]") + sf("[1,1]")).toString()).isEqualTo("[[[[0,7],4],[[7,8],[6,0]]],[8,1]]")
    }

    @Test
    fun `test complex example additions`() {
        assertThat(testSubject.addAll(listOf(
            sf("[1,1]"),
            sf("[2,2]"),
            sf("[3,3]"),
            sf("[4,4]"),
        )).toString()).isEqualTo("[[[[1,1],[2,2]],[3,3]],[4,4]]")

        assertThat(testSubject.addAll(listOf(
            sf("[1,1]"),
            sf("[2,2]"),
            sf("[3,3]"),
            sf("[4,4]"),
            sf("[5,5]"),
        )).toString()).isEqualTo("[[[[3,0],[5,3]],[4,4]],[5,5]]")

        assertThat(testSubject.addAll(listOf(
            sf("[1,1]"),
            sf("[2,2]"),
            sf("[3,3]"),
            sf("[4,4]"),
            sf("[5,5]"),
            sf("[6,6]"),
        )).toString()).isEqualTo("[[[[5,0],[7,4]],[5,5]],[6,6]]")


        assertThat(testSubject.addAll(listOf(
            sf("[[[0,[4,5]],[0,0]],[[[4,5],[2,6]],[9,5]]]"),
            sf("[7,[[[3,7],[4,3]],[[6,3],[8,8]]]]"),
            sf("[[2,[[0,8],[3,4]]],[[[6,7],1],[7,[1,6]]]]"),
            sf("[[[[2,4],7],[6,[0,5]]],[[[6,8],[2,8]],[[2,1],[4,5]]]]"),
            sf("[7,[5,[[3,8],[1,4]]]]"),
            sf("[[2,[2,2]],[8,[8,1]]]"),
            sf("[2,9]"),
            sf("[1,[[[9,3],9],[[9,0],[0,7]]]]"),
            sf("[[[5,[7,4]],7],1]"),
            sf("[[[[4,2],2],6],[8,7]]"),
        )).toString()).isEqualTo("[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]")
    }

    @Test
    fun `test example magnitudes`() {
        assertThat(sf("[9,1]").magnitude()).isEqualTo(29)
        assertThat(sf("[1,9]").magnitude()).isEqualTo(21)
        assertThat(sf("[[9,1],[1,9]]").magnitude()).isEqualTo(129)
        assertThat(sf("[[1,2],[[3,4],5]]").magnitude()).isEqualTo(143)
        assertThat(sf("[[[[0,7],4],[[7,8],[6,0]]],[8,1]]").magnitude()).isEqualTo(1384)
        assertThat(sf("[[[[1,1],[2,2]],[3,3]],[4,4]]").magnitude()).isEqualTo(445)
        assertThat(sf("[[[[3,0],[5,3]],[4,4]],[5,5]]").magnitude()).isEqualTo(791)
        assertThat(sf("[[[[5,0],[7,4]],[5,5]],[6,6]]").magnitude()).isEqualTo(1137)
        assertThat(sf("[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]").magnitude()).isEqualTo(3488)
    }

    @Test
    fun `test example input for part 1`() {
        val result = testSubject.findMagnitudeOfSumOfAllSnailfishNumbers(exampleInput)

        assertThat(result).isEqualTo(4140)
    }

    @Test
    fun `test example input for part 2`() {
        val result = testSubject.findLargestMagnitudePossibleWhenMultiplyingAnyTwoSnailfishNumbers(exampleInput)

        assertThat(result).isEqualTo(3993)
    }

    private fun testReduction(input: String, expectedOutput: String) {
        val number = sf(input)
        number.reduce()
        assertThat(number.toString()).isEqualTo(expectedOutput)
    }
}

private fun sf(input: String) = SnailfishNumber(input)