//  Copyright 2016 The Sawdust Open Source Project
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

package list.reverse;

import list.ListNode;

/**
 * <pre>
 * Difficulty: Easy
 * Reverse a singly linked list.
 *
 * click to show more hints.
 *
 * Hint:
 *      A linked list can be reversed either iteratively or recursively. Could you implement both?
 *
 * Tags Linked List
 */
class Node {
  Integer v;
  Node next;

  Node(int v) {
    this.v = v;
  }
}

public class Leetcode206ReverseLinkedList {

  public ListNode reverseList(ListNode head) {
    ListNode p = null, cur = head;
    while (cur != null) {
      ListNode n = cur.next;
      cur.next = p;
      p = cur;
      cur = n;
    }
    return p;
  }
}
