package io.github.davidmerrick.aoc.search

import io.github.davidmerrick.aoc.guava.Step

abstract class Bfs<V : Any> {
    abstract fun getAdjacent(input: V): List<V>

    /**
     * Computes shortest path between two nodes.
     * If no path found, returns INT_MAX.
     */
    fun shortestPath(source: V, destination: V): Int {
        val visited = mutableSetOf<V>()

        val start = Step(source, 0)
        val queue = ArrayDeque<Step<V>>().apply { add(start) }
        visited.add(source)

        while (queue.isNotEmpty()) {
            val step = queue.removeFirst()

            if (step.pos == destination) {
                return step.distance
            } else {
                this.getAdjacent(step.pos)
                    .filterNot { it in visited }
                    .map { step.toward(it) }
                    .forEach {
                        queue.addLast(it)
                        visited.add(it.pos)
                    }
            }
        }

        return Int.MAX_VALUE
    }
}
