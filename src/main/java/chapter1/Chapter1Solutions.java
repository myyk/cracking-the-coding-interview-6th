package chapter1;

/**
 * Arrays and Strings
 */
public class Chapter1Solutions {
  /**
   * Is Unique? Are all characters unique in the string.
   * 
   * Assumptions: Characters are ASCII characters, capitalization matters
   * 
   * Time complexity: O(min(c,n)) where c is number of characters in alphabet (128)
   * and n is the length of the string. Or O(1) technically.
   * Space complexity: O(c) where c is number of characters in alphabet (128). Or O(1) technically.
   */
  public static boolean stringHasAllUniqueCharacters(String str) {
    // If there's more characters than there are in the alphabet, then there are duplicates.
    if (str.length() > 128) {
      return false;
    } else {
      boolean[] seen = new boolean[128];
      for (int i=0; i<str.length(); i++) {
        char c = str.charAt(i);
        if (seen[c]) {
          return false;
        } else {
          seen[c] = true;
        }
      }
      return true;
    }
  }

  /**
   * Check Permutation: Given 2 string check to see if one is a permutation of the other.
   * 
   * Assumptions:
   *    ASCII characters
   *    capitalization matters
   *    whitespace matters
   *    strings aren't larger than 2^16.
   * 
   * Time complexity: O(max(c,n) where c is number of characters in alphabet (128)
   * and n is the length of the string.
   * Space complexity: O(c) where c is number of characters in alphabet (128). Or O(1) technically.
   */
  public static boolean arePermutations(String a, String b) {
    // Can't be permutations if the same length
    if (a.length() != b.length()) {
      return false;
    } else {
      short[] occurances = new short[128];
      for (int i=0; i<a.length(); i++) {
        occurances[a.charAt(i)]++;
        occurances[b.charAt(i)]--;        
      }

      for (short occurance: occurances) {
        if (occurance != 0)
          return false;
      }

      return true;
    }    
  }
}
