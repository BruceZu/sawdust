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

public class Leetcode160IntersectionofTwoLinkedLists {
  /*
  Idea:
  No magic just merge 2 steps in one.
  Need firstly get longer list's length - shorter list's length
   */
  public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
    if (headA == null || headB == null) return null;
    ListNode a = headA, b = headB;
    while (a != b) {
      a = a == null ? headB : a.next;
      b = b == null ? headA : b.next;
    }
    return a;
  }
}
