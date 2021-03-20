//  Copyright 2021 The KeepTry Open Source Project
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//

package string.palindrome;

/*
Given a string s, you can convert it to a palindrome by adding characters in front of it.
Find and return the shortest palindrome you can find by performing this transformation.

Example 1:
Input: s = "aacecaaa"
Output: "aaacecaaa"

Example 2:
Input: s = "abcd"
Output: "dcbabcd"

Constraints:
    0 <= s.length <= 5 * 104
    s consists of lowercase English letters only.
 */
public class Leetcode214ShortestPalindrome {

  // hash ---------------------------------------------------------------------
  // Basic idea. O(N) time and space
  public static String shortestPalindrome5_1(String str) {
    /*
    0 <= s.length <= 5 * 104
    */
    if (str.length() <= 1) return str;
    char[] s = str.toCharArray();
    char[] s2 = new StringBuilder(str).reverse().toString().toCharArray();
    int N = str.length();
    // hash of substring s[0]...s[i]
    int hash = 0;
    // hash of substring s[N-(i+1)]...S[N-1]
    int h = 0;
    // base use 26, because "s consists of lowercase English letters only."
    int b = 26;
    // modulus Mersenne prime
    int m = 8191;
    // b^{j}%m
    int em = 1;
    // last char index of longest palindrome starting from str[0]
    int max = 0;
    for (int i = 0; i < N; i++) {
      int c = s[i] - 'a';
      hash = hash * b % m + c;

      int c2 = s2[N - 1 - i] - 'a';
      h = (c2 * em % m + h) % m;
      em = b * em % m;
      if (hash == h) {
        // double check by compare 2 sub-string values
        boolean same = true;
        for (int x = 0; x <= i; x++) {
          if (s[x] != s2[N - 1 - i + x]) { // Note: it is N - 1 - i + x here.
            same = false;
            break;
          }
        }
        if (same) max = i;
      }
    }
    return new StringBuilder(str.substring(max + 1)).reverse().append(str).toString();
  }

  // Hash O(N) time O(1) space ------------------------------------------------
  public static String shortestPalindrome5_2(String s) {
    if (s.length() <= 1) return s;
    int N = s.length(), hash = 0, h = 0, b = 26, m = 8191, em = 1;
    int max = 0;
    for (int i = 0; i < N; i++) {
      int c = s.charAt(i) - 'a';
      hash = hash * b % m + c;

      h = (c * em % m + h) % m;
      em = b * em % m;
      if (hash == h) {
        int l = 0, r = i;
        while (l <= r && s.charAt(l++) == s.charAt(r--))
          ;
        if (l > r) max = i;
      }
    }
    return new StringBuilder(s.substring(max + 1)).reverse().append(s).toString();
  }
  // hash ---------------------------------------------------------------------
  // O(N) time and O(1) space
  // Same as JDK String.hashcode()
  // To use base 31 and default modulus is Integer.MAX_VALUE+1(it is -1 actually)
  public static String shortestPalindrome5_3(String s) {
    if (s.length() <= 1) return s;
    int N = s.length(), hash = 0, h = 0, b = 31, em = 1, max = 0;
    for (int i = 0; i < N; i++) {
      int c = s.charAt(i);
      hash = hash * b + c;

      h = c * em + h;
      em = b * em;
      if (hash == h) {
        int l = 0, r = i;
        while (l <= r && s.charAt(l++) == s.charAt(r--))
          ;
        if (l > r) max = i;
        // Here if no double check by compare 2 sub-string values
        // Leetcode still accept it and report
        // "Runtime: 1 ms, faster than 100.00% of Java online submissions for Shortest Palindrome."
        // But this only show Leetcode's test cases are not enough to spot this bug
        //
      }
    }
    return new StringBuilder(s.substring(max + 1)).reverse().append(s).toString();
  }

  // With Manacher's algorithm, O(N) time and space ---------------------------
  public static String shortestPalindrome4(String s) {
    if (s == null || s.length() <= 1) return s;
    int[] r = Manacher.getRadiusOfVirtualTranslatedStringOf(s);

    // '0 <= s.length <= 5 * 104'. So use int is enough
    int N = 2 * s.length() + 1;
    int w = 1; // at least 1: the palindromic sub-string with the first char
    for (int i = 0; i < N; i++) {
      if ((i - r[i]) / 2 == 0) {
        w = Math.max(w, r[i]);
      }
    }
    return new StringBuilder(s.substring(w)).reverse().toString() + s;
  }

  // Widest border idea, O(N) time and space ----------------------------------
  public String shortestPalindrome3(String s) {
    if (s == null || s.length() <= 1) return s;
    /*
    Widest border, or widest prefix and suffix of  s + '#' + s'
    It does not work to apply widest border idea directly to s + s' reverse
    See case of 'aabba', N=5,
            concatenate it with its reverse to be: 'aabbaabbaa',
            w[]:  0100123456 ,
            w[2*N - 1]==6 6 > N, s.substring(6) will out of index.
         or case 'aaaaa'  concatenate it with its reverse: 'aaaaaaaaaa',
    So, use s + '#' + s' reverse.
    Why select '#' and it works?
    Because `s consists of lowercase English letters only`
    */
    String concatenated = s + "#" + new StringBuilder(s).reverse().toString();
    char[] I = concatenated.toCharArray();
    int N = concatenated.length();

    int w[] = new int[N];
    for (int d = 0; d < N; d++) {
      int i = d;
      while (0 < i && I[w[i - 1]] != I[d]) i = w[i - 1];
      if (0 < i) w[d] = w[i - 1] + 1;
    }
    return new StringBuilder(s.substring(w[N - 1])).reverse().toString() + s;
  }

  // KMP(Knuth–Morris–Pratt), O(N) time and space. Find s from its reverse ----
  public String shortestPalindrome2(String s) {
    if (s == null || s.length() <= 1) return s;
    int N = s.length();
    // '0 <= s.length <= 5 * 104'
    // 's consists of lowercase English letters only.'
    char[] I = s.toCharArray();
    int[] w = new int[N];
    for (int d = 0; d < N; d++) {
      int i = d;
      while (0 < i && I[w[i - 1]] != I[d]) i = w[i - 1];
      if (0 < i) w[d] = w[i - 1] + 1;
    }

    char[] S = new StringBuilder(s).reverse().toString().toCharArray();
    int u = 0, d = 0;
    while (true) {
      while (u < N && d < N && S[u] == I[d]) {
        u++;
        d++;
      }
      // if(d == N)..., no happen in this case
      if (u == N) {
        return new StringBuilder(s.substring(d)).reverse().toString() + s;
      }
      if (d == 0) u++;
      else d = w[d - 1];
    }
  }

  // 2 pointers. O(N^2) time and O(N) space --------------------------------------
  public String shortestPalindrome1(String s) {
    if (s == null || s.length() <= 1) return s;
    int N = s.length();
    int l = 0;
    for (int r = N - 1; 0 <= r; r--) {
      // "s consists of lowercase English letters only."
      if (s.charAt(l) == s.charAt(r)) {
        l++;
      }
    }
    // Let the size of the max palindrome starting from s[0] is l.
    // Now Palindrome's attribute makes l be in scope [l+1, N].
    // - If it is on the N, then each move step of r has made l move a step too
    //   That means the s is a palindrome
    // - Else current s is cut into [0,l),[l,N-1] 2 parts and
    //   let [0,l) go on with recursion. [0,l)  is shorter than s
    //   this guarantee the recursion will reach an end at last.
    if (l == N) return s;
    String tail = s.substring(l);
    return new StringBuilder(tail).reverse().toString()
        + shortestPalindrome1(s.substring(0, l))
        + tail;
  }
}
