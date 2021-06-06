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

  Question:
    Convert a Binary Search Tree to a sorted Circular Doubly-Linked List in place.
    return the pointer to the smallest element of the linked list.
  Idea:
   divide-conquer idea:
    need help function to do:
    - sub-tree-> double linked list
    - right-tree->double linked list
    - merge left list+ current node + right list
    need checking null in advance
    the result is the head and tail of the double linked list.
    at last connect the head and tail and return the head(smallest one)

   O(N) time, O(H) space used by recursion. H is height of tree.
  */

  public static Node treeToDoublyList(Node root) {
    if (root == null) return null;
    Node[] ht = inOrder(root); // head and tail of double linked list
    Node h = ht[0], t = ht[1];
    h.left = t;
    t.right = h;
    return h;
  }

  public static Node[] inOrder(Node root) {
    Node h, t; // head and tail
    if (root.left != null) {
      Node[] ht = inOrder(root.left);
      h = ht[0];
      ht[1].right = root;
      root.left = ht[1];
    } else h = root;

    if (root.right != null) {
      Node[] ht = inOrder(root.right);
      t = ht[1];
      ht[0].left = root;
      root.right = ht[0];
    } else t = root;

    return new Node[] {h, t};
  }
}
