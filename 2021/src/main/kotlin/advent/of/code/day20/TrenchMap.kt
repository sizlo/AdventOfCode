package advent.of.code.day20

import advent.of.code.utils.*

class TrenchMap {

    enum class Pixel(val char: Char, val bit: Int) {
        LIGHT('#', 1), DARK('.', 0);

        companion object {
            fun fromChar(char: Char) = values().first { it.char == char }
            fun fromBit(bit: Int) = values().first { it.bit == bit }
        }
    }

    class Image(input: IntGrid, private val infiniteBorderPixel: Pixel = Pixel.DARK): Grid<Pixel>(input) {
        init {
            populate { _, pixelBit -> Pixel.fromBit(pixelBit) }
        }

        fun countLitPixels(): Int {
            return items.count { it == Pixel.LIGHT }
        }

        fun enhance(algorithm: List<Pixel>): Image {
            val enhancedImageInput = (-1 .. height).map { y ->
                (-1 .. width).map { x ->
                    enhancePixelAt(Coordinate(x, y), algorithm)
                }
            }

            val newInfiniteBorderPixel = when (infiniteBorderPixel) {
                Pixel.DARK -> algorithm.first()
                Pixel.LIGHT -> algorithm.last()
            }

            return Image(enhancedImageInput, newInfiniteBorderPixel)
        }

        private fun enhancePixelAt(coordinate: Coordinate, algorithm: List<Pixel>): Int {
            val indexFromWindow = coordinate
                .getNeighbourCoordinates(includeDiagonals = true)
                .let { it + coordinate }
                .sortedBy { it.y * 3 + it.x }
                .map { getItemAt(it) ?: infiniteBorderPixel }
                .map { it.bit }
                .joinToString("")
                .toInt(2)

            return algorithm[indexFromWindow].bit
        }
    }

    fun countLitPixelsAfterNIterations(input: List<String>, iterations: Int): Int {
        val algorithm = input[0].map { Pixel.fromChar(it) }
        val imageInput = input.subList(2, input.size).toIntGrid { Pixel.fromChar(it).bit }
        val image = Image(imageInput)

        val enhancedImage = (0 until iterations)
            .fold(image) { enhancedImage, _ -> enhancedImage.enhance(algorithm) }

        return enhancedImage.countLitPixels()
    }
}

fun main() {
    val input = readInputLines("/day20/input.txt")
    println(TrenchMap().countLitPixelsAfterNIterations(input, iterations = 2)) // 5432
    println(TrenchMap().countLitPixelsAfterNIterations(input, iterations = 50)) // 16016
}