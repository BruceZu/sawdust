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

package math;

import java.util.Stack;

public class Leetcode1073AddingTwoNegabinaryNumbers {

  /*
  Given two numbers arr1 and arr2 in base -2.
  guaranteed to have no leading zeros.
  Return the result of adding arr1 and arr2 in the same format:
   as an array of 0s and 1s with no leading zeros.

  1 <= arr1.length, arr2.length <= 1000
  arr1[i] and arr2[i] are 0 or 1
  arr1 and arr2 have no leading zeros

   */

  /*
  Idea:
  "Given two numbers arr1 and arr2 in base -2."
  So:
    - So it can represent any number of Z.
    - for any column possible value -1,0,1.
    - For 2 neighbor columns:
      left:-2^i | right:2^{i-1}:
      -------------------------
           x        y
           1        1
           0        0
          -1       -1
      -------------------------
           x= y*(-2)

   */
  // O(N)
  public int[] addNegabinary(int[] arr1, int[] arr2) {
    int M = arr1.length, N = arr2.length;
    int i = M - 1, j = N - 1;
    Stack<Integer> res = new Stack();
    int carry = 0;
    while (i >= 0 || j >= 0 || carry != 0) {
      if (i >= 0) carry += arr1[i--];
      if (j >= 0) carry += arr2[j--];

      res.push(carry & 1);
      carry = (-1) * (carry >> 1);
    }

    while (res.size() > 1 && res.peek() == 0) res.pop();
    int[] r = new int[res.size()];
    i = 0;
    while (!res.isEmpty()) {
      r[i++] = res.pop();
    }
    return r;
  }
}
