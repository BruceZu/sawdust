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

public class Leetcode1024VideoStitching {

  //  Greedy O(NlogN)
  public static int videoStitching1(int[][] A, int T) {
    /*
      ' 1 <= clips.length <= 100
        0 <= clips[i][0] <= clips[i][1] <= 100
        0 <= T <= 100'
       So T can be 0
      'int[][] clips' means clips[i][0] and clips[i][1] is integer
       'int T' means [0, T]
    */
    int N = A.length; // N>=1
    Arrays.sort(A, (a, b) -> a[0] != b[0] ? a[0] - b[0] : a[1] - b[1]);
    if (T == 0) return 0; // Understand it as minimum number of clips is 0 for T=0, or [0,0]
    if (0 < A[0][0]) return -1;
    // a: result; p: previous right point, r: right most point reached by far
    int a = 0, p = 0, r = -1, i = 0;
    while (true) {
      while (i < N && A[i][0] <= p) {
        r = Math.max(r, A[i][1]);
        i++;
      }
      if (i == N) return r >= T ? ++a : -1;
      if (p == r) return -1;
      if (r >= T) return ++a;
      // next
      p = r;
      a++;
    }
  }

  // Greedy O(max{N,T}) time and space
  public static int videoStitching3(int[][] clips, int T) {
    int[] v = new int[101]; // valid clips
    // O(N).  default value is 0
    // all intervals with same start time are represented by the longest one
    for (int[] i : clips) v[i[0]] = Math.max(v[i[0]], i[1]);
    if (T == 0) return 0;
    // a: result; p: previous right point, r: right most point reached by far
    int i = 0, a = 0, p = 0, r = -1;
    // O(T)
    while (true) {
      while (i <= T && i <= p) {
        r = Math.max(r, v[i]);
        i++;
      }
      // note for input is [[0, 0]], 0. now the rm==pr==0.
      if (i == T + 1) return r >= T ? ++a : -1;
      if (r == p) return -1;
      if (r >= T) return ++a;
      // next batch who has overlap with previous selected one.
      p = r;
      a++;
    }
  }

  // DP O(NR) time
  // refer 1326
  public static int videoStitching5(int[][] clips, int T) {
    if (T == 0) return 0;
    int[] v = new int[101];
    for (int[] i : clips) v[i[0]] = Math.max(v[i[0]], i[1]);

    // dp[i] value is the minimum number of clips to cover [0,i]
    // dp[T] should <= T.
    int dp[] = new int[101]; // some valid clip can be have a right bounder > T
    Arrays.fill(dp, T + 1);
    dp[0] = 0; // Understand it as minimum number of clips is 0 for T=0, or [0,0]
    for (int i = 0; i <= T; i++) {
      for (int j = i + 1; j <= v[i]; j++) {
        dp[j] = Math.min(dp[j], dp[i] + 1);
      }
    }
    return dp[T] == T + 1 ? -1 : dp[T];
  }
}
