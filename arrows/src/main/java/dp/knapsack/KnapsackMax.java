//  Copyright 2020 The KeepTry Open Source Project
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

import java.util.*;

public class KnapsackMax {
  // initial: dp[0] =0;
  // dp[j] = Integer.MAX_value; 1<= j <= W;
  // for scenario where the best solution's cost == W
  //
  // initial: dp[j]= 0; 0<= j <= W;
  // for scenario where the best solution's cost <= W

  private static void as01Knapsack(int cost, int value, int dp[]) {
    int W = dp.length - 1; // dp length = 0 .. W;
    for (int i = W; i >= cost; i--) {
      dp[i] = Math.max(dp[i], dp[i - cost] + value);
    }
  }

  private static void asCompleteKnapsack(int cost, int value, int dp[]) {
    int W = dp.length - 1; // dp length = 0 .. W;
    for (int i = cost; i <= W; ++i) {
      dp[i] = Math.max(dp[i], dp[i - cost] + value);
    }
  }

  /*
       dp[i][j] = max{ dp[i-1][ j - k*cost] + k*value }
       dp[i][j] = max{ dp[i-1][(j/cost)*cost + j%cost - k*cost] + k*value }
       dp[i][j] = max{ dp[i-1][(j/cost-k)*cost + j%cost] +k*value - (j/cost)*value }  + (j/cost)*value
       dp[i][j] = max{ dp[i-1][(j/cost-k)*cost + j%cost] - (j/cost-k)*value }  + (j/cost)*value
       dp[i][j] = max{ dp[i-1][(cnt-k)*cost + d] - (cnt-k)*value }  + cnt*value
       Where
           0 <= j <= W,
           0 <= k <= min{count, j/cost}
           d = j % cost
           cnt = j/cost
       When k=0
       dp[i-1][(cnt-k)*cost + d] - (cnt-k)*value
       = dp[i-1][j] - (cnt)*value

       The elements in the max{},not dp[i-1][j], is cut into groups, groups number = cost,
       by the value of j % cost. Each group elements can form a queue, once it is queue,
       then O(1) time is possible by sliding window to calculate the dp[i][j].
       The queue elements come from dp[i-1][Jd] and it is not changed by dp[i][Jd].
       So
       1> One dimensional dp[Jd] is possible,
       2> It is reasonable to calculate dp[Jd] with left to right order

       First element of each groups:
          queue 0              queue 1             queue 2              ...   queue cost-1
          dp[i-1][0],          dp[i-1][1],         dp[i-1][2],          ... , dp[i-1][cost-1],
       Calculate the new element with translate equation with k=0, so each queue extend as:
          dp[i-1][0+ cost]- v, dp[i-1][1+ cost]- v, dp[i-1][2+ cost]- v, ... , dp[i-1][cost-1+ cost]- v,
          dp[i-1][0+2cost]-2v, dp[i-1][1+2cost]-2v, dp[i-1][2+2cost]-2v, ... , dp[i-1][cost-1+2cost]-2v,
          dp[i-1][0+3cost]-3v, dp[i-1][1+3cost]-3v, dp[i-1][2+3cost]-3v, ... , dp[i-1][cost-1+3cost]-3v,
          dp[i-1][0+4cost]-4v, dp[i-1][1+4cost]-4v, dp[i-1][2+4cost]-4v, ... , dp[i-1][cost-1+4cost]-4v,
          dp[i-1][0+5cost]-5v, dp[i-1][1+5cost]-5v, dp[i-1][2+5cost]-5v, ... , dp[i-1][cost-1+5cost]-5v,
          dp[i-1][0+6cost]-6v, dp[i-1][1+6cost]-6v, dp[i-1][2+6cost]-6v, ... , dp[i-1][cost-1+6cost]-6v,
          ...
          dp[i-1][W-1]-(W-1)/cost)*v, dp[i-1][W]-(W)/cost)*v
                                      (assume W%cost == 1)

       Window size is k+1,  k=min{count, j/cost}
       To make simple use 1 dimensional array dp[j]

       dp index: 0 .. W;
       O(W) W is the cost/pack‘s volume
       cost: current object type's cost,
       value: current object type's value,
       count: current object type's quantity
  */

  private static void asMultipleKnapsack(int cost, int value, int count, int dp[]) {
    int W = dp.length - 1;
    // Slide window size is count +1. need keep element in advance of window to let imq refer.
    int[] q = new int[W + 1];
    // Keep top is the max value's index of current q
    Deque<Integer> imq;
    for (int d = 0; d < cost; d++) { // group by j%cost[i]
      // cnt = j/cost[i]. cnt value is 0, 1, 2, 3, ..., with j is d, d+cost, d+2*cost, ...
      int i = -1;
      imq = new LinkedList<>(); //
      for (int j = d, cnt = 0; j <= W; j += cost, cnt++) {
        i++;
        int e = dp[j] - cnt * value; // new element
        // =======  [calculate the max in slide window of queue] START =======
        q[i] = e;
        while (!imq.isEmpty() && q[imq.peekLast()] < e) imq.removeLast();
        imq.addLast(i); // keep index not value
        // index of element just out of window
        int idx = i - (count + 1); // window size is count+1
        if (idx >= 0 && imq.peekFirst() == idx) imq.removeFirst();
        int max = q[imq.peekFirst()];
        // =======  [calculate the max in slide window of queue] END =======
        dp[j] = max + cnt * value;
      }
    }
  }

  public static void knapsack(int cost, int value, int count, int dp[]) {
    int W = dp.length - 1; // dp length = 0 .. W;  W is the top limitation of the backpack
    if (count == 0 || cost == 0) return; // it is initial value.
    if (count == 1) { // 01 backpack
      as01Knapsack(cost, value, dp);
    } else if (count * cost >= W) { // complete backpack (n*cost >= W)
      asCompleteKnapsack(cost, value, dp);
    } else { // assume only 3 options: 01, complete and multiple backpack
      asMultipleKnapsack(cost, value, count, dp);
    }
  }
  // ----- no comments version  --------------------------------------------------------------
  // O(W) time
  private static void asMultipleKnapsack2(int cost, int value, int count, int dp[]) {
    int W = dp.length - 1;
    int[] q = new int[W + 1];
    Deque<Integer> imq;
    for (int d = 0; d < cost; d++) {
      int i = -1;
      imq = new LinkedList<>(); //
      for (int j = d, cnt = 0; j <= W; j += cost, cnt++) {
        i++;
        int e = dp[j] - cnt * value;
        q[i] = e;
        while (!imq.isEmpty() && q[imq.peekLast()] < e) imq.removeLast();
        imq.addLast(i);
        int idx = i - (count + 1);
        if (idx >= 0 && imq.peekFirst() == idx) imq.removeFirst();
        int max = q[imq.peekFirst()];
        dp[j] = max + cnt * value;
      }
    }
  }

  // ------------------------- test ----------------------------------------------------------
  public static void main(String[] args) {
    // Test my note book case with with multipleBackpack()
    int[][] items =
        new int[][] {
          new int[] {7, 9, 9},
          new int[] {4, 5, 9},
          new int[] {3, 3, 9},
          new int[] {2, 1, 9},
        };
    int W = 10;
    int[] dp = new int[W + 1]; // initial with 0 means
    for (int[] item : items) {
      int cost = item[0], value = item[1], counts = item[2];
      asMultipleKnapsack(cost, value, counts, dp);
      System.out.println(Arrays.toString(dp));
    }
    // Test my note book case with with pack()
    dp = new int[W + 1]; // initial with 0 means
    for (int[] item : items) {
      int cost = item[0], value = item[1], counts = item[2];
      knapsack(cost, value, counts, dp);
      System.out.println(Arrays.toString(dp));
    }
    /*
    [0, 0, 0, 0, 0, 0, 0, 9,  9,  9, 9]
    [0, 0, 0, 0, 5, 5, 5, 9, 10, 10, 10]
    [0, 0, 0, 3, 5, 5, 6, 9, 10, 10, 12]
    [0, 0, 1, 3, 5, 5, 6, 9, 10, 10, 12]
    */
  }
}
