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

import java.util.LinkedList;
import java.util.List;

public class Leetcode199BinaryTreeRightSideView {
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
  // Implement ================================================================
  /*
  Idea:
    - use `middle, right, left` order
    - how to know current node should be in result list:
      checking whether the current node' level is > visited level
      If true then this is a new level
    the result list size is just the visited level +1
    and level is 0-based. so checking
          `current node' level is > visited level`
    becomes checking
          current node' level is == result list size
   O(N) time, O(H) space H is the tree height


   The number of nodes in the tree is in the range [0, 100].
   -100 <= Node.val <= 100
    TODO: check null
  */
  public List<Integer> rightSideView(TreeNode root) {
    List<Integer> r = new LinkedList();
    help(root, 0, r);
    return r;
  }

  private void help(TreeNode n, int l, List<Integer> r) {
    if (n == null) return;
    if (r.size() == l) {
      r.add(n.val);
    }
    help(n.right, l + 1, r);
    help(n.left, l + 1, r);
  }
}
