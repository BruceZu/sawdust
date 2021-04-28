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

import java.util.LinkedList;
import java.util.List;

public class Leetcode448FindAllNumbersDisappearedinanArray {

  public List<Integer> findDisappearedNumbers(int[] nums) {
    /*
     n == nums.length
     1 <= n <= 10^5
     1 <= nums[i] <= n
    */
    // TODO: checking core cases：
    //   - 1<= nums[i] <=nums.length
    //   nums !=null
    //  Note: nums length is n and nums[i] is in [1,n]
    //   O(N) time and O(1) space assume the returned list does not count as extra space.

    for (int n : nums) {
      int t = n > 0 ? n : -n;
      if (nums[t - 1] > 0) nums[t - 1] = -nums[t - 1];
    }
    List<Integer> r = new LinkedList();
    for (int i = 0; i <= nums.length - 1; i++) if (nums[i] > 0) r.add(i + 1);
    return r;
  }
}
