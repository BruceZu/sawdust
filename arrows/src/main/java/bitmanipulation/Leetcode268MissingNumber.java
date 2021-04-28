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

package bitmanipulation;

public class Leetcode268MissingNumber {
  public int missingNumber(int[] nums) {
    int sum = 0;
    int all = 0;

    for (int i = 0; i <= nums.length - 1; i++) {
      sum += nums[i];
      all += i;
    }
    all += nums.length;
    return all - sum;
  }

  public int missingNumber2(int[] nums) {
    int x = 0;
    for (int i = 0; i <= nums.length - 1; i++) {
      x ^= nums[i];
      x ^= i;
    }
    x ^= nums.length;
    return x;
  }
}
