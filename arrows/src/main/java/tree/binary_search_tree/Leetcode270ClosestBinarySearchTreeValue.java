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

package tree.binary_search_tree;

public class Leetcode270ClosestBinarySearchTreeValue {
  /** Definition for a binary tree node. */
  public class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
      val = x;
    }
  }
  // implement ----------------------------------------------------------------
  public int closestValue(TreeNode root, double t) {
    /*
     The number of nodes in the tree is in the range [1, 104].
     0 <= Node.val <= 109
     -109 <= target <= 109
    */
    // TODO: check corner cases
    /*
    Note:
     1. This is a BST, not BT.
     2. For a not null node: 3 possible status:
         = : diff is 0
         < : discard left sub tree
         > : discard right sub tree

     3. overview: all nodes in [first node < it, first node > it ]
        need check diff
     O(H) time
     O(H) space with recursion or O(1) with loop in place.
    */
    int r = 0;
    double d = Double.MAX_VALUE;
    TreeNode n = root;
    // 'number of nodes in the tree is in the range [1, 104].' So root is not null;
    while (n != null) {
      if (n.val == t) return n.val;
      double diff = Math.abs(n.val - t);
      if (diff < d) {
        d = diff;
        r = n.val;
      }
      if (n.val < t) n = n.right;
      else n = n.left;
    }
    return r;
  }
}
