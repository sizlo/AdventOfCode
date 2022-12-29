package advent.of.code.day15

import advent.of.code.utils.Coordinate
import advent.of.code.utils.manhattanDistance
import advent.of.code.utils.readInputLines
import advent.of.code.utils.requireHeapSpaceInGigabytes
import kotlin.math.absoluteValue

class Sensor(val location: Coordinate, val closestBeaconLocation: Coordinate) {
    companion object {
        fun fromString(input: String): Sensor {
            val match = """Sensor at x=(-?\d+), y=(-?\d+): closest beacon is at x=(-?\d+), y=(-?\d+)"""
                .toRegex()
                .find(input)!!
            val sensor = Coordinate(match.groupValues[1].toInt(), match.groupValues[2].toInt())
            val beacon = Coordinate(match.groupValues[3].toInt(), match.groupValues[4].toInt())
            return Sensor(sensor, beacon)
        }
    }

    private val distanceToBeacon = manhattanDistance(location, closestBeaconLocation)

    fun getBeaconExclusionZoneWhenYIs(yToCheck: Int): List<Coordinate> {
        return getBeaconExclusionZoneAsRangeWhenYIs(yToCheck)
            .map { x -> Coordinate(x, yToCheck) }
            .filter { it != closestBeaconLocation }
    }

    fun getPointsJustOutsideBeaconExclusionZone(): List<Coordinate> {
        val distanceToExclusionZone = distanceToBeacon + 1
        return (location.y - distanceToExclusionZone .. location.y + distanceToExclusionZone).flatMap { y ->
            val remainingDistance = distanceToExclusionZone - (location.y - y).absoluteValue
            listOf(Coordinate(location.x - remainingDistance, y), Coordinate(location.x + remainingDistance, y))
        }
    }

    fun isWithinBeaconExclusionZone(locationToCheck: Coordinate): Boolean {
        return getBeaconExclusionZoneAsRangeWhenYIs(locationToCheck.y).contains(locationToCheck.x)
    }

    private fun getBeaconExclusionZoneAsRangeWhenYIs(yToCheck: Int): IntRange {
        val remainingDistance = distanceToBeacon - (location.y - yToCheck).absoluteValue
        return (location.x - remainingDistance .. location.x + remainingDistance)
    }
}

class BeaconExclusionZone(sensorData: List<String>) {

    private val sensors = sensorData.map { Sensor.fromString(it) }

    fun countHowManySpacesCannotContainABeaconWhenYIs(yToCheck: Int): Int {
        return sensors
            .flatMap { it.getBeaconExclusionZoneWhenYIs(yToCheck) }
            .distinct()
            .size
    }

    fun findTuningFrequencyOfDistressBeacon(maxXAndY: Int): Long {
        val distressBeacon = findCoordinateOfDistressBeacon(maxXAndY)
        return distressBeacon.x.toLong() * 4000000 + distressBeacon.y.toLong()
    }

    // Because there is only one correct answer, it must be totally surrounded
    // by beacon exclusion zones, meaning we can limit our search to all the points
    // one step outside og the beacon exclusion zones
    private fun findCoordinateOfDistressBeacon(maxXAndY: Int): Coordinate {
        val sensorLocations = sensors.map { it.location }
        val beaconLocations = sensors.map { it.closestBeaconLocation }

        val possibleLocations = sensors
            .flatMap { it.getPointsJustOutsideBeaconExclusionZone() }
            .distinct()
            .filter { it.x >= 0 && it.y >= 0 && it.x <= maxXAndY && it.y <= maxXAndY }
            .filterNot { sensorLocations.contains(it) }
            .filterNot { beaconLocations.contains(it) }

        possibleLocations.forEach { possibleLocation ->
            if (sensors.none { sensor -> sensor.isWithinBeaconExclusionZone(possibleLocation) }) {
                return possibleLocation
            }
        }

        throw RuntimeException("Not found any distress beacon")
    }
}

fun main() {
    requireHeapSpaceInGigabytes(8)
    val input = readInputLines("/day15/input.txt")
    println(BeaconExclusionZone(input).countHowManySpacesCannotContainABeaconWhenYIs(2000000)) // 6425133
    println(BeaconExclusionZone(input).findTuningFrequencyOfDistressBeacon(4000000)) // 10996191429555
}
