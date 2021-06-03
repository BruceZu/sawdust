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

package map;

import java.util.HashMap;
import java.util.Map;

public class Leetcode1570DotProductofTwoSparseVectors {}

class SparseVector {
  /*
  TODO:corner cases checking
  n == nums1.length == nums2.length
  1 <= n <= 10^5
  0 <= nums1[i], nums2[i] <= 100
  */
  public Map<Integer, Integer> v = new HashMap(); // index:value

  SparseVector(int[] nums) {
    for (int i = 0; i < nums.length; i++) if (nums[i] != 0) v.put(i, nums[i]);
  }

  // Return the dotProduct of two sparse vectors/array
  // O(N) time and space
  public int dotProduct(SparseVector vec) {
    int r = 0;
    for (Map.Entry<Integer, Integer> e : v.entrySet()) {
      int i = e.getKey();
      if (vec.v.containsKey(i)) r += e.getValue() * vec.v.get(i);
    }
    return r;
  }
}
