package com.github.myyk.cracking

import java.lang.{ Integer => JInt }

import scala.collection.JavaConversions.asScalaSet
import scala.collection.JavaConversions.seqAsJavaList
import scala.collection.JavaConversions.setAsJavaSet
import scala.collection.JavaConverters.setAsJavaSetConverter
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
    multiply(a, b) shouldBe (a * b)
    if (b != 0 && a.abs < 10000) {
      println(s"a = $a, b = $b")
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
    // these test cases would be too slow to complete
//    testOperations(Integer.MAX_VALUE, Integer.MAX_VALUE)
//    testOperations(Integer.MIN_VALUE, Integer.MIN_VALUE)
//    for (_ <- 1 to 100) {
//      testOperations(Random.nextInt(), Random.nextInt())
//    }
  }
}