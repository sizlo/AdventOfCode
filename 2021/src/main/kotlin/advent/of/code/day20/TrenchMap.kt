package advent.of.code.day20

import advent.of.code.utils.*

class TrenchMap {

    enum class PixelBit(val char: Char, val bit: Int) {
        LIGHT('#', 1), DARK('.', 0);

        companion object {
            fun fromChar(char: Char) = values().first { it.char == char }
        }
    }

    class Pixel(coordinate: Coordinate, image: Image, val pixelValue: Int): GridItem<Pixel>(coordinate, image) {
        fun isLit() = pixelValue == PixelBit.LIGHT.bit
    }

    class Image(input: IntGrid): Grid<Pixel>(input) {
        init {
            populate { coordinate, pixelValue -> Pixel(coordinate, this, pixelValue) }
        }

        fun padWithDarkBorder(borderSize: Int): Image {
            val bitGrid = (0 until height).map { y ->
                (0 until width).map { x ->
                    getItemAt(Coordinate(x, y))!!.pixelValue
                }
            }

            val sideBorder = List(borderSize) { PixelBit.DARK.bit }
            val withPaddedSides = bitGrid.map { row -> sideBorder + row + sideBorder }
            val topBorder = List(borderSize) { List(withPaddedSides[0].size) { PixelBit.DARK.bit } }
            return Image(topBorder + withPaddedSides + topBorder)
        }

        fun removeBorder(borderSize: Int): Image {
            val bitGrid = (borderSize until height - borderSize).map { y ->
                (borderSize until width - borderSize).map { x ->
                    getItemAt(Coordinate(x, y))!!.pixelValue
                }
            }
            return Image(bitGrid)
        }

        fun countLitPixels(): Int {
            return items.count { it.isLit() }
        }

        fun enhance(algorithm: List<Int>): Image {
            val enhancedImageInput = (0 until height).map { y ->
                (0 until width).map { x ->
                    enhancePixelAt(Coordinate(x, y), algorithm)
                }
            }

            return Image(enhancedImageInput)
        }

        private fun enhancePixelAt(coordinate: Coordinate, algorithm: List<Int>): Int {
            val binaryIndexFromWindow = coordinate
                .getNeighbourCoordinates(includeDiagonals = true)
                .let { it + coordinate }
                .sortedBy { it.y * 3 + it.x }
                .map { getItemAt(it)?.pixelValue ?: PixelBit.DARK.bit }
                .joinToString("")
            val indexFromWindow = binaryIndexFromWindow.toInt(2)

            return algorithm[indexFromWindow]
        }
    }

    fun countLitPixelsAfterNIterations(input: List<String>, iterations: Int): Int {
        val algorithm = input[0].map { PixelBit.fromChar(it).bit }
        val imageInput = input.subList(2, input.size).toIntGrid { PixelBit.fromChar(it).bit }
        val image = Image(imageInput).padWithDarkBorder(iterations * 2)

        val enhancedImage = (0 until iterations)
            .fold(image) { enhancedImage, _ -> enhancedImage.enhance(algorithm) }

        return enhancedImage.removeBorder(iterations).countLitPixels()
    }
}

fun main() {
    val input = readInputLines("/day20/input.txt")
    println(TrenchMap().countLitPixelsAfterNIterations(input, iterations = 2)) // 5432
    println(TrenchMap().countLitPixelsAfterNIterations(input, iterations = 50)) // 16016
}