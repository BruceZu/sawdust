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
       Idea 1> pre calculated a pre sum array: O(N^2) time and O(N) space
       Idea 2> a temp cumulative sum to get a algorithm O(N^2) time and O(1) space:
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
       Idea 3> With map: pre sum and its account kep
        O(N) time and O(N) space
     */
    Map<Integer, Integer> m = new HashMap(); // map Sum To its Counts
    int s = 0, r = 0; // s: presum of nums[0],..., nums[i]
    m.put(s, m.getOrDefault(s, 0) + 1);
    for (int i : nums) {
      // Once it is hit later by later_sum - k = this_sum then we find it/them
      // how many is it? check the v of k:v mapping.
      s += i;
      r += m.getOrDefault(s - k, 0);
      m.put(s, m.getOrDefault(s, 0) + 1);
    }
    return r;
  }
}
