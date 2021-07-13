//  Copyright 2018 The KeepTry Open Source Project
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

package dp.LongestIncreasingCommonSequence;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Leetcode300LongestIncreasingSubsequence {

  /*

  Input: nums = [10,9,2,5,3,7,101,18]
  Output: 4

  Input: nums = [0,1,0,3,2,3]
  Output: 4

  Input: nums = [7,7,7,7,7,7,7]
  Output: 1

  Idea:
  Just like locate right seat in country cinema to make sure
  not hiding other to watch the film.
  smaller one should stand before the taller one(replace)
  tallest one should stand alone(append)

   O(NlogN) time and O(N) space
  */
  public int lengthOfLIS(int[] nums) {
    int[] r = new int[nums.length];
    int size = 0;
    for (int i = 0; i < nums.length; i++) {
      int idx = Arrays.binarySearch(r, 0, size, nums[i]);
      if (idx < 0) {
        idx = ~idx;
        r[idx] = nums[i];
        if (idx == size) size++;
      }
    }
    return size;
  }

  /*
    1 <= nums.length <= 2500
    -104 <= nums[i] <= 104
  */
  public int lengthOfLIS2(int[] nums) {
    List<Integer> r = new LinkedList();
    r.add(nums[0]);
    for (int i = 1; i < nums.length; i++) {
      int idx = Collections.binarySearch(r, nums[i]);
      if (idx < 0) {
        idx = ~idx;
        if (idx == r.size()) r.add(nums[i]);
        else r.set(idx, nums[i]);
      }
    }
    return r.size();
  }
}
