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

package string.parentheses;

public class Leetcode536ConstructBinaryTreefromString {

  class TreeNode {
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
  // implement ----------------------------------------------------------
  int i = 0; // current index of s, used by different level of recursion

  public TreeNode str2tree(String s) {
    /*
    `You always start to construct the left child node of the parent first if it exists.`
    Idea:
      node can be null
      node value: it can be negative and can be great than 9
      node can have
        - 0
        - 1 or 2 children, first is left , second is right.
          how to switch to sub recursion: (
          how to return back pre recursion: ) for sub level, for top level: ends at i==s.length()
          So '(' and ')' is only marks to switch into/back from sub recursion

   O(N) time, N is string length.
   O(H) space. H is the height of the tree
        when the tree looks like forward slash (/)
        (can't be a backslash (\)  according to the problem)
        O(M) space M is the total node number.
    */
    /*
        0 <= s.length <= 3 * 104
        s consists of digits, '(', ')', and '-' only.
    */
    // corner case validation
    if (s == null || s.length() == 0) return null;
    int start = i;
    while (i < s.length() && (s.charAt(i) == '-' || Character.isDigit(s.charAt(i)))) i++;
    TreeNode node = new TreeNode(Integer.parseInt(s.substring(start, i)));
    while (i != s.length()) { // top level end i==s.length()
      if (s.charAt(i) == ')') { // sub level end
        i++;
        return node;
      }
      if (s.charAt(i) == '(') {
        i++;
        // `You always start to construct the left child node of the parent first if it exists.`
        if (node.left == null) node.left = str2tree(s);
        else node.right = str2tree(s);
      }
    }
    return node;
  }
}
