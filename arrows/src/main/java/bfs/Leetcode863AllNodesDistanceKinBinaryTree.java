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

public class Leetcode863AllNodesDistanceKinBinaryTree {
  static class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
      val = x;
    }
  }

  // solution---------------------------------------------------------------
  /*
   The given tree is non-empty.
   Each node in the tree has unique values 0 <= node.val <= 500.
   The target node is a node in the tree.
   0 <= K <= 1000.

   Note: node unique values

   O(N) time and space
  */

  public static List<Integer> distanceK(TreeNode root, TreeNode target, int K) {
    List<Integer> a = new ArrayList<>();
    dfs(root, 0, K, a, target, new Integer[1]);
    return a;
  }

  // check found the target or not from current node's subtree not other nodes' subtree;
  // has return found or not, once found will not continue the recursion.
  // so if it is null: has not found yet and not found again here
  public static boolean dfs(
      TreeNode n, int level, int K, List<Integer> a, TreeNode t, Integer[] tl) {
    if (n == null) return false;
    if (n == t) {
      bfs(t, K, a);
      tl[0] = level; // target node level
      return true; // need return
    }

    boolean l = dfs(n.left, level + 1, K, a, t, tl);
    boolean r = dfs(n.right, level + 1, K, a, t, tl);
    // Each node in the tree has unique value
    if (l || r) {
      int d = tl[0] - level; // distance from target node to current node
      if (d == K) a.add(n.val);
      if (l && d < K) bfs(n.right, K - d - 1, a); // -1: means minus the n.right
      if (r && d < K) bfs(n.left, K - d - 1, a); // -1: means minus the n.right
      return true;
    }
    return false;
  }

  private static void bfs(TreeNode node, int d, List<Integer> a) {
    if (node == null) return;
    if (d == 0) {
      a.add(node.val);
      return; // need return
    }
    bfs(node.left, d - 1, a);
    bfs(node.right, d - 1, a);
  }
}
