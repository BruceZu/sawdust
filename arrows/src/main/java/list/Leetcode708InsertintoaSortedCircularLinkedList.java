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

package list;

public class Leetcode708InsertintoaSortedCircularLinkedList {
  class Node {
    public int val;
    public Node next;

    public Node() {}

    public Node(int _val) {
      val = _val;
    }

    public Node(int _val, Node _next) {
      val = _val;
      next = _next;
    }
  }

  // Implementation -------------------------------------------------------------------
  /*
   Note:
      1. no-descending order:
         'Given a node from a Circular Linked List which is sorted in ascending order
         But:
         'If there are multiple suitable places for insertion,
         you may choose any place to insert the new value.
         After the insertion, the circular list should remain sorted.'

         This means actually it is no-descending order, not ascending order.

      2. It is a special case when there is only one element in the list.Just
         connect each other and will always match the required no-descending order.

      3. Find the start node, then convert question to common list.
  	     Generally, use the rule comes from ascending order: between the end and
         the start node there is an exception.

  	     But with a no-descending order list with 2 or more elements,
         A special scenario is when all elements are with the same value:
         This requires checking if the head node is step back to the original start node.
      4  find the right location for the given node. it can be before/after/in
         the common list.

      5  The right place to check if the index goes back to the start node is
         the end of the loop. If it is added to the loop condition then it will
         not step into this loop.

   O(N) time, O(1) space
  */

  public Node insert(Node head, int insertVal) {
    /*
      0 <= Number of Nodes <= 5 * 10^4
      -10^6 <= Node.val <= 10^6
      -10^6 <= insertVal <= 10^6
    */
    Node n = new Node(insertVal);
    if (head == null) { // empty
      n.next = n;
      head = n;
      return head;
    }
    if (head == head.next) { // only one. special case.
      head.next = n;
      n.next = head;
      return head;
    }
    Node cur = head;
    Node next = cur.next;
    while (cur.val <= next.val) { // <= nod-descending. Do not validate boundary here
      cur = next;
      next = next.next;
      if (cur == head) break; // here when all elements are with same value
    }

    Node start = next;
    while (next.val < n.val) { // do not validate boundary here
      cur = next;
      next = next.next;
      if (next == start) break;
    }

    cur.next = n;
    n.next = next;
    return head;
  }
}
