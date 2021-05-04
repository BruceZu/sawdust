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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Leetcode30SubstringwithConcatenationofAllWords {

  /*
      1 <= s.length <= 104
      s consists of lower-case English letters.
      1 <= words.length <= 5000
      1 <= words[i].length <= 30
      words[i] consists of lower-case English letters.
     "Return all starting indices of substring(s) in s that is
      a concatenation of each word in words exactly once, in
      any order, and without any intervening characters."
     checking:
      - one of them is null, empty;
      Idea:
       take the word in words, not char, as small unit.
      M is s.length, N is ws.length, L is ws[0] length
      N*L*M/L=M*N
      O(M*N) time, O(N*L) space
  */

  public List<Integer> findSubstring(String S, String[] ws) {
    List<Integer> res = new ArrayList();
    if (S == null || ws == null || S.length() == 0 || ws.length == 0) return res;
    // at least S have one char
    int L = ws[0].length();
    for (int from = 0; from < L; from++) {
      Map<String, Integer> m = new HashMap();
      for (String w : ws) m.put(w, m.getOrDefault(w, 0) + 1);

      int n = ws.length; // required left number of words
      int l = from, r = from; // current window[l, r)
      while (r <= S.length() - L) { // O(S.length()) time
        String w = S.substring(r, r + L);
        if (m.containsKey(w)) {
          if (m.get(w) >= 1) n--;
          m.put(w, m.get(w) - 1);
        }
        r = r + L; // current window[l, r)
        if (r - l == L * ws.length) {
          if (n == 0) res.add(l);
          String lw = S.substring(l, l + L);
          if (m.containsKey(lw)) {
            if (m.get(lw) >= 0) n++;
            m.put(lw, m.get(lw) + 1);
          }
          l = l + L;
        }
      } // m is dirty, nee reset.
    }
    return res;
  }
}
