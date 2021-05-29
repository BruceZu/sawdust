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

package bfs;

import java.util.*;

public class Leetcode1129ShortestPathwithAlternatingColors {
  /*
    1 <= n <= 100
    red_edges.length <= 400
    blue_edges.length <= 400
    red_edges[i].length == blue_edges[i].length == 2
    0 <= red_edges[i][j], blue_edges[i][j] < n

    Idea: BFS
    - use node-node(s) represents existing edges
    - the result [] initial value is -1 according to
     "the shortest path from node 0 to node X such that
     the edge colors alternate along the path
     (or -1 if such a path doesn't exist)."
    - avoid repeating walking through same color of edge to the same node,
      so the key of seen is `edge color+node number`
    - queue entry is array [color of edge connected to this node, node]
    - edge color to the first node is special. it is said
      "there could be self-edges or parallel edges.
      make algorithm general, image 2 self edges from node 0 to node 0
    - with BFS, the first value reaching result[i] sure with the smallest steps
   lazy validate and collect result: pros: make code concise. cons: performance
     - seen or not, collect result
     - steps value need match the lazy validate

  */

  public int[] shortestAlternatingPaths(int N, int[][] red_edges, int[][] blue_edges) {
    List<Integer>[] R = new ArrayList[N], B = new ArrayList[N];
    for (int[] e : red_edges) {
      if (R[e[0]] == null) R[e[0]] = new ArrayList<>();
      R[e[0]].add(e[1]);
    }
    for (int[] e : blue_edges) {
      if (B[e[0]] == null) B[e[0]] = new ArrayList<>();
      B[e[0]].add(e[1]);
    }

    int[] a = new int[N];
    Arrays.fill(a, -1);

    Set<String> seen = new HashSet<>();

    int RED = 1, BLUE = -1, ROOT_BOTH = 0;
    Queue<int[]> q = new LinkedList<>();
    q.add(new int[] {0, ROOT_BOTH});

    int s = 0; // steps of move
    while (!q.isEmpty()) {
      int size = q.size();
      for (int i = 0; i < size; i++) {
        int[] t = q.poll(); // top
        int c = t[0], n = t[1]; // c: color
        // lazy validate:
        String k = c + " " + n;
        if (seen.contains(k)) continue;
        seen.add(k);
        // next layer
        if (a[n] == -1) a[n] = s;
        if ((c == BLUE || c == ROOT_BOTH) && R[n] != null)
          for (int r : R[n]) q.add(new int[] {RED, r});
        if ((c == RED || c == ROOT_BOTH) && B[n] != null)
          for (int b : B[n]) q.add(new int[] {BLUE, b});
      }
      s++;
    }
    return a;
  }
}
