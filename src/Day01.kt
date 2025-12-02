fun main() {
    fun part1(input: List<String>): Int =
        input.fold(Pair<Int, Int>(50, 0)) { acc, line ->
            require(line.length >= 2) { "Line should have at least 2 characters" }

            val direction = line[0]
            val degree = line.substring(1, line.length).toInt()

            val position: Int = (when(direction) {
                'R' -> degree
                'L' -> (100 - degree) // all lefts are just right by 100 - left
                else -> throw IllegalArgumentException("Invalid line: $line")
            } + acc.first) % 100

            Pair(position, acc.second + if (position == 0) 1 else 0)
        }.second

    fun part2(input: List<String>): Int =
        input.fold(Pair<Int, Int>(50, 0)) { acc, line ->
            require(line.length >= 2) { "Line should have at least 2 characters" }

            val degree = line.substring(1, line.length).toInt()
            val direction = when (line[0]) {
                'R' -> 1
                'L' -> -1
                else -> throw IllegalArgumentException("Invalid line: $line")
            }

            generateSequence(acc) { it
                ((it.first + direction + 100) % 100).let { position: Int ->
                    Pair(
                        position,
                        it.second + if (position == 0) 1 else 0
                    )
                }
            }.take(degree + 1)
             .last()
        }.second

    // Test if implementation meets criteria from the description, like:
    check(part1(listOf("R50")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 3)
    check(part2(testInput) == 6)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
