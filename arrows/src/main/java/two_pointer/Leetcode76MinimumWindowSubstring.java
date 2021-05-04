//  Copyright 2017 The keepTry Open Source Project
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

package two_pointer;

public class Leetcode76MinimumWindowSubstring {
  /*
  1 <= s.length, t.length <= 105
  s and t consist of English letters.
  Checking:
   s or t is null
   s length < t length
   s or t is empty string
   Idea: slide window, 2 pointer
   O(max{M,N}) time and space, M, N is the length of s and t
  */
  public static String minWindow(String s, String t) {
    if (s == null || t == null || s.length() == 0 || s.length() < t.length()) return "";
    int[] m = new int[123];
    for (char c : t.toCharArray()) {
      m[c - 'A']++;
    }
    int l = 0, i = 0, L = t.length(); // current window index[ l, i], L is required left length
    char[] S = s.toCharArray();
    int r = Integer.MAX_VALUE; // minimum window's string length
    int rStart = -1;
    while (i < S.length) {
      int c = S[i] - 'A';
      if (m[c] >= 1) L--;
      m[c]--;

      while (L == 0) {
        if (i - l + 1 < r) {
          r = i - l + 1;
          rStart = l;
        }
        c = S[l] - 'A';
        if (m[c] >= 0) L++;
        m[c]++;
        l++;
      }
      i++;
    }
    return r == Integer.MAX_VALUE ? "" : s.substring(rStart, rStart + r);
  }
}
