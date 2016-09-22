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

  "isOneAway" should "return whether a string is one edit from another string" in {
    Chapter1Solutions.isOneAway("pale", "pale") shouldBe true
    Chapter1Solutions.isOneAway("pale", "ple") shouldBe true
    Chapter1Solutions.isOneAway("pales", "pale") shouldBe true
    Chapter1Solutions.isOneAway("pale", "bale") shouldBe true
    Chapter1Solutions.isOneAway("pale", "bake") shouldBe false
  }

  "compress" should "return a compressed string if it can compress it" in {
    Chapter1Solutions.compress("aabcccccaaa") shouldBe "a2b1c5a3"
    Chapter1Solutions.compress("abc") shouldBe "abc"
    Chapter1Solutions.compress("a") shouldBe "a"
    Chapter1Solutions.compress("aa") shouldBe "aa"
    Chapter1Solutions.compress("") shouldBe ""
  }

  "rotateMatrix90" should "rotate the input matrix" in {
    Chapter1Solutions.rotateMatrix90(Array()) shouldBe Array()
    Chapter1Solutions.rotateMatrix90(Array(Array(1))) shouldBe Array(Array(1))
    Chapter1Solutions.rotateMatrix90(Array(Array(1,2),
                                           Array(3,4))) shouldBe Array(Array(3,1),
                                                                       Array(4,2))
    //4 rotations and back to beginning
    Chapter1Solutions.rotateMatrix90(Chapter1Solutions.rotateMatrix90(Chapter1Solutions.rotateMatrix90(Chapter1Solutions.rotateMatrix90(Array(Array(1,2),Array(3,4)))))) shouldBe Array(Array(1,2),Array(3,4))
    Chapter1Solutions.rotateMatrix90(Array(Array(1,2,3),
                                           Array(4,5,6),
                                           Array(7,8,9))) shouldBe Array(Array(7,4,1),
                                                                         Array(8,5,2),
                                                                         Array(9,6,3))
    Chapter1Solutions.rotateMatrix90(Array(
      Array( 1, 2, 3, 4),
      Array( 5, 6, 7, 8),
      Array( 9,10,11,12),
      Array(13,14,15,16))) shouldBe
      Array(
      Array(13, 9, 5, 1),
      Array(14,10, 6, 2),
      Array(15,11, 7, 3),
      Array(16,12, 8, 4))
    Chapter1Solutions.rotateMatrix90(Array(
      Array( 1, 2, 3, 4, 5),
      Array( 6, 7, 8, 9,10),
      Array(11,12,13,14,15),
      Array(16,17,18,19,20),
      Array(21,22,23,24,25))) shouldBe
      Array(
      Array(21,16,11, 6, 1),
      Array(22,17,12, 7, 2),
      Array(23,18,13, 8, 3),
      Array(24,19,14, 9, 4),
      Array(25,20,15,10, 5))
  }

  "zeroMatrix" should "zeros rows and columns with zeroes in the original matrix" in {
    Chapter1Solutions.zeroMatrix(Array(Array(1))) shouldBe Array(Array(1))
    Chapter1Solutions.zeroMatrix(Array(Array(1,2),
                                       Array(3,4))) shouldBe Array(Array(1,2),
                                                                   Array(3,4))
    Chapter1Solutions.zeroMatrix(Array(Array(1,0),
                                       Array(3,4))) shouldBe Array(Array(0,0),
                                                                   Array(3,0))
    Chapter1Solutions.zeroMatrix(Array(Array(1,0,3),
                                       Array(3,4,5))) shouldBe Array(Array(0,0,0),
                                                                     Array(3,0,5))
    Chapter1Solutions.zeroMatrix(Array(
      Array( 1, 2, 3, 4, 5),
      Array( 6, 7, 8, 0,10),
      Array(11, 0,13,14,15),
      Array(16,17,18,19,20),
      Array(21,22,23,24, 0))) shouldBe
      Array(
      Array( 1, 0, 3, 0, 0),
      Array( 0, 0, 0, 0, 0),
      Array( 0, 0, 0, 0, 0),
      Array(16, 0,18, 0, 0),
      Array( 0, 0, 0, 0, 0))
    Chapter1Solutions.zeroMatrix(Array(
      Array( 0, 2, 3, 4, 5),
      Array( 6, 7, 8, 0,10),
      Array(11,12,13,14,15),
      Array(16,17,18,19,20),
      Array(21,22,23,24, 0))) shouldBe
      Array(
      Array( 0, 0, 0, 0, 0),
      Array( 0, 0, 0, 0, 0),
      Array( 0,12,13, 0, 0),
      Array( 0,17,18, 0, 0),
      Array( 0, 0, 0, 0, 0))
  }

  "isStringRotation" should "determine if there the strings are rotations of each other" in {
    Chapter1Solutions.isStringRotation("", "") shouldBe true
    Chapter1Solutions.isStringRotation("a", "a") shouldBe true
    Chapter1Solutions.isStringRotation("ab", "ab") shouldBe true
    Chapter1Solutions.isStringRotation("ba", "ab") shouldBe true
    Chapter1Solutions.isStringRotation("waterbottle", "erbottlewat") shouldBe true
    Chapter1Solutions.isStringRotation("waterbottle", "waterbottl") shouldBe false
    Chapter1Solutions.isStringRotation("waterbottl", "waterbottle") shouldBe false
  }
}
