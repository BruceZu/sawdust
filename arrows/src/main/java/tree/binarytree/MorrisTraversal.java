//  Copyright 2019 The KeepTry Open Source Project
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

/**
 * <pre>
 * Morris Traversal
 *
 * Require space complexity is O(1).
 * - No use stack and recursion.
 * - not modify tree structure and Node data structure.
 *
 * Think:
 *  - how to end the loop?
 *  - for 3 kinds of traversal order, what is the difference between
 *    node has not left subtree and left subtree has been handled
 *  - how to use the method {@link MorrisTraversal#flap(Node)}
 *
 *  All possible binary scenario:
 *    current node | left subtree | right subtree
 *    null
 *  1>  x           | null         | null
 *  2>  x           | null         | x
 *  3>  x           | x            | null
 *  4>  x           | x            | x
 */
public class MorrisTraversal {
  static class Node {
    String name;
    Node l, r;

    public Node() {}

    public Node(String name) {
      this.name = name;
    }
  }

  public MorrisTraversal() {
    super();
  }

  /**
   * <pre>
   *  1> If node 'n' has not left child, return false.
   *  2> Else find out the 'n''s in-order predecessor child, or its left sub tree's right most
   *     child pointer
   *     If the 'n''s in-order predecessor child.r == null, this means node 'n''s left subtree
   *     has not been processed, then let child.r = node 'n'. (This is a memory mark to show
   *     weather node 'n''s left subtree as been processed) and return true.
   *     Else let child.r = null, and return false.
   *
   *  Caller can understand result
   *  - 'true' as node 'n''s left subtree has not been processed, link is created
   *  - 'false' as node 'n' has no left sub tree. or 'n''s left subtree has been processed
   *     link is broken.
   */
  public static boolean flap(Node n) {
    if (n.l == null) return false;
    Node pred = n.l; // left sub tree right most node, node 'n''s predecessor child in-order
    while (pred.r != null && pred.r != n) {
      pred = pred.r;
    }
    if (pred.r == null) {
      pred.r = n;
      return true;
    } else {
      pred.r = null;
      return false;
    }
  }

  public static String inOrderWithComments(Node root) {
    StringBuilder r = new StringBuilder();
    Node n = root;
    /*
    The tree rightmost node.r is null. other non null node under processing always has right child
    and can always switch to right subtree/linked parent.
    {@link MorrisTraversal#isRightLinked(MorrisTraversal.Node)} will check whether current.l is null.
    Code in loop will switch current node to it's left subtree only when the left subtree is not
    null.
    */
    while (n != null) {
      if (flap(n)) {
        // current node has left subtree and the left subtree has not been processed. switch
        // current node to its left subtree
        n = n.l;
        continue;
      }
      // current node has not left,reached left most node., or left subtree has been
      // processed
      r.append(n.name);
      n = n.r; // continue right subtree
    }
    return r.toString();
  }

  public static String inOrder(Node root) {
    StringBuilder r = new StringBuilder();
    Node n = root;
    while (n != null) {
      if (flap(n)) {
        n = n.l;
        continue;
      }
      r.append(n.name);
      n = n.r;
    }
    return r.toString();
  }

  public static String preOrder(Node root) {
    StringBuilder r = new StringBuilder();
    Node n = root;
    while (n != null) {
      // r.append(n.name); is wrong here. when current node's left subtree has been handled
      // So put off handling after checking n.l existence, even put off it after checking thread.
      if (flap(n)) {
        r.append(n.name);
        n = n.l;
        continue;
      }
      // current node has not left subtree.
      if (n.l == null) {
        r.append(n.name);
      } // else current node has left subtree which has been processed.
      n = n.r;
    }
    return r.toString();
  }

  public static String postOrder(Node root) {
    StringBuilder r = new StringBuilder();
    Node n = root;
    while (n != null) {
      if (flap(n)) {
        n = n.l;
        continue;
      }
      // n has left subtree but it has been processed or h has not left subtree
      if (n.l != null) {
        printRightLineBottomUpOf(n.l, r); // now left subtree is done.
      }
      n = n.r;
    }
    if (root != null) printRightLineBottomUpOf(root, r);
    return r.toString();
  }

  /** without comments version */
  public static String postOrder2(Node root) {
    StringBuilder r = new StringBuilder();
    Node n = root;
    while (n != null) {
      if (flap(n)) {
        n = n.l;
        continue;
      }
      if (n.l != null) {
        printRightLineBottomUpOf(n.l, r);
      }
      n = n.r;
    }
    if (root != null) printRightLineBottomUpOf(root, r);
    return r.toString();
  }

  /** The algorithm guarantee the space complexity O(1), without using stack */
  private static void printRightLineBottomUpOf(Node top, StringBuilder result) {
    if (top == null) return;
    Node end = null; // initial value
    while (end != top) {
      Node p = top; // need printed
      while (p.r != end) p = p.r;
      result.append(p.name);
      end = p;
    }
  }

  // --------------------------------------------------------------------------//
  public static void main(String[] args) {
    // null
    System.out.println(inOrderWithComments(null).equals(""));
    System.out.println(preOrder(null).equals(""));
    System.out.println(postOrder(null).equals(""));
    System.out.println(postOrder2(null).equals(""));
    // one node
    Node root = new Node("A");
    System.out.println(inOrderWithComments(root).equals("A"));
    System.out.println(preOrder(root).equals("A"));
    System.out.println(postOrder(root).equals("A"));
    System.out.println(postOrder2(root).equals("A"));

    Node l = new Node("L");
    Node r = new Node("R");

    // no right subtree
    root.l = l;
    System.out.println(inOrderWithComments(root).equals("LA"));
    System.out.println(preOrder(root).equals("AL")); //
    System.out.println(postOrder(root).equals("LA"));
    System.out.println(postOrder2(root).equals("LA"));
    // no left subtree
    root.l = null;
    root.r = r;

    System.out.println(inOrderWithComments(root).equals("AR"));
    System.out.println(preOrder(root).equals("AR"));
    System.out.println(postOrder(root).equals("RA"));
    System.out.println(postOrder2(root).equals("RA"));
    // has both left and right subtree
    root.l = l;
    System.out.println(inOrderWithComments(root).equals("LAR"));
    System.out.println(preOrder(root).equals("ALR")); //
    System.out.println(postOrder(root).equals("LRA"));
    System.out.println(postOrder2(root).equals("LRA"));

    // other case.
    Node n1 = new Node("1");
    Node n2 = new Node("2");
    Node n3 = new Node("3");
    Node n4 = new Node("4");
    Node n5 = new Node("5");
    Node n6 = new Node("6");
    Node n7 = new Node("7");
    Node n8 = new Node("8");
    Node n9 = new Node("9");
    n1.l = n2;
    n1.r = n7;
    n2.l = n3;
    n2.r = n4;
    n4.l = n5;
    n4.r = n6;
    n7.r = n8;
    n8.l = n9;
    System.out.println(inOrderWithComments(n1).equals("325461798"));
    System.out.println(preOrder(n1).equals("123456789"));
    System.out.println(postOrder(n1).equals("356429871"));
    System.out.println(postOrder2(n1).equals("356429871"));
  }
}
