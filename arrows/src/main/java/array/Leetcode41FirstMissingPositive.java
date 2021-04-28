//  Copyright 2016 The Sawdust Open Source Project
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

package array;

public class Leetcode41FirstMissingPositive {

  public int firstMissingPositive1(int[] nums) {
    /*
     Question:
        n is nums.length
        find the smallest number of the [1,...,n,n+1] but not in nums
    Idea:
        1. loop nums, for any nums[i] not in [1, n], reset its value as n+1
        2. loop each nums[i] if it is in [1,n] then
          res set nums[nums[i]-1] as negative if it not negative.
        3. loop again to find the first positive nums[i]
           i+1 is the answer.
        O(N) time and O(1) space
     */
    /*
    1 <= nums.length <= 300
    -2^31 <= nums[i] <= 2^31 - 1
    */
    // TODO: checking nums is not null

    int n = nums.length;
    for (int i = 0; i < n; i++) {
      if (nums[i] < 1 || nums[i] > n) nums[i] = n + 1;
    }
    for (int i = 0; i < n; i++) {
      int v = Math.abs(nums[i]); // Note here
      if (v != n + 1 && nums[v - 1] > 0) nums[v - 1] = -nums[v - 1];
    }
    for (int i = 0; i < n; i++) {
      if (nums[i] > 0) return i + 1;
    }
    // how about case [1,2,3]?
    return n + 1;
  }

  // Alternative --------------------------------------------------------------
  public int firstMissingPositive(int[] A) {
    int n = A.length;
    // O(N)
    for (int i = 0; i < n; i++)
      // At most N, in that case left in the outer loop need not go into the inner loop.
      while (A[i] >=1  && A[i] <= n && A[A[i] - 1] != A[i]) swap(A, i, A[i] - 1);

    for (int i = 0; i < n; i++) if (A[i] != i + 1) return i + 1;
    return n + 1;
  }

  private void swap(int[] A, int l, int r) {
    if (l != r) {
      int x = A[l] ^ A[r];
      A[l] = x ^ A[l];
      A[r] = x ^ A[r];
    }
  }
}
