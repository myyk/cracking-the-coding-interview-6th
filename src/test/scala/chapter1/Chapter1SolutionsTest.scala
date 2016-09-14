package chapter1

import org.scalatest.FlatSpec
import org.scalatest.Matchers

class Chapter1SolutionsTest extends FlatSpec with Matchers {

  "stringHasAllUniqueCharacters" should "return whether or not a string has all unique characters" in {
    Chapter1Solutions.stringHasAllUniqueCharacters("") shouldBe true
    Chapter1Solutions.stringHasAllUniqueCharacters("  ") shouldBe false
    Chapter1Solutions.stringHasAllUniqueCharacters("a") shouldBe true
    Chapter1Solutions.stringHasAllUniqueCharacters("abc") shouldBe true
    Chapter1Solutions.stringHasAllUniqueCharacters("aA") shouldBe true
    Chapter1Solutions.stringHasAllUniqueCharacters("aaa") shouldBe false
    Chapter1Solutions.stringHasAllUniqueCharacters("abbc") shouldBe false
  }

  "arePermutations" should "return whether two strings are permuations of each other" in {
    Chapter1Solutions.arePermutations("", "") shouldBe true
    Chapter1Solutions.arePermutations("a", "") shouldBe false
    Chapter1Solutions.arePermutations("", "a") shouldBe false
    Chapter1Solutions.arePermutations("a", "a") shouldBe true
    Chapter1Solutions.arePermutations("a", "A") shouldBe false
    Chapter1Solutions.arePermutations("aA", "Aa") shouldBe true
    Chapter1Solutions.arePermutations("abc", "bac") shouldBe true
    Chapter1Solutions.arePermutations("aabc", "bac") shouldBe false
    Chapter1Solutions.arePermutations("abc", "bbac") shouldBe false
    Chapter1Solutions.arePermutations("abc", "xyz") shouldBe false
    Chapter1Solutions.arePermutations("abc", "a") shouldBe false
    Chapter1Solutions.arePermutations("a", "bca") shouldBe false
  }
}
