package advent.of.code.day06

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class TuningTroubleTest {

    private val testSubject = TuningTrouble()

    @ParameterizedTest
    @ValueSource(strings = [
        "mjqjpqmgbljsphdztnvjfqwrcgsmlb,7",
        "bvwbjplbgvbhsrlpgdmjqwftvncz,5",
        "nppdvjthqldpwncqszvftbrmjlhg,6",
        "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg,10",
        "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw,11",
    ])
    fun `test example inputs for part 1`(datastreamAndExpectedResult: String) {
        val (datastream, expectedResultAsString) = datastreamAndExpectedResult.split(",")
        val expectedResult = expectedResultAsString.toInt()

        val result = testSubject.getEndPositionOfFirstStartOfPacketMarker(datastream)

        assertThat(result).isEqualTo(expectedResult)
    }

    @ParameterizedTest
    @ValueSource(strings = [
        "mjqjpqmgbljsphdztnvjfqwrcgsmlb,19",
        "bvwbjplbgvbhsrlpgdmjqwftvncz,23",
        "nppdvjthqldpwncqszvftbrmjlhg,23",
        "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg,29",
        "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw,26",
    ])
    fun `test example inputs for part 2`(datastreamAndExpectedResult: String) {
        val (datastream, expectedResultAsString) = datastreamAndExpectedResult.split(",")
        val expectedResult = expectedResultAsString.toInt()

        val result = testSubject.getEndPositionOfFirstStartOfMessageMarker(datastream)

        assertThat(result).isEqualTo(expectedResult)
    }
}