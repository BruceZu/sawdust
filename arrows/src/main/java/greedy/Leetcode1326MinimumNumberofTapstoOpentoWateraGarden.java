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

package greedy;

import java.util.Arrays;
import java.util.HashMap;

public class Leetcode1326MinimumNumberofTapstoOpentoWateraGarden {
  // delete covered tap by selected tap
  // at last check it can cover all garden
  /*
  Greedy
   1. from the range create intervals:
      the start point at least is 0; ** else for [4,0,0,0,4,0,0,0,4] it will select 2 **

   2. sort(O(NlogN)) ranges by start in ascending order and end in descending order
    -------------(selected)
    ------
    ---
       ----------------( do not select this)
       ------
       ---
              ----------------(selected)
              ------


   3 in the sorted result with the specified order
     the first one should be selected as one of result.
     which is next one?
     right way is to select from the scope where each interval has
     overlap with the previous one. not the scope where each interval
     has the same start point.
     in the specific scope, select the one with rightmost point
     if its right most point >value of previous one.
     it is selected.
   4 at each time after updating the right most value, also
     check if it have covered the target n.
   5 at each time run into a interval whose start time > rightmost
     then return -1

   O(NlogN)
  */
  /*
      1 <= n <= 10^4
      ranges.length == n + 1
      0 <= ranges[i] <= 100
     l:
     - previous selected interval right time.
     - also with it a batch is defined as  those following intervals whose start time
       less than this value. overlapped ones.
     r: the right point can reached by far
  */
  public static int minTapsOriginalGreedy(int n, int[] ranges) {
    if (n <= 0 || ranges == null || ranges.length == 0) return 0;
    // check each element value of ranges to be 0 <= ranges[i] <= 100
    int N = ranges.length;
    int[][] A = new int[ranges.length][2];
    for (int i = 0; i < N; i++) A[i] = new int[] {Math.max(0, i - ranges[i]), i + ranges[i]};
    Arrays.sort(A, (a, b) -> a[0] == b[0] ? b[1] - a[1] : a[0] - b[0]);

    int answer = 1;
    int[] p = A[0]; // previous range
    if (0 < p[0]) return -1;
    int l = p[1], r = p[1];
    if (r >= n) return answer;
    for (int i = 1; i < A.length; i++) {
      int[] cur = A[i];
      if (cur[0] <= l) {
        if (cur[1] > r) r = cur[1];
      } else {
        // - till now can we know the last batch intervals is over.
        //   so need to check whether there is one we can select into result.
        // - for the last batch, need handle them after the loop is over
        if (r != l) answer++;
        if (r >= n) return answer;
        l = r;
        // last batch is end, start a new batch
        if (l < cur[0]) return -1;
        else i--;
      }
    }
    if (r != l) answer++;
    if (r < n) return -1;
    return answer;
  }

  /*DP ==========================================================================
  the status transformation need care the overlap
  O(2^N)
  care corner case "3, [0,0,0,0]"
  help function is to get minimum number of taps available index scope [i, n-1] to cover range [from, to]
  */
  public static int minTapsOriginalDP(int n, int[] ranges) {
    return help(0, n, 0, ranges, new HashMap<String, Integer>());
  }
  //  Time Limit Exceeded
  private static int help(int from, int to, int i, int[] ranges, HashMap<String, Integer> cache) {
    if (i == ranges.length) return -1;
    String key = Arrays.toString(new int[] {from, to}) + i;
    if (cache.containsKey(key)) return cache.get(key);
    int l = i - ranges[i], r = i + ranges[i];
    int v = 0;
    if (from < l || r <= from) {
      v = help(from, to, i + 1, ranges, cache); // can not be used
    } else if (r >= to) v = 1;
    else {
      int t1 = help(r, to, i + 1, ranges, cache); // temporary value 1 for used
      int t2 = help(from, to, i + 1, ranges, cache); // not use
      if (t1 != -1 && t2 != -1) v = Math.min(1 + t1, t2);
      else if (t1 == -1 && t2 == -1) v = -1;
      else if (t1 == -1) v = t2;
      else if (t2 == -1) v = 1 + t1;
    }
    cache.put(key, v);
    return cache.get(key);
  }
  /* --------------------------------------------------------------------------
   DP
   dp[i] is the minimum number of taps to water [0, i].
   Initialize dp[i] with max = n + 2
   dp[0] = [0] need no tap to water nothing.

   Find the leftmost point of garden to water with tap i.
   Find the rightmost point of garden to water with tap i.
   We can water [left, right] with one tap and water [0, left - 1] with dp[left - 1] taps.

  O(NR) time, R is max ranges[i] <= 100; O(N) space
    */
  public int minTaps3(int n, int[] A) {
    int[] dp = new int[n + 1];
    Arrays.fill(dp, n + 2);
    dp[0] = 0;
    for (int i = 0; i <= n; i++) {
      int l = Math.max(0, i - A[i]), r = Math.min(n, i + A[i]); // valid scope of current tap
      for (int j = l + 1; j <= r; j++) dp[j] = Math.min(dp[j], dp[l] + 1);
    }
    return dp[n] < n + 2 ? dp[n] : -1;
  }
}
