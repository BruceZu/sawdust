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

package backtracing;

public class Leetcode1799MaximizeScoreAfterNOperations {
  /*
  https://leetcode.com/problems/maximize-score-after-n-operations/
  1 <= n <= 7
  nums.length == 2 * n
  1 <= nums[i] <= 10^6

  */
  /*
  Idea:
  - DP(top down)
  - cache the result by representing the status with bit mask + recursion + backtracking
  - Runtime O(n^3) time. Each recursion is N^2,N=2n, recursion depth is n. Without take cache into account.
  Note:
    << has lower priority than +
  */

  public static int maxScore(int[] nums) {
    //
    return backtracking(1, nums, 0, new int[1 << nums.length]);
  }

  /**
   * @param p: current operation number
   * @param A: number array
   * @param b: bit used to represent status: elements who have been selected or not
   * @param cache
   * @return
   */
  private static int backtracking(int p, int[] A, int b, int cache[]) {
    if (p == A.length / 2 + 1) { // or status == (int) Math.pow(2d, nums.length * 1d) - 1
      return 0;
    }
    if (cache[b] == 0) {
      int v = 0; // score value
      for (int i = 0; i < A.length; i++) {
        if (0 != (b & 1 << i)) continue;
        for (int j = i + 1; j < A.length; j++) {
          if (0 != (b & (1 << j))) continue;
          v =
              Math.max(
                  v, p * gcd(A[i], A[j]) + backtracking(p + 1, A, b + (1 << j) + (1 << i), cache));
        }
      }
      cache[b] = v;
    }
    return cache[b];
  }

  private static int gcd(int a, int b) {
    return b == 0 ? a : gcd(b, a % b);
  }

  // --------------------------------------------------------------------------
  public static void main(String[] args) {
    System.out.println(maxScore(new int[] {3, 4, 6, 8}) == 11);
    System.out.println(maxScore(new int[] {1, 2}) == 1);
  }
}
