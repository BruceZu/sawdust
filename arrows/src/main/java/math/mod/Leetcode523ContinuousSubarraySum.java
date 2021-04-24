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

package math.mod;

import java.util.HashMap;
import java.util.Map;

public class Leetcode523ContinuousSubarraySum {
  /*
  -  'a continuous subarray of size at least two'
  -  'An integer x is a multiple of k if
      there exists an integer n such that x = n * k.
      0 is always a multiple of k.'
   Let   presum_diff = presum_i - presum_j
   it means to find presum_diff % k == 0 and i-j >= 2.
   or to find presum_i % k  == presum_j % k and i-j >= 2.
   as       presum_i + presum_diff = presum_j
   and      presum_diff % k == 0
   so       presum_i % k  == presum_j % k
   Thus algorithm is to use a map to keep (presum%k : first index finding this key)

    O(n) time
    O(min(n,k)) space. Depends on the value of  sum % k

   Note
   - '0 is always a multiple of k'. This means it is also right if presum_diff is 0
      e.g.: array is [5, 0, 0], k = 3

   -  Keep the (s % k : firstly found index) mapping relationship in map.
      If s % k appears again, do not update its map value, the index, to make sure not missing result
      because it requires 'a continuous subarray of size at least two'

   -  Note 0 can not be applied to %
      when k is 0. it means to find presum_diff == 0, e.g.: array is [5, 0, 0], k = 3
  */
  public static boolean checkSubarraySum(int[] nums, int k) {
    /*
    1 <= nums.length <= 10^5
    0 <= nums[i] <= 10^9
    0 <= sum(nums[i]) <= 2^31 - 1
    1 <= k <= 2^31 - 1
    TODO: corner cases validation
     */
    int s = 0;
    Map<Integer, Integer> map = new HashMap();
    int i = 0;
    map.put(0, -1); // for the first key is m%k == 0
    for (int n : nums) {
      s += n;
      if (k != 0) s %= k; // Note 0 can not be applied to %
      if (map.containsKey(s)) {
        // a continuous subarray of size at least two
        if (i - map.get(s) >= 2) return true;
      } else map.put(s, i);
      // keep the index of first key=s
      i++;
    }
    return false;
  }
}
