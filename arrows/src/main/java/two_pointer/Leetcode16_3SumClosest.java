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

package two_pointer;

import java.util.Arrays;

public class Leetcode16_3SumClosest {

  /*
  Ask:
  Given an array nums of n integers and an integer target,
  find three integers in nums such that the sum is closest to target.
  Return the sum of the three integers.
  You may assume that each input would have exactly one solution.

  3 <= nums.length <= 10^3
  -10^3 <= nums[i] <= 10^3
  -10^4 <= target <= 10^4

  So user int[] and int for nums and target
  cases:
      note: 3 <= nums.length <= 10^3
      [2,2,2,2], 8, return 0;
      note: You may assume that each input would have
            exactly one solution

   O(N^2)time, O(1）assume Arrays.sort(A) use O(1) space
   Arrays.sort(A):  O(NlogN) time, space is depends on, O(N) or O(1)
  */
  public int threeSumClosest(int[] A, int target) {
    // assume that each input would have exactly one solution
    // so need not check if these initial values has been updated at last.
    int a = 0, d = Integer.MAX_VALUE;
    Arrays.sort(A);
    outer:
    for (int i = 0; i < A.length; i++) {
      int l = i + 1, r = A.length - 1;
      while (l < r) {
        int sum = A[i] + A[l] + A[r];
        int cd = Math.abs(sum - target);
        if (cd < d) {
          a = sum;
          d = cd;
        }
        if (sum < target) l++;
        else if (sum > target) r--;
        else break outer;
      }
    }
    return a;
  }
}
