import java.lang.Integer.max
import java.lang.Integer.min
import kotlin.math.pow

fun main() {
  fun String.findLargestDigitIndex(
      minIndex: Int,
      maxIndex: Int,
  ): Pair<Int, Int> {
    require(minIndex in 0..min(maxIndex, this.length))
    require(maxIndex in max(0, minIndex)..this.length)

    return (maxIndex downTo minIndex)
        .fold(Pair(maxIndex, 0)) { (lastIndex, maxDigit), i ->
          this[i].digitToInt().let { digit ->
            if (digit >= maxDigit) {
              Pair(i, digit)
            } else {
              Pair(lastIndex, maxDigit)
            }
          }
        }
  }

  fun String.findLargestJoltage(digitRange: IntProgression): Long =
      digitRange
          .fold(Pair(0, 0L)) { (maxIndex, joltage), nDigit ->
            val (nextIndex, jDigit) = this.findLargestDigitIndex(maxIndex, this.length - nDigit)

            Pair(
                nextIndex + 1,
                joltage + (jDigit * 10.0.pow(nDigit - 1).toLong()),
            )
          }.second

  fun List<String>.findLargestJoltage(nBatteries: Int): Long =
      sumOf {
        it.findLargestJoltage(nBatteries downTo 1)
      }

  fun part1(input: List<String>): Long = input.findLargestJoltage(nBatteries = 2)

  fun part2(input: List<String>): Long = input.findLargestJoltage(nBatteries = 12)

  // Test if implementation meets criteria from the description, like:
  check(part1(listOf("987654321111111")) == 98L)
  check(part2(listOf("987654321111111")) == 987654321111L)
  check(part1(listOf("811111111111119")) == 89L)
  check(part2(listOf("811111111111119")) == 811111111119L)
  check(part1(listOf("234234234234278")) == 78L)
  check(part2(listOf("234234234234278")) == 434234234278L)
  check(part1(listOf("818181911112111")) == 92L)
  check(part2(listOf("818181911112111")) == 888911112111L)

  // Or read a large test input from the `src/Day03_test.txt` file:
  val testInput = readInput("Day03_test")
  check(part1(testInput) == 357L)
  check(part2(testInput) == 3121910778619L)

  // Read the input from the `src/Day03.txt` file.
  val input = readInput("Day03")
  println("Day03: Part 1")
  val pt1 = part1(input)
  check(pt1 == 17095L)
  pt1.println()
  println("Day03: Part 2")
  val pt2 = part2(input)
  check(pt2 == 168794698570517L)
  pt2.println()
}