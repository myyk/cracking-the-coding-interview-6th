package com.github.myyk.cracking

import com.github.myyk.cracking.Chapter5Solutions.NextNumberResult

import scala.language.implicitConversions

import org.scalatest._
import flatspec._
import matchers._

class Chapter5SolutionsTest extends AnyFlatSpec with should.Matchers {
  implicit def enrichStringContext(sc: StringContext): RichStringContext = new RichStringContext(sc)

  class RichStringContext(sc: StringContext) {
    def b(): Int = {
      def parseBinary(s: String): Int = {
        var i = s.length - 1
        var sum = 0
        var mult = 1
        while (i >= 0) {
          s.charAt(i) match {
            case '1' => sum += mult
            case '0' =>
          }
          mult *= 2
          i -= 1
        }
        sum
      }
      val s = sc.parts.mkString
      parseBinary(s)
    }
  }

  "insert" should "insert the integer into the other" in {
    Chapter5Solutions.insert(1, 1, 0, 0) shouldBe 1
    Chapter5Solutions.insert(1, 1, 0, 0) shouldBe 1
    Chapter5Solutions.insert(2, 1, 0, 0) shouldBe 3
    Chapter5Solutions.insert(1, 1, 1, 1) shouldBe 3
    Chapter5Solutions.insert(1, b"111", 8, 6) shouldBe b"111000001"
    Chapter5Solutions.insert(b"101000000000", b"1101", 4, 2) shouldBe b"101000110100"
    Chapter5Solutions.insert(~0, b"1101", 4, 2) shouldBe ~0 ^ (b"1111"<<2) | (b"1101"<<2)
    Chapter5Solutions.insert(b"111111", b"1010", 4, 1) shouldBe b"110101"
  }

  "floatToBinaryString" should "convert a double to a string" in {
    Chapter5Solutions.floatToBinaryString(1) shouldBe "ERROR"
    Chapter5Solutions.floatToBinaryString(0.5) shouldBe ".1"
    Chapter5Solutions.floatToBinaryString(0.75) shouldBe ".11"
    Chapter5Solutions.floatToBinaryString(0.25) shouldBe ".01"
    Chapter5Solutions.floatToBinaryString(0.72) shouldBe "ERROR"
    Chapter5Solutions.floatToBinaryString(b"10101010".toDouble/Math.pow(2, 8)) shouldBe ".1010101"
  }

  "flipBitToWin" should "find the longest sequence possible with one flip" in {
    Chapter5Solutions.flipBitToWin(b"0") shouldBe 1
    Chapter5Solutions.flipBitToWin(b"1") shouldBe 2
    Chapter5Solutions.flipBitToWin(b"10") shouldBe 2
    Chapter5Solutions.flipBitToWin(b"1010") shouldBe 3
    Chapter5Solutions.flipBitToWin(b"101") shouldBe 3
    Chapter5Solutions.flipBitToWin(b"10010") shouldBe 2
    Chapter5Solutions.flipBitToWin(b"10000110100") shouldBe 4
    Chapter5Solutions.flipBitToWin(b"100001101100001101") shouldBe 5
    Chapter5Solutions.flipBitToWin(b"10100000000000000000000000000001") shouldBe 3
    Chapter5Solutions.flipBitToWin(-1) shouldBe 32
    Chapter5Solutions.flipBitToWin(b"11011101111") shouldBe 8 // book example
  }

  "nextNumber" should "find the next smaller and next larger integers with the same number of 1s in the binary representation" in {
    Chapter5Solutions.nextNumber(b"10") shouldBe new NextNumberResult(b"1", b"100")
    Chapter5Solutions.nextNumber(b"110") shouldBe new NextNumberResult(b"101", b"1001")
    Chapter5Solutions.nextNumber(b"01010000000000000000000000000000") shouldBe new NextNumberResult(b"01001000000000000000000000000000",b"01100000000000000000000000000000")

    Chapter5Solutions.nextNumber(-1) shouldBe new NextNumberResult(-1, -1)
    Chapter5Solutions.nextNumber(0) shouldBe new NextNumberResult(-1, -1)
    Chapter5Solutions.nextNumber(1) shouldBe new NextNumberResult(-1, b"10")
    Chapter5Solutions.nextNumber(b"11") shouldBe new NextNumberResult(-1, b"101")
  }

  "bitsToConvert" should "find the number of bits to convert one int to the other" in {
    Chapter5Solutions.bitsToConvert(9873453, 9873453) shouldBe 0
    Chapter5Solutions.bitsToConvert(0, 1) shouldBe 1
    Chapter5Solutions.bitsToConvert(0, b"1010") shouldBe 2
    Chapter5Solutions.bitsToConvert(0, b"101011") shouldBe 4
    Chapter5Solutions.bitsToConvert(0, -1) shouldBe 32
    Chapter5Solutions.bitsToConvert(b"11101", b"01111") shouldBe 2
  }

  "pairwiseSwap" should "swap all the bits in an integer with the neighboring bits" in {
    Chapter5Solutions.pairwiseSwap(0) shouldBe 0
    Chapter5Solutions.pairwiseSwap(b"01") shouldBe b"10"
    Chapter5Solutions.pairwiseSwap(b"10") shouldBe b"01"
    Chapter5Solutions.pairwiseSwap(b"1011") shouldBe b"0111"
    Chapter5Solutions.pairwiseSwap(b"1010101010101010101010101010101010101010101010101010101010101010") shouldBe b"0101010101010101010101010101010101010101010101010101010101010101"
    Chapter5Solutions.pairwiseSwap(b"0101010101010101010101010101010101010101010101010101010101010101") shouldBe b"1010101010101010101010101010101010101010101010101010101010101010"
    Chapter5Solutions.pairwiseSwap(b"1111111111111111111111111111111100000000000000000000000000000000") shouldBe b"1111111111111111111111111111111100000000000000000000000000000000"
    Chapter5Solutions.pairwiseSwap(b"0000000000000000000000000000000011111111111111111111111111111111") shouldBe b"0000000000000000000000000000000011111111111111111111111111111111"
  }

  "drawLine" should "draw a line in the screen" in {
    Chapter5Solutions.drawLine(new Array(2), 8, 0, 8, 0) shouldBe Array[Byte](0xFF.toByte, 0)
    Chapter5Solutions.drawLine(new Array(2), 8, 0, 8, 1) shouldBe Array[Byte](0, 0xFF.toByte)
    Chapter5Solutions.drawLine(new Array(2), 8, 2, 6, 1) shouldBe Array[Byte](0, b"00111100".toByte)
    Chapter5Solutions.drawLine(new Array(4), 16, 1, 6, 1) shouldBe Array[Byte](0, 0, b"01111100".toByte, 0)
    Chapter5Solutions.drawLine(new Array(8), 32, 1, 31, 1) shouldBe Array[Byte](0, 0, 0, 0, b"01111111".toByte, 0xFF.toByte, 0xFF.toByte, b"11111110".toByte)
  }
}