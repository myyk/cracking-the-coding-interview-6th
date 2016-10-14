package com.github.myyk.cracking.chapter7

import org.scalatest.FlatSpec
import org.scalatest.Matchers

class CircularArrayTest extends FlatSpec with Matchers {
  "iterator" should "should iterate through the collection" in {
    val array = new CircularArray[Int]
    for (i <- 1 until 10) {
      array.add(i)
    }
    var it = array.iterator
    for (i <- 1 until 10) {
      it.next() shouldBe i
    }

    array.rotateRight(3)
    it = array.iterator
    for (i <- 7 until 10) {
      it.next() shouldBe i
    }
    for (i <- 1 until 7) {
      it.next() shouldBe i
    }

    array.rotateLeft(3)
    it = array.iterator
    for (i <- 1 until 10) {
      it.next() shouldBe i
    }

    array.rotateLeft(3)
    array.add(10)
    array.add(11)
    it = array.iterator
    for (i <- 4 until 10) {
      it.next() shouldBe i
    }
    for (i <- 1 until 4) {
      it.next() shouldBe i
    }
    it.next() shouldBe 10
    it.next() shouldBe 11
  }
}