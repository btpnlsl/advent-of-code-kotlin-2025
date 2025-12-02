fun main() {
  /**
   * Find invalid IDs which repeat a sequence twice
   */
  fun findInvalidIdPt1(id: String): Boolean =
      if (id.length % 2 == 0) {
        id.splitIntoN(2).hasInvalidId()
      } else {
        false
      }

  /**
   * Find invalid IDs which repeat a sequence 2 or more times,
   * where the maximum number of times is the number length.
   */
  fun findInvalidIdPt2(id: String): Boolean =
      (2 .. id.length).any { n: Int ->
        if (id.length % n == 0) {
          id.splitIntoN(n).hasInvalidId()
        } else {
          false
        }
      }

  /**
   * Parse input strings into a list of numerical ranges
   */
  fun List<String>.parseInput(): List<LongRange> =
      this.flatMap { it.split(",") }
          .map {
            it.split("-").let { (low, high) -> low.toLong()..high.toLong() }
          }

  fun solve(
      input: List<String>,
      invalidIdFn: (String) -> Boolean
  ): Long =
      input
          .parseInput()
          .flatMap { idRange ->
            idRange.filter { id: Long ->
              invalidIdFn(id.toString())
            }.also {
              if (it.isNotEmpty()) {
                println("[$idRange] = ${it.joinToString(",")}")
              }
            }
          }
          .sum()

  fun part1(input: List<String>): Long =
      solve(input, ::findInvalidIdPt1)

  fun part2(input: List<String>): Long =
      solve(input, ::findInvalidIdPt2)

  // simple validation check
  println("Sanity test")
  check(part1(listOf("11-22")) == 33L)

  val testInput = readInput("Day02_test")
  println("Day 2 Test, Part 1")
  check(part1(testInput) == 1227775554L)
  println("Day 2 Test, Part 2")
  check(part2(testInput) == 4174379265L)

  val input = readInput("Day02")
  println("Day 2, Part 1")
  val result1 = part1(input)
  check(result1 == 38437576669L)
  result1.println()

  println("Day 2, Part 2")
  val result2 = part2(input)
  check(result2 == 49046150754L)
  result2.println()
}
