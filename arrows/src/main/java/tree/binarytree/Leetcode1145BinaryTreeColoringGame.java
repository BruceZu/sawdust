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

public class Leetcode1145BinaryTreeColoringGame {
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
  /*
   given the root of this binary tree, and the number of nodes n in the tree.
   n is **odd**,
   each node has a distinct value from 1 to n.

   the first player names a value x with 1 <= x <= n, a
   the second player names a value y with 1 <= y <= n and y != x.
   In each turn, that player chooses a node of their color
   (red if player 1, blue if player 2) and colors an **uncolored neighbor**
   of the chosen node (either the left child, right child, or
   parent of the chosen node.)

  If (and only if) a player cannot choose such a node in this way,
  they must pass their turn.
  If both players pass their turn, the game ends,
  the winner is the player that colored more nodes.

   You are the second player.
   If it is possible to choose such a y to ensure you win the game, return true.
   If it is not possible, return false.

   "1 <= x <= n <= 100"
   means: root is not null and the tree has at least one node

   Idea:
   y must be immediately adjacent to x, since it locks out that subtree.
   O(n) time and O(height) space
   */
  private int xl = 0, xr = 0;

  public boolean btreeGameWinningMove(TreeNode root, int n, int x) {
    count(root, x);
    int up = n - xl - xr - 1;
    int y = Math.max(up, Math.max(xl, xr));
    return y > n / 2;
  }

  private int count(TreeNode n, int x) {
    if (n == null) return 0;
    int l = count(n.left, x), r = count(n.right, x);
    if (n.val == x) {
      xl = l;
      xr = r;
    }
    return l + r + 1;
  }
}
