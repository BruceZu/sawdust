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

package game;

import java.util.HashMap;
import java.util.Map;

public class Leetcode1140StoneGameII {
  /*
  1 <= piles.length <= 100
  1 <= piles[i] <= 104

   Alice starting first.  Initially, M = 1
   1 <= X <= 2M.  Then, we set
   M = max(M, X)
   both play optimally. how??

   return the maximum number of stones
   Alice can get

  Idea:
    how to make both play optimally?
    not only try all possible option   1 <= X <= 2M
    also select the max numbers got from these options
    the max number = current number + number got in next steps which is left total - got by peer
  */
  public int stoneGameII(int[] A) {
    int[] fixSum = new int[A.length];
    fixSum[A.length - 1] = A[A.length - 1];
    for (int i = A.length - 2; i >= 0; i--) {
      fixSum[i] = A[i] + fixSum[i + 1];
    }
    Map<String, Integer> cache = new HashMap();
    return getMax(A, 0, 1, cache, fixSum);
  }

  private int getMax(int[] A, int f, int M, Map<String, Integer> c, int[] fs) {
    if (f >= A.length) return 0;
    if (f + 2 * M >= A.length) return fs[f];
    String k = f + "" + M;
    if (c.containsKey(k)) return c.get(k);
    int max = 0, cur = 0;
    for (int i = f; i < f + 2 * M; i++) {
      cur += A[i];
      max = Math.max(max, cur + fs[i + 1] - getMax(A, i + 1, Math.max(M, i - f + 1), c, fs));
    }

    c.put(k, max);
    return max;
  }
}
