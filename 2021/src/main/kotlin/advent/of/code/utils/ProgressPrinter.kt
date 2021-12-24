package advent.of.code.utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ProgressPrinter {
    var lastPercentPrinted = Int.MIN_VALUE

    fun printProgress(message: String) {
        println("${getPrefix()} $message")
    }

    fun printPercent(message: String, progressCalculator: () -> Int) {
        val thisPercent = progressCalculator()
        if (thisPercent > lastPercentPrinted) {
            println("${getPrefix()} ${thisPercent}%    -    $message")
            lastPercentPrinted = thisPercent
        }
    }

    private fun getPrefix() = "${LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME)} Progress:"
}