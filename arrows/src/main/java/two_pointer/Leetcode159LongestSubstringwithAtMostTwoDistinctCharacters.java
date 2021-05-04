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

import java.util.HashMap;
import java.util.Map;

public class Leetcode159LongestSubstringwithAtMostTwoDistinctCharacters {

  /*
   1 <= s.length <= 104
   s consists of English letters.
   "Given a string s, return the length of the longest substring
    that contains at most two distinct characters."
  TODO:  Check null, empty
  Alternative special way is check current char with char on
   - index l
   - current index -1
  But it is not general solution.
  O(N) time O(2) space
   */
  public static int lengthOfLongestSubstringTwoDistinct(String s) {
    Map<Character, Integer> map = new HashMap<>();
    int l = 0, i = 0, r = 0; // current window[l,i]
    while (i < s.length()) {
      char c = s.charAt(i);
      map.put(c, map.getOrDefault(c, 0) + 1);
      while (map.size() == 3) {
        char lc = s.charAt(l);
        map.put(lc, map.get(lc) - 1);
        if (map.get(lc) == 0) map.remove(lc); // O(1)
        l++;
      }
      r = Math.max(r, i - l + 1);
      i++;
    }
    return r;
  }
}
