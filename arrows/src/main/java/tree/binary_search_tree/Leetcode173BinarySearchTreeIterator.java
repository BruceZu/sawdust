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

/**
 * <pre>
 * <a href="https://leetcode.com/problems/binary-search-tree-iterator/">LeetCode</a>
 * Implement an iterator over a binary search tree (BST). Your iterator will be initialized with the root node of a BST.
 *
 * Calling next() will return the next smallest number in the BST.
 *
 * Note: next() and hasNext() should run in average O(1) time and uses O(h) memory,
 * where h is the height of the tree.
 */
public class Leetcode173BinarySearchTreeIterator {
  /** Definition for binary tree */
  public class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
      val = x;
    }
  }

  // implement ----------------------------------------------------------------
  class BSTIterator {
    // top down in ascending order. not descending
    private Stack<TreeNode> s = new Stack();

    public BSTIterator(TreeNode n) {
      while (n != null) {
        s.push(n);
        n = n.left;
      }
    }

    public boolean hasNext() {
      return !s.empty();
    }

    public int next() {
      if (!hasNext()) return -1;

      TreeNode n = s.pop(); // care: not peek
      int v = n.val;

      if (n.right != null) {
        n = n.right;
        while (n != null) {
          s.push(n);
          n = n.left;
        }
      }
      return v;
    }
  }
}
