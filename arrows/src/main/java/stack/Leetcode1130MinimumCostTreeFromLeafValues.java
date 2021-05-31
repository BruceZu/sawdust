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

package stack;

import java.util.Stack;

public class Leetcode1130MinimumCostTreeFromLeafValues {
  /*
    "2 <= arr.length <= 40"
    "1 <= arr[i] <= 15 "
  means each leaf value is positive.
    "It is guaranteed that the answer fits into a 32-bit signed integer (ie. it is less than 2^31)."

  Idea:
  - Question name is "Minimum Cost Tree From Leaf Values"
  - Related Topics: stack
  - Similar question: Next Great Element I, Next Great Element  III
  - Hints are about DP which is O(N^3) time and O(N^2) space

  Observation: for a smaller element A[i] once it is
   used to calculated the value of no-leaf node, the product, with another
   element whose value >=A[i], A[i] then will never be used anymore to
   calculated the value of any no-leaf node. Here the value of no-leaf node,
   the product, can be took as the `cost` of `removing` A[i].

  For achieving the Minimum Cost Tree From Leaf Values
  - compared with the smaller element, the bigger element should be used
   later to avoid being used more times to calculate the cost of `removing`
   smaller ones. The biggest one will be used at most twice to calculate the
   cost of removing the second(the value of root) and the third biggest one
   if both of them are not on the same side of the biggest one.
  - when it is time to remove A[i], use the smaller one of 2 nearest bigger neighbor
    on both sides to make the cost of removing A[i] as small as possible,
    If there are smaller ones between A[i] and its 2 nearest bigger neighbor, then
    they should be removed firstly before removing A[i]. Monotonic descending stack
    is used in this kind of scenario. if there are A[x], A[i], A[y] with the same
    value, left one always be remove by the right one.

  - The order of removing of each element does not affect the result
  - with Integer.MAX_VAL as the first element in Monotonic descending stack
    -- need not checking empty
    -- make code of calculating the cost of A[i] concise when remove A[i]
       in scenario where the stack status is (MAX_VAL,A[i]) and
       current element `cur` is bigger one. cost = A[i]*cur
   */
  public int mctFromLeafValues(int[] A) {
    int r = 0;
    Stack<Integer> s = new Stack<>();
    s.push(Integer.MAX_VALUE); // dummy one
    for (int cur : A) {
      while (s.peek() <= cur) r += s.pop() * Math.min(s.peek(), cur); // pre > it <= cur
      s.push(cur);
    }
    while (s.size() > 2) r += s.pop() * s.peek();
    return r;
  }
}
