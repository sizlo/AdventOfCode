package advent.of.code.day23

import advent.of.code.utils.Coordinate
import advent.of.code.utils.Part
import advent.of.code.utils.readInputLines
import advent.of.code.utils.requireHeapSpaceInGigabytes
import kotlin.system.measureTimeMillis

typealias Spaces = Map<Coordinate, AmphipodSorting.Amphipod?>
private fun Spaces.isEmptyAt(coordinate: Coordinate) = this.contains(coordinate) && this[coordinate] == null

private const val HALLWAY = 1
private val STOPPABLE_X_VALUES = listOf(1, 2, 4, 6, 8, 10, 11)

class AmphipodSorting(private val part: Part) {

    class MutableInt(var value: Int)
    
    data class State(val spaces: Spaces, val energySoFar: Int)

    enum class Amphipod(val moveEnergy: Int, val destinationX: Int) {
        AMBER(1, 3),
        BRONZE(10, 5),
        COPPER(100, 7),
        DESERT(1000, 9);
        companion object {
            fun fromChar(char: Char) = values().firstOrNull { it.name[0] == char }
        }
    }

    fun findLeastAmountOfEnergyToSort(input: List<String>): Int {
        requireHeapSpaceInGigabytes(8)

        val realInput = if (part == Part.PART_1) {
            input
        } else {
            input.slice(0 .. 2) + listOf("  #D#C#B#A#", "  #D#B#A#C#") + input.slice(3 until input.size)
        }

        val spaces = parseInput(realInput)
        return tryAllPossibleMoves(spaces).value
    }

    private fun parseInput(input: List<String>): Spaces {
        val coordinatesAndChars = input.flatMapIndexed { y, _ ->
            input[y].mapIndexed { x, char ->
                Pair(Coordinate(x, y), char)
            }
        }

        return coordinatesAndChars
            .filter { listOf('.', 'A', 'B', 'C', 'D').contains(it.second) }
            .associate { (coordinate, char) -> coordinate to Amphipod.fromChar(char) }
    }

    private fun tryAllPossibleMoves(
        currentSpaces: Spaces,
        energySoFar: Int = 0,
        bestEnergyToComplete: MutableInt = MutableInt(Int.MAX_VALUE),
        alreadyProcessedStates: MutableSet<State> = mutableSetOf()
    ): MutableInt {
        if (energySoFar > bestEnergyToComplete.value) return bestEnergyToComplete

        val state = State(currentSpaces, energySoFar)
        if (alreadyProcessedStates.contains(state)) return bestEnergyToComplete
        alreadyProcessedStates.add(state)

        if (Amphipod.values().all { amphipodsOfThisTypeAreSorted(currentSpaces, it) }) {
            bestEnergyToComplete.value = energySoFar
            return bestEnergyToComplete
        }

        currentSpaces
            .filter { it.value != null }
            .forEach { entry ->
                tryAllPossibleMovesForOneAmphipod(currentSpaces, energySoFar, bestEnergyToComplete, alreadyProcessedStates, entry.key)
            }
        return bestEnergyToComplete
    }

    private fun tryAllPossibleMovesForOneAmphipod (
        currentSpaces: Spaces,
        energySoFar: Int,
        bestEnergyToComplete: MutableInt,
        alreadyProcessedStates: MutableSet<State>,
        coordinate: Coordinate,
    ) {
        val amphipod = currentSpaces[coordinate]!!

        if (amphipodsOfThisTypeAreSorted(currentSpaces, amphipod)) return

        if (coordinate.y == HALLWAY) {
            moveIntoRoom(currentSpaces, coordinate, energySoFar, bestEnergyToComplete, alreadyProcessedStates)
            return
        }

        if (coordinate.x != amphipod.destinationX) {
            moveIntoHallway(currentSpaces, coordinate, energySoFar, bestEnergyToComplete, alreadyProcessedStates)
            return
        }

        if (amphipodBelowNeedsToGetOut(currentSpaces, coordinate)) {
            moveIntoHallway(currentSpaces, coordinate, energySoFar, bestEnergyToComplete, alreadyProcessedStates)
        }
    }

    private fun amphipodsOfThisTypeAreSorted(currentSpaces: Spaces, amphipod: Amphipod): Boolean {
        return getRoomCoordinatesForAmphipod(amphipod).all { currentSpaces[it] == amphipod }
    }

    private fun getRoomCoordinatesForAmphipod(amphipod: Amphipod): List<Coordinate> {
        val roomYs = if (part == Part.PART_1) listOf(2, 3) else listOf(2, 3, 4, 5)
        return roomYs
            .map { Coordinate(amphipod.destinationX, it) }
    }

    private fun amphipodBelowNeedsToGetOut(currentSpaces: Spaces, coordinate: Coordinate): Boolean {
        val thisAmphipod = currentSpaces[coordinate]!!
        return getRoomCoordinatesForAmphipod(thisAmphipod)
            .filter { it.y > coordinate.y }
            .mapNotNull { currentSpaces[it] }
            .any { it != thisAmphipod}
    }

    private fun moveIntoRoom(
        currentSpaces: Spaces,
        coordinate: Coordinate,
        energySoFar: Int,
        bestEnergyToComplete: MutableInt,
        alreadyProcessedStates: MutableSet<State>
    ) {
        val amphipod = currentSpaces[coordinate]!!

        val cannotEnterRoom = getRoomCoordinatesForAmphipod(amphipod)
            .mapNotNull { currentSpaces[it] }
            .any { it != amphipod }

        if (cannotEnterRoom) return

        var energyUsed = 0
        var movingCoordinate = coordinate

        val getRight: (Coordinate) -> Coordinate = { it.right() }
        val getLeft: (Coordinate) -> Coordinate = { it.left() }
        val nextSpace = if (amphipod.destinationX > coordinate.x) getRight else getLeft
        while(movingCoordinate.x != amphipod.destinationX && currentSpaces.isEmptyAt(nextSpace(movingCoordinate))) {
            movingCoordinate = nextSpace(movingCoordinate)
            energyUsed += amphipod.moveEnergy
        }
        if (movingCoordinate.x != amphipod.destinationX) return // We got blocked in the hallway

        while(currentSpaces.isEmptyAt(movingCoordinate.below())) {
            movingCoordinate = movingCoordinate.below()
            energyUsed += amphipod.moveEnergy
        }

        move(currentSpaces, coordinate, movingCoordinate, energySoFar + energyUsed, bestEnergyToComplete, alreadyProcessedStates)
    }

    private fun moveIntoHallway(
        currentSpaces: Spaces,
        coordinate: Coordinate,
        energySoFar: Int,
        bestEnergyToComplete: MutableInt,
        alreadyProcessedStates: MutableSet<State>
    ) {
        val amphipod = currentSpaces[coordinate]!!
        var energyUsedMovingUp = 0

        var movingUpCoordinate = coordinate
        while (movingUpCoordinate.y != HALLWAY && currentSpaces.isEmptyAt(movingUpCoordinate.above())) {
            movingUpCoordinate = movingUpCoordinate.above()
            energyUsedMovingUp += amphipod.moveEnergy
        }
        if (movingUpCoordinate.y != HALLWAY) return // We got blocked trying to move up

        var energyUsedMovingLeft = energyUsedMovingUp
        var movingLeftCoordinate = movingUpCoordinate
        while(currentSpaces.isEmptyAt(movingLeftCoordinate.left())) {
            movingLeftCoordinate = movingLeftCoordinate.left()
            energyUsedMovingLeft += amphipod.moveEnergy
            if (STOPPABLE_X_VALUES.contains(movingLeftCoordinate.x)) {
                move(currentSpaces, coordinate, movingLeftCoordinate, energySoFar + energyUsedMovingLeft, bestEnergyToComplete, alreadyProcessedStates)
            }
        }

        var energyUsedMovingRight = energyUsedMovingUp
        var movingRightCoordinate = movingUpCoordinate
        while(currentSpaces.isEmptyAt(movingRightCoordinate.right())) {
            movingRightCoordinate = movingRightCoordinate.right()
            energyUsedMovingRight += amphipod.moveEnergy
            if (STOPPABLE_X_VALUES.contains(movingRightCoordinate.x)) {
                move(currentSpaces, coordinate, movingRightCoordinate, energySoFar + energyUsedMovingRight, bestEnergyToComplete, alreadyProcessedStates)
            }
        }
    }

    private fun move(
        currentSpaces: Spaces,
        moveFrom: Coordinate,
        moveTo: Coordinate,
        energySoFar: Int,
        bestEnergyToComplete: MutableInt,
        alreadyProcessedStates: MutableSet<State>
    ) {
        val newSpaces = currentSpaces.toMutableMap()
        newSpaces[moveTo] = newSpaces[moveFrom]
        newSpaces[moveFrom] = null
        tryAllPossibleMoves(newSpaces, energySoFar, bestEnergyToComplete, alreadyProcessedStates)
    }
}

fun main() {
    val input = readInputLines("/day23/input.txt")

    val timeForPart1 = measureTimeMillis {
        val part1 = AmphipodSorting(Part.PART_1).findLeastAmountOfEnergyToSort(input)
        println("Part 1: $part1") // 16506
    }
    println("Time taken for part 1: ${timeForPart1}ms") // 2561ms =~ 3 seconds

    val timeForPart2 = measureTimeMillis {
        val part2 = AmphipodSorting(Part.PART_2).findLeastAmountOfEnergyToSort(input)
        println("Part 2: $part2") // 48304
    }
    println("Time taken for part 2: ${timeForPart2}ms") // 1218ms =~ 1 second
}