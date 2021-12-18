package advent.of.code.day18

import advent.of.code.utils.isAllDigits
import advent.of.code.utils.readInputLines

private fun String.consumeUntilBracketsBalance(): String {
    var bracketCount = 0
    var position = 0
    do {
        if (this[position] == '[') bracketCount++
        if (this[position] == ']') bracketCount--
        position++
    } while (bracketCount != 0 || this[position].isDigit())
    return this.substring(0 until position)
}

class Snailfish {

    class SnailfishNumber(input: String, val parent: SnailfishNumber? = null) {

        var value: Int?
        var lhs: SnailfishNumber?
        var rhs: SnailfishNumber?

        init {
            if (input.isAllDigits()) {
                value = input.toInt()
                lhs = null
                rhs = null
            } else {
                value = null
                val withoutOtherBrackets = input.substring(1 until input.length - 1)
                val lhsInput = withoutOtherBrackets.consumeUntilBracketsBalance()
                val rhsInput = withoutOtherBrackets.substring(lhsInput.length + 1)
                lhs = SnailfishNumber(lhsInput, this)
                rhs = SnailfishNumber(rhsInput, this)
            }
        }

        override fun toString() = value?.toString() ?: "[$lhs,$rhs]"
        operator fun plus(other: SnailfishNumber) = SnailfishNumber("[$this,$other]").also { it.reduce() }
        fun magnitude(): Int = value ?: (3 * lhs!!.magnitude() + 2 * rhs!!.magnitude())

        fun reduce() {
            do {
                val didChange = explode() || split()
            } while(didChange)
        }

        private fun explode(depth: Int = 0): Boolean {
            if (value != null) {
                return false
            }

            if (depth == 4) {
                incrementFirstNumberToTheLeftOfThisBy(lhs!!.magnitude())
                incrementFirstNumberToTheRightOfThisBy(rhs!!.magnitude())
                replaceSelf("0")
                return true
            }

            return lhs!!.explode(depth + 1) || rhs!!.explode(depth + 1)
        }

        private fun split(): Boolean {
            if (value != null) {

                if (value!! > 9) {
                    val lhs = value!! / 2
                    val rhs = value!! - lhs
                    replaceSelf("[$lhs,$rhs]")
                    return true
                }

                return false
            }

            return lhs!!.split() || rhs!!.split()
        }

        private fun incrementFirstNumberToTheLeftOfThisBy(increment: Int) {
            findFirstParentWithADifferentLeftBranch()
                ?.lhs
                ?.findRightMostChild()
                ?.let { it.value = it.value!! + increment }
        }

        private fun incrementFirstNumberToTheRightOfThisBy(increment: Int) {
            findFirstParentWithADifferentRightBranch()
                ?.rhs
                ?.findLeftMostChild()
                ?.let { it.value = it.value!! + increment }
        }

        private fun findFirstParentWithADifferentLeftBranch(): SnailfishNumber? {
            var childToCompare = this
            var parentToCheck = this.parent

            while(parentToCheck != null && parentToCheck.lhs == childToCompare) {
                parentToCheck = parentToCheck.parent
                childToCompare = childToCompare.parent!!
            }

            return parentToCheck
        }

        private fun findFirstParentWithADifferentRightBranch(): SnailfishNumber? {
            var childToCompare = this
            var parentToCheck = this.parent

            while(parentToCheck != null && parentToCheck.rhs == childToCompare) {
                parentToCheck = parentToCheck.parent
                childToCompare = childToCompare.parent!!
            }

            return parentToCheck
        }

        private fun findLeftMostChild(): SnailfishNumber {
            var child = this
            while (child.value == null) {
                child = child.lhs!!
            }
            return child
        }

        private fun findRightMostChild(): SnailfishNumber {
            var child = this
            while (child.value == null) {
                child = child.rhs!!
            }
            return child
        }

        private fun replaceSelf(stringRepresentation: String) {
            val replacement = SnailfishNumber(stringRepresentation, parent)
            if (parent!!.rhs == this) {
                parent.rhs = replacement
            } else {
                parent.lhs = replacement
            }
        }
    }

    fun findMagnitudeOfSumOfAllSnailfishNumbers(input: List<String>): Int {
        val numbers = input.map { SnailfishNumber(it) }
        return addAll(numbers).magnitude()
    }

    fun findLargestMagnitudePossibleWhenMultiplyingAnyTwoSnailfishNumbers(input: List<String>): Int {
        val numbers = input.map { SnailfishNumber(it) }
        var maxMagnitude = Int.MIN_VALUE

        numbers.forEachIndexed { lhsIndex, lhs ->
            numbers.forEachIndexed { rhsIndex, rhs ->
                if (lhsIndex != rhsIndex) {
                    maxMagnitude = maxOf(maxMagnitude, (lhs + rhs).magnitude())
                }
            }
        }

        return maxMagnitude
    }

    fun addAll(snailfishNumbers: List<SnailfishNumber>): SnailfishNumber {
        val initial = snailfishNumbers[0]
        val rest = snailfishNumbers.subList(1, snailfishNumbers.size)
        return rest.fold(initial) { total, next -> total + next }
    }
}

fun main() {
    val input = readInputLines("/day18/input.txt")
    println(Snailfish().findMagnitudeOfSumOfAllSnailfishNumbers(input)) // 3725
    println(Snailfish().findLargestMagnitudePossibleWhenMultiplyingAnyTwoSnailfishNumbers(input)) // 4832
}