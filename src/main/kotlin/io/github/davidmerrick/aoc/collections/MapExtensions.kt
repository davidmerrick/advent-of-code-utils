package io.github.davidmerrick.aoc.collections

fun <K, V> Map<K, out V>.invert() = this.map { it.value to it.key }.toMap()

/**
 * For setting values in maps that contain other maps
 */
operator fun <A, B, C> Map<A, MutableMap<B, C>>.set(key1: A, key2: B, value: C) {
    getValue(key1)[key2] = value
}
