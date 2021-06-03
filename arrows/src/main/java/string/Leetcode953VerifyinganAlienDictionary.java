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

package string;

import java.util.HashMap;
import java.util.Map;

public class Leetcode953VerifyinganAlienDictionary {
  /*
   "1 <= words.length <= 100
   1 <= words[i].length <= 20
   order.length == 26
   All characters in words[i] and order are English lowercase letters."

   Understanding:
    use Map or array int[26]

    O(N*M) time M is the longest word in words and N is words length
  */
  public boolean isAlienSorted(String[] words, String order) {
    // TODO: check null
    Map<Character, Integer> d = new HashMap();
    for (int i = 0; i < order.length(); i++) d.put(order.charAt(i), i + 1); // O(N)
    // O(N*M) M is the longest word in words
    for (int i = 0; i <= words.length - 2; i++) {
      if (!inOrder(words[i], words[i + 1], d)) return false;
    }
    return true;
  }

  private boolean inOrder(String l, String r, Map<Character, Integer> d) {
    int i = 0, j = 0;
    while (i < l.length() && j < r.length()) {
      // Note the meaning of lexicographically order
      if (d.get(l.charAt(i)) < d.get(r.charAt(j))) return true;
      if (d.get(l.charAt(i)) > d.get(r.charAt(j))) return false;
      // same
      i++;
      j++;
    }
    return i == l.length();
  }
}
