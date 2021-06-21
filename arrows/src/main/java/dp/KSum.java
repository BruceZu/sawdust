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

package dp;

public class KSum {
  /*
    given a distinct(optional) and integer array
    find k number to make their sum = target,
    1 <= k < = length
    target is positive number
    ask the number of solutions.

    Idea:
    require distinct if requiring the tuples value are unique.
    why target is positive ? target is used as index

    bottom up ------------------------------------------------------------------
     dp[l][k][t]  the number of tuples which has k elements and sum is t for
                 sub array, A[0]~A[l-1]
     dp[l][0][0] = 1; 0 <= l <= A.length // used to calculate tuples where t!=0 or t!=0
     O(L*K*T) time, space, L is array length, T is target value;
  */
  static int ksum(int[] A, int K, int T) {
    int L = A.length;
    int[][][] dp = new int[L + 1][K + 1][T + 1];
    for (int l = 0; l <= L; l++) {
      for (int k = 0; k <= K; k++) {
        for (int t = 0; t <= T; t++) {
          if (k == 0 && t == 0) {
            dp[l][0][0] = 1;
          } else if (l >= 1 && k != 0 && t != 0) {
            dp[l][k][t] = dp[l - 1][k][t]; // not use A[l - 1]
            int v = A[l - 1];
            if (t - v >= 0) dp[l][k][t] += dp[l - 1][k - 1][t - v]; // use A[l - 1]
          }
        }
      }
    }

    return dp[L][K][T];
  }

  // concise version of bottom up  --------------------------------------------
  static int ksumConcise(int[] A, int K, int T) {
    int L = A.length;
    int[][][] dp = new int[L + 1][K + 1][T + 1];

    for (int l = 0; l <= L; l++) dp[l][0][0] = 1;
    // l, k, t >=1 based on dp[0][0][0]
    for (int l = 1; l <= L; l++) {
      for (int k = 1; k <= K; k++) {
        for (int t = 1; t <= T; t++) {
          dp[l][k][t] = dp[l - 1][k][t];
          int v = A[l - 1];
          if (t - v >= 0) {
            dp[l][k][t] += dp[l - 1][k - 1][t - v];
          }
        }
      }
    }

    return dp[L][K][T];
  }

  // top-down idea ------------------------------------------------------------
  // l is length of subarray A[0]~A[l-1], initial value is A.length;
  static int ksumTonDown(int[] A, int l, int k, int T) {
    if (l >= 0 && k == 0 && T == 0) return 1;
    if (l >= 1) return ksumTonDown(A, l - 1, k, T) + ksumTonDown(A, l - 1, k - 1, T - A[l - 1]);
    return 0;
  }
  // --------------------------------------------------------------------------
  public static void main(String[] args) {
    System.out.println(ksum(new int[] {1, 2, 3, 4}, 2, 5) == 2);
    System.out.println(ksumConcise(new int[] {1, 2, 3, 4}, 2, 5) == 2);
    System.out.println(ksumTonDown(new int[] {1, 2, 3, 4}, 4, 2, 5) == 2);

    System.out.println(ksum(new int[] {1, 2, 3, 4, 5}, 3, 6) == 1);
    System.out.println(ksumConcise(new int[] {1, 2, 3, 4, 5}, 3, 6) == 1);
    System.out.println(ksumTonDown(new int[] {1, 2, 3, 4, 5}, 5, 3, 6) == 1);
    System.out.println(ksumTonDown(new int[] {1, -2, 2, -3, 3, -4, 4, 5}, 5, 3, 6) == 1);
  }
}
