fun main() {
  fun List<String>.parseInput(): Array<Array<Char>> = map { it.toCharArray().toTypedArray() }.toTypedArray()

  fun Array<Array<Char>>.isIndexOutOfBounds(
      x: Int,
      y: Int,
  ): Boolean = (x < 0 || y < 0) || (y >= this.size || x >= this[y].size)

  fun Array<Array<Char>>.getNumberOfNeighborRolls(
      xIndex: Int,
      yIndex: Int,
  ): Int =
      (xIndex - 1..xIndex + 1)
          .sumOf { x ->
            (yIndex - 1..yIndex + 1).sumOf { y ->
              if (isIndexOutOfBounds(x, y) || (x == xIndex && y == yIndex)) {
                0
              } else {
                if (this[y][x] == '@') {
                  1
                } else {
                  0
                }
              }
            }
          }

  fun List<List<Char>>.toTypedArray(): Array<Array<Char>> = this.map { it.toTypedArray() }.toTypedArray()

  fun Array<Array<Char>>.removeRolls(): Pair<Array<Array<Char>>, Int> {
    val passed =
        this
            .mapIndexed { y, line ->
              line
                  .mapIndexed { x, c ->
                    if ((c == '@') && (this.getNumberOfNeighborRolls(x, y) < 4)) {
                      'x'
                    } else {
                      c
                    }
                  }
            }

    return Pair(
        passed.map { line -> line.map { c -> if (c == 'x') '.' else c } }.toTypedArray(),
        passed.sumOf { line -> line.sumOf { c -> if (c == 'x') 1 else 0 } },
    )
  }

  fun part1(input: List<String>): Int =
      input
          .parseInput()
          .removeRolls()
          .second

  fun part2(input: List<String>): Int =
      generateSequence(
          Pair<Array<Array<Char>>, Int?>(
              input.parseInput(),
              null,
          ),
      ) { (i, _) ->
        i.removeRolls()
      }.takeWhile { (_, nRemoved) -> nRemoved == null || nRemoved > 0 }
          .sumOf { (_, nRemoved) -> nRemoved ?: 0 }

  println("Sanity test")
  check(
      part1(
          listOf(
              "....",
              "....",
              "....",
              "....",
          ),
      ) == 0,
  )

  val testInput = readInput("Day04_test")
  println("Day 4 Test, Part 1")
  check(part1(testInput) == 13)
  println("Day 4 Test, Part 2")
  check(part2(testInput) == 43)

  val input = readInput("Day04")
  println("Day 4, Part 1")
  val result1 = part1(input)
  check(result1 == 1549)
  result1.println()

  println("Day 4, Part 2")
  val result2 = part2(input)
  check(result2 == 8887)
  result2.println()
}