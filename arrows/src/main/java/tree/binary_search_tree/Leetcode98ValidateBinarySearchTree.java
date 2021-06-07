//  Copyright 2017 The keepTry Open Source Project
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

import java.util.Stack;

public class Leetcode98ValidateBinarySearchTree {

  /*
  Validate BST
   definition:

    The left subtree of a node contains only nodes with keys <  the node's key.
    The right subtree of a node contains only nodes with keys > the node's key.
    Both the left and right subtrees must also be binary search trees.


    The number of nodes in the tree is in the range [1, 104].
    -2^31 <= Node.val <= 2^31 - 1
    **Note**:  it is unique value, no equal relation

  Idea:
    Keep the track of pre value
    **Note**:  the pre variable should not be initialed with Integer.MIN_VALUE.
    E.g. a tree with only one node  whose value is Integer.MIN_VALUE
  */

  private Integer pre;

  public boolean isValidBST(TreeNode root) {
    pre = null;
    return inorder(root);
  }

  // (N) time, space
  private boolean inorder(TreeNode n) {
    if (n == null) return true; // do nothing just return true
    if (!inorder(n.left)) return false;
    if (pre != null && !(pre < n.val)) return false;
    pre = n.val;
    return inorder(n.right);
  }

  // no-recursion vesion ------------------------------------------------------
  // O(N) time, space
  public boolean isValidBST2(TreeNode n) {
    Stack<TreeNode> s = new Stack<>();
    Integer pre = null; // use Integer to

    while (!s.isEmpty() || n != null) { // in order
      while (n != null) {
        s.push(n);
        n = n.left;
      }
      n = s.pop();
      if (pre != null && n.val <= pre) return false;

      pre = n.val;
      n = n.right;
    }
    return true;
  }
}
