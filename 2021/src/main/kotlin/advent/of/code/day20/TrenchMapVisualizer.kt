package advent.of.code.day20

import advent.of.code.utils.Coordinate
import advent.of.code.utils.readInputLines
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import kotlin.system.measureTimeMillis

private val SCALE = 4
private val LARGEST_ENHANCED_IMAGE_WIDTH = 200
private val LARGEST_ENHANCED_IMAGE_HEIGHT = 200
private val IMAGE_BORDER = 10
private val PIXEL_WIDTH = LARGEST_ENHANCED_IMAGE_WIDTH + IMAGE_BORDER
private val PIXEL_HEIGHT = LARGEST_ENHANCED_IMAGE_HEIGHT + IMAGE_BORDER
private val CANVAS_WIDTH = (PIXEL_WIDTH * SCALE)
private val CANVAS_HEIGHT = (PIXEL_HEIGHT * SCALE)
private val UI_HEIGHT = 50
private val WINDOW_WIDTH = CANVAS_WIDTH
private val TITLE_BAR_HEIGHT = 28
private val WINDOW_HEIGHT = CANVAS_HEIGHT + UI_HEIGHT + TITLE_BAR_HEIGHT

fun TrenchMap.Pixel.colour(): Color {
    return when(this) {
        TrenchMap.Pixel.LIGHT -> Color.White
        TrenchMap.Pixel.DARK -> Color.Black
    }
}

class Enhancer(var image: TrenchMap.Image, val algorithm: List<TrenchMap.Pixel>) {

    var playing = false

    fun step() {
        image = image.enhance(algorithm)
    }

    fun play() {
        playing = true
    }

    fun pause() {
        playing = false
    }
}

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Trench Map Visualizer",
        state = rememberWindowState(width = WINDOW_WIDTH.dp, height = WINDOW_HEIGHT.dp)
    ) {
        val algorithmAndImage = TrenchMap().parseInput(readInputLines("/day20/game-of-life-input.txt"))
        val enhancer = remember { mutableStateOf(Enhancer(algorithmAndImage.second, algorithmAndImage.first)) }
        val numEnhancements = remember { mutableStateOf(0) }

        MaterialTheme {
            Column(modifier = Modifier.fillMaxSize()) {
                Column(modifier = Modifier.fillMaxWidth().height(UI_HEIGHT.dp)) {
                    Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                        Button(modifier = Modifier.padding(end = 5.dp),
                            onClick = {
                                enhancer.value.step()
                                numEnhancements.value++
                            }) {
                            Text("Step")
                        }
                        Button(modifier = Modifier.padding(end = 5.dp),
                            onClick = {
                                enhancer.value.play()
                            }) {
                            Text("Play")
                        }
                        Button(modifier = Modifier.padding(end = 5.dp),
                            onClick = {
                                enhancer.value.pause()
                            }) {
                            Text("Pause")
                        }
                        Text("Total iterations: ${numEnhancements.value}", modifier = Modifier.align(Alignment.CenterVertically))
                    }
                }

                Canvas(modifier = Modifier.fillMaxSize()) {
                    val image = enhancer.value.image

                    val topLeft = Coordinate(
                        x = (PIXEL_WIDTH - image.width) / 2,
                        y = (PIXEL_HEIGHT - image.height) / 2
                    )

                    (0 until PIXEL_HEIGHT).forEach { y ->
                        (0 until PIXEL_WIDTH).forEach { x->
                            val coordinateRelativeToImage = Coordinate(x, y) - topLeft
                            val pixel = image.getItemAt(coordinateRelativeToImage) ?: image.infiniteBorderPixel
                            drawRect(
                                color = pixel.colour(),
                                topLeft = Offset(x = (x * SCALE).toFloat(), y = (y * SCALE).toFloat()),
                                size = Size(SCALE.toFloat(), SCALE.toFloat())
                            )
                        }
                    }
                }
            }
        }

        LaunchedEffect(Unit) {
            val frameRate = 100

            while (true) {
                withFrameMillis {
                    if (enhancer.value.playing) {
                        val frameTimeMillis = measureTimeMillis {
                            enhancer.value.step()
                        }
                        numEnhancements.value++
                        if (frameTimeMillis < frameRate) {
                            Thread.sleep(frameRate - frameTimeMillis)
                        }
                    }
                }
            }
        }
    }
}