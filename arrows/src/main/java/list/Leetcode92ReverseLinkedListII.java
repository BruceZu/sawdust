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

public class Leetcode92ReverseLinkedListII {

  static class ListNode {
    int val;
    ListNode next;

    ListNode() {}

    ListNode(int val) {
      this.val = val;
    }

    ListNode(int val, ListNode next) {
      this.val = val;
      this.next = next;
    }
  }

  // ---------------------------------------------------------------------------
  /*
   Find index l=left-1;
   l maybe the first node head so make a dumy Node.
   always return dumy.next at last;

   before start:
   pre-> [l ->...-> r]-> other
   H=pre  T=l

   after reverse
   pre   [l <-...<- r]   other
                    pre  cur|next

   T is the l node, H is the l node's pre node
   H.next=pre; T.next=cur;

   Note:   1 <= left <= right <= n.
  O(N) time, O(1) space
    */
  public ListNode reverseBetween(ListNode head, int left, int right) {
    if (head == null) return null;
    ListNode d = new ListNode();
    d.next = head;
    ListNode p = d;

    int i = -1;
    while (i + 1 != left - 1) { // 0 based index
      p = p.next;
      i++;
    }
    ListNode H = p;
    ListNode cur = p.next, T = p.next;
    i = 0; // distance from cur node to initial cur node
    while (i <= right - left) {
      ListNode n = cur.next;
      cur.next = p;
      p = cur;
      cur = n;
      i++;
    }

    H.next = p;
    T.next = cur;
    return d.next;
  }
}
