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

package two_pointer;

import java.util.HashMap;
import java.util.Map;

public class Leetcode904FruitIntoBaskets {
  public int totalFruit(int[] tree) {
    /*
        1 <= tree.length <= 40000
        0 <= tree[i] < tree.length
        TODO: checking null, empty
    */
    /*
    Idea:
    the tree[] is type of fruit, not number of fruit.
    So the question is longest sub-array containing only 2 type of fruit.
    O(N) time, space;
    */
    // use a map to keep tree type and and its frequency in current window
    // keep only 2 distinguish valid type in the sliding window
    Map<Integer, Integer> map = new HashMap();

    int i = 0, l = 0; // current window is [l,i]
    int r = 0;
    for (; i < tree.length; i++) {
      int t = tree[i]; // tree type
      map.put(t, map.getOrDefault(t, 0) + 1);
      while (map.size() == 3) {
        r = Math.max(r, i - l);
        t = tree[l];
        map.put(t, map.get(t) - 1);
        if (map.get(t) == 0) map.remove(t);
        l++;
      }
      r = Math.max(r, i - l + 1);
    }
    return r;
  }
}
