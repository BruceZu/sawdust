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

public class Leetcode1871JumpGameVII {
  /*
  i is start index 0 or index whose value has been marked by j with '2'
  j is in jump scope made by minJump and maxJump from i and never processed
  the `never processed` is guarantee by
  `j = Math.max(j, i + minJump)`
  To avoid duplicated operation caused by overlap of jump scopes made by previous jump scopes.

  the previous value of j is decided by the previous jum scope's right boundary index
  `j <= Math.min((int) s.length - 1, i + maxJump)`
  So i and j move O(N) times in total
  space O(N)
  */
  public boolean canReach(String A, int minJump, int maxJump) {
    char[] s = A.toCharArray();
    int i = 0, j = 0;
    for (i = 0; i < s.length; ++i)
      if (i == 0 || s[i] == '2')
        for (j = Math.max(j, i + minJump); j <= Math.min((int) s.length - 1, i + maxJump); j++)
          if (s[j] == '0') s[j] = '2';
    return s[s.length - 1] == '2';
  }

  // O(N) time O(N) space
  public boolean canReach2(String A, int min, int max) {
    int N = A.length();
    boolean[] dp = new boolean[N];
    dp[0] = true;
    // cum: cumulated number of valid jump-from index in in [i - maxJump, i - minJump].
    int cum = 0;
    for (int i = min; i < N; ++i) {
      if (dp[i - min]) cum++;
      if (i - max - 1 >= 0 && dp[i - max - 1]) cum--;
      dp[i] = A.charAt(i) == '0' && cum >= 1;
    }
    return dp[N - 1];
  }
  /*
   avoid the repeated operation caused by overlap of jump scopes by
   pre sum idea.
  */
}
