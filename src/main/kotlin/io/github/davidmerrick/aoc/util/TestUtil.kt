package io.github.davidmerrick.aoc.util

import java.io.BufferedReader
import kotlin.reflect.KClass

object TestUtil {

    fun readLines(thisClass: KClass<out Any>, fileName: String): List<String> {
        return bufferedReader(thisClass, fileName).readLines()
    }

    fun lineSequence(thisClass: KClass<out Any>, fileName: String): Sequence<String> {
        return bufferedReader(thisClass, fileName).lineSequence()
    }

    fun readText(thisClass: KClass<out Any>, fileName: String): String {
        return bufferedReader(thisClass, fileName).readText()
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
