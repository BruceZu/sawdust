//  Copyright 2017 The keepTry Open Source Project
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

package two_pointer;

import java.util.Arrays;

public class Leetcode259The3SumSmaller {

  /*
  Given an array of n integers nums and a target,
     find the number of index triplets i, j, k
     with 0 <= i < j < k < n that satisfy the condition
     nums[i] + nums[j] + nums[k] < target.

    nums = [-2, 0, 1, 3], and target = 2.
    Return 2. because:  [-2, 0, 1], [-2, 0, 3]

  Idea:
      n == nums.length
      0 <= n <= 3500

     -100 <= nums[i] <= 100
     -100 <= target <= 100
     so use int[]  and int for nums and target

     cases:
      nums is null return 0;
      nums length <=2 return 0;
      nums is duplicated [2,2,2,2] target is 8 return 0
                         [2,2,2,2] target is 9 return 4
      general nums is [ -1, 3, 1,2]
      sort it: [ -1, 1, 2, 3]
      duplicate value are accepted,  only require the the index is unique

    2 pointers for 2 sum
    O(N^2) time, O(1) space
    easy fault:
     - forget to try l++
     - did not check duplicate vale allowed
  */
  public int threeSumSmaller(int[] A, int target) {
    if (A == null || A.length <= 2) return 0;
    Arrays.sort(A);
    int a = 0;
    for (int i = 0; i < A.length; i++) {
      int l = i + 1, r = A.length - 1;
      while (l < r) {
        if (A[i] + A[l] + A[r] >= target) r--;
        else {
          a += r - l;
          l++;
        }
      }
    }
    return a;
  }
}
