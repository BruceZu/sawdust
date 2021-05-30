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

public class Leetcode1872StoneGameVIII {
  /*
    n == stones.length
    2 <= n <= 105
    -104 <= stones[i] <= 104

     "game, with Alice starting first."
     "The score difference between Alice and Bob is
     (Alice's score - Bob's score).
     Alice's goal is to maximize the score difference, and
     Bob's goal is the minimize the score difference."
  Means:
     current player will try to make current score difference
     as bigger as possible in current status


     "Choose an integer x > 1, and remove the leftmost x stones from the row.
     Add the sum of the removed stones' values to the player's score.
     Place a new stone, whose value is equal to that sum, on the left side of the row."
   Means:
      for current scope [i, N-1], A[i] can be taken as the presum of index range 0~i.
      current play can only select j which is[ i+1,N-1] as the end index of index range to
      get the score which actually is presum[j].
      let dp[i] is the max score current player can make in current
      index range [i, N-1]
       dp[i]=max{presum[j]-dp[j]}; j is[ i+1,N-1]
       dp[0] is answer
       dp[N-1]: current player face 1 stone left: presum(N-1)
               rule does no apply here, game have to stop.  score is 0;
       dp[N-2]: current player face 2 stones left, have to take all of them
               so it is presum(N-1).

    // presum A[] is ready;
    int[] dp = new int[N]; // dp[N-1]
    Arrays.fill(dp, -100000);
    dp[N - 1] = 0;
    dp[N - 2] = A[N - 1];
    // The game stops when only one stone is left in the row.
    for (int i = N - 3; i >= 0; i--) {
      for (int j = i + 1; j <= N - 1; j++) {
        dp[i] = Math.max(dp[i], A[j] - dp[j]);
      }
    }

    O(N^2) time and O(N)space
    Time Limit Exceeded
    there are overlap parts between dp[i] and dp[i+1]
    the overlap parts is just output dp[i-1]
    so 2 loop is reduced to one loop


    // presum A[] is ready;
    int[] dp = new int[N]; // dp[N-1]
    Arrays.fill(dp, -100000);
    dp[N - 1] = 0;
    dp[N - 2] = A[N - 1];
    // The game stops when only one stone is left in the row.
    for (int i = N - 3; i >= 0; i--) {
      dp[i] = Math.max(A[i + 1] - dp[i + 1], dp[i + 1]);
    }

     O(N) time, O(N)  space

     dp[N - 1] is not used anymore. because
     - dp[i] only depends on dp[i+1]
     - at least 2 element. loop from N-3;
     so the dp[] can be reduced to a variable with initial value of dp[N-2]
     O(N) time and O(1) space

      if n is limited to 10 ^ 5 or higher,
      Solution time should be better than quadratic
   */

  public int stoneGameVIII(int[] A) {
    if (A == null || A.length <= 1) return 0;
    int N = A.length;

    for (int i = 1; i < N; i++) A[i] = A[i - 1] + A[i];
    // presum A[] is ready;
    int dp = A[N - 1]; // dp[N-2]
    // The game stops when only one stone is left in the row.
    for (int i = N - 3; i >= 0; i--) dp = Math.max(A[i + 1] - dp, dp);
    return dp; // dp[0]
  }
}
