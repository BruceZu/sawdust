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

package array;

public class Leetcode1800MaximumAscendingSubarraySum {
  public int maxAscendingSum(int[] nums) {
    /*
        1 <= nums.length <= 100
        1 <= nums[i] <= 100
    */

    /*
    ??
    */
    int ans = 0;
    int max = nums[0];
    for (int i = 1; i < nums.length; i++) {
      if (nums[i] > nums[i - 1]) {
        max += nums[i];
      } else {
        ans = Math.max(ans, max);
        max = nums[i];
      }
    }
    return Math.max(ans, max);
  }
}
