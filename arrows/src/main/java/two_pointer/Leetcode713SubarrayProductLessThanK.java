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

package two_pointer;

import java.util.Arrays;

public class Leetcode {

  public int numSubarrayProductLessThanK(int[] nums, int K) {
    if (K == 0) return 0;
    double k = K;
    double[] pre = new double[nums.length];
    pre[0] = nums[0];
    for (int i = 1; i < nums.length; i++) {
      pre[i] = pre[i - 1] * nums[i];
    }

    int a = 0;
    for (int i = 0; i < pre.length; i++) {
      int l = i + 1, r = pre.length - 1;
      int tmp = Arrays.binarySearch(pre, l, r + 1, i == 0 ? k : k / pre[i - 1]);
      if (tmp < 0) {
        tmp = ~tmp;
        if (tmp == i + 1) a += 1;
        else a += tmp - i;
      } else {
        a += tmp - i;
      }
    }
    return a;
  }

  public static void main(String[] args) {

    [10,5,2,6]
    100

  }
}
