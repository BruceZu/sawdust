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
 *   why we need it? e.g. for build system
 * Condition:
 *          DAG：Directed Acyclic Graph
 * Aim:     a linear ordering of vertexes such that for every directed edge uv, vertex u comes before v in the ordering.
 * the solution is not necessarily unique;
 * Only one result condition:
 *          If a topological sort has the property that all pairs of consecutive vertices in the sorted order
 *          are connected by edges, then these edges form a directed Hamiltonian path in the DAG.
 *          If a Hamiltonian path exists, the topological sort order is unique;
 *          全序: DAG中的任意一对顶点还需要有明确的关系(反映在图中，就是单向连通的关系)
 *
 * 1> Kahn's algorithm:
 *    Data structures:
 *      The node class should have 'ins' and 'outs'
 *      ALL: graph nodes
 *      L:  list that will contain the result. initialized by an empty list.
 *      S: container (it can be set, queue, stack) of all nodes with no incoming edges. find out all of them firstly.
 *
 *    Algorithm: BFS
 *      while S is non-empty do
 *          remove a node n from S.
 *          insert n into L, and remove each edge e from n to this outs, if a give out of n has no ins anymore, then
 *          push this out in S.
 *
 *      if S is empty and graph still has edges left
 *          return error (graph has at least one cycle)
 *      else
 *          return L (a topologically sorted order)
 *
 *     cons: V need has ins;
 *           need find out all S firstly.
 *           need update the V
 *
 *     pros: can detect circle. do not need "visited" Set to check circle
 *
 *      todo A variation of Kahn's algorithm that breaks ties lexicographically forms a key component
 *      of the Coffman–Graham algorithm for parallel scheduling and layered graph drawing.
 *
 *  2> Depth-first search
 *
 *  data structure:
 *    Set<V> all nodes
 *    V class:  only outs
 *    Stack:  to keep the result 倒退时放入
 *    Set: visited. avoid duplicated at merge point.
 *         不是用来checking circle
 *         (using isInCurVisitedPath checking circle see
 *         {@link Leetcode207CourseSchedule#hasCircle(int, List[], boolean[])}
 *
 *    algorithm:
 *      post order + DFS
 *
 *    cons:  need check circle firstly
 *    pros:  from any node to start
 *
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

    static class G {
        // make it simple, assume all Vertex is marked with ID from 0 to V-1.
        // thus it is possible use array, else have to use Map and Node.
        private List<Integer>[] vToAdjest;
        private int[] inDegree;
        private int V;

        public G(int /* number of Vertex */ V) {
            this.V = V;
            vToAdjest = new List[V];
            for (int i = 0; i < V; i++) {
                vToAdjest[i] = new ArrayList();
            }
            inDegree = new int[V];
        }

        public G edge(int from, int to) {
            vToAdjest[from].add(to);
            inDegree[to]++;
            return this;
        }

        public List topologicalOrder() {
            Queue<Integer> relax = new LinkedList();

            for (int i = 0; i < V; i++) {
                if (inDegree[i] == 0) {
                    relax.add(i);
                }
            }
            List<Integer> r = new ArrayList<>(V);
            Integer cur;
            while ((cur = relax.poll()) != null) {
                r.add(cur);
                for (int ad : vToAdjest[cur]) {
                    if (--inDegree[ad] == 0) {
                        relax.add(ad);
                    }
                }
            }

            if (r.size() != V) {
                System.out.println("There is circle. no topologicall sort order");
                return null;
            }
            return r;
        }
    }

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

    // ----------------------------------------------------------
    // as do not care edge weight, make the data structure simple.
    class V {
        Set<V> outgoings; // neighbors
    }

    // Assume this is not circle
    public static Stack<V> topoSort(Set<V> vs) {
        Stack<V> r = new Stack();
        Set<V> visited = new HashSet<>();

        for (V v : vs) {
            dfsPosterOrder(v, r, visited);
        }
        return r;
    }

    private static void dfsPosterOrder(V v, Stack<V> r, Set<V> visited) {
        if (!visited.contains(v)) {
            visited.add(v);
            for (V out : v.outgoings) {
                dfsPosterOrder(out, r, visited);
            }
            r.push(v); // post order
        }
    }
}
