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

public class Leetcode714BestTimetoBuyandSellStockwithTransactionFee {
  /*
  Question:
     as many transactions as you like, but you need to pay the transaction fee for each transaction.
     you must sell the stock before you buy again.

  From the Leetcode188BestTimetoBuyandSellStockIV
  Easy to get this variation
  But is too slow  O(N^2) time and O(N) space
  */
  public int maxProfit2(int[] prices, int fee) {
    if (prices == null || prices.length <= 1) return 0;
    int N = prices.length;
    int[] c = new int[N - 1]; // candidate-cost
    Arrays.fill(c, Integer.MAX_VALUE);
    int[] pro = new int[N - 1];
    for (int p : prices) {
      for (int k = 0; k <= N - 2; k++) {
        c[k] = Math.min(c[k], p - (k == 0 ? 0 : pro[k - 1]));
        pro[k] = Math.max(pro[k], p - c[k] - fee);
      }
    }
    return pro[N - 1];
  }
  /*
  As the there is no limit on the times of TX 'as many transactions as you like'
  Then the inner loop should not be there anymore
  the state machine now for any day will be in only one of 2 status
  - hold : comes from sold or rest
  - sold : comes from hold or rest
  O(N) time and O(1) space
   */
  public int maxProfit(int[] prices, int fee) {
    if (prices == null || prices.length <= 1) return 0;
    int N = prices.length;
    int h = Integer.MIN_VALUE; // do not start from this status
    int s = 0;
    for (int p : prices) {
      h = Math.max(h, s - p);
      s = Math.max(s, h + p - fee);
    }
    return s;
  }
}
