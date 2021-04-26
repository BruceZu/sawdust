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

package tree.binarytree;

// not find the fast way yet.
public class Leetcode124BinaryTreeMaximumPathSum {

  public static class TreeNode {
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
  // implement--------------------------------------------------------------------------
  /*
  IDEA: 6 possible length
    1 v
    2 l (processed in the sub tree, ignore it)
    3 r (processed in the sub tree, ignore it))
    4 l+v
    5 r+v
    6 l+v+r
    three of them starting from current node and the biggest one of these 3
    need be return and used for above level

    then the bigger one of result and l+v+r will be used to update max=Math.max(max,l+v+r )


   O(N) time, O(H) space
   N is number of nodes
   H is a tree height
  */
  int max = Integer.MIN_VALUE;

  public int maxPathSum(TreeNode root) {
    /*
      The number of nodes in the tree is in the range [1, 3 * 10^4].
      -1000 <= Node.val <= 1000
      TODO: check corner cases
    */
    help(root);
    return max;
  }

  private Integer help(TreeNode root) {
    if (root == null) return 0;
    int l = help(root.left);
    int r = help(root.right);
    int result = Math.max(Math.max(r, l) + root.val, root.val);
    max = Math.max(max, Math.max(result, l + r + root.val));
    return result;
  }
}
