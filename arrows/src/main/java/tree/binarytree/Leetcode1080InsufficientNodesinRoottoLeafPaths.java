//  Copyright 2019 The KeepTry Open Source Project
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

/**
 * <pre>
 *
 * Given the root of a binary tree, consider all root to leaf paths:
 * paths from the root to any leaf.  (A leaf is a node with no children.)
 *
 * A node is insufficient if **every** such root to leaf path intersecting
 * this node has sum strictly less than limit.
 *
 * Delete all insufficient nodes simultaneously, and return the root
 * of the resulting binary tree.
 *
 *
 *
 * Example 1:
 *
 * Input: root = [1,2,3,4,-99,-99,7,8,9,-99,-99,12,13,-99,14], limit = 1
 * Output: [1,2,3,4,null,null,7,8,9,null,14]
 *
 * Example 2:
 *
 * Input: root = [5,4,8,11,null,17,4,7,1,null,null,5,3], limit = 22
 * Output: [5,4,8,11,null,17,4,7,null,null,null,5]
 *
 *
 * Note:
 *
 * The given tree will have between 1 and 5000 nodes.
 * -10^5 <= node.val <= 10^5
 * -10^9 <= limit <= 10^9
 *
 * Note
 *          width                     minimum                         maximum
 *
 * SIGNED
 * byte:     8 bit                        -128                            +127
 * short:   16 bit                     -32 768                         +32 767
 * int:     32 bit              -2 147 483 648                  +2 147 483 647
 * long:    64 bit  -9 223 372 036 854 775 808      +9 223 372 036 854 775 807
 *
 * UNSIGNED
 * char     16 bit                           0                         +65 535
 */

// POST order
public class Leetcode1080InsufficientNodesinRoottoLeafPaths {

  /**
   * <pre>
   * Definition for a binary tree node.
   * public class TreeNode {
   *     int val;
   *     TreeNode left;
   *     TreeNode right;
   *     TreeNode(int x) { val = x; }
   * }
   */
  static class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int value) {
      val = value;
    }
  }

  public static TreeNode sufficientSubset(TreeNode root, int limit) {
    if (root == null) return null;
    if (root.right == null && root.left == null) return root.val < limit ? null : root;

    if (root.left != null) root.left = sufficientSubset(root.left, limit - root.val);
    if (root.right != null) root.right = sufficientSubset(root.right, limit - root.val);
    // root ever has at least one child, but now all are gone caused by not match the limit.
    // So root need not exist anymore.
    return root.left == null && root.right == null ? null : root;
  }

  public static TreeNode sufficientSubset3(TreeNode root, int limit) {
    if (root == null) return null;
    return maxPathSum(root, 0, limit) < limit ? null : root;
  }

  // assume node is not null
  private static long maxPathSum(TreeNode node, long sum, int limit) {
    if (node.left == null && node.right == null) // leaf
    return sum + node.val;

    if (node.left == null && node.right != null) {
      long rSum = maxPathSum(node.right, sum + node.val, limit);
      if (rSum < limit) node.right = null;
      return rSum;
    }
    if (node.left != null && node.right == null) {
      long lSum = maxPathSum(node.left, sum + node.val, limit);
      if (lSum < limit) node.left = null;
      return lSum;
    }

    long lSum = maxPathSum(node.left, sum + node.val, limit);
    long rSum = maxPathSum(node.right, sum + node.val, limit);
    if (lSum < limit) node.left = null;
    if (rSum < limit) node.right = null;
    return Math.max(lSum, rSum);
  }

  // test ---------------------------------------------------------------------
  public static void main(String[] args) {
    // [0, null, -1, null, null];
    // limit = 0;
    // expected return is null

    TreeNode root = new TreeNode(0);
    root.right = new TreeNode(-1);
    root = sufficientSubset(root, 0);
    System.out.println(root == null);

    // [1, null, 10, -100, -100, null, null, null, null];
    // limit = 10;
    // expected return is null;
    root = new TreeNode(1);
    root.right = new TreeNode(10);
    root.right.left = new TreeNode(-100);
    root.right.right = new TreeNode(-100);

    System.out.println(sufficientSubset(root, 10) == null);

    // [2, -3];
    // limit = 1;
    // expected return is null;
    root = new TreeNode(2);
    root.left = new TreeNode(-3);
    root = sufficientSubset(root, 1);
    System.out.println(root == null);

    // [1,2,-3,-5,null,4,null]
    // limit = -1
    // expected return  [1,null,-3,4]

    root = new TreeNode(1);
    root.left = new TreeNode(2);
    root.left.left = new TreeNode(-5);
    root.right = new TreeNode(-3);
    root.right.left = new TreeNode(4);
    root = sufficientSubset(root, -1);
    System.out.println(
        root.val == 1
            && root.left == null
            && root.right.val == -3
            && root.right.left.val == 4
            && root.right.right == null);
  }
}
