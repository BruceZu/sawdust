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

/**
 * <pre>
 * Condition: Directed Acyclic Graph(DAG)
 * Only one result condition:
 *          If a topological sort has the property that all pairs of consecutive vertices in
 *          the sorted order  are connected by edges, then these edges form a directed
 *          Hamiltonian path in the DAG.
 *          If a Hamiltonian path exists, the topological sort order is unique;
 *
 * 1> Kahn's algorithm(BFS)
 *    See used in {@link Leetcode269AlienDictionary}
 *    Data structures:
 *      2 map<node, set<node>>: 'ins'(1:n), 'outs' (1:n)
 *                               ins can be map<node, Integer>. But this is not good choice as this need
 *                               to avoid duplicated operations to increase the Integer value
 *                               for 2 same relation or use map<node, list<ode> data structure for `out`
 *      set<node> all: all nodes in graph, this can be merged with indegree maps `ins`, but
 *                     not good as concept is not clean
 *      queue: nodes with no incoming edges.
 *      list/String (keep result)
 *
 *    Algorithm steps:
 *      initial `all` set
 *      find out all edge to initial `in` and `out`
 *      initial queue by all.removeAll(in.keySet())
 *      while(  queue  is not  empty) {
 *        BFS:
 *          if current node `k` has not out set `value`, provide a empty set, do not run into null pointer.
 *                                                       or initial out with an empty set during initialize the `all`
 *          remove key in ins if its value set is empty: no ins for this node
 *      }
 *      if  `ins` set is not empty
 *          return error (graph has at least one cycle)
 *
 *
 *    Algorithm  cons:
 *           need has ins;
 *           need find out all nodes(Kept in `all` set) and edges(kept in and out maps).
 *           need update the ins
 *
 *    Algorithm pros: can detect circle. do not need "visited" Set to check circle
 *    O(V+E) time and space
 *      todo A variation of Kahn's algorithm that breaks ties lexicographically forms a key component
 *      of the Coffman–Graham algorithm for parallel scheduling and layered graph drawing.
 *
 *  2> Depth-first search(Assume it is DAG. no circle, need
 *    - checking circle before running this algorithm)
 *    - or merge  checking circle with  topological sort order together see
 *      See used in {@link Leetcode269AlienDictionary}
 *
 *  data structure:
 *    Set: keep all nodes
 *    map: only outs
 *    stack:  to keep the result during on the back forward path
 *    Set: visited. avoid duplicated at merge point.
 *         Not be used to checking circle.
 *
 *   steps:  from each node to try DFS +  post order to keep result
 *
 *    cons:  need check circle firstly
 *    pros:  need not update the edge/connection relationship in indegree map, so no indegree map
 *
 *   O(V+E) time and space
 *  todo The topological order can also be used to quickly compute shortest paths through
 *  a weighted directed(negative wight is allowed) acyclic graph.
 *
 *   <a href="https://en.wikipedia.org/wiki/Topological_sorting"> wiki </a>
 *   <a href="https://www.youtube.com/watch?v=ddTC4Zovtbc"> DFS from youtube </a>
 *   <a href="http://www.cse.cuhk.edu.hk/~taoyf/course/2100sum11/lec14.pdf"> DFS </a>
 *
 *  --- circle ---
 *   o
 *    \
 *     o -o
 *     |  |
 *     o- o
 *    /
 *   o
 *
 *  --- no circle ---
 *    o
 *     \
 * o    o
 *  \ / \
 *   o   o
 *   |   |
 *   o   o
 *    \ /
 *     o
 */
public class TopologicalSort {
  // 1> Kahn's algorithm ------------------------------------------------------
  static class G {
    private List<Integer>[] out;
    private int[] in; // number of in nodes of the node whose ID is index of array in
    private int V; // number of Vertex which has ID from 0 to V-1

    public G(int V) {
      this.V = V;
      out = new List[V];
      for (int i = 0; i < V; i++) {
        out[i] = new ArrayList();
      }
      in = new int[V];
    }

    public G edge(int from, int to) {
      out[from].add(to);
      in[to]++;
      return this;
    }

    public List topologicalOrder() {
      Queue<Integer> q = new LinkedList();
      for (int i = 0; i < V; i++) {
        if (in[i] == 0) {
          q.add(i); // those nodes who has not  in node(s)
        }
      }
      List<Integer> r = new ArrayList<>(V);
      Integer n; // node Id
      while ((n = q.poll()) != null) {
        r.add(n);
        for (int o : out[n]) {
          if (--in[o] == 0) {
            // if the in use map then also need remove map entry when the node has no in
            // nodes
            q.add(o);
          }
        }
      }

      if (r.size() != V) {
        return null; // there is circle
      }
      return r;
    }
  }

  // 2> DFS -------------------------------------------------------------------
  // as do not care edge weight, make the data structure simple.
  class Node {
    public char v; // unique ID
  }

  /*
  DFS used only for DAG. So need check if there is circle in the graph
  which is presents with outgoing relation where for node which has not
  edge the out.get(node) is a empty set.
  */
  public static String topologicalSortOrder(Map<Node, Set<Node>> out) {
    Map<Node, Boolean> inPath = new HashMap<>();
    for (Node n : out.keySet()) if (hasCircle(n, out, inPath)) return "";

    StringBuilder r = new StringBuilder();
    Set<Node> visited = new HashSet();
    for (Node v : out.keySet()) dfsCalculateTopologicalOrder(v, out, r, visited);
    return r.reverse().toString();
  }
  /*
   hasCircle`(1) with  `topological sort order`(2)
   Both (1) and（2）
     - start each node to try
     - visit all nodes and edges

   But:
   (1) requires to exit once meet visited node
   (2) requires stop once meet visited node

   (1) requires clean tracks on the back forward path
   (2) requires never clean the visited record

   They can merged together but not easy to read
  */
  private static void dfsCalculateTopologicalOrder(
      Node n, Map<Node, Set<Node>> out, StringBuilder r, Set<Node> calculated) {
    if (calculated.contains(n)) return; // stop this path
    calculated.add(n);
    for (Node o : out.get(n)) dfsCalculateTopologicalOrder(o, out, r, calculated);
    r.append(n); // Topological Order: Only all sub tree are recorded can record current node.
  }

  private static boolean hasCircle(Node n, Map<Node, Set<Node>> out, Map<Node, Boolean> inPath) {
    // Do not use:  if (inPath.containsKey(n) && inPath.get(n)) return true;
    // if it is false: need not repeat the ever visited and no circle founded path.
    if (inPath.containsKey(n)) return inPath.get(n); // stop this path
    inPath.put(n, true);
    for (Node o : out.get(n))
      if (hasCircle(o, out, inPath)) return true; // stop the loop and feedback directly
    inPath.put(n, false);
    return false;
  }
  // merged 2 function: find a circle?(1) + calculate Topological Sort Order(2)
  private static boolean merged(
      Node n, Map<Node, Boolean> mark, StringBuilder r, Map<Node, Set<Node>> out) {
    if (mark.containsKey(n)) {
      // For (2): it is calculated and need not continue.and calculate more.
      // For (1):
      // - value is true : then current path has circle now, need to exit;
      // - value is false: not in current path, but as it visited, need not repeat it. can sure no
      // circle on that part else it has exit.
      return mark.get(n);
    }
    // never visited
    mark.put(n, true); // never remove this entry. used by (2)
    for (Node o : out.get(n)) {
      if (merged(o, mark, r, out)) return true;
      // else: no circle found in current sub path/tree and finished calculating topological sort
      // order need check left sub path/tree
    }
    // in all current sub path/tree: no circle found and finished calculating topological sort order
    // For（2）: According to Topological Order: Only all sub tree are recorded can record current
    // node. It is time to record current node
    r.append(n.v); // keep record into result on the back forward path so need reverse at last
    // For(1): clean current path track, the `true` value is used to judge on the same current path
    mark.put(n, false);
    return false; // no circle is found
  }
  // --------------------------------------------------------------------------
  public static void main(String[] args) {
    // graph is a case in algs4.cs.princeton.edu
    G g = new G(8);
    g.edge(0, 1)
        .edge(0, 7)
        .edge(0, 4)
        .edge(1, 3)
        .edge(1, 2)
        .edge(1, 7)
        .edge(4, 5)
        .edge(4, 6)
        .edge(4, 7)
        .edge(7, 2)
        .edge(7, 5)
        .edge(5, 2)
        .edge(5, 6)
        .edge(2, 3)
        .edge(2, 6)
        .edge(3, 6);
    System.out.println(g.topologicalOrder());
  }
}
