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

public class Leetcode1081SmallestSubsequenceofDistinctCharacters {
  /*
  Return the lexicographically smallest subsequence of s
  that contains all the distinct characters of s exactly once.

  Note: This question is the same as
   316: https://leetcode.com/problems/remove-duplicate-letters/
  */

  public static String smallestSubsequence(String s) {
    /*
       1 <= s.length <= 1000
       s consists of lowercase English letters.
       TODO: corner cases validation
    */
    int N = s.length();
    int[] left = new int[26]; // not used in sequence
    for (int i = 0; i < N; i++) {
      left[s.charAt(i) - 'a']++;
    }
    /*
    The stack values represent the lexicographically smallest subsequence
    of string `s` that contains all the distinct characters of s exactly once.

    The left available number of a char means only that whether the char can
    be kick off from the stack when another char with precedence lexicographically
    order appear and try to replace it. The left available number does not
    mean whether the char has ever been used in the sequence stack.
    So another variable 'used' is used for this reason.

    If the current char has been used in sequence stack, then it will not be used
    again in the sequence stack. Reminder here is because it is skipped here and
    its left available number should minus 1
    */
    boolean[] used = new boolean[26];
    Stack<Character> st = new Stack();
    st.push('1'); // Use dummy head char "1" in sequence stack to make algorithm easy.
    for (int i = 0; i < N; i++) {
      char c = s.charAt(i);
      int idx = c - 'a'; // index in `used`
      if (used[idx]) {
        left[idx]--;
        continue;
      }
      while (st.peek() >= c && left[st.peek() - 'a'] != 0) {
        used[st.peek() - 'a'] = false;
        st.pop();
      }
      st.push(c);
      left[idx]--;
      used[idx] = true;
    }
    //  st.toString() contains '[,',',']'
    StringBuilder r = new StringBuilder();
    while (st.size() > 1) { // not include the dummy head.
      r.append(st.pop());
    }
    return r.reverse().toString();
  }

  public static void main(String[] args) {
    System.out.println(smallestSubsequence("bcbcbcababa"));
  }
}
