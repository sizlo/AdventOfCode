package advent.of.code.utils

fun requireHeapSpaceInGigabytes(gigabytes: Long) {
    val bytes = gigabytes * 1024 * 1024 * 1024

    if (Runtime.getRuntime().maxMemory() < bytes) {
        throw RuntimeException(
            "This operation is Not Good™ and requires at least ${gigabytes}GB of heap space\n" +
                "Please supply the JVM arguments: -Xmx${gigabytes}g"
        )
    }
}
