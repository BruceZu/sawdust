//  Copyright 2016 The Sawdust Open Source Project
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

/**
 * <pre>
 * try make it simple again ---------------------------------------
 *     all possible operation on day i and result status:
 *          endByBuy                        ->  1 share in hand
 *          endWith0Share                   ->  0 share in hand
 *
 *     so max profit of 0 share on be last day:
 *          max_0[prices.length-1]
 *
 *     DP translations of max profit
 *      max_1[i] = Math.max(max_1[i - 1], max_0[i - 2] - prices[i]);
 *      max_0[i] = Math.max(max_0[i - 1], max_1[i - 1] + prices[i]);
 *
 *     i-3        i-2        i-1        i
 *
 *     max_0 -->  max_0 -->  max_0  --> max_0
 *             /        \ /
 *            /          /\
 *           /          /   \
 *         sell      sell     buy
 *        on i-2    on i-1    on i
 *        /          /           \
 *       /          /              \
 *      /          /                 \
 *    max_1     max_1        max_1 -->  max_1
 *
 *     max_1[i] = max_1[i-1] + buy on  day i
 *                             buy on  day i = max_0[i-2] -prices[i]
 *    because:          sell on day i-1 will require cooldown on day i.
 *     so need exclude  sell on day i-1
 *     max_0[i-1] = max(sell on day i-1, max_0[i-2])
 *     max_0[i-2] = max(sell on day i-2, max_0[i-3])
 *
 *
 * try make it simple again ---------------------------------------
 */
public class Leetcode309BestTimetoBuyandSellStockwithCooldown2 {

  public int maxProfit(int[] prices) {
    if (prices.length <= 1) return 0;

    int[] max_1 = new int[prices.length]; // 1 means one stock unit
    int[] max_0 = new int[prices.length];

    max_0[0] = 0;
    max_0[1] = Math.max(prices[1] - prices[0], 0);
    max_1[0] = -prices[0];
    max_1[1] = Math.max(-prices[1], -prices[0]);
    for (int i = 2; i < prices.length; i++) {
      max_1[i] = Math.max(max_1[i - 1], max_0[i - 2] - prices[i]);
      max_0[i] = Math.max(max_0[i - 1], max_1[i - 1] + prices[i]);
    }
    return max_0[prices.length - 1];
  }

  // space O(1)
  public int maxProfit2(int[] prices) {
    if (prices.length <= 1) {
      return 0;
    }

    int no2 = 0;
    int have2 = 0 - prices[0];

    int no1 = Math.max(have2 + prices[1], no2);
    int have1 = Math.max(0 - prices[1], have2);

    for (int i = 2; i < prices.length; i++) {
      int newHave1 = Math.max(have1, no2 - prices[i]);
      int newNo1 = Math.max(no1, have1 + prices[i]);
      no2 = no1;
      no1 = newNo1;
      have1 = newHave1;
    }
    return no1;
  }
}
