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

package recursion;

public class Leetcode24SwapNodesinPairs {
  /*
  Given a linked list, swap every two adjacent nodes and return its head.
      The number of nodes in the list is in the range [0, 100].
      0 <= Node.val <= 100
   */

  static class ListNode {
    int val;
    ListNode next;

    ListNode(int x) {
      val = x;
    }
  }

  // solution ---------------------------------------------------------------
  // recursion O(N) time and space
  public ListNode swapPairs(ListNode head) {
    if ((head == null) || (head.next == null)) return head;
    // need swap   a->b->
    ListNode a = head;
    ListNode b = head.next;

    // Swapping
    a.next = swapPairs(b.next);
    b.next = a;
    return b;
  }

  // solution ---------------------------------------------------------------
  // Iterative O(N) time and O(1) space
  public ListNode swapPairs2(ListNode i) {
    // "0 <= Node.val <= 100"
    ListNode pre = new ListNode(-1);
    pre.next = i;

    ListNode p = pre;
    while ((i != null) && (i.next != null)) {
      // Nodes to swapp   p->[a->b]->?
      ListNode a = i;
      ListNode b = i.next;

      // Swapping
      p.next = b;
      a.next = b.next; // right side
      b.next = a;

      // Reinitializing the head and prevNode for next swap
      p = a;
      i = a.next; // jump
    }

    // Return the new head node.
    return pre.next;
  }
}
