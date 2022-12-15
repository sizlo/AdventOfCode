package advent.of.code.day03

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class RucksackReorganizationTest {

    val exampleInput = listOf(
        "vJrwpWtwJgWrhcsFMMfFFhFp",
        "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL",
        "PmmdzqPrVvPwwTWBwg",
        "wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn",
        "ttgJtRGJQctTZtZT",
        "CrZsJsPPZsGzwwsLwLmpwMDw",
    )

    val testSubject = RucksackReorganization()

    @Test
    fun `test example input for part 1`() {
        val result = testSubject.getSumOfPrioritiesOfWrongItemInEachRucksack(exampleInput)
        assertThat(result).isEqualTo(157)
    }

    @Test
    fun `test example input for part 2`() {
        val result = testSubject.getSumOfPrioritiesOfGroupBadges(exampleInput)
        assertThat(result).isEqualTo(70)
    }
}