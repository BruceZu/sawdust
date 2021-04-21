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

import java.util.Arrays;
import java.util.Random;

public class Leetcode528RandomPickwithWeight {}

class Solution {
  int[] sum;
  Random r = new Random(); // use the same one.
  int ranBound;

  public Solution(int[] w) {
    /*
      1 <= w.length <= 10000
      1 <= w[i] <= 10^5
      pickIndex will be called at most 10000 times.
     TODO: corner cases validation

     Basic idea:
     1. Image array composed by the value is of target ID, it
        is index value here, number of each target ID is its weight.
     2. Then each pickup operation is given a uniform random number `r`
        in the  [1, sum(w_i)]

     Thus for k time pickup the time that target i is picked up is
     k*w_i/sum(w_i)
     This algorithm is O(1) time and O(sum(w_i)) space

     Space complexity improvement:
     The uniform random`r` is mapping to a cumulative sum which is in
     ascending order so it is sorted array,  thus with `Arrays.binarySearch()`
     to find index of `r`. The index is also the index of target in original array.

     O(logN) time, O(N) space.
    */
    sum = new int[w.length];
    sum[0] = w[0];
    for (int i = 1; i < w.length; i++) sum[i] = sum[i - 1] + w[i];
    ranBound = Arrays.stream(w).sum();
  }

  public int pickIndex() {
    int rv = r.nextInt(ranBound) + 1; // rv is in [1, ranBound]
    int index = Arrays.binarySearch(sum, rv);
    return index >= 0 ? index : -(index + 1); // return index >=0 or -index-1
  }
}
