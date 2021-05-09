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

package tree.binarytree;

import java.util.*;

public class Leetcode1110DeleteNodesAndReturnForest {

  public class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode() {}

    TreeNode(int val) {
      this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
      this.val = val;
      this.left = left;
      this.right = right;
    }
  }
  // implement ---------------------------------------------------------------
  // O(N) time and space
  public List<TreeNode> delNodes(TreeNode root, int[] to_delete) {
    if (root == null) return new LinkedList<TreeNode>();
    if (to_delete == null) return Arrays.asList(root);
    Set<Integer> d = new HashSet<>();
    for (int t : to_delete) d.add(t);
    List<TreeNode> r = new LinkedList<>();
    pre(root, true, d, r);
    return r;
  }

  private TreeNode pre(TreeNode n, boolean pDeleted, Set<Integer> d, List<TreeNode> r) {
    if (n == null) return null;
    if (d.contains(n.val)) {
      pre(n.left, true, d, r);
      pre(n.right, true, d, r);
      return null;
    } else {
      if (pDeleted) r.add(n);
      n.left = pre(n.left, false, d, r);
      n.right = pre(n.right, false, d, r);
      return n;
    }
  }
}
