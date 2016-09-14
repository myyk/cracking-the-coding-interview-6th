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

  "urlify" should "replace spaces with '%20' in place" in {
    val chars1 = Array('a', 'b', 'c', '\0')
    Chapter1Solutions.urlify(chars1, 3)
    new String(chars1) shouldBe "abc\0"

    val chars2 = Array('a', ' ', 'c', '\0', ' ', ' ')
    Chapter1Solutions.urlify(chars2, 3)
    new String(chars2) shouldBe "a%20c\0"

    // string is "a c d  e "
    val chars3 = Array('a', ' ', 'c', ' ', 'd', ' ', ' ', 'e', ' ', '\0', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ')
    Chapter1Solutions.urlify(chars3, 9)
    new String(chars3) shouldBe "a%20c%20d%20%20e%20\0"
  }

  "canHavePalindromePermutation" should "return whether a string can form a palindrome" in {
    Chapter1Solutions.canHavePalindromePermutation("") shouldBe true
    Chapter1Solutions.canHavePalindromePermutation(" ") shouldBe true
    Chapter1Solutions.canHavePalindromePermutation("a") shouldBe true
    Chapter1Solutions.canHavePalindromePermutation("ab") shouldBe false
    Chapter1Solutions.canHavePalindromePermutation("aA abB") shouldBe true
    Chapter1Solutions.canHavePalindromePermutation("Tact Coa") shouldBe true
    Chapter1Solutions.canHavePalindromePermutation("Myyk") shouldBe false
  }
}
