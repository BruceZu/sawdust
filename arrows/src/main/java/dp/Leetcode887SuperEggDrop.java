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

import jdk.nashorn.internal.runtime.ECMAException;

import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Leetcode887SuperEggDrop {
  // ---------------- least_drops(floors, eggs)  -----------------------------------
  Map<Integer, Integer> memo = new HashMap();
  // O(K*N*logN) time
  public int superEggDrop4(int K, int N) {
    if (!memo.containsKey(N * 100 + K)) {
      int r;
      if (N == 0) r = 0;
      else if (K == 1) r = N;
      else {
        int l = 1, h = N;
        while (l + 1 < h) {
          int m = (l + h) / 2;
          int t1 = superEggDrop4(K - 1, m - 1);
          int t2 = superEggDrop4(K, N - m);

          if (t1 < t2) l = m; // l is known as it comes from middle
          else if (t1 > t2) h = m; // h is known as it comes from middle
          else l = h = m; // m is just the cross point
        }
        // result is l or h, so need check both of them
        r =
            1
                + Math.min(
                    Math.max(superEggDrop4(K - 1, l - 1), superEggDrop4(K, N - l)),
                    Math.max(superEggDrop4(K - 1, h - 1), superEggDrop4(K, N - h)));
      }
      memo.put(N * 100 + K, r);
    }
    return memo.get(N * 100 + K);
  }

  // O(KN)
  public static int superEggDrop3(int K, int N) {
    int[] dp = new int[N + 1];
    for (int i = 0; i <= N; ++i) dp[i] = i; // 1 egg column
    for (int k = 2; k <= K; ++k) { // start from 2 eggs column
      int[] dp2 = new int[N + 1]; // current k eggs column
      int o = 1; // nearest floor to intersect point of last loop. initial is 1 for n==1;
      for (int n = 1; n <= N; ++n) { // start from 1 floor row
        // n - (o + 1) >= 0 means o < n
        while (n - (o + 1) >= 0
            && Math.max(dp[o - 1], dp2[n - o]) > Math.max(dp[o], dp2[n - (o + 1)])) o++;
        // The final answer happens at this x.
        dp2[n] = 1 + Math.max(dp[o - 1], dp2[n - o]);
      }
      dp = dp2;
    }
    return dp[N];
  }

  // ---------------- max_floors(eggs,drops)  ------------------------------------
  // O( K*T)
  public static int superEggDrop2(int K, int F) {
    // One egg with T drops at most can judge T floors
    // K egg with 1 drop at most can jude 1 floor
    //
    // Let Fmax(K, T) as the at most judged floors number for K eggs and T drops
    // Fmax(K, T) = Fmax(K-1, T-1) + 1 + Fmax(K, T-1)
    //
    // special case:
    // Fmax(2, T) = Fmax(1, T-1) + 1 + Fmax(2, T-1)
    // Fmax(2, T) = T - 1 + 1 + Fmax(2, T-1)
    // Fmax(2, T) = T + Fmax(2, T-1)
    /*
     '  1 <= K <= 100
        1 <= N <= 10000 '
    */

    if (K == 1) return F;
    int T = 1;
    int[] Fmax = new int[K + 1];
    Arrays.fill(Fmax, 1);
    if (Fmax[K] > F) return T;
    // O( K*T)
    while (Fmax[K] < F) {
      // Back forward to use old/above value
      for (int i = K; i > 1; i--) {
        Fmax[i] = 1 + Fmax[i] + Fmax[i - 1];
      }
      // Note: after the inner loop to update F[1]
      Fmax[1] = ++T;
    }
    return T;
  }
  // ---------------
  // Let k = 2,F = 100
  // O(K*logN) N is given max floor
  public static int superEggDrop1(int K, int Fmax) {
    /*  1 <= K <= 100
    1 <= N <= 10000  */
    // binary search
    int l = 0, r = Fmax; // drops number bounders
    while (l <= r) { // Note when l==r, still calculate
      int mi = (l + r) >>> 1;
      int mv = fmaxOf(mi, K, Fmax);
      if (mv == Fmax) return mi;
      else if (mv < Fmax) l = mi + 1; // But the value may just be within mi and mi+1
      else r = mi - 1; // But the value may just be within mi-1 and mi
    }
    return l;
  }

  // N is used to avoid overflow
  private static int fmaxOf(int drops, int eggs, int N) {
    if (eggs == 0) return 0;
    // combination and fmax
    // C(drops,0)=1, it is only used to calculate C(drops,1)
    // fmax(drops,0)=0
    int c = 1, fmax = 0; // C(drops,0)=1, it is only used to calculate C(drops,1)
    for (int i = 1; i <= eggs; i++) {
      c = c * (drops - (i - 1)) / i; // g(drops, i-1) = C(drops,i) = C(drops,i-1)*(drops-(i-1))/i
      fmax += c; // fmax(drops,i) = fmax(drops,i-1) + g(drops, i-1)
      if (fmax > N) return fmax; // This is used only for overflow.
    }
    return fmax;
  }

  public static void main(String[] args) {
    System.out.println(superEggDrop3(2, 6) == 3);
    System.out.println(superEggDrop1(6, 200) == 8);
  }
}
