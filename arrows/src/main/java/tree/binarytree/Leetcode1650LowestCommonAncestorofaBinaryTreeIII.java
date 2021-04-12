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

class Node {
  public int val;
  public Node left;
  public Node right;
  public Node parent;
}

public class Leetcode1650LowestCommonAncestorofaBinaryTreeIII {
  public Node lowestCommonAncestor(Node p, Node q) {
    // TODO: corner cases checking
    /*
    The number of nodes in the tree is in the range [2, 10^5].
     -109 <= Node.val <= 10^9
     All Node.val are unique.
     p != q
     p and q exist in the tree.
     */

    /*
         O(L+S-C) Time
         L is the steps from the longer one
         L ------------------
         S     --------------
                     C-------
         L: from the node of p,q with longer height to root
         S: from the node of p,q with shorter height to root
         C: from common ancestor to root
    */
    Node pi = p, qi = q;
    while (pi != qi) {
      pi = pi == null ? q : pi.parent;
      qi = qi == null ? p : qi.parent;
    }
    return pi;
  }
}
