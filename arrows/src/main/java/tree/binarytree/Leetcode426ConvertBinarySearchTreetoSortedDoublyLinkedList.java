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

public class Leetcode426ConvertBinarySearchTreetoSortedDoublyLinkedList {
  static class Node {
    public int val;
    public Node left;
    public Node right;

    public Node() {}

    public Node(int _val) {
      val = _val;
    }

    public Node(int _val, Node _left, Node _right) {
      val = _val;
      left = _left;
      right = _right;
    }
  }


  /*
   The number of nodes in the tree is in the range [0, 2000].
   -1000 <= Node.val <= 1000
   All the values of the tree are unique.
  */
  // Assume node is not null
  // O(N) time, O(H) space. H is height of tree.
  public static Node treeToDoublyList(Node root) {
    // TODO: corner cases validation
    if (root == null) return null;
    Node[] lr = inOrder(root);
    Node l = lr[0], r = lr[1];
    l.left = r; // Note: the smallest Node's left is null and will connect to the biggest one
    r.right = l;
    return l;
  }

  public static Node[] inOrder(Node root) {
    Node l, r;
    if (root.left != null) {
      Node[] lr = inOrder(root.left);
      l = lr[0];
      lr[1].right = root;
      root.left = lr[1];
    } else l = root;

    if (root.right != null) {
      Node[] lr = inOrder(root.right);
      r = lr[1];
      lr[0].left = root;
      root.right = lr[0];
    } else r = root;

    return new Node[] {l, r};
  }
}
