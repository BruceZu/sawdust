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

package random;

import java.util.Random;

public class Leetcode398RandomPickIndex {
  /*
  1 <= nums.length <= 2 * 104
  -231 <= nums[i] <= 231 - 1
  target is an integer from nums.
  At most 104 calls will be made to pick.
  */
  static class Solution {
    int[] nums;
    Random r = new Random();

    public Solution(int[] nums) {
      // TODO: corner cases checking
      this.nums = nums;
    }

    /*
    Idea:
      This is a special case of `Reservoir Sampling` when the `k` is 1 The benefit of `Reservoir
      Sampling` algorithm is O(1) space
     */
    // O(N) time and O(1) space
    public int pick(int target) {
      int counts = 0;
      int resultIndex = 0;
      for (int i = 0; i < nums.length; i++) {
        if (nums[i] != target) continue;
        if (r.nextInt(++counts) == 0) resultIndex = i;
      }
      return resultIndex;
    }
  }
}

/**
 * Your Solution object will be instantiated and called as such: Solution obj = new Solution(nums);
 * int param_1 = obj.pick(target);
 */
