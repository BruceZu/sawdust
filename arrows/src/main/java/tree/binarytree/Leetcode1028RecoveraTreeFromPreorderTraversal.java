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

public class Leetcode1028RecoveraTreeFromPreorderTraversal {

  public TreeNode recoverFromPreorder(String S) {
    // The number of nodes in the original tree is in the range [1, 1000].
    return preOrder(new String[] {S}, 0);
  }

  // s is the reference of original String
  // d is the depth of current node
  // return current sub tree head
  public static TreeNode preOrder(String[] s, int d) {
    if (s[0] == null) return null;
    // If a node has only one child, that child is guaranteed to be the left child.
    int l = 0; // length of expected substring
    while (l < s[0].length() && s[0].charAt(l) == '-') l++;
    if (l == d) { // match location
      s[0] = s[0].substring(l);
      //  '1 <= Node.val <= 10^9' so value maybe not one number
      l = 0;
      while (l < s[0].length() && s[0].charAt(l) != '-') l++; //
      TreeNode node = new TreeNode(Integer.valueOf(s[0].substring(0, l)));
      s[0] = s[0].substring(l);
      node.left = preOrder(s, d + 1);
      node.right = preOrder(s, d + 1);
      return node;
    }
    return null;
  }
}
