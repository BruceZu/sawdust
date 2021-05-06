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

public class Leetcode309BestTimetoBuyandSellStockwithCooldown {

  /*
    At any moment, we can only be in one state.
    1. held, have stock in hand:
            can not buy,
            can not cool down,
            can only rest or sell
    2. sold, having stock at hand-
            no stock at hand
            can not buy
            can not sell
            can only cool down
    3. reset, after be forced cool down
           can buy
           can rest.
           can not sell,

    Action:
     1. buy  : `reset` -(buy)> -> `held`
     2. sell : `held`  -(sell) -> `sold`
     3. rest :  `sold` -(rest/cool down) -> `reset`;
                `held` -(rest) -> `held`
                `reset`-(rest) -> `reset`

    https://en.wikipedia.org/wiki/Finite-state_machine
    Assemble the above states and actions into a state machine,
    - `You may not engage in multiple transactions simultaneously (i.e., you must sell the stock before you buy again).'
    -  `After you sell your stock, you cannot buy stock on the next day (i.e., cooldown one day)`
    hold ---(sell)---> sold
    hold ---(rest)---> hold ||  reset---(buy)--> hold
    reset---(rest)--> reset ||  sold ---( forced cool down/rest)--> reset
    at lost check the bigger one of profit of `sold` and `reset`
    Need not care `hold` which bought in the stock at the last price point, which only leads
    to the reduction of profits.
    Use Integer.MIN_VALUE as the initial profit value of `sold` and `hold`
    which are intended to render the paths that start from these two states impossible.
  O(N) time O(1) space
  */
  public int maxProfit2(int[] p) {
    // s, h, r are the max profit of previous status `sold`, `hold` and `reset`
    // they could got from their previous action + status transform path
    if (p.length == 0) return 0;
    int s = Integer.MIN_VALUE;
    int r = 0; // only `reset` could buy
    int h = Integer.MIN_VALUE;
    ;
    for (int i = 0; i < p.length; i++) {
      int curS = h + p[i];
      int curH = Math.max(r - p[i], h);
      int curR = Math.max(r, s);
      s = curS;
      h = curH;
      r = curR;
    }
    return Math.max(s, r); //  at the last price point become `hold` which means
    // it bought in the stock which only leads to the reduction of profits.
  }

  public int maxProfit(int[] prices) {
    int s = Integer.MIN_VALUE, h = Integer.MIN_VALUE, r = 0;
    for (int p : prices) {
      int tmp = s;
      s = h + p;
      h = Math.max(h, r - p);
      r = Math.max(r, tmp);
      // Notice the order: s, then h, then r.
      // at last here s has been updated and not the previous s, that is why need a `preS`
    }
    return Math.max(s, r);
  }
}
