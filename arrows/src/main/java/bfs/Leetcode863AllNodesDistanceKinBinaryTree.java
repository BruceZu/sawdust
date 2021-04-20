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
  class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
      val = x;
    }
  }

  // origin idea ----------------------------------------------------------------
  /*
  TODO: corner cases checking

  The given tree is non-empty.
  Each node in the tree has unique values 0 <= node.val <= 500.
  The target node is a node in the tree.
  0 <= K <= 1000.
   */

  /*
  Note:
  1. Each node in the tree has unique values
  2. result is List<Integer>  not List<TreeNode>
  O(N) time
  3> current solution have 2 parts:
     -31- collection via parents. need avoid the path from target to current parent
     -32- collection from target and its children
   */
  public List<Integer> distanceK2(TreeNode root, TreeNode target, int K) {
    Map<TreeNode, TreeNode> p = new HashMap();
    findParents(root, null, target, p);
    List<Integer> r = new ArrayList<>();
    // A:  collect from up direction. Need avoid path from target to current parent
    int upSteps = 1; // distance from target to current parent
    TreeNode cur = target;
    TreeNode parent = p.get(cur);
    while (upSteps <= K && parent != null) {
      getFromUpDirection(parent, cur, K - upSteps, r);

      cur = parent;
      parent = p.get(parent);
      upSteps++;
    }
    // B: collect from target and downside direction
    bfs2(target, K, r);
    return r;
  }

  // Assume TreeNode 'from' and 'exclude' is not null
  // O(N) time
  private void getFromUpDirection(
      TreeNode from, TreeNode exclude, int distance, List<Integer> result) {
    if (distance == 0) {
      result.add(from.val);
      return;
    }
    TreeNode node = from.left == exclude ? from.right : from.left;
    if (distance == 1 && node != null) {
      result.add(node.val);
      return;
    }
    // distance>=2
    if (node == null) return;
    bfs2(node, distance - 1, result);
  }

  // DFS O(N) time
  private void findParents(
      TreeNode node, TreeNode parent, TreeNode target, Map<TreeNode, TreeNode> p) {
    if (node == null) return;
    p.put(node, parent);
    if (node == target) return;
    findParents(node.left, node, target, p);
    findParents(node.right, node, target, p);
  }

  private void bfs2(TreeNode from, int level, List<Integer> r) {
    Queue<TreeNode> q = new LinkedList();
    q.offer(from);
    int l = 0; // `from` Node is level 0
    while (l != level) {
      l++; // next batch
      int left = q.size();
      while (left-- > 0) {
        TreeNode top = q.poll();
        if (top.left != null) q.offer(top.left);
        if (top.right != null) q.offer(top.right);
      }
    }
    while (!q.isEmpty()) {
      r.add(q.poll().val);
    }
  }
  // Implement: BFS in DFS ----------------------------------------------------------------
  // Idea comes from 'Leetcode Solution 2'
  // (N）Time
  public List<Integer> distanceK(TreeNode root, TreeNode target, int K) {
    List<Integer> r = new ArrayList<>();
    dfs(root, new int[1], K, r, target);
    return r;
  }

  // `int[] dis` keep the value of distance which is calculated from target, which is 0,
  //             to current node.
  //             It can be as result be return back to above recursion. in his case the default
  //             value will  be -1.
  public boolean dfs(TreeNode node, int[] dis, int K, List<Integer> result, TreeNode target) {
    if (node == null) return false;
    if (node == target) {
      bfs(target, K, result);
      dis[0] = 1;
      return true;
    }
    boolean l = dfs(node.left, dis, K, result, target), r = dfs(node.right, dis, K, result, target);
    if ((l || r) && dis[0] == K) result.add(node.val);
    if (l && dis[0] + 1 <= K && node.right != null) bfs(node.right, K - (dis[0] + 1), result);
    if (r && dis[0] + 1 <= K && node.left != null) bfs(node.left, K - (dis[0] + 1), result);

    if (l || r) dis[0]++;
    return l || r;
  }

  // BFS.
  // level is calculated from node from which is level 0
  // Assume node is not null and level >=0
  // get children node value at specific level
  private void bfs(TreeNode node, int level, List<Integer> r) {
    if (node == null) return;
    if (level == 0) {
      r.add(node.val);
      return;
    }
    bfs(node.left, level - 1, r);
    bfs(node.right, level - 1, r);
  }
}
