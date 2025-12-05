fun main() {
  fun List<String>.parseInput(): Pair<List<LongRange>, List<Long>> {
    fun String.toLongRange(): LongRange = this.split("-").let { (start, end) -> LongRange(start.toLong(), end.toLong()) }

    data class IngredientsDb(
        val freshRanges: List<LongRange> = emptyList(),
        val ingredients: List<Long>? = null,
    )

    return this
        .fold(IngredientsDb()) { acc, line ->
          if (line.isBlank()) {
            acc
          } else if (acc.ingredients == null) {
            acc.copy(
                freshRanges = acc.freshRanges + listOf(line.toLongRange()),
            )
          } else {
            acc.copy(
                ingredients = acc.ingredients + line.toLong(),
            )
          }
        }.let {
          Pair(
              it.freshRanges,
              requireNotNull(it.ingredients),
          )
        }
  }

  fun part1(input: List<String>): Long {
    val (freshIngredients, unknownIngredients) = input.parseInput()

    return unknownIngredients.sumOf { ingredient ->
      if (freshIngredients.any { it.contains(ingredient) }) 1L else 0L
    }
  }

  fun part2(input: List<String>): Long {
    val (freshIngredients, _) = input.parseInput()

    val result =
        freshIngredients
            .sortedBy { it.first }
            .fold(Pair(0L, 0L)) { (highWaterIngredientId, freshIngredientCount), range ->
              // go through the sorted list of fresh ingredient ranges and add their element
              // count to the total count, using a high water variable to keep track of the
              // highest known fresh ingredient id.  If the high water variable is in the
              // middle of a range, we only need to count elements form the high water to
              // the end of the range.
              if (highWaterIngredientId > range.last) {
                highWaterIngredientId to freshIngredientCount
              } else if (highWaterIngredientId < range.first) {
                Pair(
                    range.last + 1,
                    freshIngredientCount + (range.last - range.first + 1),
                )
              } else {
                Pair(
                    range.last + 1,
                    freshIngredientCount + (range.last - highWaterIngredientId + 1),
                )
              }
            }

    return result.second
  }

  // simple validation check
  println("Sanity test")
  check(part1(listOf("3-5", "", "1", "5")) == 1L)
  check(part2(listOf("3-5", "", "1", "5")) == 3L)

  val testInput = readInput("Day05_test")
  println("Day 5 Test, Part 1")
  check(part1(testInput) == 3L)
  println("Day 5 Test, Part 2")
  check(part2(testInput) == 14L)

  val input = readInput("Day05")
  println("Day 5, Part 1")
  val result1 = part1(input)
  check(result1 == 563L)
  result1.println()

  println("Day 5, Part 2")
  val result2 = part2(input)
  check(result2 == 338693411431456L)
  result2.println()
}
