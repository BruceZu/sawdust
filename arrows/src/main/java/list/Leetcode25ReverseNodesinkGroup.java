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

package list;

public class Leetcode25ReverseNodesinkGroup {
  /*
  Given this linked list: 1->2->3->4->5

    k = 2, you should return: 2->1->4->3->5
    k = 3, you should return: 3->2->1->4->5

    The number of nodes in the list is in the range sz.
    1 <= sz <= 5000
    0 <= Node.val <= 1000
    1 <= k <= sz


  Idea:
    nodes status:                 [head ->, .., -> end] -> next head
    after swap:      next head  <-[head <-, .., <- end]

    like stack:
               firstly need to know the next head.
               - with it to do recursion and use the new next head as
                 pre node used in swap current k node
               - with it or k to know the right time to stop swap process
  O(N) time. process each node exactly twice
  O(N/k) space used up by the recursion stack.
   */
  public ListNode reverseKGroup(ListNode head, int k) {
    // TODO: check null, positive
    ListNode nh = head; // find the next head, use
    int count = 0;
    while (nh != null && count != k) {
      count++;
      nh = nh.next;
    }
    if (count == k) { // need swap:
      ListNode pre = reverseKGroup(nh, k), cur = head, next;
      while (cur != nh) {
        next = cur.next;
        cur.next = pre;
        pre = cur;
        cur = next;
      }
      return pre; // updated head now
    }
    return head;
  }

  public ListNode reverseKGroup2(ListNode head, int k) {
    int count = 0;
    ListNode nh = head;
    while (count < k && nh != null) {
      count++;
      nh = nh.next;
    }
    if (count == k) {
      ListNode pre = null, cur = head;
      while (count > 0) {
        ListNode next = cur.next;
        cur.next = pre;
        pre = cur;
        cur = next;
        count--;
      }
      // connect reversed k nodes' tail to next reversed k nodes head
      head.next = reverseKGroup(nh, k);
      return pre;
    }
    return head;
  }

  // improve O(N/k) space to O(1) without recursion
  public ListNode reverseKGroup3(ListNode head, int k) {
    ListNode i = head;
    ListNode pTail = null; // pre k nodes' tail node
    ListNode final_head = null;

    while (i != null) {
      int count = 0;
      // Start counting nodes from the head
      i = head;
      // Find the head of the next k nodes
      while (count < k && i != null) {
        i = i.next;
        count += 1;
      }
      if (count == k) { // need reverse k nodes and get the new head
        ListNode pre = null;
        ListNode n = head;

        while (count > 0) {
          ListNode next_node = n.next;
          n.next = pre;
          pre = n;
          n = next_node;
          count--;
        }
        ListNode revHead = pre;
        if (final_head == null) final_head = revHead;
        if (pTail != null) pTail.next = revHead;

        pTail = head;
        head = i;
      }
    }
    // attach the final, possibly un-reversed portion
    if (pTail != null) pTail.next = head;
    return final_head == null ? head : final_head;
  }
}
