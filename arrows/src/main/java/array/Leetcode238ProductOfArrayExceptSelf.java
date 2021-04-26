//  Copyright 2016 The Sawdust Open Source Project
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

import java.util.Arrays;

public class Leetcode238ProductOfArrayExceptSelf {
  /*
  Idea:
   result[i] = product[0,i-1] * product[i+1,length-1]
   So It can be calculated in 2 steps.
    1> result[i] = product[0,i-1]
    2> result[i] = result[i] * product[i+1,length-1]
   If initial element of result[] with 1
    1> result[i] = result[i] * product[0,i-1]
    2> result[i] = result[i] * product[i+1,length-1]
   */
  public int[] productExceptSelf(int[] nums) {
    int[] re = new int[nums.length];
    Arrays.fill(re, 1); // initial re[i] is 1
    int N = nums.length;
    int l = 1, r = 1; // cumulative product of [0,i-1] and of [j+1,length-1]
    for (int i = 0, j = N - 1; i < N && j >= 0; i++, j--) {
      re[i] *= l; // note it is *= not =
      l *= nums[i];

      re[j] *= r;
      r *= nums[j];
    }
    return re;
  }
}
