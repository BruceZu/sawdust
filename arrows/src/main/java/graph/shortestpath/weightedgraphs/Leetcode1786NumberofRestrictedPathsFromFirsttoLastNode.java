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

package graph.shortestpath.weightedgraphs;

import java.util.*;

public class Leetcode1786NumberofRestrictedPathsFromFirsttoLastNode {
  /*
  Context:
    '
    - a given undirected weighted connected graph.
    - a given positive integer n which denotes that the graph has n nodes labeled from 1 to n
    - a given aray edges where each edges[i] = [ui, vi, weights]  denotes that there is an edge
      between nodes ui and vi with weight equal to weights.
    - concept 'path' from node start to node end is a sequence of nodes [z0, z1, z2, ..., zk]
      such that z0 = start and zk = end and there is an edge between zi and zi+1 where 0 <= i <= k-1.
    - concept 'distance' of a path is the sum of the weights on the edges of the path.
      Let distanceToLastNode(x) denote the shortest distance of a path between node n and node x.
    - concept 'restricted path' is a path that also satisfies that
        distanceToLastNode(zi) > distanceToLastNode(zi+1) where 0 <= i <= k-1.
    -  Return the number of restricted paths from node 1 to node n.
       Since that number may be too large, return it modulo 10^9 + 7.
    '
  Restrictions:
    '
    1 <= n <= 2 * 104
    n - 1 <= edges.length <= 4 * 104
    edges[i].length == 3
    1 <= ui, vi <= n
    ui != vi
    1 <= weight <= 105
    There is at most oe edge between any two nodes.
    There is at least one path between any two nodes.
    '

    This means:
    1> '1 <= weight <= 105', positive value means Dijkstra can be used to calculate the shortest distance
    2> no circle on one or any two nodes. So need not visited variable in Dijkstra algorithm to avoid circle
    3> All notes are connected.
    4> edges number * max weight =  4 * 104 * 105 < Integer.MAX_VALUE. So distance use int type is okay.

    Idea:
    - Dijkstra shortest distance + Customized Heap
    - DFS + cache
    By the way:
    - 'n nodes labeled from 1 to n' makes implementation simple
     */
  // Solution A: With PriorityQueue to implement Dijkstra shortest distance
  public static int countRestrictedPaths2(int N, int[][] edges) {
    INode[] g = new INode[N + 1];
    for (int i = 1; i <= N; i++) {
      g[i] = new INode(i);
    }
    for (int[] e : edges) {
      int n1 = e[0], n2 = e[1], w = e[2];
      g[n1].nbrWgt.put(n2, w);
      g[n2].nbrWgt.put(n1, w);
    }
    // 'relaxed' can be saved by move line 90 and 91 in the line 86 block
    boolean[] relaxed = new boolean[N + 1];
    PriorityQueue<INode> min = new PriorityQueue(N + 1);
    g[N].sDi = 0;
    min.offer(g[N]);
    while (min.size() > 0) {
      INode i = min.poll();
      relaxed[i.id] = true;
      for (Map.Entry<Integer, Integer> nbrInf : i.nbrWgt.entrySet()) {
        int ni = nbrInf.getKey();
        if (relaxed[ni]) continue;
        INode nn = g[ni];
        int tmp = i.sDi + nbrInf.getValue();
        if (tmp < 0) tmp = Integer.MAX_VALUE; // can be saved because it is connected graph
        if (tmp < nn.sDi) {
          nn.sDi = tmp;
          // nn.sId = i.id;
        }
        if (min.contains(nn)) min.remove(nn); // O( |V|)
        min.offer(nn);
      }
    }
    int[] result = new int[N + 1];
    Arrays.fill(result, -1);
    result[N] = 1;
    return dfs(g[1], N, g, result, (int) Math.pow(10, 9) + 7);
  }

  static class INode implements Comparable {
    // neighbor Id : Weight
    Map<Integer, Integer> nbrWgt = new HashMap();
    // Distance of the shortest path from end node to current node via edge [sId, ID]
    // int sId; saved because it is not calculating the shortest path
    int sDi = Integer.MAX_VALUE;
    int id; // Need to know which node is this when pulling it out of min heap

    public INode(int id) {
      this.id = id;
    }

    @Override
    public int compareTo(Object o) {
      return this.sDi - ((INode) o).sDi;
    }
  }

  // Based on shortest distance and neighbor to calculate result.
  // Cache the intermediate result to improve performance
  private static int dfs(INode node, int targetId, INode[] g, int[] cache, int M) {
    if (cache[node.id] == -1) {
      int r = 0;
      for (int neiId : node.nbrWgt.keySet()) {
        if (node.sDi <= g[neiId].sDi) continue;
        r += dfs(g[neiId], targetId, g, cache, M);
        r %= M;
      }
      cache[node.id] = r;
    }
    return cache[node.id];
  }
  // Solution B: With a customized heap to implement Dijkstra shortest distance
  // ø(|E|log|V|) time. |E|is edges number and |V| is nodes number.
  public static int countRestrictedPaths1(int N, int[][] edges) {
    INode[] g = new INode[N + 1];
    for (int i = 1; i <= N; i++) {
      g[i] = new INode(i);
    }
    for (int[] e : edges) {
      int n1 = e[0], n2 = e[1], w = e[2];
      g[n1].nbrWgt.put(n2, w);
      g[n2].nbrWgt.put(n1, w);
    }
    // --- Calculate the shortest distance from the end node to every node  ---
    // 'relaxed' is to avoid endless switching in/out of heap for start node and
    // next selected node. 'relaxed' can be saved by move line 162 and 163 in the line 158 block,
    // cons is
    // increasing the heap high.
    boolean[] relaxed = new boolean[N + 1];
    IBinaryHeap min = new IBinaryHeap(N + 1);
    g[N].sDi = 0;
    min.offer(g[N]);
    while (min.size() > 0) {
      INode i = min.poll();
      relaxed[i.id] = true;
      for (Map.Entry<Integer, Integer> nbrInf : i.nbrWgt.entrySet()) {
        int ni = nbrInf.getKey();
        if (relaxed[ni]) continue;
        INode nn = g[ni];
        int tmp = i.sDi + nbrInf.getValue();
        if (tmp < 0) tmp = Integer.MAX_VALUE; // can be saved because it is connected graph
        if (tmp < nn.sDi) {
          nn.sDi = tmp;
          // nn.sId = i.id;
        }
        if (min.contains(nn)) min.shiftUp(nn); // O(log|V|)
        else min.offer(nn); // O(log|V|)
      }
    }

    // --- DFS + Cache---
    int[] result = new int[N + 1];
    Arrays.fill(result, -1);
    result[N] = 1;
    return dfs(g[1], N, g, result, (int) Math.pow(10, 9) + 7);
  }

  // --------------------------------------------------------------------------
  /*
  Alternative of PriorityQueue: a customized minimum heap to make
  - contains(INode o) O(1) time
  - shiftUp(INode n)  O(logN) time
  */
  static class IBinaryHeap {
    private int size;
    // 1 index based to make implement simple
    private INode[] heap;
    private int[] indexes;

    private void shiftDown(INode n, int index) {
      if (size == 0) return;
      while (index <= (size >>> 1)) {
        int l = index << 1, r = l + 1, smallerIndex = l;
        INode smaller = heap[l];
        if (r <= size && smaller.compareTo(heap[r]) > 0) {
          smaller = heap[r];
          smallerIndex = r;
        }
        if (smaller.compareTo(n) < 0) {
          heap[index] = smaller;
          indexes[smaller.id] = index;
          index = smallerIndex;
        } else {
          break;
        }
      }
      heap[index] = n;
      indexes[n.id] = index;
    }

    private void shiftUp(INode n, int index) {
      while (index > 1) {
        int pre = index >>> 1;
        INode pn = heap[pre];
        if (n.compareTo(pn) < 0) {
          heap[index] = pn;
          indexes[pn.id] = index;
          index = pre;
        } else {
          break;
        }
      }
      heap[index] = n;
      indexes[n.id] = index;
    }
    // O(1) time
    private Integer indexOf(INode n) {
      return indexes[n.id];
    }

    public IBinaryHeap(int capacity) {
      size = 0;
      // 1 index based to make implement simple
      heap = new INode[capacity + 1];
      indexes = new int[capacity + 1];
    }

    public void shiftUp(INode n) {
      if (this.contains(n)) shiftUp(n, indexOf(n));
      else throw new IllegalStateException();
    }

    public boolean offer(INode n) {
      if (n == null) throw new NullPointerException();
      size++;
      shiftUp(n, size);
      return true;
    }

    public INode poll() {
      if (size == 0) {
        throw null;
      }
      INode result = heap[1];
      INode last = heap[size];
      heap[size] = null;
      indexes[result.id] = 0;
      size--;

      if (size != 0) {
        shiftDown(last, 1);
      }
      return result;
    }

    // O(1) time
    public boolean contains(INode o) {
      return indexOf(o) != 0;
    }

    public int size() {
      return size;
    }
  }

  // --- Common version without comments, Inode and customized IHeap ----------
  public static int countRestrictedPaths(int N, int[][] edges) {
    Map<Integer, Integer>[] nbrWgt = new Map[N + 1]; // node i and its neighbor:weight
    Arrays.fill(nbrWgt, new HashMap());
    for (int i = 1; i <= N; i++) {
      nbrWgt[i] = new HashMap<>();
    }
    for (int[] e : edges) {
      int n1 = e[0], n2 = e[1], w = e[2];
      nbrWgt[n1].put(n2, w);
      nbrWgt[n2].put(n1, w);
    }

    int shtDis[][] = new int[N + 1][2]; // node i and its shortest distance to node N
    for (int i = 1; i <= N; i++) {
      shtDis[i] = new int[] {i, Integer.MAX_VALUE};
    }

    boolean[] relaxed = new boolean[N + 1]; // 'relaxed' can be saved
    // shortest distance of node n -> node i
    PriorityQueue<int[]> min = new PriorityQueue(N + 1, (Comparator<int[]>) (a, b) -> a[1] - b[1]);
    shtDis[N][1] = 0;
    min.offer(shtDis[N]);
    while (min.size() > 0) {
      int myId = min.poll()[0];
      relaxed[myId] = true;
      for (Map.Entry<Integer, Integer> nbr : nbrWgt[myId].entrySet()) {
        int id = nbr.getKey(), w = nbr.getValue();
        if (relaxed[id]) continue;
        int tmp = shtDis[myId][1] + w;
        if (tmp < shtDis[id][1]) {
          shtDis[id][1] = tmp;
        }
        if (min.contains(shtDis[id])) min.remove(shtDis[id]); // O(|V|)
        min.offer(shtDis[id]);
      }
    }
    // Cached DFS
    int[] result = new int[N + 1];
    Arrays.fill(result, -1);
    result[N] = 1;
    return dfs(1, N, nbrWgt, shtDis, result, (int) Math.pow(10, 9) + 7);
  }

  private static int dfs(
      int i, int t, Map<Integer, Integer>[] nbrWgt, int[][] shtDis, int[] cache, int M) {
    if (cache[i] == -1) {
      int r = 0;
      for (int ngbId : nbrWgt[i].keySet()) {
        if (shtDis[i][1] <= shtDis[ngbId][1]) continue;
        r += dfs(ngbId, t, nbrWgt, shtDis, cache, M);
        r %= M;
      }
      cache[i] = r;
    }
    return cache[i];
  }
  // --------------------------------------------------------------------------
  public static void main(String[] args) {
    int[][] graph =
        new int[][] {
          new int[] {6, 2, 9},
          new int[] {2, 1, 7},
          new int[] {6, 5, 10},
          new int[] {4, 3, 1},
          new int[] {3, 1, 8},
          new int[] {4, 6, 4},
          new int[] {5, 1, 7},
          new int[] {1, 4, 7},
          new int[] {4, 5, 3},
          new int[] {3, 6, 6},
          new int[] {5, 3, 9},
          new int[] {1, 6, 6},
          new int[] {3, 2, 2},
          new int[] {5, 2, 8},
        };
    int n = 6;
    System.out.println(countRestrictedPaths1(n, graph) == 4);
    System.out.println(countRestrictedPaths2(n, graph) == 4);
    System.out.println(countRestrictedPaths(n, graph) == 4);
    graph =
        new int[][] {
          new int[] {1, 2, 3},
          new int[] {1, 3, 3},
          new int[] {2, 3, 1},
          new int[] {1, 4, 2},
          new int[] {5, 2, 2},
          new int[] {3, 5, 1},
          new int[] {5, 4, 10},
        };
    n = 5;
    System.out.println(countRestrictedPaths1(n, graph) == 3);
    System.out.println(countRestrictedPaths2(n, graph) == 3);
    System.out.println(countRestrictedPaths(n, graph) == 3);
  }
}
