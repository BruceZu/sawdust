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

public class Leetcode543DiameterofBinaryTree {
  public int diameterOfBinaryTree(TreeNode root) {
    /*
     The number of nodes in the tree is in the range [1, 104].
     -100 <= Node.val <= 100
     TODO: corner cases validation
    */

    int[] r = new int[1];
    dfs(root, r);
    return r[0];
  }
  // get node numbers of the path from the root to the deepest leaf
  // by the way to calculate diameter via each node.
  // O(N) time
  // O(N) space:  the size of implicit call stack during our DFS
  //              relates to the height of the tree.
  //              In the worst case,  O(N)
  //              in the balanced tree O(logN).

  private int dfs(TreeNode root, int[] d) {
    if (root == null) return 0;
    int l = dfs(root.left, d);
    int r = dfs(root.right, d);
    d[0] = Math.max(d[0], l + r + 1 - 1); // length is nodes number -1
    return Math.max(l, r) + 1;
  }
}
