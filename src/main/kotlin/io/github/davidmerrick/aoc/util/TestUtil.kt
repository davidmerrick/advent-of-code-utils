package io.github.davidmerrick.aoc.util

import io.github.davidmerrick.aoc.collections.filterNotEmpty
import java.io.BufferedReader
import kotlin.reflect.KClass

object TestUtil {

    /**
     * Returns a list of lines from a file
     * By default, filters out empty lines
     */
    fun readLines(thisClass: KClass<out Any>, fileName: String, includeEmpty: Boolean = false): List<String> {
        val lines = bufferedReader(thisClass, fileName).readLines()
        return if (includeEmpty) lines else lines.filterNotEmpty()
    }

    fun lineSequence(thisClass: KClass<out Any>, fileName: String): Sequence<String> {
        return bufferedReader(thisClass, fileName)
            .lineSequence()
    }

    /**
     * Returns a String of the file's contents
     * Trimmed, by default
     */
    fun readText(thisClass: KClass<out Any>, fileName: String, trim: Boolean = true): String {
        return bufferedReader(thisClass, fileName)
            .readText()
            .let { if (trim) it.trim() else it }
    }

    /**
     * Parses input that's line-separated
     */
    fun <T : Any> parseInput(
        thisClass: KClass<out Any>,
        fileName: String,
        mapper: (String) -> T
    ) = readLines(thisClass, fileName).map(mapper)

    private fun bufferedReader(thisClass: KClass<out Any>, fileName: String): BufferedReader {
        return thisClass.java.getResourceAsStream(fileName)
            .bufferedReader()
    }
}
