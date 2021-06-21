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

public class Leetcode1099TwoSumLessThanK {
  /*
  Ask: an array nums of integers and integer k
     return the maximum sum such that there exists
     i < j with nums[i] + nums[j] = sum and
     sum < k.
     If no i, j exist satisfying this equation, return -1.

      1 <= nums.length <= 100
      1 <= nums[i] <= 1000
      1 <= k <= 2000


     nums = [34,23,1,24,75,33,54,8], k = 60
     output: 58

     nums = [5,5,5], k = 15
     output: 10

     nums = [10,20,30], k = 15
     output: -1

    Idea: Set
    O(NlogN) time, O(logN) space used by sort
  */
  public int twoSumLessThanK(int[] A, int k) {
    // 1 <= nums.length <= 100
    Arrays.sort(A);
    int l = 0, r = A.length - 1;
    int re = -1;
    while (l < r) {
      int sum = A[l] + A[r];
      if (sum >= k) r--;
      else {
        if (sum > re) re = sum;
        l++;
      }
    }
    return re;
  }

  /* --------------------------------------------------------------------------
  Idea: Binary search from right side
   binary search k - A[L] in index range [ L+1, R]
   Arrays.binarySearch(): "If the range contains multiple elements with the specified value,
   there is no guarantee which one will be found."

   O(NlogN) time,
   O(logN) space used by sort
  */
  public int twoSumLessThanKBS(int[] A, int k) {
    // 1 <= nums.length <= 100
    Arrays.sort(A);
    int L = 0, R = A.length - 1;
    int sum = -1;
    while (L < R) {
      int r = Arrays.binarySearch(A, L + 1, R + 1, k - A[L]);
      if (r >= 0) while (L < r && A[r] == A[r - 1]) r--;
      else r = ~r;
      r--;
      if (L < r && A[L] + A[r] > sum) sum = A[L] + A[r];
      while (L < r && A[L] == A[L + 1]) L++;
      L++;
      R = r;
    }
    return sum;
  }
  // [34,34,23,1,24,52,33,52,54,54,8,8], k = 60
  // [1,8,8,23,24,33,34,34,52,52,54,54]
  //  0 1 2  3  4  5  6  7  8  9 10 11
  // L=0, R=11, r=11,  sum= 55,
  // L=1, R=11  r=7,
  // L=3  R=7   r=7   sum= 57
  // L=4  R=7   r=7   sum= 58
  // L=5  R=7   r=5
  // L=6  R=5 stop

  /*
  -----------------------------------------------------------------------------
  use the array element value range, like sort
  '1 <= nums[i] <= 1000'
  start from l=1 and right =1000
  Note the case: [5,5,5], k=15
  when l=r if counts[l]>=2 and 2*nums[l]<k, check it with result.

  O(M) time, M=max{N,1000}
  O(M) space
  */
  public int twoSumLessThanKVR(int[] nums, int k) {
    int[] A = new int[1001];
    for (int v : nums) A[v]++;
    int l = 1, r = 1000, a = -1;
    while (l < r) {
      while (l < r && A[l] == 0) l++;
      while (l < r && A[r] == 0) r--;
      if (l < r) {
        int sum = l + r;
        if (k <= sum) r--;
        else {
          if (a < sum) a = sum;
          l++;
        }
      }
    }
    if (l == r && A[l] >= 2 && a < l + r && l + r < k) return l + r;
    return a;
  }
}
