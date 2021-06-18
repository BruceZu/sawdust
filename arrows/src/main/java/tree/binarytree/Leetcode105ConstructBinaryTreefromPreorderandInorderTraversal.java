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

import java.util.HashMap;
import java.util.Map;

public class Leetcode105ConstructBinaryTreefromPreorderandInorderTraversal {

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

  // Solution -------------------------------------------------------

  int root_pre_i; // keep the current root index in pre order array
  Map<Integer, Integer> IN_reverted; // for finding the index of node in inorder array
 /*
  O(N) time and space
  */
  public TreeNode buildTree(int[] PRE, int[] IN) {
    root_pre_i = 0;
    IN_reverted = new HashMap<>();
    for (int i = 0; i < IN.length; i++) {
      IN_reverted.put(IN[i], i);
    }

    return help(PRE, 0, PRE.length - 1);
  }

  // build subtree with index range [l,r] in inorder array
  private TreeNode help(int[] PRE, int l, int r) {
    if (l > r) return null;
    // select the preorder_index element as the root and increment it
    int v = PRE[root_pre_i++];
    TreeNode root = new TreeNode(v);

    // build left and r subtree
    // excluding inorderIndexMap[rootValue] element because it's the root
    root.left = help(PRE, l, IN_reverted.get(v) - 1);
    root.right = help(PRE, IN_reverted.get(v) + 1, r);
    return root;
  }
}
