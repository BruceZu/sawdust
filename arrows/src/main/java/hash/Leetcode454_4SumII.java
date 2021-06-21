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

package hash;

import java.util.HashMap;
import java.util.Map;

public class Leetcode454_4SumII {
  /*
  Ask:
   Given four integer arrays nums1, nums2, nums3, and nums4
   all of length n, return the number of tuples (i, j, k, l) such that:

      0 <= i, j, k, l < n
      nums1[i] + nums2[j] + nums3[k] + nums4[l] == 0


      n == nums1.length
      n == nums2.length
      n == nums3.length
      n == nums4.length
      1 <= n <= 200
      -228 <= nums1[i], nums2[i], nums3[i], nums4[i] <= 228

   cases:
    nums1 = [1,2], nums2 = [-2,-1], nums3 = [-1,2], nums4 = [0,2]
    Output: 2
      two tuples are:
      1. (0, 0, 0, 1) -> nums1[0] + nums2[0] + nums3[0] + nums4[1] = 1 + (-2) + (-1) + 2 = 0
      2. (1, 1, 0, 0) -> nums1[1] + nums2[1] + nums3[0] + nums4[0] = 2 + (-1) + (-1) + 0 = 0

    Input: nums1 = [0], nums2 = [0], nums3 = [0], nums4 = [0]
    Output: 1

   Idea:
     duplicated value is accepted: A[i]  B[j]  C[i]  D[j] are same as A[i2] B[j2] C[i2] D[j2]
   */
  // O(N^2) time and space
  public int fourSumCount(int[] A, int[] B, int[] C, int[] D) {
    // 0 <= i, j, k, l < n
    // so any array is not null
    // arrays are all have the same length
    int N = A.length;
    Map<Integer, Integer> m = new HashMap();
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++) {
        int v = A[i] + B[j];
        m.put(A[i] + B[j], m.getOrDefault(v, 0) + 1);
      }
    }

    int r = 0;
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++) {
        int v = C[i] + D[j];
        r += m.getOrDefault(-v, 0);
      }
    }
    return r;
  }

  // --------------------------------------------------------------------------
  /*
   Idea:
    K sum: make the above idea general
    k arrays:
    use a map sum:counts of half of k arrays [0, l.length / 2)
    calculate the answer from the left arrays index range [l.length / 2, length)

   Easy fault:
     forget return in halfSumInMap

   O(N^e) time, space  e=max{N/2, N-N/2}, space for the map;
  */
  public int fourSumCount2(int[] A, int[] B, int[] C, int[] D) {
    int[][] as = new int[][] {A, B, C, D};
    Map<Integer, Integer> m = new HashMap();
    halfSumInMap(as, m, 0, 0);
    return calculate(as, m, as.length / 2, 0);
  }

  private void halfSumInMap(int[][] as, Map<Integer, Integer> m, int i, int sum) {
    if (i == as.length / 2) {
      m.put(sum, m.getOrDefault(sum, 0) + 1);
      return;
    }
    for (int v : as[i]) halfSumInMap(as, m, i + 1, sum + v);
  }

  private int calculate(int[][] as, Map<Integer, Integer> m, int i, int target) {
    if (i == as.length) return m.getOrDefault(target, 0);
    int r = 0;
    for (int v : as[i]) {
      r += calculate(as, m, i + 1, target - v);
    }
    return r;
  }
}
