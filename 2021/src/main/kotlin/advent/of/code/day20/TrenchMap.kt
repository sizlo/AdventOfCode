package advent.of.code.day20

import advent.of.code.utils.*

class TrenchMap {

    enum class PixelBit(val char: Char, val bit: Int) {
        LIGHT('#', 1), DARK('.', 0);

        companion object {
            fun fromChar(char: Char) = values().first { it.char == char }
            fun fromBit(bit: Int) = values().first { it.bit == bit }
        }
    }

    class Pixel(coordinate: Coordinate, image: Image, val pixelValue: Int): GridItem<Pixel>(coordinate, image) {
        fun isLit() = pixelValue == PixelBit.LIGHT.bit
    }

    class Image(input: IntGrid, private val infiniteBorderPixel: PixelBit = PixelBit.DARK): Grid<Pixel>(input) {
        init {
            populate { coordinate, pixelValue -> Pixel(coordinate, this, pixelValue) }
        }

        fun countLitPixels(): Int {
            return items.count { it.isLit() }
        }

        fun enhance(algorithm: List<Int>): Image {
            val enhancedImageInput = (-1 .. height).map { y ->
                (-1 .. width).map { x ->
                    enhancePixelAt(Coordinate(x, y), algorithm)
                }
            }

            val newInfiniteBorderPixel = when (infiniteBorderPixel) {
                PixelBit.DARK -> PixelBit.fromBit(algorithm.first())
                PixelBit.LIGHT -> PixelBit.fromBit(algorithm.last())
            }

            return Image(enhancedImageInput, newInfiniteBorderPixel)
        }

        private fun enhancePixelAt(coordinate: Coordinate, algorithm: List<Int>): Int {
            val binaryIndexFromWindow = coordinate
                .getNeighbourCoordinates(includeDiagonals = true)
                .let { it + coordinate }
                .sortedBy { it.y * 3 + it.x }
                .map { getItemAt(it)?.pixelValue ?: infiniteBorderPixel.bit }
                .joinToString("")
            val indexFromWindow = binaryIndexFromWindow.toInt(2)

            return algorithm[indexFromWindow]
        }
    }

    fun countLitPixelsAfterNIterations(input: List<String>, iterations: Int): Int {
        val algorithm = input[0].map { PixelBit.fromChar(it).bit }
        val imageInput = input.subList(2, input.size).toIntGrid { PixelBit.fromChar(it).bit }
        val image = Image(imageInput)

        val enhancedImage = (0 until iterations)
            .foldIndexed(image) { index, enhancedImage, _ ->
                enhancedImage.enhance(algorithm)
            }

        return enhancedImage.countLitPixels()
    }
}

fun main() {
    val input = readInputLines("/day20/input.txt")
    println(TrenchMap().countLitPixelsAfterNIterations(input, iterations = 2)) // 5432
    println(TrenchMap().countLitPixelsAfterNIterations(input, iterations = 50)) // 16016
}