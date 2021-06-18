//  Copyright 2016 The Sawdust Open Source Project
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

package nosubmmitted;

public class LC323NumberofConnectedComponentsinanUndirectedGraph {

  /*
   Number of Connected Components in an Undirected Graph

   Given n nodes labeled from 0 to n - 1 and a list of undirected edges (each edge is a pair of nodes),
   edges[i] = [ai, bi] indicates that there is an edge between ai and bi in the graph
   ask the the number of connected components in an undirected graph.

   You can assume that
   no duplicate edges will appear in edges.
   all edges are undirected, [0, 1] is the same as [1, 0] and thus will not appear together in edges.


    1 <= n <= 2000
    1 <= edges.length <= 5000
    edges[i].length == 2
    0 <= ai <= bi < n
    ai != bi
    There are no repeated edges.
  Idea:
    Union_Found
    O(N) time and space
   */
  public int countComponents(int n, int[][] edges) {
    if (n <= 1) return n;
    int[] roots = new int[n];
    int[] depth = new int[n];
    for (int i = 0; i < n; i++) roots[i] = i;
    for (int[] edge : edges) {
      // union-found
      // '0 <= ai <= bi < n  ai != bi There are no repeated edges'
      int x = find(roots, edge[0]);
      int y = find(roots, edge[1]);
      if (x != y) {
        int min = depth[x] == Math.min(depth[x], depth[y]) ? x : y;
        int max = min == x ? y : x;
        roots[max] = min;
        if (min == max) depth[max]++;
        n--;
      }
    }
    return n;
  }

  private int find(int[] roots, int i) {
    int x = i;
    while (roots[i] != i) {
      i = roots[i];
    }
    while (roots[x] != i) {
      int tmp = roots[x];
      roots[x] = i;
      x = tmp;
    }
    return i;
  }
}
