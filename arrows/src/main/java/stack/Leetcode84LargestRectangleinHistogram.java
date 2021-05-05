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

package stack;

import java.util.Stack;

public class Leetcode84LargestRectangleinHistogram {
  /*
  Ascending stack saves index of bar
  if the next bar which will be pushed has the same height with the top one in stack.
  Skip it to improve performance
  */
  static int largestRectangleArea(int[] H) {
    Stack<Integer> s = new Stack<>(); // ascending stack
    int N = H.length;
    int r = 0;
    for (int i = 0; i < N; i++) {
      if (s.empty() || H[s.peek()] <= H[i]) s.push(i);
      else {
        while (!s.isEmpty() && H[s.peek()] > H[i]) {
          int h = H[s.pop()];
          int w = s.isEmpty() ? i : i - s.peek() - 1;
          r = Math.max(h * w, r);
        }
        s.push(i);
      }
    }

    while (s.empty() == false) { // remaining bars in stack
      int h = H[s.pop()];
      int w = s.isEmpty() ? N : N - s.peek() - 1;
      int area = h * w;
      r = r < area ? area : r;
    }
    return r;
  }
  // O(N)
  static int largestRectangleArea2(int[] H) {
    Stack<Integer> s = new Stack<>(); // ascending stack
    int r = 0;
    for (int i = 0; i <= H.length; i++) {
      int hi = i == H.length ? 0 : H[i];
      while (!s.isEmpty() && H[s.peek()] > hi) {
        int h = H[s.pop()];
        int w = s.isEmpty() ? i : i - s.peek() - 1; // -1 is minus i index
        r = Math.max(h * w, r);
      }
      s.push(i);
    }
    return r;
  }
  // others ------------------------------------------------------------------
  /*
   height [3, 6, 7,   2, 3]
   output [6, 7, null, 3, null]。
   <a href="http://www.geeksforgeeks.org/find-next-smaller-next-greater-array/"> next greater and smaller</a>
   O(n)
  */
  static int[] nextHigher(int[] height) {
    Stack<Integer> s = new Stack<>(); // ascendingHeight
    int[] r = new int[height.length];
    for (int i = height.length - 1; i >= 0; i--) {
      while (!s.isEmpty() && s.peek() <= height[i]) s.pop();
      r[i] = s.isEmpty() ? -1 : s.peek();
      s.push(height[i]);
    }
    return r;
  }

  static int[] nextSmaller(int[] height) {
    Stack<Integer> s = new Stack<>(); // decendingHeight
    int[] r = new int[height.length];
    for (int i = height.length - 1; i >= 0; i--) {
      while (!s.isEmpty() && s.peek() >= height[i]) s.pop();
      r[i] = s.isEmpty() ? -1 : s.peek();
      s.push(height[i]);
    }
    return r;
  }
}
