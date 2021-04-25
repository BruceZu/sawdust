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
  /*
  Understand:
  - From the provided cases the strings in `words` are sorted lexicographically
   by the rules of this new language.
   Note here. It may be not in increasing order. So algorithm need detects this
   kind of issue and exit. As once this case happen no way to figure out
   the required increasing lexicographical order

  - May be there is not a lexicographically increasingly order in it. Or only
   there is a conflicted order

  - In the under building graph presented by  Map out (1:n) and Map in (n:1).
   Note one node may have more than 1 `out`  node, and one node may have more
   than one` in` nodes

  - the result should include all unique letters that were within the input list.

  well sorted case:
  1> Input: words = ["wrt",  "wrf", "er", "ett", "rftt"]
  Output: "wertf"

  2> ["z", "z"]
  Output: "z"

  3> ["ac", "ab", "zc", "zb"]
  Output: "azcb":  duplicated c-b edge

  Wrong sorted case:
  1> Input: words = ["z","x","z"]
  Output: ""
  Explanation: The order is invalid, so return "".

  2> Input: words = ["abc","ab"]
  Output: ""
  Explanation: The order is invalid, so return "".

  Idea:
  Analyse well sorted case <1>
  ["wrt",  "wrf"] ->  t-f
  [ "wrf",  "er"] ->  w-e
  ["er",   "ett"] ->  r-t
  [ "ett","rftt"] ->  e-r

  Algorithm:
  1. collect valid relations from words, exit if detects invalid issue
     during the process
    Skill:
     -  collect valid relations from words: `beak; ` at once after meet a
        pair of not same chars in 2 neighbor words
  2. calculate topological sorted order by Kahn's algorithm or DFS.
     the later need check circle.

  C is the total length of all the words
  N is words.length.  N < C.
  U is the total number of unique letters in words. it is <=26
  O(C) Time
     the topological sorted order is O(V+E),
     V is the number of nodes it is U.
     E is the number of edges it is at most N−1. it is impossible for there
       to be more than one edge between each pair of nodes. With U nodes,
       this means there can't be more than U^2  edges
     O(C)+O(U+min(U^2,N))=O(C+U+min(U^2,N))=O(C)
  O(1) or O(U+min(U^2,N)) space
  */

  /*
   Kahn's algorithm -----------------------------------------------------------
  -  Java: set.removeAll(another set);
  -  Kahn's algorithm(BSF) need update in map:
      -- `for (Character o : out.getOrDefault(c, new HashSet<>())) {`
         The last valid character has not `out`. So in this case provide a
         empty set else it is null.
      -- in.remove(o);
         When the map `in`'s value set is empty remove this map entry
         because at the end the  map `in` size will be used to judge if
         there is circle(invalid topological order)
   */
  public static String alienOrder2(String[] words) {
    Set<Character> all = new HashSet();
    // all char will be take in account.
    Arrays.stream(words)
        .forEach(
            s -> {
              for (char c : s.toCharArray()) all.add(c);
            });
    Map<Character, Set<Character>> out = new HashMap<>();
    Map<Character, Set<Character>> in = new HashMap<>();
    // `in` can be k: int(keep sum of indegree). but it requires
    //   - to avoid duplicated increase the sum
    //   - or let `out` use list value.
    for (int i = 0; i <= words.length - 2; i++) {
      int N = Math.min(words[i].length(), words[i + 1].length());
      if (words[i].length() > words[i + 1].length() && words[i].startsWith(words[i + 1])) return "";
      for (int j = 0; j < N; j++) {
        char u = words[i].charAt(j), d = words[i + 1].charAt(j);
        if (u != d) {
          out.computeIfAbsent(u, k -> new HashSet<>()).add(d);
          in.computeIfAbsent(d, k -> new HashSet<>()).add(u);
          break; // stop at once after meet a pair of not same chars in 2 neighbor words
          // only this pair is valid.
        }
      }
    }
    // Kahn's algorithm
    all.removeAll(in.keySet()); // Note the remove();
    Queue<Character> q = new LinkedList();
    for (Character c : all) {
      q.offer(c);
    }
    StringBuilder result = new StringBuilder();
    while (!q.isEmpty()) {
      Character c = q.poll();
      result.append(c);
      // Note: the last valid character has not `out`. In this case provide a empty
      // set else it is null point
      for (Character o : out.getOrDefault(c, new HashSet<>())) {
        in.get(o).remove(c);
        if (in.get(o).isEmpty()) {
          q.offer(o);
          // Whe the map entry of `in` when value is empty set. Because
          // at the end the  map `in` size will be used to judge if there is
          // circle(invalid topological order)
          in.remove(o);
        }
      }
    }
    if (!in.isEmpty())
      return ""; // or: `result.length()!= all nodes number` which can be got after all is initiated
    return result.toString();
  }

  public static void main(String[] args) {
    alienOrder(new String[] {"ac", "ab", "zc", "zb"});
  }
  // DFS ----------------------------------------------------------------------
  /*
  C is the total length of all the words
  N is words.length.  N < C.
  U is the total number of unique letters in words. it is <=26
  O(C) Time
  O(1) or O(U+min(U^2,N)) space
   */
  public static String alienOrder(String[] words) {
    Map<Character, Set<Character>> out = new HashMap<>();
    for (String word : words) for (char c : word.toCharArray()) out.putIfAbsent(c, new HashSet<>());

    for (int i = 0; i <= words.length - 2; i++) {
      String w = words[i];
      String w2 = words[i + 1];
      if (w.length() > w2.length() && w.startsWith(w2)) return "";
      // Find the first non match
      for (int j = 0; j < Math.min(w.length(), w2.length()); j++) {
        if (w.charAt(j) != w2.charAt(j)) {
          out.get(w.charAt(j)).add(w2.charAt(j));
          break;
        }
      }
    }

    // Step 2: DFS to build up the output list.
    //  contains(k): ever visited: contains, v: whether in current path,
    Map<Character, Boolean> mark = new HashMap<>();
    StringBuilder r = new StringBuilder();
    for (Character c : out.keySet()) if (merged(c, mark, r, out)) return "";
    if (r.length() < out.size()) return "";
    return r.reverse().toString();
  }

  // merged 2 function: find a circle?(1) + calculate Topological Sort Order(2)
  private static boolean merged(
      Character c,
      Map<Character, Boolean> mark,
      StringBuilder r,
      Map<Character, Set<Character>> out) {
    if (mark.containsKey(c)) return mark.get(c);
    mark.put(c, true);
    for (char o : out.get(c)) if (merged(o, mark, r, out)) return true;
    r.append(c);
    mark.put(c, false);
    return false;
  }
}
