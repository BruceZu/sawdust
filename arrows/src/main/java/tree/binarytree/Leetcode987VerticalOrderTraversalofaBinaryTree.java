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

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class Leetcode987VerticalOrderTraversalofaBinaryTree {
  static class TreeNode {
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
  // O(NlogN) time, O(N) space
  private void recurse(TreeNode n, int c, int r, PriorityQueue q) {
    if (n == null) return;
    q.offer(new int[] {c, r, n.val});
    recurse(n.left, c - 1, r + 1, q);
    recurse(n.right, c + 1, r + 1, q);
  }

  // O(NlogN) time, O(N) space
  public List<List<Integer>> verticalTraversal(TreeNode root) {
    /*
      TODO: corner cases checking

        The number of nodes in the tree is in the range [1, 1000].
        0 <= Node.val <= 1000
    */

    // ordered by column, then row, then value
    PriorityQueue<int[]> q =
        new PriorityQueue<>(
            (a, b) -> {
              if (a[0] != b[0]) return a[0] - b[0]; // by column
              if (a[1] != b[1]) return a[1] - b[1]; // by row
              return a[2] - b[2]; // by value
            });
    recurse(root, 0, 0, q);
    List<List<Integer>> r = new ArrayList();
    // O(N)
    while (!q.isEmpty()) {
      int c = q.peek()[0];
      List<Integer> curCol = new ArrayList();
      while (!q.isEmpty() && q.peek()[0] == c) {
        curCol.add(q.peek()[2]);
        q.poll();
      }
      r.add(curCol);
    }
    return r;
  }
}
