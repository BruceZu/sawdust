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

  1 <= n <= 7
  nums.length == 2 * n
  1 <= nums[i] <= 10^6

  */
  /*
  Idea:
  - DP(top down) + cache the result by representing the status with bit mask + recursion + backtracking
  - Runtime O(n^3) time. Eache recursion is N^2,N=2n, recursion depth is n. Without take cache into account.
  Note:
    << has lower priority than +
  */

  public static int maxScore(int[] nums) {
    return backtracking(1, nums, 0, new int[1 << nums.length]);
  }

  /*
  t: without it, no way to know which time current recursion is of to receive the score.
  status: with it to represent current status.
  cache: with it to keep current status's max score, avoid duplicate calculation.
  */
  private static int backtracking(int t, int[] nums, int status, int cache[]) {
    if (t == nums.length / 2 + 1) { // or status == (int) Math.pow(2d, nums.length * 1d) - 1
      return 0;
    }
    if (cache[status] == 0) {
      int score = 0;
      for (int i = 0; i < nums.length; i++) {
        if (0 != (status & 1 << i)) continue;
        for (int j = i + 1; j < nums.length; j++) {
          if (0 != (status & (1 << j))) continue;
          score =
              Math.max(
                  score,
                  t * gcd(nums[i], nums[j])
                      + backtracking(t + 1, nums, status + (1 << j) + (1 << i), cache));
        }
      }
      cache[status] = score;
    }
    return cache[status];
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
