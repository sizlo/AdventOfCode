package advent.of.code.day07

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class NoSpaceLeftOnDeviceTest {

    private val exampleInput = """
        ${'$'} cd /
        ${'$'} ls
        dir a
        14848514 b.txt
        8504156 c.dat
        dir d
        ${'$'} cd a
        ${'$'} ls
        dir e
        29116 f
        2557 g
        62596 h.lst
        ${'$'} cd e
        ${'$'} ls
        584 i
        ${'$'} cd ..
        ${'$'} cd ..
        ${'$'} cd d
        ${'$'} ls
        4060174 j
        8033020 d.log
        5626152 d.ext
        7214296 k
    """.trimIndent().removeSurrounding("\n")


    @Test
    fun `test example input for part 1`() {
        val result = NoSpaceLeftOnDevice(exampleInput).findTotalSizeOfAllDirectoriesWhichHaveAtMost100000Size()
        assertThat(result).isEqualTo(95437)
    }

    @Test
    fun `test example input for part 2`() {
        val result = NoSpaceLeftOnDevice(exampleInput).findSizeOfSmallestDirectoryWhichFreesUpEnoughSpaceWhenDeleted()
        assertThat(result).isEqualTo(24933642)
    }
}