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

package stack;

import java.util.Stack;

public class Leetcode1762BuildingsWithanOceanView {
  public int[] findBuildings(int[] heights) {
    /*
    TODO: corner cases checking
    1 <= heights.length <= 105
    1 <= heights[i] <= 109

    Return a list of indices (0-indexed) of buildings that have an ocean view, sorted in
    increasing order.
    */

    // O(N) with stack
    Stack<Integer> s = new Stack();
    int max = Integer.MIN_VALUE;
    for (int i = heights.length - 1; i >= 0; i--) {
      if (heights[i] > max) {
        s.push(i);
        max = Math.max(max, heights[i]);
      }
    }
    int[] r = new int[s.size()];
    int i = 0;
    while (!s.isEmpty()) {
      r[i++] = s.pop();
    }
    return r;
  }
}
