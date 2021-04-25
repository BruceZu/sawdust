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

package graph.directed_graphs.topological_sort;

import java.util.*;

public class Leetcode269AlienDictionary {
  public static String alienOrder(String[] words) {
    Set<Character> all = new HashSet();
    Arrays.stream(words)
        .forEach(
            s -> {
              for (char c : s.toCharArray()) {
                all.add(c); // all char will be take in account.
              }
            });
    Map<Character, Set<Character>> out = new HashMap<>();
    Map<Character, Set<Character>> in = new HashMap<>();
    // it can be k: int(sum). but it requires to avoid duplicated increase the sum
    for (int i = 0; i <= words.length - 2; i++) {
      int N = Math.min(words[i].length(), words[i + 1].length());
      boolean allSame = true;
      for (int j = 0; j < N; j++) {
        char u = words[i].charAt(j), d = words[i + 1].charAt(j);
        if (u != d) {
          allSame = false;
          out.computeIfAbsent(u, k -> new HashSet<>()).add(d);
          in.computeIfAbsent(d, k -> new HashSet<>()).add(u);
          break; // only this pair is valid.
        }
      }
      if (allSame && words[i].length() > words[i + 1].length()) return "";
    }

    all.removeAll(in.keySet()); // Note the remove();
    Queue<Character> q = new LinkedList();
    for (Character c : all) {
      q.offer(c);
    }
    StringBuilder result = new StringBuilder();
    while (!q.isEmpty()) {
      int size = q.size();
      while (size-- > 0) { // Note it is > 0 . left size
        Character c = q.poll();
        result.append(c);
        // c->o(s)
        // Note: the last valid character has not `out`. provide a empty
        // set else it is null
        for (Character o : out.getOrDefault(c, new HashSet<>())) {
          in.get(o).remove(c);
          if (in.get(o).isEmpty()) {
            q.offer(o);
            in.remove(o); // for judge at the end if there is circle: invalid topological order
          }
        }
      }
    }
    if (!in.isEmpty()) return "";
    return result.toString();
  }
}
