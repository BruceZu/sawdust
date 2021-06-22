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

package two_pointer;

import java.util.Arrays;

public class Leetcode713SubarrayProductLessThanK {
  /*
  Ask:
     an array of positive integers nums and an integer k,
     return the number of contiguous subarrays where
     the product of all the elements in the subarray is strictly less than k.
     Note:

      1 <= nums.length <= 3 * 10^4
      1 <= nums[i] <= 1000
      0 <= k <= 10^6

    Idea:
      k can be 0, 1
      nums[i] is positive integer. not 0 or negative
   */
  /*
  -----------------------------------------------------------------------------
  Slide window:
    based on no-decreasing product of sub array based on positive int array
  A:
    calculate the number of subarray with same right element
    O(N) time, O(1) space
   */
  public int numSubarrayProductLessThanK(int[] nums, int k) {
    int p = 1, l = 0, a = 0;
    for (int r = 0; r < nums.length; r++) {
      p *= nums[r];
      while (p >= k && l < nums.length) p /= nums[l++];
      if (l <= r) a += r - l + 1;
    }
    return a;
  }

  /*
   ---------------------------------------------------------------------------
   B:
    calculate the number of subarray with same left element
    logic:
      move right side when it is possible to move right side
      else move left  if it is possible to move left
      else stop and return result.
  */
  public int numSubarrayProductLessThanKSW2(int[] nums, int k) {
    int l = 0, r = 0, p = nums[0], a = p < k ? 1 : 0, N = nums.length;
    while (true) {
      if (r + 1 < N && p * nums[r + 1] < k) {
        r++;
        p *= nums[r];
        a++;
      } else if (l + 1 < N) {
        p /= nums[l];
        l++;
        if (p < k && l <= r) a += r - l + 1;
      } else return a;
    }
  }

  /*
  -----------------------------------------------------------------------------
  Binary search
    convert A[i] to logA[i], else
    premul -> presum, non-decreasing array
    O(NlogN) time, O(N) space
   */
  public static int numSubarrayProductLessThanKBS(int[] nums, int K) {
    if (K == 0) return 0;
    double k = Math.log(K);
    double[] p = new double[nums.length + 1]; // length+1
    for (int i = 1; i < p.length; i++) p[i] = p[i - 1] + Math.log(nums[i - 1]);

    int a = 0;
    for (int i = 1; i < p.length; i++) {
      int t = Arrays.binarySearch(p, i, p.length, p[i - 1] + k);
      if (t < 0) t = ~t;
      else while (i <= t && p[t] == p[t - 1]) t--;
      t--;
      if (i <= t) a += t - i + 1;
    }
    return a;
  }
}
