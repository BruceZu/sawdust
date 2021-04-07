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
import java.util.HashMap;
import java.util.Map;

public class Leetcode1815MaximumNumberofGroupsGettingFreshDonuts {
  /*
  Idea:
  Check the 4 hints

   1 <= batchSize <= 9
   1 <= groups.length <= 30
   1 <= groups[i] <= 109

  */
  public static int maxHappyGroups(int batchSize, int[] groups) {
    int N = groups.length;
    int[] cnt = new int[batchSize];
    int r = 0;

    for (int i = 0; i < N; i++) {
      int remainder = groups[i] % batchSize;
      cnt[remainder]++;
    }
    // take out the groups whose remainder is 0
    r += cnt[0];
    // take out the 2 remainder's min groups if their sum is batchSize.
    // it still works but slow without this step
    // Note: < batchSize / 2 to avoid when i is batchSize / 2 it will subtract itself
    for (int i = 1; i < batchSize / 2; i++) {
      if (cnt[i] != 0 && cnt[batchSize - i] != 0) {
        int min = Math.min(cnt[i], cnt[batchSize - i]);
        cnt[batchSize - i] -= min;
        cnt[i] -= min;
        r += min;
      }
    }

    r += dfs(0, cnt, new HashMap<>());
    return r;
  }

  /**
   * Space and Time: O(N^B) N is groups length, B is the batchSize Space: All possible permutation,
   * during the process count the partitions
   *
   * @param remainder used to know if it reach a partition
   * @param cnt
   * @param dp cache intermedia result: partitions
   * @return
   */
  public static int dfs(int remainder, int[] cnt, Map<String, Integer> dp) {
    int batchSize = cnt.length;
    // int the last partition: the sum(group * remainder) may < batchSize

    String key = Arrays.toString(cnt);
    if (dp.containsKey(key)) return (dp.get(key));
    // System.out.println(key);
    // ----------

    int max = 0; // partitions
    boolean hasLeftGroups = false;
    for (int i = 1; i < batchSize; i++) {
      if (cnt[i] == 0) continue;
      hasLeftGroups = true;
      cnt[i]--;
      max = Math.max(max, dfs((remainder + i) % batchSize, cnt, dp));
      cnt[i]++;
    }
    // ----------
    // if (left groups number == 0) return 0; end the backtracking
    // remainder == 0 means started a new partition
    dp.put(key, remainder == 0 && hasLeftGroups ? max + 1 : max);
    return dp.get(key);
  }

  public static void main(String[] args) {
    maxHappyGroups(4, new int[] {1, 3, 2, 5, 2, 2, 1, 6});
  }
}
