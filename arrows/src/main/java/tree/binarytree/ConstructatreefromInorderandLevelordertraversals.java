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

public class ConstructatreefromInorderandLevelordertraversals {

  static class Node {
    public int v;
    public Node l, r;

    Node(int v) {
      this.v = v;
    }
  }

  /*
  Given inorder and level-order traversals of a Binary Tree,
  construct the Binary Tree.
  Idea:
   In a Level order sequence, the first element is the root of the tree
   then By searching ’20’ in Inorder sequence, we can find out all elements on left side of
   the root are in left subtree and elements on right are in right subtree.
   locate current tree node:
      its index is in currant index range of inOrder array
      and it appear first in levelOrder array.
  */
  static Node buildTree(int LEVEL[], int IN[]) {
    return help(LEVEL, IN, 0, IN.length - 1);
  }

  // construct subtree by index range [s,e] in inorder array
  static Node help(int[] LEVEL, int[] IN, int s, int e) {
    if (s > e) return null;
    if (s == e) return new Node(IN[s]);

    Node root = null;
    int index = 0;
    out:
    for (int i = 0; i < LEVEL.length - 1; i++) {
      for (int j = s; j < e; j++) {
        if (LEVEL[i] == IN[j]) {
          root = new Node(LEVEL[i]);
          index = j;
          break out;
        }
      }
    }

    root.l = help(LEVEL, IN, s, index - 1);
    root.r = help(LEVEL, IN, index + 1, e);
    return root;
  }
}
