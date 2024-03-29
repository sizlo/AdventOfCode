package advent.of.code.day11

import advent.of.code.utils.Part
import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class MonkeyInTheMiddleTest {

    private val exampleInput = """
        Monkey 0:
          Starting items: 79, 98
          Operation: new = old * 19
          Test: divisible by 23
            If true: throw to monkey 2
            If false: throw to monkey 3
        
        Monkey 1:
          Starting items: 54, 65, 75, 74
          Operation: new = old + 6
          Test: divisible by 19
            If true: throw to monkey 2
            If false: throw to monkey 0
        
        Monkey 2:
          Starting items: 79, 60, 97
          Operation: new = old * old
          Test: divisible by 13
            If true: throw to monkey 1
            If false: throw to monkey 3
        
        Monkey 3:
          Starting items: 74
          Operation: new = old + 3
          Test: divisible by 17
            If true: throw to monkey 0
            If false: throw to monkey 1
    """.trimIndent().removeSurrounding("\n")

    @Test
    fun `test example input for part 1`() {
        val result = MonkeyInTheMiddle(exampleInput, Part.PART_1).getMonkeyBusinessLevelAfterPlayingGame()
        assertThat(result).isEqualTo(10605)
    }

    @Test
    fun `test example input for part 2`() {
        val result = MonkeyInTheMiddle(exampleInput, Part.PART_2).getMonkeyBusinessLevelAfterPlayingGame()
        assertThat(result).isEqualTo(2713310158L)
    }
}