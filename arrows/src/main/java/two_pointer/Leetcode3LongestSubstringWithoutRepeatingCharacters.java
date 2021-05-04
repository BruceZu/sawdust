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

package two_pointer;

public class Leetcode3LongestSubstringWithoutRepeatingCharacters {
  /*

  0 <= s.length <= 5 * 104
  s consists of English letters, digits, symbols and spaces.
  https://medium.com/@vanvlymenpaws/ascii-vs-unicode-4174def5c09d
  checking s:
    null, empty
  Idea:
    data structure:
    - 2 pointer for current window [l, i], initial from 0 index
    - a map keeping at most 1 frequency number of char in current window
    algorithm:
    if the frequency  number of current step in char at index i reach 2
    then move ahead left point till the frequency  number of current step in char at index i
    back to 1
    O(N) time, and space
   */
  public static int lengthOfLongestSubstring(String s) {
    if (s == null || s.length() == 0) return 0;
    // at least length is 1
    int[] f = new int[128]; // https://medium.com/@vanvlymenpaws/ascii-vs-unicode-4174def5c09d

    char[] S = s.toCharArray();
    int l = 0, i = 0; // current window [l, i]
    int maxL = Integer.MIN_VALUE;
    while (i < s.length()) {
      int c = S[i];
      f[c]++;
      while (f[c] == 2) {
        int lc = S[l];
        f[lc]--;
        l++;
      }
      // current l may be at the repeated char
      maxL = Math.max(i - l + 1, maxL);
      i++;
    }
    return maxL;
  }
}
