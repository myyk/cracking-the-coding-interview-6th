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
   *    strings aren't larger than 2^16
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

  /**
   * URLify: replace spaces in a string with '%20'. Perform in place.
   *
   * Assumptions:
   *  there is enough space in char array to hold additional characters
   *  given true length of string
   *
   * Time complexity: O(n)
   * Space complexity: O(1)
   */
  public static void urlify(char[] chars, int length) {
    int numSpaces = 0;
    for (int i=0; i<length; i++) {
      if (chars[i] == ' ') {
        numSpaces++;
      }
    }

    // Iterate backwards shifting the characters by number of spaces seen up to that point times 2.
    chars[length + 2*numSpaces] = '\0';
    for (int i=length-1; i>=0 && numSpaces>0; i--) {
      char c = chars[i];
      if (c == ' ') {
        numSpaces--;
        chars[i+2*numSpaces] = '%';
        chars[i+2*numSpaces+1] = '2';
        chars[i+2*numSpaces+2] = '0';
      } else {
        chars[i+2*numSpaces] = c;
      }
    }
  }

  /**
   * Palindrome Permutation: Given a string check if a permutation can be made from it.
   *
   * Highly optimized solution using a single int for space.
   *
   * Assumptions:
   *   case insensitive
   *   alphabet is A-Z only
   *
   * Time complexity: O(n)
   * Space complexity: O(1)
   */
  public static boolean canHavePalindromePermutation(String str) {
    int bitMap = 0;
    for (int i=0; i<str.length(); i++) {
      int pos = getPosition(str.charAt(i));
      if (pos >= 0 ) { 
        bitMap ^= 1<<pos;
      }
    }
    return containsOnlyOneOdd(bitMap);
  }

  // returns -1 if not there
  private static int getPosition(char c) {
    if(c >= 'a' && c <= 'z') {
      return c-'a';
    } else if(c >= 'A' && c <= 'Z') {
      return c-'A';
    } else {
      return -1;
    }
  }

  private static boolean containsOnlyOneOdd(int bitMap) {
    return (bitMap & (bitMap-1)) == 0;
  }

  /**
   * One Away: If there are only insert, remove, update edit operations can 2 strings be one (or less)
   *  edit away from each other.
   *
   * Assumptions:
   *
   * Time complexity: O(n)
   * Space complexity: O(1)
   */
  public static boolean isOneAway(String str1, String str2) {
    //optimization
    if(Math.abs(str1.length() - str2.length()) > 1) {
      return false;
    } else {
      String shorter = str1, longer = str2;
      if (str1.length() > str2.length()) {
        shorter = str2;
        longer = str1;
      }

      boolean differenceFound = false;
      for (int i = 0, j = 0; i < shorter.length() && j < longer.length(); i++, j++) {
        if (shorter.charAt(i) != longer.charAt(j)) {
          if (differenceFound) {
            return false;
          }
          if (shorter.length() != longer.length()) {
            j++;
          }
          differenceFound = true;
        }
      }
      return true;
    }
  }

  /**
   * String Compression: Using basic compression of letter followed by frequency. Returns the original
   *   string if compressed version is not smaller.
   *
   * Assumptions:
   *   it's ok to allocate a string buffer of size equal to the input string
   *   it's better to have simpler code and not figure out if compressed result is smaller before creating it
   *
   * Time complexity: O(n)
   * Space complexity: O(n)
   */
  public static String compress(String str) {
    if (str.isEmpty()) {
      return "";
    }

    int count = 1;
    char last = str.charAt(0);
    StringBuffer buf = new StringBuffer(str.length());
    for (int i=1; i<str.length(); i++) {
      char next = str.charAt(i);
      if (last == next) {
        count++;
      } else {
        buf.append(last).append(count);
        count = 1;
        last = next;
      }
    }
    buf.append(last).append(count);

    return buf.length()<str.length() ? buf.toString() : str;
  }

  /**
   * Rotate Matrix: Rotate a matrix 90 degrees clockwise in place.
   *
   * Assumptions:
   *  the matrix is square - bc otherwise it's just error handling
   *  top left is 0,0
   *  y is first array index, x is second array index.
   *
   * Time complexity: O(n^2) where n is n dimensions of the matrix
   * Space complexity: O(1)
   */
  public static int[][] rotateMatrix90(final int[][] matrix) {
    // A little tricky, don't swap the corners twice. And don't rotate everything twice by going too far with i.
    for (int i = 0; i<matrix.length/2; i++) {
      for (int j = i; j<matrix.length-1-i; j++) {
        final int swap = matrix[i][j];
        matrix[i][j] = matrix[matrix.length-1-j][i];
        matrix[matrix.length-1-j][i] = matrix[matrix.length-1-i][matrix.length-1-j];
        matrix[matrix.length-1-i][matrix.length-1-j] = matrix[j][matrix.length-1-i];
        matrix[j][matrix.length-1-i] = swap;
      }
    }
    return matrix;
  }

  /**
   * Zero Matrix: In a NxM matrix, if an element is 0, then zero out the row and column.
   *
   * Assumptions:
   *   in place
   * 
   * Time complexity: O(n*m)
   * Space complexity: O(n+m)
   */
  public static int[][] zeroMatrix(final int[][] matrix) {
    int n = matrix.length;
    int m = matrix[0].length;
    final boolean[] shouldZeroY = new boolean[m];
    final boolean[] shouldZeroX = new boolean[n];
    for (int i = 0; i<n; i++) {
      for (int j = 0; j<m; j++) {
        if (matrix[i][j] == 0) {
          shouldZeroX[i] = true;
          shouldZeroY[j] = true;
        }
      }
    }

    for (int i = 0; i<n; i++) {
      if (shouldZeroX[i]) {
        for (int j = 0; j<m; j++) {
          matrix[i][j] = 0;
        }
      }
    }
    for (int j = 0; j<m; j++) {
      if (shouldZeroY[j]) {
        for (int i = 0; i<n; i++) {
          matrix[i][j] = 0;
        }
      }
    }

    return matrix;
  }
}
