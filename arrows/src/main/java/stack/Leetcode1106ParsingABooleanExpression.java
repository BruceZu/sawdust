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

package stack;

import java.util.Stack;

public class Leetcode1106ParsingABooleanExpression {
  /*
  1 <= expression.length <= 20000
   expression[i] consists of characters in {'(', ')', '&', '|', '!', 't', 'f', ','}.
   expression is a valid expression representing a boolean, as given in the description.
   TODO check above 3 cases
   Idea: recursion or stack
  */

  public boolean parseBoolExpr(String expression) {
    if (expression == null) return false; // let it is false by default
    return dfs(expression, null, new int[1]);
  }
  /*
  Return the boolean value at current level and from i index
  If current char is `f` or `t`:
  - <1> Need to know if current is the last element of a `&()`, `|()` and `!()`group by
        check sub recursion result == null. `!()` group still use this way to make
        sure the index move one step to keep on the right location for the following
        calculation
  - <2> Need to know the relation is "&&" or “||” in current `&()` or `|()`group
        It is not used to resolve <1>, not be used when not in `&()` or `|()`group
   isAndWithSuccessor:
   - true  if current `t` or `f` is in a `&()` group
   - false if current `t` or `f` is in a `|()` group
   - null  if current `t` or `f` is not in either `&()` group or `|()` group
  int[] i is current index. It can be a global variable.
  O(N) time and space
   */
  private static Boolean dfs(String s, Boolean and, int[] i) {
    if (i[0] == s.length()) return null;
    char c = s.charAt(i[0]);

    if (c == ')') {
      i[0]++;
      return null;
    }
    if (c == ',') {
      i[0]++;
      return dfs(s, and, i);
    }

    if (c == 't') {
      i[0]++;
      Boolean lgv = dfs(s, and, i);
      if (lgv == null) return true;
      return and ? (true && lgv) : (true || lgv);
    }
    if (c == 'f') {
      i[0]++;
      Boolean lgv = dfs(s, and, i); // left group value at the current level
      if (lgv == null) return false;
      return and ? (false && lgv) : (false || lgv);
    }

    boolean r = false;
    i[0] += 2;
    if (c == '!') r = !dfs(s, null, i); // also need check sub where let index move one step
    if (c == '&') r = dfs(s, true, i);
    if (c == '|') r = dfs(s, false, i);
    Boolean lgv = dfs(s, and, i);
    if (lgv == null) return r;
    return and ? (r && lgv) : (r || lgv);
  }
  // --------------------------------------------------------------------------
  // stack
  // O(N) time and space
  public static boolean parseBoolExpr1(String S) {
    Stack<Character> s = new Stack<>();
    for (char c : S.toCharArray()) {
      if (c == ')') {
        boolean hasT = false, hasF = false;
        while (s.peek() == 't' || s.peek() == 'f') {
          hasT |= s.peek() == 't';
          hasF |= s.peek() == 'f';
          s.pop();
        }
        switch (s.pop()) {
          case '!':
            s.push(hasF ? 't' : 'f');
            break;
          case '&':
            s.push(hasF ? 'f' : 't');
            break;
          case '|':
            s.push(hasT ? 't' : 'f');
        }
      } else if (c != ',' && c != '(') s.push(c);
    }
    return s.peek() == 't' ? true : false;
  }
}
