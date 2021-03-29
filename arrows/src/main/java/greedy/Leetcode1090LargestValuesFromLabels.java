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

package greedy;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class Leetcode1090LargestValuesFromLabels {
  /*
  selected set size <= num_wanted
  in the selected set the count of values with the same label <= use_limit.

  1 <= values.length == labels.length <= 20000
  0 <= values[i], labels[i] <= 20000
  1 <= num_wanted, use_limit <= values.length
   */

  /*
  Idea:
  Greedy, so need order by value, also need the value's label, so use entry(int[]) to bundle them together
  and use max heap to order them by the value.

  When deciding whether select current entry, need to check the counts of entry/value with the
  same used label as the current entry's label. So need a map to keep the label: counts of entry/value

  Note:
  - max heap's comparator
  - not each entry in max heap will be selected and make 'num_wanted--'
   */
  // O(NlogN) N is array length
  public int largestValsFromLabels(int[] values, int[] labels, int num_wanted, int use_limit) {
    PriorityQueue<int[]> maxHeap = new PriorityQueue<>((a, b) -> b[0] - a[0]); // max heap
    for (int i = 0; i < values.length; i++) {
      maxHeap.add(new int[] {values[i], labels[i]});
    }
    Map<Integer, Integer> lim = new HashMap<>();
    int r = 0;

    while (num_wanted > 0 && !maxHeap.isEmpty()) {
      int[] cur = maxHeap.poll();
      if (lim.getOrDefault(cur[1], 0) < use_limit) {
        r += cur[0];
        lim.put(cur[1], lim.getOrDefault(cur[1], 0) + 1);
        num_wanted--; // num_wanted-- not for each loop.
      }
    }
    return r;
  }
}
