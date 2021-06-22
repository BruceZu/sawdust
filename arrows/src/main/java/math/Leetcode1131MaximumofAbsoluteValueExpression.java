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

public class Leetcode1131MaximumofAbsoluteValueExpression {
  /*
  Ask
    Given two arrays of integers with equal lengths, return the maximum value of:
    |arr1[i] - arr1[j]| + |arr2[i] - arr2[j]| + |i - j|
    where the maximum is taken over all 0 <= i, j < arr1.length.

    Example 1:
     Input: arr1 = [1,2,3,4], arr2 = [-1,4,5,6]
     Output: 13

    Example 2:
     Input: arr1 = [1,-2,-5,0,10], arr2 = [0,-2,-1,-7,-4]
     Output: 20


     2 <= arr1.length == arr2.length <= 40000
     -10^6 <= arr1[i], arr2[i] <= 10^6
   */

  /*
    |A[i] - A[j]| + |B[i] - B[j]| + |i - j|
    = max of 8 possible result
     A[i] - A[j] + B[i] - B[j] + i - j
     A[i] - A[j] + B[i] - B[j] - i + j
     A[i] - A[j] - B[i] + B[j] + i - j
     A[i] - A[j] - B[i] + B[j] - i + j
    -A[i] + A[j] + B[i] - B[j] + i - j
    -A[i] + A[j] + B[i] - B[j] - i + j
    -A[i] + A[j] - B[i] + B[j] + i - j
    -A[i] + A[j] - B[i] + B[j] - i + j

    = max of 8 possible result
    (A[i] + B[i] + i) -(A[j] +  B[j] + j)
    (A[i] + B[i] - i) -(A[j] +  B[j] - j)
    (A[i] - B[i] + i) -(A[j] -  B[j] + j)
    (A[i] - B[i] - i) -(A[j] -  B[j] - j)

  - (A[i] - B[i] - i) +(A[j] -  B[j] - j)
  - (A[i] - B[i] + i) +(A[j] -  B[j] + j)
  - (A[i] + B[i] - i) +(A[j] +  B[j] - j)
  - (A[i] + B[i] + i) +(A[j] +  B[j] + j)

    = max of 4 possible result
    (A[i] + B[i] + i) -(A[j] +  B[j] + j)   e1
    (A[i] + B[i] - i) -(A[j] +  B[j] - j)   e2
    (A[i] - B[i] + i) -(A[j] -  B[j] + j)   e3
    (A[i] - B[i] - i) -(A[j] -  B[j] - j)   e4

    = max of
    max(e1)-min(e1),
    max(e2)-min(e2),
    max(e3)-min(e3),
    max(e4)-min(e4),

    O(N) time and O(1) space
     */
  public static int maxAbsValExpr(int[] A, int[] B) {
    int x1 = Integer.MIN_VALUE;
    int x2 = Integer.MIN_VALUE;
    int x3 = Integer.MIN_VALUE;
    int x4 = Integer.MIN_VALUE;

    int i1 = Integer.MAX_VALUE;
    int i2 = Integer.MAX_VALUE;
    int i3 = Integer.MAX_VALUE;
    int i4 = Integer.MAX_VALUE;
    for (int i = 0; i < A.length; i++) {
      x1 = Integer.max(A[i] + B[i] + i, x1);
      i1 = Integer.min(A[i] + B[i] + i, i1);

      x2 = Integer.max(A[i] + B[i] - i, x2);
      i2 = Integer.min(A[i] + B[i] - i, i2);

      x3 = Integer.max(A[i] - B[i] - i, x3);
      i3 = Integer.min(A[i] - B[i] - i, i3);

      x4 = Integer.max(A[i] - B[i] + i, x4);
      i4 = Integer.min(A[i] - B[i] + i, i4);
    }
    int d1 = x1 - i1;
    int d2 = x2 - i2;
    int d3 = x3 - i3;
    int d4 = x4 - i4;
    return Integer.max(Integer.max(d1, d2), Integer.max(d3, d4));
  }
}
