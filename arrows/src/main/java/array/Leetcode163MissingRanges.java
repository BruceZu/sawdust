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

package array;

import java.util.LinkedList;
import java.util.List;

public class Leetcode163MissingRanges {
  // O(N) time and O(N) space
  public List<String> findMissingRanges(int[] A, int lower, int upper) {
    List<String> a = new LinkedList<>();
    if (A == null || A.length == 0) { //   0 <= array.length <= 100
      a.add(help(lower, upper));
      return a;
    }
    int p = lower - 1;
    for (int i = 0; i <= A.length; i++) {
      int cur = i == A.length ? upper + 1 : A[i];
      //   All the values of array are unique.
      if (cur != p && cur - 1 != p) a.add(help(p + 1, cur - 1));
      p = cur;
    }
    return a;
  }
  // (l,r) lower <= nums[i] <= upper
  private String help(int l, int r) {
    if (l == r) return "" + l;
    return l + "->" + r;
  }
}
