package com.github.myyk.cracking

import java.lang.{ Integer => JInt }

import scala.collection.JavaConversions._
import scala.collection.JavaConversions.seqAsJavaList
import scala.collection.JavaConversions.setAsJavaSet
import scala.collection.JavaConverters._
import scala.math.BigInt.javaBigInteger2bigInt
import scala.util.Random

import org.scalatest.FlatSpec
import org.scalatest.Matchers
import com.github.myyk.cracking.Chapter16Solutions.MutableInteger
import com.github.myyk.cracking.Chapter16Solutions.WordFrequencies
import com.github.myyk.cracking.Chapter16Solutions.isWonTicTacToe
import com.github.myyk.cracking.Chapter16Solutions.TicTacToe
import com.github.myyk.cracking.Chapter16Solutions.factorialZeroes
import com.github.myyk.cracking.Chapter16Solutions.smallestDifference
import com.github.myyk.cracking.Chapter16Solutions.numberMax
import com.github.myyk.cracking.Chapter16Solutions.EnglishIntMaker
import com.github.myyk.cracking.Chapter16Solutions.subtract
import com.github.myyk.cracking.Chapter16Solutions.multiply
import com.github.myyk.cracking.Chapter16Solutions.divide
import com.github.myyk.cracking.Chapter16Solutions.livingPeople
import com.github.myyk.cracking.Chapter16Solutions.livingPeopleBruteForce
import com.github.myyk.cracking.Chapter16Solutions.Person
import com.github.myyk.cracking.Chapter16Solutions.countDivingBoardsOfKPieces
import com.github.myyk.cracking.Chapter16Solutions.countDivingBoardsOfSize
import com.github.myyk.cracking.Chapter16Solutions.bestLine
import com.github.myyk.cracking.Chapter16Solutions.Point
import com.github.myyk.cracking.Chapter16Solutions.Line
import com.github.myyk.cracking.Chapter16Solutions.masterMindScore
import com.github.myyk.cracking.Chapter16Solutions.masterMindScore2
import com.github.myyk.cracking.Chapter16Solutions.MasterMindResult
import com.github.myyk.cracking.Chapter16Solutions._
import javafx.util.Pair
import com.github.myyk.cracking.Chapter16Solutions.AntGrid.AntGridResult
import com.github.myyk.cracking.Chapter16Solutions.AntGrid.Direction

class Chapter16SolutionsTest extends FlatSpec with Matchers {
  "swapInPlace" should "swap the two integers without using additional space" in {
    val originalA = 123456
    val originalB = 67890
    val a = new MutableInteger(originalA)
    val b = new MutableInteger(originalB)
    Chapter16Solutions.swapInPlace(a, b)
    a.value shouldBe originalB
    b.value shouldBe originalA
  }

  "WordFrequencies" should "be able to get a word's frequency from a given text" in {
    val wordFreqs = new WordFrequencies("a: b c d, å åå e f g aaa' a a a -aaa- a")
    wordFreqs.getFrequency("apple") shouldBe 0
    wordFreqs.getFrequency("a") shouldBe 5
    wordFreqs.getFrequency("A") shouldBe 5
    wordFreqs.getFrequency("aaa") shouldBe 2
    wordFreqs.getFrequency("c") shouldBe 1
    wordFreqs.getFrequency("å") shouldBe 1
    wordFreqs.getFrequency("åå") shouldBe 1
  }

  "isWonTicTacToe" should "figure out if a board is won or not already" in {
    // not super proud of these tests, but they will do for now.
    var board = TicTacToe.newBoard;
    isWonTicTacToe(board) shouldBe false
    board(0)(0) = TicTacToe.X;
    isWonTicTacToe(board) shouldBe false
    board(0)(1) = TicTacToe.O;
    isWonTicTacToe(board) shouldBe false
    board(0)(2) = TicTacToe.X;
    isWonTicTacToe(board) shouldBe false
    board(0)(1) = TicTacToe.X;
    isWonTicTacToe(board) shouldBe true

    board = TicTacToe.newBoard;
    isWonTicTacToe(board) shouldBe false
    board(0)(0) = TicTacToe.X;
    board(1)(1) = TicTacToe.X;
    board(2)(2) = TicTacToe.X;
    isWonTicTacToe(board) shouldBe true

    board = TicTacToe.newBoard;
    isWonTicTacToe(board) shouldBe false
    board(0)(2) = TicTacToe.X;
    board(1)(2) = TicTacToe.X;
    board(2)(2) = TicTacToe.X;
    isWonTicTacToe(board) shouldBe true

    board = TicTacToe.newBoard;
    isWonTicTacToe(board) shouldBe false
    board(0)(0) = TicTacToe.X;
    board(1)(1) = TicTacToe.X;
    board(2)(2) = TicTacToe.X;
    isWonTicTacToe(board) shouldBe true

    board = TicTacToe.newBoard;
    isWonTicTacToe(board) shouldBe false
    board(0)(2) = TicTacToe.X;
    board(1)(1) = TicTacToe.X;
    board(2)(0) = TicTacToe.X;
    isWonTicTacToe(board) shouldBe true
  }

  "factorialZeroes" should "get the number of trailing 0s of n!" in {
    for (i <- 0 to 4) {
      factorialZeroes(i) shouldBe 0
    }
    for (i <- 5 to 9) {
      factorialZeroes(i) shouldBe 1
    }
    for (i <- 10 to 14) {
      factorialZeroes(i) shouldBe 2
    }
    factorialZeroes(25) shouldBe 6
    factorialZeroes(50) shouldBe 12
  }

  "smallestDifference" should "find the smallest difference between any two numbers in the arrays" in {
    smallestDifference(Array(1,3,15,11,2), Array(23,127,235,19,8)) shouldBe 3
    smallestDifference(Array(1,3,15,2), Array(23,127,235,19,8)) shouldBe 4
    smallestDifference(Array(1,3,15,2), Array(23,127,235,3,8)) shouldBe 0
    smallestDifference(Array(1), Array(23,127,235,312,8)) shouldBe 7
  }

  def testNumberMax(a: Int, b: Int): Unit = {
    numberMax(a, b) shouldBe (a max b)
    numberMax(b, a) shouldBe (a max b)
  }

  "numberMax" should "find the max between two numbers" in {
    testNumberMax(0, 0)
    testNumberMax(1, 0)
    testNumberMax(-1, 0)
    testNumberMax(-1, -2)
    testNumberMax(123, 321)
    testNumberMax(Int.MaxValue - 1, Int.MaxValue)
    testNumberMax(Int.MinValue + 1, Int.MaxValue)
  }

  "englishInt" should "get a word representation of an interger" in {
    val maker = new EnglishIntMaker()
    maker.englishInt(0) shouldBe "Zero"
    maker.englishInt(1000) shouldBe "One Thousand"
    maker.englishInt(100) shouldBe "One Hundred"
    maker.englishInt(101) shouldBe "One Hundred One"
    maker.englishInt(1234) shouldBe "One Thousand, Two Hundred Thrity Four"
    maker.englishInt(-1234) shouldBe "Negative One Thousand, Two Hundred Thrity Four"
    maker.englishInt(9341234) shouldBe "Nine Million, Three Hundred Forty One Thousand, Two Hundred Thrity Four"
    maker.englishInt(Int.MaxValue) shouldBe "Two Billion, One Hundred Forty Seven Million, Four Hundred Eighty Three Thousand, Six Hundred Forty Seven"
    maker.englishInt(Int.MinValue) shouldBe "Negative Two Billion, One Hundred Forty Seven Million, Four Hundred Eighty Three Thousand, Six Hundred Forty Eight"
    maker.englishInt(Int.MinValue+1) shouldBe "Negative Two Billion, One Hundred Forty Seven Million, Four Hundred Eighty Three Thousand, Six Hundred Forty Seven"
  }

  def testOperations(a: Int, b: Int): Unit = {
    testOperationsHelper(a, b)
    testOperationsHelper(b, a)
  }

  def testOperationsHelper(a: Int, b: Int): Unit = {
    subtract(a, b) shouldBe (a - b)
    if (a.abs < 10000 && b.abs < 10000) {
      multiply(a, b) shouldBe (a * b)  
    }
    if (b != 0 && a.abs < 10000) {
      divide(a, b) shouldBe (a / b)
    }
  }

  "operations" should "do subtraction, multiplication and division properly" in {
    testOperations(0, 0)
    testOperations(1, 0)
    testOperations(-1, 1)
    testOperations(-1, 0)
    testOperations(Integer.MAX_VALUE, 2)
    testOperations(Integer.MIN_VALUE + 1, 2)
    testOperations(123, 32)
    testOperations(123, -32)
    testOperations(Integer.MAX_VALUE, Integer.MAX_VALUE)
    testOperations(Integer.MIN_VALUE, Integer.MIN_VALUE)
    for (_ <- 1 to 100) {
      testOperations(Random.nextInt(), Random.nextInt())
    }
  }

  def testLivingPeople(people: Set[Person]): Unit = {
    livingPeople(people) shouldBe livingPeopleBruteForce(people)
  }

  "livingPeople" should "return the year where the most people were living" in {
    livingPeople(Set(new Person(1900, 2000), new Person(1910,1910))) shouldBe 1910

    val people = for (_: Int <- (1 to 10000).toSet) yield {
      val birth = 1900 + Random.nextInt(100)
      new Person(birth, birth + Random.nextInt(100))
    }
    testLivingPeople(people)
  }

  "countDivingBoardsOfKPieces" should "return the number of ways to build a diving board of with k boards" in {
    countDivingBoardsOfKPieces(5, 10, 4).toSet shouldBe Set(20, 25, 30, 35, 40)
    countDivingBoardsOfKPieces(3, 7, 4).toSet shouldBe Set(12, 16, 20, 24, 28)
    countDivingBoardsOfKPieces(10, 10, 4).toSet shouldBe Set(40)
  }

  "countDivingBoardsOfSize" should "return the number of ways to build a diving board of size k" in {
    countDivingBoardsOfSize(5, 10, 200) shouldBe Chapter7Solutions.coinsCount(Set(5, 10).map(JInt.valueOf(_)).asJava, 200)
  }

  "bestLine" should "find a line that goes throught the most points" in {
    bestLine(Set(new Point(1,1), new Point(2,2))) shouldBe new Line(new Point(1,1), new Point(2,2))
    bestLine(Set(new Point(1,1), new Point(3,3))) shouldBe new Line(new Point(1,1), new Point(2,2))
    bestLine(Set(new Point(0,1), new Point(0,3))) shouldBe new Line(new Point(0,3), new Point(0,3))
    bestLine(Set(new Point(1,1), new Point(2,2), new Point(3,3), new Point(0,1), new Point(0,3))) shouldBe new Line(new Point(0,0), new Point(1,1))
  }

  "masterMindScore" should "compute the game score" in {
    masterMindScore("RGBY", "GGRR") shouldBe new MasterMindResult(1, 1)
    masterMindScore2("RGBY", "GGRR") shouldBe new MasterMindResult(1, 1)
  }

  "subSortIndexes" should "give the indexes of the minimum subarray to get a sorted array" in {
    subSortIndexes(Array(1, 2, 4, 7, 10, 11, 7, 12, 6, 7, 16, 18, 19)) shouldBe new Pair(3, 9)
    subSortIndexes(Array(1, 2, 4, 7, 10, 11, 7, 12, 7, 7, 16, 18, 19)) shouldBe new Pair(4, 9)
    subSortIndexes(Array(1, 2, 4, 7, 10, 11, 7, 12, 7, 7, 16, 18, 5)) shouldBe new Pair(3, 12)
    subSortIndexes(Array(3, 2, 4, 7, 10, 11, 7, 12, 6, 7, 16, 18, 19)) shouldBe new Pair(0, 9)
  }

  def testMaxContiguousSequenceSum(array: Array[Int], expected: Int): Unit = {
    maxContiguousSequenceSum(array) shouldBe expected
    maxContiguousSequenceSum2(array) shouldBe expected
  }

  "maxContiguousSequenceSum" should "get the maximum sum of a contiguous subarray" in {
    testMaxContiguousSequenceSum(Array(2, -8, 3, -2, 4, -10), 5)
    testMaxContiguousSequenceSum(Array(-10), -10)
    testMaxContiguousSequenceSum(Array(-10, -2), -2)
    testMaxContiguousSequenceSum(Array(-2, -10), -2)
  }

  "doesPatternMatch" should "determine if the pattern matches the value" in {
    doesPatternMatch("", "catcatgocatgo") shouldBe false
    doesPatternMatch("a", "catcatgocatgo") shouldBe true
    doesPatternMatch("b", "catcatgocatgo") shouldBe true
    doesPatternMatch("ab", "catcatgocatgo") shouldBe true
    doesPatternMatch("aabab", "catcatgocatgo") shouldBe true
    doesPatternMatch("aabac", "catcatgocatgo") shouldBe false
  }

  "findPondSizes" should "find the sizes of the various ponds in the topography" in {
    findPondSizes(Array(Array(1))).toSet shouldBe Set()
    findPondSizes(Array(Array(0))).toSet shouldBe Set(1)
    findPondSizes(Array(Array(0, 0), Array(0, 0))).toSet shouldBe Set(4)

    findPondSizes(Array(
        Array(0, 2, 1, 0),
        Array(0, 1, 0, 1),
        Array(1, 1, 0, 1),
        Array(0, 1, 0, 1))).toSet shouldBe Set(1,2,4)
  }

  def testSumSwap(a: Array[Int], b: Array[Int]): Unit = {
    val result = sumSwap(a, b)
    val swappedA =  -result(0) :: result(1) :: a.toList
    val swappedB =  result(0) :: -result(1) :: b.toList

    swappedA.sum shouldBe swappedB.sum
  }

  "sumSwap" should "try to find integers to swap to get arrays of the same sum" in {
    testSumSwap(Array(4, 1, 2, 1, 1, 2), Array(3, 6, 3, 3))
  }

  def printAntWalk(grid: AntGridResult): Unit = {
    println(s"ant = (${grid.ant.getKey}, ${grid.ant.getValue}), direction = ${grid.direction}")
    for {
      col <- 0 until grid.isBlack.length
      row <- 0 until grid.isBlack(0).length
    } {
      if (grid.ant.getKey == col && grid.ant.getValue == row) {
        if (grid.isBlack(col)(row)) {
          print('X');
        } else {
          print('O');
        }
      } else if (grid.isBlack(col)(row)) {
        print('x');
      } else {
        print('o');
      }

      if (row == grid.isBlack(0).length - 1) {
        println();
      }
    }
  }

  "antWalk" should "walk the ant according to it's rules and return the result" in {
    // Useful to manually validate, but too verbose otherwise.
//    val grid = new AntGrid();
//    for (i <- 0 to 20) {
//      println(s"------ k = ${i} ${"-"*25}")
//      printAntWalk(grid.getResult)
//      grid.moveAnt()
//    }

    val expectedArray = Array(
      Array(false, false, true),
      Array(true,  true,  true),
      Array(true,  true,  false)
    )
    antWalk(10) shouldBe new AntGridResult(new Pair(0, 0), expectedArray, Direction.Left)
  }

  "rand7" should "return random numbers 0 until 7" in {
    val values = for {
      i <- (0 until 1000)
    } yield {
      rand7()
    }
//    println(values.groupBy { a => a }.map{case(a, b) => (a, b.size)}.toList.sortBy{case (a, b) => a})

    values.toSet shouldBe (0 until 7).toSet
  }

  "findPairsWithSum" should "find all the pairs with the sum in the array" in {
    findPairsWithSum(Array(2, -3, 5, -7, 8, -1, 0, 1), 1).toMap shouldBe Map(-1 -> 2, -7 -> 8, 0 -> 1)
  }

  "LRUCache" should "work like a LRU cache with a max size" in {
    val cache = new LRUCache[Integer, Integer](5)
    // test basic fill
    for (i <- 1 to 5) {
      cache.put(i, i) shouldBe null
      cache.containsKey(i) shouldBe true
      cache.get(i) shouldBe i
    }

    // test evict all
    for (i <- 6 to 10) {
      cache.put(i, i) shouldBe null
      cache.containsKey(i) shouldBe true
      cache.get(i) shouldBe i
    }
    for (i <- 1 to 5) {
      cache.containsKey(i) shouldBe false
    }

    // test update beginning
    cache.put(6, 8)
    cache.get(6) shouldBe 8
    for (i <- 7 to 10) {
      cache.containsKey(i) shouldBe true
      cache.get(i) shouldBe i
    }
    cache.get(6) shouldBe 8
    cache.put(6, 6)

    // test update end
    cache.put(10, 11)
    cache.get(10) shouldBe 11
    for (i <- 6 to 9) {
      cache.containsKey(i) shouldBe true
      cache.get(i) shouldBe i
    }
    cache.get(10) shouldBe 11
  }
}