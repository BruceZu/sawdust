//  Copyright 2017 The keepTry Open Source Project
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

package binarysearch;

import java.util.Arrays;
import java.util.List;


/*
   with BST.
   O(NlogN~N^2) time  balanced tree ~ not balanced tree.
   Time Limit Exceeded
 */
public class Leetcode315CountofSmallerNumbersAfterSelf4 {
  static class BST { // binary search tree
    final int v;
    int l_sum; // number moving over the node to its left, or left sub tree nodes account.

    BST l, r;

    public BST(int v) {
      this.v = v;
    }
  }
  /*
   During the process of building BST calculate the smaller number of each A[i]
   start from right most element of A[i], moving it
   from root to its destiny location where the node is null.
   during the process
    - along the routine, at the location of each existing node to calculate the number of nodes
      whose value <= A[i] and keep the number in v.
    - all existing node's l_sum is the number of nodes moving over the node to its left
      so keep updating existing node.l_sum if current A[i] moving over it to its left.
    - once A[i] reach a location where node is null, then it reach its destiny.
      create a new node with A[i] there and integrate it with BST.
      at the time the v is a[i]

   for duplicated number: if current A[i] reach a node, both of them
   have the same value, then take the relation as A[i] < node.value,
   thus the v will not include the the node and current A[i] will move
   to current node left sub-tree branch.
  */
  public static List<Integer> countSmaller(int[] A) {
    Integer[] a = new Integer[A.length];
    BST root = new BST(A[A.length - 1]);
    root.l_sum = 0;
    a[A.length - 1] = 0;
    // use null as value of root to call build() then no way to keep previous BST status
    for (int i = A.length - 2; i >= 0; i--) build(A, i, root, 0, a);
    return Arrays.asList(a);
  }

  private static void build(int[] A, int i, BST n, int v, Integer[] a) {
    while (n != null) {
      if (A[i] <= n.v) {
        n.l_sum = n.l_sum + 1; // According to lsm definition
        if (n.l == null) {
          n.l = new BST(A[i]);
          a[i] = v;
          return;
        }
        n = n.l;
      } else {
        v = v + n.l_sum + 1; // BST attribute
        if (n.r == null) {
          n.r = new BST(A[i]);
          a[i] = v;
          return;
        }
        n = n.r;
      }
    }
  }
}
