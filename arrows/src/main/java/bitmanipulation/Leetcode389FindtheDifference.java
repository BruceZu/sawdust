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

package bitmanipulation;

import java.util.HashMap;

public class Leetcode389FindtheDifference {

  /*

  0 <= s.length <= 1000
  t.length == s.length + 1
  s and t consist of lower-case English letters.

  Ia:
  O(N) time
  O(1) space: lower-case English letters
   */
  public char findTheDifference(String s, String t) {
    // TODO check null
    HashMap<Character, Integer> m = new HashMap<>();
    for (int i = 0; i < s.length(); i++) {
      char c = s.charAt(i);
      m.put(c, m.getOrDefault(c, 0) + 1);
    }

    for (int i = 0; i < t.length(); i += 1) {
      char c = t.charAt(i);
      int n = m.getOrDefault(c, 0);
      if (n == 0) return c; // 2 scenario: m does not contain the key, or not.
      else m.put(c, n - 1);
    }
    return '\0'; // not reach here
  }

  public char findTheDifference2(String s, String t) {
    // Initialize c with 0, because 0 ^ X = X
    char c = 0;
    // XOR all the characters of both s and t.
    for (int i = 0; i < s.length(); i += 1) c ^= s.charAt(i);
    for (int i = 0; i < t.length(); i += 1) c ^= t.charAt(i);
    return c;
  }
}
