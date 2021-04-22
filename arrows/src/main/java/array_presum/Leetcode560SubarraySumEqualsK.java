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

package array_presum;

import java.util.HashMap;
import java.util.Map;

/**
 * Given an array of integers and an integer k, you need to find the total number of continuous
 * subarrays whose sum equals to k.
 *
 * <pre>
 *   Example 1:
 *   Input:nums = [1,1,1], k = 2
 *   Output:  2
 *
 *   Note:
 *   The length of the array is in range [1, 20,000].
 *   The range of numbers in the array is [-1000, 1000]
 *   and the range of the integer k is [-1e7, 1e7].
 */
public class Leetcode560SubarraySumEqualsK {

  public int subarraySum(int[] nums, int k) {
    /*
      1 <= nums.length <= 2 * 104
      -1000 <= nums[i] <= 1000
      -107 <= k <= 107
       TODO: corner cases validation
    */

    /*
    Idea
     With a pre calculated pre sum array to get a algorithm O(N^2) time and O(N) space
     further with a temp cumulative sum to get a algorithm O(N^2) time and O(1) space:
       public int subarraySum(int[] nums, int k) {
         int count = 0;
         for (int start = 0; start < nums.length; start++) {
            int sum=0;
            for (int end = start; end < nums.length; end++) {
                sum+=nums[end];
                if (sum == k)
                    count++;
            }
         }
         return count;
       }

     With pre sum and its account kep in a map to get a algorithm O(N) time and O(N) space
     */
    Map<Integer, Integer> map = new HashMap(); // map Sum To its Counts
    int preSum = 0, r = 0; // sum is presum of nums[0],..., nums[i]
    for (int i : nums) {
      // Once it is hit later by later_sum - k = this_sum then we find it/them
      // how many is it? check the v of k:v mapping.
      map.put(preSum, map.getOrDefault(preSum, 0) + 1);
      preSum += i;
      r += map.getOrDefault(preSum - k, 0);
    }
    return r;
  }
}
