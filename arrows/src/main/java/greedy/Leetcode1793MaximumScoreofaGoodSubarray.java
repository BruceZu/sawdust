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

package greedy;

public class Leetcode1793MaximumScoreofaGoodSubarray {
  public int maximumScore2(int[] nums, int k) {
    /*
     1 <= nums.length <= 105
     1 <= nums[i] <= 2 * 104
     0 <= k < nums.length
    */
    int result = nums[k];
    int l = k, r = k;
    int min = nums[k];
    while (l - 1 >= 0 || r + 1 <= nums.length - 1) {
      if (l == 0) {
        r++;
        min = Math.min(min, nums[r]);
      } else if (r == nums.length - 1) {
        l--;
        min = Math.min(min, nums[l]);
      } else {
        if (nums[l - 1] < nums[r + 1]) {
          r++;
          min = Math.min(min, nums[r]);
        } else {
          l--;
          min = Math.min(min, nums[l]);
        }
      }
      result = Math.max(result, min * (r - l + 1));
    }
    return result;
  }
  // or
  public int maximumScore(int[] nums, int k) {
    /*
     1 <= nums.length <= 105
     1 <= nums[i] <= 2 * 104
     0 <= k < nums.length
     Greedy. and why it works
     E.g. [1,4,3,7,4,5], k = 3
                    k
     index 0  1  2  3  4  5
           1  4  3  7  4  5
     2 length sub-array ------------
                 3  7
                    7  4
      [3,7] score = 3*2. decided by 3，
      [7,4] score = 4*2. decided by 4，
      compared 3 and 4. select [7,4]
      3 length sub-array ------------

              4  3  7
                 3  7  4
                    7  4  5
      compare 3 and 5, Note 3 has been compared by 4
      [7, 4 ,5] score = min{4,5} *3 > 3*3
      [3, 7, 4] score = 3*3
      [4, 3, 7] score = min{4,3} *3 <=3*3
      Take it in general:
       y1>3, y2>3
      [7, y1,y2] score = min{y1,y2} *3 > 3*3
      [3, 7, y1] score = 3*3
      [x, 3,  7] score = min{x,3} *3 <=3*3

      Do not forget to notice the failed one in the comparison,
      with it the selected one used to extend the current
      sub-array always make the new sub-array have the biggest score in
      the same size sub-arrays which must includes the number[k]. Or
      the failed one use its value to separate the same size sub-array
      including the number[k] into 2 group
      1.  > size * number[k]. only one.
      2. <= size * number[k]. others
  
      Also notice the number[k] sometimes affect the min within the sub-array score
      - number[k] >= any number[i]: above case
      - number[k] <= any number[i]:
      - other case
    */
    int result = nums[k];
    int l = k, r = k;
    int min = nums[k];
    // O(N)
    while (l - 1 >= 0 || r + 1 <= nums.length - 1) {
      if (l == 0) {
        r++;
      } else if (r == nums.length - 1) {
        l--;
      } else if (nums[l - 1] < nums[r + 1]) {
        r++;
      } else {
        l--;
      }
      min = Math.min(min, nums[l]);
      min = Math.min(min, nums[r]);
      result = Math.max(result, min * (r - l + 1));
    }
    return result;
  }
}
