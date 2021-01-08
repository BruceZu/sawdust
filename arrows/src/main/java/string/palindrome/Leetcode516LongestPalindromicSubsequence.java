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

package string.palindrome;

public class Leetcode516LongestPalindromicSubsequence {
  public int longestPalindromeSubseq(String s) {
    if (s == null || s.length() == 0) return 0;
    int N = s.length();
    int[][] dp = new int[N][N];
    // initial, others are default 0
    for (int i = 0; i < N; i++) dp[i][i] = 1;

    for (int i = 0; i < N; i++) {
      // need calculate all dp[0<=j<i][i]
      for (int j = i - 1; j >= 0; j--) { // backward to use known to get unknown
        if (s.charAt(j) == s.charAt(i)) {
          // palindrome symmetry
          dp[j][i] = 2 + dp[j + 1][i - 1]; // (j==i-1)?2:2+dp[j+1][i-1];
        } else {
          dp[j][i] = Math.max(dp[j + 1][i], dp[j][i - 1]);
        }
      }
    }
    return dp[0][N - 1];
  }

  // top down
  public int longestPalindromeSubseq2(String s) {
    int n = s.length();
    int[][] a = new int[n][n];
    for (int i = 0; i < n; i++) a[i][i] = 1;
    return helper(a, 0, n - 1, s);
  }

  private int helper(int[][] dp, int l, int r, String s) {
    if (l >= r || dp[l][r] != 0) return dp[l][r]; // 0 or cached

    if (s.charAt(l) == s.charAt(r)) dp[l][r] = helper(dp, l + 1, r - 1, s) + 2;
    else dp[l][r] = Math.max(helper(dp, l, r - 1, s), helper(dp, l + 1, r, s));

    return dp[l][r];
  }
}
