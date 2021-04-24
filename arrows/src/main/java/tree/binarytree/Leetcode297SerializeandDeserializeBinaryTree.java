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

package tree.binarytree;

import java.util.*;

public class Leetcode297SerializeandDeserializeBinaryTree {
  public class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
      val = x;
    }
  }
  // Implementation------------------------------------------------------------
  /*
  - root need to be the first one in the serialized string
  Thus this will be preorder traversal
  - how to mark null child? #
  - how to know left and right subtree boundary? ( ) or ,

  Preorder guarantee need not checking end of tree and serialized string.
  It is no more no less.
  `,` will make it easy to split the serialized string
  Data structure:
    serialization: use StringBuilder for any kind of value
    deserialization: use LinkedList or char[] and current index.
  O(N) time and space
  */

  // it is cost to keep all null
  private void bs(StringBuilder b, TreeNode n) {
    if (n == null) {
      // `-1000 <= Node.val <= 1000`
      b.append("#,");
      return;
    }
    b.append(n.val + ",");
    bs(b, n.left);
    bs(b, n.right);
  }

  public String serialize(TreeNode root) {
    StringBuilder b = new StringBuilder();
    bs(b, root);
    return b.toString();
  }

  public TreeNode deserialize(String data) {
    return bt(new LinkedList(Arrays.asList(data.split(",")))); // O(N)
  }

  private TreeNode bt(List<String> l) {
    String v = l.remove(0); // O(1) as the index is 0.
    if (v.equals("#")) return null; // Note use equals() not ==
    TreeNode n =
        new TreeNode(Integer.parseInt(v)); // string to integer value. It is parseInt() not parse()
    n.left = bt(l);
    n.right = bt(l);
    return n;
  }
  /*

   The number of nodes in the tree is in the range [0, 104].
   -1000 <= Node.val <= 1000
  TODO: corner cases validation
  */
}
