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

/*
121. Best Time to Buy and Sell Stock
You are given an array prices where p is
the price of a given stock on the ith day.

You want to maximize your profit by choosing
a single day to buy one stock and choosing a
different day in the future to sell that stock.

Return the maximum profit you can achieve from
this transaction. If you cannot achieve any profit,
return 0.
*/
/*
Idea:
which is the bottom point to buy in?
once find the bottom point it is easy to get max profit by comparing diff/profit
 */
public class Leetcode121BestTimetoBuyandSellStock {
  public int maxProfit2(int prices[]) {
    int min = Integer.MAX_VALUE;
    int r = 0;
    for (int p : prices) {
      if (p < min) min = p;
      else if (p - min > r) r = p - min;
    }
    return r;
  }
}
