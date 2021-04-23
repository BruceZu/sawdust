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

public class Leetcode1105FillingBookcaseShelves {
  public int minHeightShelves(int[][] books, int shelf_width) {
    /*
    ‘the order of the books we place is the same order as the given sequence of books. ’
    O(N*M) time and O(N) space. N is books number and m is the shelf width
    Idea: DP bottom up
     for a current ith book it has 2 choices:
     -  be added to the side of the i-1th book at the same book shelf
        if the current shelf width is enough
     -  else be put into the next shelf as the first one
    */

    /*
      1 <= books.length <= 1000
      1 <= books[i][0] <= shelf_width <= 1000
      1 <= books[i][1] <= 1000
      TODO: corner cases validation
    */

    int[] dp = new int[books.length];
    // dp[i]:  minimal high the previous i+1 books can get
    // i is 0-based
    dp[0] = books[0][1];
    for (int i = 1; i < books.length; i++) {
      int w = books[i][0], h = books[i][1]; // accumulated width and max height of books [j,i]
      dp[i] = dp[i - 1] + h; // book i itself
      for (int j = i - 1; j >= 0 && books[j][0] + w <= shelf_width; j--) {
        w += books[j][0];
        h = Math.max(h, books[j][1]);
        dp[i] = Math.min(dp[i], (j == 0 ? 0 : dp[j - 1]) + h); // note: the boundary
      }
    }
    return dp[books.length - 1];
  }
}
