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

package string.parentheses;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Leetcode301RemoveInvalidParentheses {
  /*
   'remove the minimum number of invalid parentheses to make the input string valid.'
   'Return all the possible results. You may return the answer in any order.'
   '
     1 <= s.length <= 25
     s consists of lowercase English letters and parentheses '(' and ')'.
     There will be at most 20 parentheses in s.
   '

  Understand:
   - the minimum number = | number'(' - number')' |
   - some remove options can get same result.
   - valid: 1> number'(' == number')' : '())' or '(()' is wrong
            2> paired : ')(' is wrong

  Idea:
    when the string is invalid, which one should be removed?
    backtracking:
      Every bracket has two options:
      - remove it: keep the removed number of '(' and ')'.
      - keep it: when current char is ')' and it has already had the same number of '('.
                 Stop trying.
                 Thus always keep the number of '(' >= ')', plus removing
                 only the expected number of '(' and ')' thus at last the '(' and ')'
                 are paired at last.

    at the end of the string to check whether the current expression is valid by
    checking the removed number of '(' and ')' are expected only.

  Skill:  keep ')' only when the number of '(' >= that of ')'.
          E.g. if the number of '('   is 0, current ')' will be took as redundant.

  O(2^N) time. When the string has only parentheses.
  O(N) space. The intermediate space used by recursive stack. the max depth of recursion.
              not considering the space required to store the valid expressions.

  Limitation:
    We cannot determine which of the parentheses are misplaced.
    Because some remove options can get the same result.
    But have to find all of them.

    The result expressions will have the same length and have the same number
    of characters removed. Because the expected number of '(' and ')' are to
    be removed from the original expression can be known.
  */
  // backtracking
  public List<String> removeInvalidParentheses(String s) {
    int l = 0, r = 0;
    for (int i = 0; i < s.length(); i++) {
      char c = s.charAt(i);
      if (c == '(') l++;
      if (c == ')') {
        if (l == 0) r++;
        else l--;
      }
    }
    Set<String> result = new HashSet();
    backtracking(s, new StringBuilder(), 0, 0, 0, l, r, result);
    return new ArrayList(result);
  }

  // l, r: expected number of ( and ) left and should be removed from expression string
  // L, R: number of ( and ) added to string s
  private void backtracking(
      String s, StringBuilder sb, int from, int L, int R, int l, int r, Set<String> result) {
    if (l < 0 || r < 0) return;
    if (from == s.length()) {
      if (l == 0 && r == 0) result.add(sb.toString());
      return;
    }
    // no loop, at each char to do recursion
    char c = s.charAt(from);
    if (c != '(' && c != ')') {
      sb.append(c);
      backtracking(s, sb, from + 1, L, R, l, r, result);
      sb.deleteCharAt(sb.length() - 1);
    } else {
      // skip/remove it. s: no append, not delete
      backtracking(s, sb, from + 1, L, R, c == '(' ? l - 1 : l, c == ')' ? r - 1 : r, result);
      // keep it
      sb.append(c);
      if (c == '(') backtracking(s, sb, from + 1, L + 1, R, l, r, result);
      else if (L > R) backtracking(s, sb, from + 1, L, R + 1, l, r, result);
      sb.deleteCharAt(sb.length() - 1);
    }
  }
  // Alternative -------------------------------------------------------------
  /*
  Idea:
   1. Remove redundant `)` :
   From left to right to check the status of paired  `(` and `)` by calculating
   the number of `(` in `l` and number of `)` in `r`
   and keeping a status:  `l >= r` by balanced it once `r=l+1`.

   2. Remove redundant `(` :
   From right to left, or reverse the s processed by step 1 and from left to right,
   check the status of paired  `)` and  `(` by calculating ...same logic as step 1...
   Note: when do the balance: For avoid producing duplicated result, only remove the
        first R in the series of consecutive R. Note the current express string may have
        more consecutive R at [J, i].  E.g.: `()())()` -> `(())()`  or `()()()`,
        The condition  `j==J` comes from the following `s.charAt(j - 1) != R`
        `J` is the first index j can start,  with  `j==J` to avoid invalid index
        pointer of` j-1` index, e.g.: `)(`
    */

  public List<String> removeInvalidParentheses2(String s) {
    List<String> result = new ArrayList();
    balance(s, 0, 0, '(', ')', result);
    return result;
  }

  private void balance(
      String s, int checkFrom, int searchRemoveFrom, char L, char R, List<String> result) {
    int p = 0; // pair status
    for (int i = checkFrom; i < s.length(); i++) {
      char c = s.charAt(i);
      if (c == L) p++;
      if (c == R) p--;
      if (p >= 0) continue;
      // need balance
      for (int j = searchRemoveFrom; j <= i; j++) {
        if (s.charAt(j) == R && (j == searchRemoveFrom || s.charAt(j - 1) != R)) {
          balance(s.substring(0, j) + s.substring(j + 1), i, j, L, R, result);
        }
      }
      return;
    }

    // if (L == '(') redundant ')' is removed then now to remove redundant '('
    if (L == '(') balance(new StringBuilder(s).reverse().toString(), 0, 0, ')', '(', result);
    // else redundant '(' is removed too. ready to be added in result.
    else result.add(new StringBuilder(s).reverse().toString());
  }
}
