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

import java.util.HashSet;
import java.util.Set;

public class Leetcode236LowestCommonAncestorofaBinaryTree {
  static class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
      val = x;
    }
  }
  // implement ----------------------------------------------------------------
  /*
  O(N) time and space.
  Alternative Idea:
   - return type is boolean: find p or q from sub tree.

   */
  TreeNode r = null;
  TreeNode dummy = new TreeNode(Integer.MAX_VALUE);

  public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
    /*
    The number of nodes in the tree is in the range [2, 105].
    -10^9 <= Node.val <= 10^9
    All Node.val are unique. (Note)
    p != q
    p and q will exist in the tree.  (Note)
    */
    // TODO: check corner cases

    h(root, p, q);
    return r;
  }

  public TreeNode h(TreeNode root, TreeNode p, TreeNode q) {
    if (root == null) return null;
    Set<TreeNode> t = new HashSet();
    t.add(h(root.left, p, q));
    t.add(h(root.right, p, q));
    t.add(root);
    if (t.contains(dummy)) return dummy;
    if (t.contains(p) && t.contains(q)) {
      r = root;
      return dummy;
    } else if (t.contains(p)) return p;
    else if (t.contains(q)) return q;
    return null;
  }
}
