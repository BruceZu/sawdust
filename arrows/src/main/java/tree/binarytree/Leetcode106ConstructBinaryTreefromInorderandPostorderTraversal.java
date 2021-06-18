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
/*

   1 <= inorder.length <= 3000
   postorder.length == inorder.length
   -3000 <= inorder[i], postorder[i] <= 3000
   inorder and postorder consist of unique values.
   Each value of postorder also appears in inorder.
   inorder is guaranteed to be the inorder traversal of the tree.
   postorder is guaranteed to be the postorder traversal of the tree.

*/
public class Leetcode106ConstructBinaryTreefromInorderandPostorderTraversal {

  int root_p_i;
  int[] POST;
  int[] IN;
  Map<Integer, Integer> reverted = new HashMap<Integer, Integer>();
  public TreeNode buildTree(int[] IN, int[] POST) {
    this.POST = POST;
    this.IN = IN;
    // start from the last postorder element
    root_p_i = POST.length - 1; // current root index in post order array

    int idx = 0;
    for (Integer val : IN) reverted.put(val, idx++); // reverted index of value in inorder array

    return helper(0, IN.length - 1);
  }

  private TreeNode helper(int l, int r) {
    if (l > r) return null;
    int v = POST[root_p_i];
    TreeNode root = new TreeNode(v);

    int i = reverted.get(v);
    // recursion
    root_p_i--;
    root.right = helper(i + 1, r); //   left <- right <-  middle
    root.left = helper(l, i - 1);
    return root;
  }
}
