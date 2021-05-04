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

package graph.directed_graphs;

import java.util.*;

public class Leetcode133CloneGraph {

  static class Node {
    public int val;
    public List<Node> neighbors;

    public Node() {
      val = 0;
      neighbors = new ArrayList<Node>();
    }

    public Node(int _val) {
      val = _val;
      neighbors = new ArrayList<Node>();
    }

    public Node(int _val, ArrayList<Node> _neighbors) {
      val = _val;
      neighbors = _neighbors;
    }
  }
  // implement ------------------------------------------------------------------
  public Node cloneGraph(Node node) {
    /*
    1 <= Node.val <= 100
    Node.val is unique for each node.
    Number of Nodes will not exceed 100.
    There is no repeated edges and no self-loops in the graph.
    The Graph is connected and all nodes can be visited starting from the given node.
    */
    /*
    Idea:
     recursion. With a created map of v: Node to avoid duplicated clone
     O(N) time and space
    */
    return help(node, new HashMap<>());
  }

  private Node help(Node n, Map<Integer, Node> created) {
    if (n == null) return null;
    if (created.containsKey(n.val)) return created.get(n.val);
    Node c = new Node(n.val);
    created.put(n.val, c);
    for (Node nei : n.neighbors) c.neighbors.add(help(nei, created));
    return c;
  }
}
