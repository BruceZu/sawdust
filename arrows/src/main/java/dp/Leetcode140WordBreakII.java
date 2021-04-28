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

package dp;

import java.util.*;

public class Leetcode140WordBreakII {
  public List<String> wordBreak(String s, List<String> wordDict) {
    /*
    that the same word in the dictionary may be reused multiple times in the segmentation.

    1 <= s.length <= 20
    1 <= wordDict.length <= 1000
    1 <= wordDict[i].length <= 10
    s and wordDict[i] consist of only lowercase English letters.
    All the strings of wordDict are unique.
     */
    // TODO: check corner caces
    // O(?) time and space
    Set<String> d = new HashSet();
    d.addAll(wordDict);

    return dfs(s, d, new HashMap<>());
  }

  List<String> dfs(String s, Set<String> d, HashMap<String, List<String>> cache) {
    if (cache.containsKey(s)) return cache.get(s);
    List<String> ss = new LinkedList<>(); // sentences
    if (s.length() == 0) {
      ss.add("");
      return ss;
    }
    for (String w : d) {
      if (s.startsWith(w)) {
        List<String> subss = dfs(s.substring(w.length()), d, cache);
        for (String subs : subss) ss.add(w + (subs.isEmpty() ? "" : " ") + subs);
      }
    }
    cache.put(s, ss);
    return ss;
  }
}
