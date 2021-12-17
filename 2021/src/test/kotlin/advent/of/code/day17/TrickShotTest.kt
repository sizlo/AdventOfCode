package advent.of.code.day17

import advent.of.code.day17.TrickShot.Area
import advent.of.code.day17.TrickShot.Launcher
import advent.of.code.utils.Velocity
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class TrickShotTest {

    private val exampleInput = "target area: x=20..30, y=-10..-5"

    private val testSubject = TrickShot(exampleInput)

    @Test
    fun `test example input for part 1`() {
        val result = testSubject.findHighestPossibleYPositionProbeCanReachAndStillHitTargetArea()

        assertThat(result).isEqualTo(45)
    }

    @Test
    fun `test example for part 2`() {
        val result = testSubject.findNumberOfInitialVelocitiesWhereProbeHitsTarget()

        assertThat(result).isEqualTo(112)
    }

    @Nested
    inner class LauncherTest {
        private val testSubject = Launcher(Area(20 .. 30, -10 .. -5))

        @ParameterizedTest
        @CsvSource(
            "7,2",
            "6,3",
            "9,0",
        )
        fun `test examples which hit`(x: String, y: String) {
            val initialVelocity = Velocity(x.toInt(), y.toInt())
            assertThat(testSubject.hitsTargetWithInitialVelocity(initialVelocity)).isTrue()
        }

        @Test
        fun `test example which misses`() {
            val initialVelocity = Velocity(17, -4)
            assertThat(testSubject.hitsTargetWithInitialVelocity(initialVelocity)).isFalse()
        }
    }

}