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

package doublepoint;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Leetcode1782CountPairsOfNodes {
  public static int[] countPairs(int n, int[][] edges, int[] qs) {
    /*
    'an undirected graph with n nodes,
     edges[i] = [ui, vi] indicates undirected edge between ui and vi.
     array queries: The answer to the jth query is the number of pairs of
     nodes (a, b) that satisfy the following conditions:
       - a < b
       - cnt > queries[j], where cnt is the number of edges incident to a or b.
     '

    'Note that there can be repeated edges.'

    2 <= n <= 2 * 104
    1 <= edges.length <= 105
    1 <= ui, vi <= n
    ui != vi
    1 <= queries.length <= 20
    0 <= queries[j] < edges.length


     This mean
      -  Edges number >= 1 between 2 nodes.
      -
     Idea:
       1 need to know each nodes' edges, sorted nodes by its edges, then possible pairs sum will appear after
         applying 2 pointer algorithm.
       2 need to know the common shard edges between 2 give nodes. Not matched pairs sum2
         are those pair in pair sum but
          edges number of node a and b  - shared edges <= queries[j]
       3. answer = sum -sum2;
     */
    int[] es = new int[n + 1], sorted = new int[n + 1];
    Map<Integer, Integer>[] shared = new Map[n + 1];
    //  O(|E|) time
    for (int[] e : edges) {
      sorted[e[0]] = es[e[0]] = es[e[0]] + 1;
      sorted[e[1]] = es[e[1]] = es[e[1]] + 1;
      int n1 = Math.min(e[0], e[1]), n2 = Math.max(e[0], e[1]);
      if (shared[n1] == null) shared[n1] = new HashMap();
      shared[n1].put(n2, shared[n1].getOrDefault(n2, 0) + 1);
    }
    Arrays.sort(sorted);
    int[] ans = new int[qs.length];
    for (int q = 0; q < qs.length; q++) {
      int l = 1, r = n;
      //  O(|V|) time
      while (l < r)
        if (sorted[l] + sorted[r] <= qs[q]) l++;
        else ans[q] += (r--) - l;
      //  O(|V|+|E|) time
      for (int i = 1; i < n; i++)
        if (shared[i] != null)
          for (Map.Entry<Integer, Integer> p : shared[i].entrySet()) {
            int j = p.getKey(), sEs = p.getValue();
            if (qs[q] < es[i] + es[j] && qs[q] + sEs >= es[i] + es[j]) ans[q]--;
          }
    }
    return ans;
  }
  // --------------------------------------------------------------------------
  public static void main(String[] args) {
    System.out.println(
        Arrays.toString(
            countPairs(
                4,
                new int[][] {
                  new int[] {1, 2},
                  new int[] {2, 4},
                  new int[] {1, 3},
                  new int[] {2, 3},
                  new int[] {2, 1}
                },
                new int[] {2, 3})));
    // [6,5]
  }
}
