package io.github.davidmerrick.aoc.util

fun <K, V> Map<K, out V>.invert() = this.map { it.value to it.key }.toMap()
