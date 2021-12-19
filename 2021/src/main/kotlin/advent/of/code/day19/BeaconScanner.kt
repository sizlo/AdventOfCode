package advent.of.code.day19

import advent.of.code.utils.Coordinate
import advent.of.code.utils.readInputLines
import advent.of.code.utils.splitOnBlankLines

typealias CoordinateTransformation = (Coordinate) -> Coordinate

private val possibleScannerOrientationTransformations = listOf<CoordinateTransformation>(
    // Facing positive Z
    { coordinate -> Coordinate(coordinate.x, coordinate.y, coordinate.z) },
    { coordinate -> Coordinate(-coordinate.y, coordinate.x, coordinate.z) },
    { coordinate -> Coordinate(-coordinate.x, -coordinate.y, coordinate.z) },
    { coordinate -> Coordinate(coordinate.y, -coordinate.x, coordinate.z) },

    // Facing negative z
    { coordinate -> Coordinate(-coordinate.x, coordinate.y, -coordinate.z) },
    { coordinate -> Coordinate(-coordinate.y, -coordinate.x, -coordinate.z) },
    { coordinate -> Coordinate(coordinate.x, -coordinate.y, -coordinate.z) },
    { coordinate -> Coordinate(coordinate.y, coordinate.x, -coordinate.z) },

    // Facing positive x
    { coordinate -> Coordinate(-coordinate.z, coordinate.y, coordinate.x) },
    { coordinate -> Coordinate(-coordinate.y, -coordinate.z, coordinate.x) },
    { coordinate -> Coordinate(coordinate.z, -coordinate.y, coordinate.x) },
    { coordinate -> Coordinate(coordinate.y, coordinate.z, coordinate.x) },

    // Facing negative x
    { coordinate -> Coordinate(coordinate.z, coordinate.y, -coordinate.x) },
    { coordinate -> Coordinate(-coordinate.y, coordinate.z, -coordinate.x) },
    { coordinate -> Coordinate(-coordinate.z, -coordinate.y, -coordinate.x) },
    { coordinate -> Coordinate(coordinate.y, -coordinate.z, -coordinate.x) },

    // Facing positive y
    { coordinate -> Coordinate(coordinate.x, -coordinate.z, coordinate.y) },
    { coordinate -> Coordinate(coordinate.z, coordinate.x, coordinate.y) },
    { coordinate -> Coordinate(-coordinate.x, coordinate.z, coordinate.y) },
    { coordinate -> Coordinate(-coordinate.z, -coordinate.x, coordinate.y) },

    // Facing negative y
    { coordinate -> Coordinate(coordinate.x, coordinate.z, -coordinate.y) },
    { coordinate -> Coordinate(-coordinate.z, coordinate.x, -coordinate.y) },
    { coordinate -> Coordinate(-coordinate.x, -coordinate.z, -coordinate.y) },
    { coordinate -> Coordinate(coordinate.z, -coordinate.x, -coordinate.y) },
)

class BeaconScanner(scannerReport: List<String>) {

    class Scanner(scannerInput: List<String>) {
        val relativeBeaconPositions: Set<Coordinate>
        var position: Coordinate? = null
        var transformationRequiredToOrientWithFirstScanner: CoordinateTransformation? = null

        init {
            relativeBeaconPositions = scannerInput
                .subList(1, scannerInput.size)
                .map { Coordinate(it) }
                .toSet()
        }

        fun isAligned() = position != null

        fun getAbsoluteBeaconPositions(): Set<Coordinate> {
            return relativeBeaconPositions
                .map { orientCoordinateWithFirstScanner(it) }
                .map { moveToAbsolutePosition(it) }
                .toSet()
        }

        fun tryToAlignWith(other: Scanner) {
            if (isAligned()) return

            // For every possible orientation of this scanners beacons
                // For every beacon bThis in this scanner
                    // For every beacon bOther in the other scanner
                        // Translate all beacons in this scanner by the difference between bThis and bOther
                        // If there are at least 12 beacons in common
                            // Remember orientation transformation
                            // position = difference between bThis and bOther
                            // stop

            val otherScannerAbsoluteBeaconPositions = other.getAbsoluteBeaconPositions()

            possibleScannerOrientationTransformations.forEach { orientationTransformation ->
                val orientedBeaconPositions = relativeBeaconPositions.map(orientationTransformation)
                orientedBeaconPositions.forEach { positionToAlignWithOtherScanner ->
                    otherScannerAbsoluteBeaconPositions.forEach { positionFromOtherScannerToAlignTo ->
                        val difference = positionFromOtherScannerToAlignTo - positionToAlignWithOtherScanner
                        val translatedBeaconPositions = orientedBeaconPositions.map { it + difference }.toSet()
                        val numAlignedBeacons = otherScannerAbsoluteBeaconPositions.intersect(translatedBeaconPositions).size
                        if (numAlignedBeacons >= 12) {
                            transformationRequiredToOrientWithFirstScanner = orientationTransformation
                            position = difference
                            return
                        }
                    }
                }
            }
        }

        private fun orientCoordinateWithFirstScanner(coordinate: Coordinate): Coordinate {
            return transformationRequiredToOrientWithFirstScanner!!.invoke(coordinate)
        }

        private fun moveToAbsolutePosition(coordinate: Coordinate): Coordinate {
            return position!! + coordinate
        }
    }

    private val scanners = scannerReport
        .splitOnBlankLines()
        .map { Scanner(it) }

    fun findTotalNumberOfBeacons(): Int {
        return getAllAbsoluteBeaconPositions().size
    }

    fun findLargestManhattanDistanceBetweenAnyTwoScanners(): Int {
        getAllAbsoluteBeaconPositions()

        return scanners.maxOf { lhs ->
            scanners.maxOf { rhs ->
                lhs.position!!.manhattanDistance(rhs.position!!)
            }
        }
    }

    private fun getAllAbsoluteBeaconPositions(): Set<Coordinate> {
        scanners[0].position = Coordinate(0, 0, 0)
        scanners[0].transformationRequiredToOrientWithFirstScanner = { coordinate -> coordinate }

        do {
            val alignedScanners = scanners.filter { it.isAligned() }
            val unalignedScanners = scanners.filter { !it.isAligned() }

            unalignedScanners.forEach { unalignedScanner ->
                alignedScanners.forEach { alignedScanner ->
                    unalignedScanner.tryToAlignWith(alignedScanner)
                }
            }

        } while (scanners.any { !it.isAligned() })

        return scanners
            .flatMap { it.getAbsoluteBeaconPositions() }
            .toSet()
    }
}

fun main() {
    val input = readInputLines("/day19/input.txt")
    val beaconScanner = BeaconScanner(input)
    println(beaconScanner.findTotalNumberOfBeacons()) // 405
    println(beaconScanner.findLargestManhattanDistanceBetweenAnyTwoScanners()) // 12306
}