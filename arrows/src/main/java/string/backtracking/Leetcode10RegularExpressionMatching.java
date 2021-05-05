//  Copyright 2016 The Sawdust Open Source Project
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

package string.backtracking;

public class Leetcode10RegularExpressionMatching {
  /*
  Given an input string (s) and a pattern (p),
  implement regular expression matching with support for '.' and '*' where:
      '.' Matches any single character.
      '*' Matches zero or more of the preceding element.

  The matching should cover the entire input string (not partial).
  Implement regular expression matching with support for '.' and '*'.

      0 <= s.length <= 20
      0 <= p.length <= 30
      s contains only lowercase English letters.
      p contains only lowercase English letters, '.', and '*'.
   It is guaranteed for each appearance of the character '*',
   there will be a previous valid character to match.
   */

  /*
  Idea recursion:
    Observer:
    ` p contains only lowercase English letters, '.', and '*'
    current char is :
      a char, '.', or '*'
    need care the next one:
      a char, '.', or '*'
    (a). If the next one is `*`
    then current 2 char can be
     `a char*` : text can be cut: 0, 1, 2, ... any the  `a char` and continue compare
              the left text with p cut `a char*`
     .*:  text can be cut: 0, 1, 2, ... any char and continue compare
              the left text with p cut `.*`
     (no **) as " It is guaranteed for each appearance of the character '*',
   there will be a previous valid character to match."

      e.g. text= aaac, p=a*c
      it is equal to:
             aaac vs c ||
              aac vs c ||
               ac vs c ||
                c vs c
      Note it is equal to:   aaac vs c  || aac vs a*c
      `aac vs a*c` happen until current char of text is same as the current char of pattern
       and ends up with text does not starts with `a`.
      e.g. text= abc, p=.*c
      it is equal to:
             abc vs c ||
              bc vs c ||
               c vs c ||
               ""vs c
      Note it is equal to:   abc vs c  || bc vs .*c
      `bc vs .*c` always happen as any current char of text match  the current char of pattern which is '.'
       and ends up with text is empty.
    (b). If the next one is not `*`
       current is a char: compare with current char of text
       or current is '.' it match any current char of text


  p: pattern,
  s: text
  Idea
   1> * can not be exits independently so
   only check curent char is . or commone small case letter
   then check next char is * or not`
   2> according to the definition of `.*` and `x*
   we check them firstly before checking  curent char is . or commone small case letter
     21>  `.*` or `x*
        these 2 char can be ignore according to definitation
        or if there is first char match which only apply to current char of pattern is not .
        then we can minus the matched char of text and compared it with current x* or .*
     22> for the common char of text and current char of pattern: common char or .
         match then continue next substring of text and pattern
         else return false
     stop condition or recursion
     till one of them become empty
       if both are empty then return true;
       if pattern is empty only return false
       if pattern is empty only contiue as the current pattern can be .*

  Check cases:
    s= "",   p= "b*"
    s= "ab", p= ".*c"
    s= "aa", p= "a*aa"
   without cache the O(?) time and space?
   */
  public boolean isMatch(String s, String p) {
    if (s == null || p == null) return false;
    if (p.isEmpty()) return s.isEmpty();

    // p is not empty,  s? not sure
    boolean f = !s.isEmpty() && (s.charAt(0) == p.charAt(0) || p.charAt(0) == '.');
    if (p.length() >= 2 && p.charAt(1) == '*') {
      return isMatch(s, p.substring(2)) || f && isMatch(s.substring(1), p);
    } else {
      return f && isMatch(s.substring(1), p.substring(1));
    }
    // not reach here
  }

  // DP Top Down: add cache to isMatch()---------------------------------------
  public boolean isMatch2(String s, String p) {
    // use Boolean not boolean to use null value to show not set yet
    Boolean[][] cache = new Boolean[s.length() + 1][p.length() + 1];
    return dp(0, 0, s, p, cache);
  }
  // i is for text, j is for pattern
  public boolean dp(int i, int j, String s, String p, Boolean[][] cache) {
    if (cache[i][j] != null) return cache[i][j] == true;
    boolean r;
    int M = s.length(), N = p.length();
    if (j == N) r = i == M;
    else {
      boolean f = (i < M && (p.charAt(j) == s.charAt(i) || p.charAt(j) == '.'));
      if (j + 1 < N && p.charAt(j + 1) == '*')
        r = (dp(i, j + 2, s, p, cache) || f && dp(i + 1, j, s, p, cache));
      else r = f && dp(i + 1, j + 1, s, p, cache);
    }
    cache[i][j] = r;
    return r;
  }

  // compare cur char back tracking -------------------------------------------
  public static boolean isMatch3(String str, String p) {
    return f(str.toCharArray(), 0, p.toCharArray(), 0);
  }
  // compare cur char back tracking
  private static boolean f(char[] s, int si, char[] p, final int pi) {
    if (pi == p.length && si == s.length) return true;
    if (pi == p.length) return false; // e.g. 'a', 'ab*'

    // pi is valid index now, check cur char pair
    char pic = p[pi];
    if (pi + 1 < p.length && p[pi + 1] == '*') {
      if (f(s, si, p, pi + 2)) return true;
      else {
        while (si < s.length && (s[si] == pic || pic == '.')) {
          ++si;
          if (f(s, si, p, pi + 2)) return true;
        }
        return false;
      }
    }
    // only when pattern is not .* or x*
    // can check si index is s.length or not .e.g. text=a, pattern=ab*
    if (si == s.length) return false;
    // si is valid index now
    if (s[si] == pic || pic == '.') // else can be saved
    return f(s, si + 1, p, pi + 1);
    else return false;
  }

  // DP bottom up------------------------------------------------------------------------
  // O(MN) time and space, M ans N is length of text and pattern
  public boolean isMatch4(String S, String P) {
    int M = S.length(), N = P.length();
    boolean[][] dp = new boolean[M + 1][N + 1];
    dp[M][N] = true; // initial value, handle from backend to front end, decided by .* or x*

    for (int s = M; s >= 0; s--) { // empty text vs .* or x*
      for (int p = N - 1; p >= 0; p--) {
        boolean f = (s < M && (P.charAt(p) == S.charAt(s) || P.charAt(p) == '.'));
        if (p <= N - 2 && P.charAt(p + 1) == '*') {
          dp[s][p] = dp[s][p + 2] || f && dp[s + 1][p];
        } else {
          dp[s][p] = f && dp[s + 1][p + 1];
        }
      }
    }
    return dp[0][0];
  }
}
