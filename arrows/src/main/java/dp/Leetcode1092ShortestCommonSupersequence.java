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

package dp;

import java.util.Arrays;

public class Leetcode1092ShortestCommonSupersequence {
  /*
  return the shortest string that has both str1 and str2 as subsequences.
  If multiple answers exist, you may return any of them.
   */

  // O(M*N)
  static String shortestCommonSupersequence(String str1, String str2) {
    /*
       1 <= str1.length, str2.length <= 1000
       str1 and str2 consist of lowercase English letters.
    */
    int M = str1.length(), N = str2.length();
    String[][] dp = new String[M + 1][N + 1];
    Arrays.fill(dp[0], "");
    for (int i = 0; i <= M; i++) { // note it is <=
      dp[i][0] = "";
    }
    for (int i = 1; i <= M; i++) { // note it is stat from 1 and  <=M
      for (int j = 1; j <= N; j++) { // note it is stat from 1 and  <=M
        if (str1.charAt(i - 1) == str2.charAt(j - 1))
          dp[i][j] = dp[i - 1][j - 1] + str1.charAt(i - 1);
        else {
          dp[i][j] = dp[i - 1][j].length() > dp[i][j - 1].length() ? dp[i - 1][j] : dp[i][j - 1];
        }
      }
    }
    String lcs = dp[M][N];
    int i = 0, j = 0;
    StringBuilder r = new StringBuilder();
    for (char k : lcs.toCharArray()) { // need not k < lcs.length() && (i < M || j < N)
      while (i < M && str1.charAt(i) != k) {
        r.append(str1.charAt(i));
        i++;
      }
      while (j < N && str2.charAt(j) != k) {
        r.append(str2.charAt(j));
        j++;
      }
      r.append(k); // Note here
      i++;
      j++;
    }
    // Note here 2 string left chars
    if (i < M) r.append(str1.substring(i));
    if (j < N) r.append(str2.substring(j));
    return r.toString();
  }
}
