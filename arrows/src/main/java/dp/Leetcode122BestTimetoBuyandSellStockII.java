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
 You are given an array prices where prices[i]
 is the price of a given stock on the ith day.

Find the maximum profit you can achieve.
You may complete as many transactions as you
like (i.e., buy one and sell one share of the stock multiple times).

you must sell the stock before you buy again
 */
public class Leetcode122BestTimetoBuyandSellStockII {
  public int maxProfit(int[] p) {
    int r = 0;
    for (int i = 1; i < p.length; i++) {
      if (p[i] > p[i - 1]) r += p[i] - p[i - 1];
    }
    return r;
  }
}
