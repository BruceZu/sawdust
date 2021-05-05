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

package wiggle_sort;

import java.util.Arrays;

public class Leetcode280WiggleSort {
  /*
  Given an integer array nums, reorder it such that
  nums[0] <= nums[1] >= nums[2] <= nums[3]....
  You may assume the input array always has a valid answer. (Note here)

     1 <= nums.length <= 5 * 104
     0 <= nums[i] <= 104
     It is guaranteed that there will be an answer for the given input nums.
     */
  private void swap(int[] n, int i, int j) {
    int t = n[i];
    n[i] = n[j];
    n[j] = t;
  }
  // O(NlogN)
  // You may assume the input array always has a valid answer. (Note here)
  public void wiggleSortOriginal(int[] n) {
    Arrays.sort(n);
    for (int i = 1; i <= n.length - 2; i += 2) swap(n, i, i + 1);
  }

  // You may assume the input array always has a valid answer. (Note here)
  // O(N)
  public void wiggleSort3(int[] n) {
    // l means: should the relationship between current number and next number be `<=` ?
    boolean l = true;
    for (int i = 0; i <= n.length - 2; i++) {
      if (l) {
        if (n[i] > n[i + 1]) swap(n, i, i + 1);
      } else if (n[i] < n[i + 1]) swap(n, i, i + 1);
      l = !l; // switch, according the wiggle sort definition
    }
  }

  // other format O(N) time
  public void wiggleSort2(int[] nums) {
    for (int i = 0; i < nums.length - 1; i++) {
      if ((i & 1) == 0 && nums[i] > nums[i + 1] // even index, expect <=
          || (i & 1) == 1 && nums[i] < nums[i + 1]) {
        swap(nums, i, i + 1);
      }
    }
  }
  // O(N) time
  public void wiggleSort(int[] nums) {
    for (int i = 0; i < nums.length - 1; i++) {
      if (((i & 1) == 0) == (nums[i] > nums[i + 1])) {
        swap(nums, i, i + 1);
      }
    }
  }
}
