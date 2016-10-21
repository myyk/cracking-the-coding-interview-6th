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
      println(i)
      factorialZeroes(i) shouldBe 2
    }
    factorialZeroes(25) shouldBe 6
    factorialZeroes(50) shouldBe 12
  }
}