package io.github.davidmerrick.aoc.coordinates

data class Pos3d(val x: Int, val y: Int, val z: Int) {


    companion object {
        /**
         * Parses a Pos3d from a comma-separated string
         * i.e. "1,1,3"
         */
        fun of(str: String) = str.split(",")
            .map { it.trim() }
            .map { it.toInt() }
            .let { Pos3d(it.first(), it[1], it.last()) }
    }
}
