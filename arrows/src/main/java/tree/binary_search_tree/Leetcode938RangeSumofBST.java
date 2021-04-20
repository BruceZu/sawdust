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

public class Leetcode938RangeSumofBST {
  public int rangeSumBST(TreeNode root, int low, int high) {
    /*
     TODO: corner cases checking

     The number of nodes in the tree is in the range [1, 2 * 104].
     1 <= Node.val <= 105
     1 <= low <= high <= 105
     All Node.val are unique.
    */
    /*
    Note:  All Node.val are unique.
    O(N) time
     */
    if (root == null) return 0;
    return (low <= root.val && root.val <= high ? root.val : 0)
        + (root.val < low ? 0 : rangeSumBST(root.left, low, high))
        + (root.val > high ? 0 : rangeSumBST(root.right, low, high));
  }
}
