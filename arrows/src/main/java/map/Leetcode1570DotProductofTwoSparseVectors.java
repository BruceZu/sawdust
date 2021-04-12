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
  public Map<Integer, Integer> v = new HashMap();
  public int size = 0;

  SparseVector(int[] nums) {
    for (int i = 0; i < nums.length; i++) {
      if (nums[i] != 0) {
        v.put(i, nums[i]);
      }
    }
    size = nums.length;
  }

  // Return the dotProduct of two sparse vectors
  // O(N) time and space. N is the min(size of 2 SparseVector)
  public int dotProduct(SparseVector vec) {
    if (vec.size == 0 || this.size == 0) return 0;
    if (vec.size < this.size) return vec.dotProduct(this);
    // what is the definition of dot product? should not it be same length?
    int sum = 0;
    for (Map.Entry<Integer, Integer> cur : v.entrySet()) {
      int idx = cur.getKey();
      if (vec.v.containsKey(idx)) sum += cur.getValue() * vec.v.get(idx);
    }
    return sum;
  }
}

// Your SparseVector object will be instantiated and called as such:
// SparseVector v1 = new SparseVector(nums1);
// SparseVector v2 = new SparseVector(nums2);
// int ans = v1.dotProduct(v2);
