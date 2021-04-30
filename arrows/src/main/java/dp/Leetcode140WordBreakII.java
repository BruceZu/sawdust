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

    // test cases:
    // defend cases:
    //    null,
    //    duplicated value in dictionary
    //    "亚洲"(industry code)
    // special cases:
    //    str does not contain any word in dictionary;
    //     ""
    //    str contain only one word not in dictionary;

    /*
    Idea:
      DFS(backtracking, DP top-down which need a cache)
      if current str is "" then return "";
      For each word the current str starts with in dictionary:
        merge it with all substring returned sentences(if existing) to result;
        if no sub sentences, return empty result
        if no word found the cur str starts with return empty result

     TODO: O(?) time and space. depends on directory and str.
    */

    Set<String> d = new HashSet<>();
    wordDict.stream().forEach(w -> d.add(w));
    // assume no hash collision; or `d.addAll(wordDict);`
    return dfs(s, d, new HashMap<>());
  }

  List<String> dfs(String s, Set<String> d, Map<String, List<String>> c) {
    // when to stop if dfs
    if (c.containsKey(c)) return c.get(s);
    List<String> r = new LinkedList();
    if (s.isEmpty()) {
      r.add("");
      // previous part of current sentence is valid and end. its meaning is not same as
      // empty list which means
      // can find word in dictionary the current s starts with and
      // thus current sentence is invalid
      return r;
    }
    for (String w : d) { // for each w from dictionary to check if s starts with it.
      if (s.startsWith(w)) {
        List<String> subs = dfs(s.substring(w.length()), d, c);
        for (String stc : subs) {
          String v = stc.isEmpty() ? "" : " " + stc;
          r.add(w + v);
        }
      }
    }
    c.put(s, r);
    return r; // empty list if no word the current s starts with
  }
}
