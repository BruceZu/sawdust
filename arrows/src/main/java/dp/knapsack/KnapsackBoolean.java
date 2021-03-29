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

package dp.knapsack;

import java.util.Arrays;

/**
 * Apply basic idea in BackPackMax to calculate boolean logic E.g. If it is possible to use existing
 * coin ( type, counts) to exactly make number of price
 */
public class KnapsackBoolean {
  // boolean dp[]
  // initial: dp[0] = true;
  // dp[j] = false; 1<= j <= W;

  private static void as01knapsack(int coinValue, boolean dp[]) {
    int W = dp.length - 1; // dp length = 0 .. W;
    for (int price = W; price >= coinValue; price--) {
      dp[price] = dp[price] || dp[price - coinValue];
    }
  }
  // unbounded knapsack problem (UKP)
  private static void asCompleteKnapsack(int coinValue, boolean dp[]) {
    int W = dp.length - 1; // dp length = 0 .. W;
    for (int price = coinValue; price <= W; ++price) {
      dp[price] = dp[price] || dp[price - coinValue];
    }
  }

  /*
       dp[i][j] = ||{ dp[i-1][ j - k*v]}
       where
           0 <= j <= W,
           0 <= k <= min{count, j/v}
           d = j % v
           cnt = j/v
           v = coin Value
       when k=0
         dp[i-1][j - k*v]
       = dp[i-1][j]

       Cut dp[i-1][j] into groups, groups number = cost, by the value of j % cost.
       Each group elements dp[i-1][Jd] can form a queue, thus O(1) time is possible
       by sliding window to calculate the dp[i][j].
       The queue elements come from dp[i-1][Jd] and it is not changed by dp[i][Jd]
       So
       1> One dimensional dp[Jd] is possible,
       2> It is reasonable to calculate dp[Jd] with left to right order

          queue 0            queue 1            queue 2           ...   queue v-1
          dp[i-1][0],        dp[i-1][1],        dp[i-1][2],       ... , dp[i-1][v-1],
       Calculate the new element with translate equation with k=1, so each queue followed by
          dp[i-1][0 +   v] , dp[i-1][1 +   v] , dp[i-1][2 +   v], ... , dp[i-1][v-1 +   v],
          dp[i-1][0 + 2*v] , dp[i-1][1 + 2*v] , dp[i-1][2 + 2*v], ... , dp[i-1][v-1 + 2*v],
          dp[i-1][0 + 3*v] , dp[i-1][1 + 3*v] , dp[i-1][2 + 3*v], ... , dp[i-1][v-1 + 3*v],
          dp[i-1][0 + 4*v] , dp[i-1][1 + 4*v] , dp[i-1][2 + 4*v], ... , dp[i-1][v-1 + 4*v],
          dp[i-1][0 + 5*v] , dp[i-1][1 + 5*v] , dp[i-1][2 + 5*v], ... , dp[i-1][v-1 + 5*v],
          dp[i-1][0 + 6*v] , dp[i-1][1 + 6*v] , dp[i-1][2 + 6*v], ... , dp[i-1][v-1 + 6*v],
          ...
          dp[i-1][W-1],      dp[i-1][W]
                             (assume W%cost == 1)


       window size is k+1,  k=min{count, j/v}
       to make simple use 1 dimensional array dp[j]

       dp index: 0 .. W;
       O(W) W is the cost/pack‘s volume
       value: current object type's value,
       count: current object type's quantity
  */
  // bounded knapsack problem (BKP)
  private static void asMultipleKnapsack(int value, int count, boolean dp[]) {
    int W = dp.length - 1;
    // Slide window size is count +1. need keep element in advance of window to let imq refer.
    boolean[] q = new boolean[W + 1];
    int sum = 0;
    for (int d = 0; d < value; d++) { // group by j%cost[i]
      // cnt = j/value. cnt value is 0, 1, 2, 3, ..., with j is d, d+value, d+2*value, ...
      int i = -1;
      // use 1 and 0 mapping to true and false then use sum of
      // slide window each element to represent the result of || is easy.
      int possible = 0;
      for (int j = d; j <= W; j += value) {
        i++;
        boolean e = dp[j]; // new element
        // =======  [calculate the "||" of each element in slide window of queue] START ====
        q[i] = e;
        possible += e ? 1 : 0;
        // index of element just out of window
        int idx = i - (count + 1); // window size is count+1
        if (idx >= 0) possible -= (q[idx] ? 1 : 0);
        // =======  [calculate the "||" of each element in slide window of queue] END =======
        dp[j] = possible > 0;
      }
    }
  }

  public static void knapsack(int value, int count, boolean dp[]) {
    int W = dp.length - 1; // dp length = 0 .. W;  W is the top limitation of the backpack
    if (count == 0) return; // it is initial value.
    if (count == 1) { // 01 backpack
      as01knapsack(value, dp);
    } else if (count * value >= W) { // complete backpack (n*value >= W)
      asCompleteKnapsack(value, dp);
    } else { // assume only 3 options: 01, complete and multiple backpack
      asMultipleKnapsack(value, count, dp);
    }
  }

  // POJ 1742 Coins.  Multiple backpack  O(N*P) time.
  static int multipleKnapsackInt(int[] coinsValue, int[] coinCounts, int P) {
    int N = coinsValue.length;

    int dp[] = new int[P + 1];
    Arrays.fill(dp, -1);
    // Check if each price possible  can be compose with current coins exactly. So initial
    // P>=1 with -1 not 0.

    dp[0] = 0; // one dimensional array
    // O(N*P) time. N: coins type number, P: target price exactly can be get without change is
    // Note: how to control the quantity limitation of each coin type.
    // Thus apply complete backpack algorithm to multiple backpack.
    for (int i = 0; i < N; i++) { // row coin value and quantity
      for (int p = 0; p <= P; p++) { // column  price
        if (dp[p] >= 0) dp[p] = coinCounts[i];
        if (dp[p] < 0 && p - coinsValue[i] >= 0 && dp[p - coinsValue[i]] >= 1) {
          dp[p] = dp[p - coinsValue[i]] - 1;
        }
      }
    }
    int sum = 0; // Number of price that can be pay by current coins exactly without change back
    for (int i = 1; i <= P; i++) { // P=0 is not be took account in
      if (dp[i] >= 0) // It means the P is possible be compose with current coins exactly
      sum++;
    }
    return sum;
  }
  // ----- no comments version  --------------------------------------------------------------
  private static void asMultipleKnapsack2(int value, int count, boolean dp[]) {
    int W = dp.length - 1;
    boolean[] q = new boolean[W + 1];
    int sum = 0;
    for (int d = 0; d < value; d++) {
      int i = -1;
      int possible = 0;
      for (int j = d; j <= W; j += value) {
        i++;
        boolean e = dp[j];
        q[i] = e;
        possible += e ? 1 : 0;
        int idx = i - (count + 1);
        if (idx >= 0) possible -= (q[idx] ? 1 : 0);
        dp[j] = possible > 0;
      }
    }
  }
  // POJ 1742 Coins.  Multiple backpack  O(N*P) time.
  static int multipleKnapsack2(int[] coinsValue, int[] coinCounts, int P) {
    int N = coinsValue.length;
    int dp[] = new int[P + 1];
    Arrays.fill(dp, -1);
    dp[0] = 0;
    for (int i = 0; i < N; i++) {
      for (int p = 0; p <= P; p++) {
        if (dp[p] >= 0) dp[p] = coinCounts[i];
        if (dp[p] < 0 && p - coinsValue[i] >= 0 && dp[p - coinsValue[i]] >= 1) {
          dp[p] = dp[p - coinsValue[i]] - 1;
        }
      }
    }
    int sum = 0;
    for (int i = 1; i <= P; i++) {
      if (dp[i] >= 0) sum++;
    }
    return sum;
  }
  // ------------------------- test ----------------------------------------------------------
  public static void main(String[] args) {
    // First test case of POJ 1742 Coins with asMultiplePack()
    // the value[i] = cost[i]
    int items[][] =
        new int[][] {
          new int[] {1, 1, 2},
          new int[] {2, 2, 1},
        };
    int W = 4;
    boolean[] dp = new boolean[W + 1];
    // initial with false for index j [1, W]. Because it is required to just make number j, no
    // more no less.
    dp[0] = true;
    for (int[] item : items) {
      int cost = item[0], value = item[1], counts = item[2];
      asMultipleKnapsack(value, counts, dp);
    }
    int sum = 0;
    for (int i = 1; i <= W; i++) {
      if (dp[i]) sum++;
    }
    System.out.println(sum == 4);
    // First  test case of  POJ 1742 Coins with pack()
    dp = new boolean[W + 1];
    dp[0] = true;
    for (int[] item : items) {
      int value = item[1], counts = item[2];
      knapsack(value, counts, dp);
    }
    sum = 0;
    for (int i = 1; i <= W; i++) {
      if (dp[i]) sum++;
    }
    System.out.println(sum == 4);
    // Second test case of  POJ 1742 Coins with asMultiplePack()
    items =
        new int[][] {
          new int[] {1, 1, 2},
          new int[] {2, 2, 1},
          new int[] {4, 4, 1},
        };
    W = 10;
    dp = new boolean[W + 1];
    dp[0] = true;
    for (int[] item : items) {
      int value = item[1], counts = item[2];
      asMultipleKnapsack(value, counts, dp);
    }
    sum = 0;
    for (int i = 1; i <= W; i++) {
      if (dp[i]) sum++;
    }
    System.out.println(sum == 8);
    Arrays.fill(dp, false);
    // Second test case of POJ 1742 Coins with pack()
    dp = new boolean[W + 1];
    dp[0] = true;
    for (int[] item : items) {
      int value = item[1], counts = item[2];
      knapsack(value, counts, dp);
    }
    sum = 0;
    for (int i = 1; i <= W; i++) {
      if (dp[i]) sum++;
    }
    System.out.println(sum == 8);

    // 2  test case of POJ 1742 Coins with multipleBackpack()
    System.out.println(multipleKnapsackInt(new int[] {1, 2}, new int[] {2, 1}, 5) == 4);
    System.out.println(multipleKnapsackInt(new int[] {1, 2, 4}, new int[] {2, 1, 1}, 10) == 8);
  }
}
