package advent.of.code.day07

import advent.of.code.utils.readInput

open class File(private val size: Long, val name: String, val parent: Directory?) {
    open fun getTotalSize() = size
    open fun getDescendants() = listOf(this)
}

class Directory(name: String, parent: Directory?): File(0, name, parent) {
    val children = mutableListOf<File>()

    fun getChild(name: String): File {
        return children.first { it.name == name }
    }

    override fun getTotalSize() = children.sumOf { it.getTotalSize() }

    override fun getDescendants(): List<File> {
        return children.flatMap { it.getDescendants() } + this
    }
}

abstract class Operation {
    companion object {
        fun fromString(commandWithOutput: String): Operation {
            val lines = commandWithOutput.split("\n").filterNot { it.isBlank() }
            val command = lines.first()
            return when {
                command.startsWith("cd") -> CdOperation(command.split(" ").last())
                command.startsWith("ls") -> LsOperation(lines.subList(1, lines.size))
                else -> throw RuntimeException("Unknown command: $command")
            }
        }
    }

    abstract fun apply(cwd: Directory): Directory
}

class CdOperation(private val toLocation: String): Operation() {
    override fun apply(cwd: Directory): Directory {
        return if (toLocation == "..") {
            cwd.parent!!
        } else {
            cwd.getChild(toLocation) as Directory
        }
    }
}

class LsOperation(private val output: List<String>): Operation() {
    override fun apply(cwd: Directory): Directory {
        output.map {
            if (it.startsWith("dir")) {
                return@map Directory(it.split(" ").last(), cwd)
            } else {
                val (sizeAsString, name) = it.split(" ")
                return@map File(sizeAsString.toLong(), name, cwd)
            }
        }.forEach { cwd.children.add(it) }

        return cwd
    }
}

class NoSpaceLeftOnDevice(input: String) {
    private val root = buildFileSystem(input)

    fun findTotalSizeOfAllDirectoriesWhichHaveAtMost100000Size(): Long {
        return root
            .getDescendants()
            .filterIsInstance<Directory>()
            .map { it.getTotalSize() }
            .filter { it <= 100000 }
            .sum()
    }

    fun findSizeOfSmallestDirectoryWhichFreesUpEnoughSpaceWhenDeleted(): Long {
        val totalSpace = 70000000
        val requiredSpace = 30000000
        val usedSpace = root.getTotalSize()
        val amountToBeDeleted = requiredSpace - (totalSpace - usedSpace)

        return root
            .getDescendants()
            .filterIsInstance<Directory>()
            .map { it.getTotalSize() }
            .filter { it >= amountToBeDeleted }
            .minOf { it }
    }

    private fun buildFileSystem(input: String): Directory {
        val root = Directory("", null)
        root.children.add(Directory("/", root))

        val operations = input
            .split("$ ")
            .filterNot { it.isBlank() }
            .map { Operation.fromString(it) }

        operations.fold(root) { cwd, operation -> operation.apply(cwd) }

        return root
    }
}

fun main() {
    val noSpaceLeftOnDevice = NoSpaceLeftOnDevice(readInput("/day07/input.txt"))
    println(noSpaceLeftOnDevice.findTotalSizeOfAllDirectoriesWhichHaveAtMost100000Size()) // 1989474
    println(noSpaceLeftOnDevice.findSizeOfSmallestDirectoryWhichFreesUpEnoughSpaceWhenDeleted()) // 1111607
}