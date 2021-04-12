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

import java.util.Stack;

public class Leetcode1249MinimumRemovetoMakeValidParentheses {
  public String minRemoveToMakeValid(String S) {
    /*

        1 <= s.length <= 10^5
        s[i] is one of  '(' , ')' and lowercase English letters.

    */

    /*
    A: remove invalid ')'
     1. search from left to right: ignore all ')' before meet the first '(': stack is empty.
     2. continue to search
      - '(':  push in stack.
      - ')':  pop stack top. if stack is empty ignore it like 1.
    B: if stack is not empty. go right to left with same logic to remove invalid '(
       alternative: if keep index in string builder in stack then now the invalid '(' can be deleted directly.

    minimum: means only delete invalid ones
    // O(N) Time and space
    */

    StringBuilder sb = new StringBuilder();
    char[] s = S.toCharArray();
    Stack<Character> st = new Stack();
    // O(N)
    for (char c : s) {
      if (st.isEmpty() && c == ')') continue;
      else {
        if (c == '(') st.push(c);
        if (c == ')') st.pop();
        sb.append(c);
      }
    }
    if (st.isEmpty()) return sb.toString();
    s = sb.toString().toCharArray();
    sb = new StringBuilder();
    st = new Stack();
    // O(N)
    for (int i = s.length - 1; i >= 0; i--) {
      char c = s[i];
      if (st.isEmpty() && c == '(') continue;
      else {
        if (c == ')') st.push(c);
        if (c == '(') st.pop();
        sb.append(c);
      }
    }
    // O(N)
    return sb.reverse().toString(); // need reverse
  }
}
