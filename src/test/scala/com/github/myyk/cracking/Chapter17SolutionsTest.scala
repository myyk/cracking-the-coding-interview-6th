package com.github.myyk.cracking

import com.github.myyk.cracking.Chapter17Solutions._

import java.lang.{Integer => JInt}
import scala.jdk.CollectionConverters._
import scala.util.Random

import org.scalatest._
import flatspec._
import matchers._

class Chapter17SolutionsTest extends AnyFlatSpec with should.Matchers {
  "addWithoutPlus" should "add two integers" in {
    def testAddWithoutPlus(a: Int, b: Int): Unit = {
      addWithoutPlus(a, b) shouldBe (a + b)
      addWithoutPlus(b, a) shouldBe (a + b)
    }

    testAddWithoutPlus(0, 0)
    testAddWithoutPlus(1, 0)
    testAddWithoutPlus(-1, 0)
    testAddWithoutPlus(-1, -1)
    testAddWithoutPlus(-1, 1)
    testAddWithoutPlus(1, 1)

    for (_ <- 1 to 100) {
      testAddWithoutPlus(Random.nextInt(), Random.nextInt())
    }
  }

  "shuffle" should "shuffle a deck with equal probabilities" in {
    val deck: Array[Integer] = (0 to 51).map(Integer.valueOf).toArray
    shuffle(deck)
//    println(deck.mkString(","))
  }

  "randomSet" should "create a random set from an array of values" in {
    randomSet((0 to 51).toArray, 10) should have size 10
  }

  "missingNumber" should "find the missing number in the array" in {
    missingNumber(Array.emptyIntArray) shouldBe 0
    missingNumber(Array(0)) shouldBe 1
    missingNumber(Array(1)) shouldBe 0
    for (i <- 0 to 3) {
      missingNumber((0 to 3).filter(_ != i).toArray) shouldBe i
    }
    for (i <- 0 to 4) {
      missingNumber((0 to 4).filter(_ != i).toArray) shouldBe i
    }

    for (i <- 12 to 100) {
      missingNumber((0 to i).filter(_ != 10).toArray) shouldBe 10
    }
  }

  "findLongestSubArrayWithEqualLettersAndNumbers" should "find longest subarray with equal letters and numbers" in {
    findLongestSubArrayWithEqualLettersAndNumbers(
      Array.emptyCharArray
    ) shouldBe Array.emptyCharArray
    findLongestSubArrayWithEqualLettersAndNumbers(
      Array('a')
    ) shouldBe Array.emptyCharArray
    findLongestSubArrayWithEqualLettersAndNumbers(
      Array('1')
    ) shouldBe Array.emptyCharArray
    findLongestSubArrayWithEqualLettersAndNumbers(
      Array('1', 'a')
    ) shouldBe Array('1', 'a')
    Set(Array('a', '1'), Array('1', 'b')) should contain(
      findLongestSubArrayWithEqualLettersAndNumbers(Array('a', '1', 'b', 'c'))
    )
    Set(Array('1', 'a'), Array('2', '3')) should contain(
      findLongestSubArrayWithEqualLettersAndNumbers(Array('1', 'a', '2', '3'))
    )
    Set(
      Array('z', '1', 'a', '2', '3', 'b'),
      Array('1', 'a', '2', '3', 'b', 'c')
    ) should contain(
      findLongestSubArrayWithEqualLettersAndNumbers(
        Array('z', '1', 'a', '2', '3', 'b', 'c', 'z')
      )
    )
  }

  def countsOfTwoBruteForce(n: Int): Int = {
    if (n <= 0) {
      0
    } else {
      (0 to n).map(_.toString).view.flatten.count(_ == '2')
    }
  }

  def testCountsOfTwo(n: Int): Unit = {
    countsOfTwo(n) shouldBe countsOfTwoBruteForce(n)
  }

  "countsOfTwo" should "find the number of 2s in all the numbers 0 to n" in {
    countsOfTwoBruteForce(25) shouldBe 9
    testCountsOfTwo(25)
    testCountsOfTwo(125)
    testCountsOfTwo(325)
    testCountsOfTwo(1125)
    testCountsOfTwo(12314)
    testCountsOfTwo(22314)
    testCountsOfTwo(52314)
  }

  def babyNameFrequencyReductionScala(
      freqs: Map[String, Int],
      syn: List[(String, String)]
  ): Map[String, Int] = {
    babyNameFrequencyReduction(
      freqs.map { case (name, freq) => name -> JInt.valueOf(freq) }.asJava,
      syn.asJava
    ).asScala.map { case (name, freq) => name -> Int.unbox(freq) }.toMap
  }

  "babyNameFrequencyReduction" should "reduce baby name frequencies to one of the synonyms to the total of the synonym frequencies" in {
    babyNameFrequencyReductionScala(
      Map(
        "Myyk" -> 1,
        "John" -> 15,
        "Jon" -> 12,
        "Chris" -> 13,
        "Kris" -> 4,
        "Christopher" -> 19
      ),
      List(
        "Jon" -> "John",
        "John" -> "Johnny",
        "Chris" -> "Kris",
        "Chris" -> "Christopher",
        "Poop" -> "Poo"
      )
    ) shouldBe Map("John" -> 27, "Chris" -> 36, "Myyk" -> 1)
  }

  "circusTowerHeight" should "compute the largest human tower height that can be constructed" in {
    circusTowerHeight(
      Set(
        new Person(65, 100),
        new Person(70, 150),
        new Person(56, 90),
        new Person(75, 190),
        new Person(60, 95),
        new Person(68, 110)
      ).asJava
    ) shouldBe 6
  }

  def testKthNumber(k: Int, expected: Int): Unit = {
    kthNumber(k) shouldBe expected
    kthNumber2(k) shouldBe expected
  }

  "kthNumber" should "be the Kth number such that it's only prime factors are 3, 5, and 7" in {
    for (
      (expected, kMinus1) <- Seq(1, 3, 5, 7, 9, 15, 21, 25, 27, 35, 45,
        49).zipWithIndex
    ) {
      testKthNumber(kMinus1 + 1, expected)
    }
    testKthNumber(100, 33075)
  }

  "findMajority" should "find the majority element in the array if it exists" in {
    findMajority(Array(1)) shouldBe 1
    findMajority(Array(1, 2)) shouldBe -1
    findMajority(Array(1, 2, 1)) shouldBe 1
    findMajority(Array(1, 2, 5, 9, 5, 9, 5, 5, 5, 2)) shouldBe -1
    findMajority(Array(1, 2, 5, 9, 5, 9, 5, 5, 5)) shouldBe 5
    findMajority(Array(5, 9, 5, 9, 5, 5, 5, 1, 2)) shouldBe 5
    findMajority(Array(5, 9, 2, 9, 5, 5, 5, 1, 5)) shouldBe 5
  }

  "wordDistance" should "find the minimum word distance between two words" in {}
}
