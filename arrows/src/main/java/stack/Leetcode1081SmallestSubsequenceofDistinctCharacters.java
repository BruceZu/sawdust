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
    */
    int N = s.length();
    int[] left = new int[26]; // not used in sequence
    for (int i = 0; i < N; i++) {
      left[s.charAt(i) - 'a']++;
    }
    // left counts can only show if we can kick if off from stack
    // but it not show if the char has ever been used in result.
    // so need another variable 'used'
    boolean[] used = new boolean[26];
    Stack<Character> st = new Stack();
    st.push('1');
    for (int i = 0; i < N; i++) {
      char c = s.charAt(i);
      int idx = c - 'a';
      if (used[idx]) {
        left[idx]--; // even it is not used in sequence but the available left number should minus 1
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
    while (st.size() > 1) {
      r.append(st.pop());
    }
    return r.reverse().toString();
  }

  public static void main(String[] args) {
    System.out.println(smallestSubsequence("bcbcbcababa"));
  }
}
