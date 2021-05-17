//  Copyright 2019 The KeepTry Open Source Project
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

package two_pointer;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Leetcode1124LongestWellPerformingInterval {
  /*
   We are given hours, a list of the number of hours worked per day for a given employee.
   A day is considered to be a tiring day if and only if the number of hours worked is
   (strictly) greater than 8.

   A well-performing interval is an interval of days for which the number of tiring days is
   strictly larger than the number of non-tiring days.
   Return the length of the longest well-performing interval.

   Input: hours = [9,9,6,0,6,6,9]
   Output: 3

      1 <= hours.length <= 10000
      0 <= hours[i] <= 16

  Idea:
    presum + map(presum, index)
  O(N)
  */
  public static int longestWPI(int[] h) {
    Map<Integer, Integer> m = new HashMap<>();
    int s = 0, r = 0;
    for (int i = 0; i < h.length; i++) {
      s += h[i] > 8 ? 1 : -1;
      if (s >= 1) {
        r = i + 1;
      } else {
        m.putIfAbsent(s, i); // keep the left most one, or the first presum with the same value.
        if (m.containsKey(s - 1)) r = Math.max(r, i - m.get(s - 1));
      }
    }
    return r;
  }

  /* Alternative --------------------------------------------------------------
   with a strictly monotone decreasing stack to keep index of pre sum;
   pre sum sum[0] is 0 and be push firstly in the stack, others pushed in is negative number
   draw a picture to see what it is
   Actually the` p[i] - p[s.peek()] >= 1` only valid when `i > statck.peek()`
   else `i - s.peek()` is negative
   and will be ignore by   `r = Math.max(r, i - s.peek())`

   O(N) time and space
  */
  public static int longestWPI2(int[] h) {
    // prefix sum; sum[0] is 0
    int[] p = new int[h.length];
    int sum = 0;
    for (int i = 0; i < h.length; ++i) {
      sum += h[i] > 8 ? 1 : -1;
      p[i] = sum;
    }
    Stack<Integer> s = new Stack();
    for (int i = 0; i < h.length; i++) {
      if (p[i] < 0 && (s.empty() || p[i] < p[s.peek()])) s.push(i);
    }
    int r = 0;
    for (int i = h.length - 1; i >= 0; i--) {
      if (p[i] > 0) r = Math.max(r, i + 1);
      else
        while (!s.empty() && p[i] - p[s.peek()] >= 1) { //  check empty
          r = Math.max(r, i - s.peek());
          s.pop();
        }
    }
    return r;
  }
}
