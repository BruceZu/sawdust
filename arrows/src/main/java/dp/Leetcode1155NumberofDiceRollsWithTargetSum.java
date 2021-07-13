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

package dp;

import java.util.*;

public class Leetcode1155NumberofDiceRollsWithTargetSum {
  /*
  out of f^d total ways.
  Return the number of possible ways
  modulo 10^9 + 7 to roll the dice so
  the sum of the face-up numbers equals target.

  1 <= d, f <= 30
  1 <= target <= 1000

  Input: d = 1, f = 6, target = 3
  Output: 1

  Input: d = 2, f = 6, target = 7
  Output: 6

  Input: d = 2, f = 5, target = 10
  Output: 1

  Input: d = 1, f = 2, target = 3
  Output: 0

  Input: d = 30, f = 30, target = 500
  Output: 222616187

  Idea: each dice will take part in.
  O(f ^ d) runtime.
  O(d) space for the stack.
  */

  private Map<String, Integer> cache = new HashMap();

  int numRollsToTarget(int dice, int face, int target) {
    if (dice == 0 && target == 0) return 1;
    if (dice == 0 || target <= 0) return 0;
    String k = dice + "-" + target;
    if (cache.containsKey(k)) return cache.get(k);
    int sum = 0;
    for (int f = 1; f <= face; f++)
      sum = (sum + numRollsToTarget(dice - 1, face, target - f)) % 1000000007;
    cache.put(k, sum);
    return sum;
  }

  /*
  bottom up
  processing dp[target] backwards,
  clear the previous dp[target]
  O(dice * face * target) time, O(target) space
  */
  int numRollsToTarget2(int dice, int face, int target) {
    int[] dp = new int[target + 1];
    dp[0] = 1;
    for (int d = 1; d <= dice; d++) {
      for (int t = target; t >= 0; t--) {
        dp[t] = 0;
        for (int pred = t - 1; pred >= Math.max(0, t - face); pred--) {
          dp[t] = (dp[t] + dp[pred]) % 1000000007;
        }
      }
    }
    return dp[target];
  }

  public int lengthOfLIS(int[] nums) {
    List<Integer> r = new LinkedList();
    r.add(nums[0]);
    for (int i = 1; i < nums.length; i++) {
      int idx = Collections.binarySearch(r, nums[i]);
      if (idx < 0) {
        idx=~idx;
        if(idx==0) r.add(0,nums[i]);
        else if(idx==r.size()) r.add(nums[i]);
        else r.set(~idx, nums[i]);
      }
    }
    return r.size();
  }
}
