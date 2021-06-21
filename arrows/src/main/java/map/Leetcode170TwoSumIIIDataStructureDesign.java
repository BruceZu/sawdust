//  Copyright 2017 The keepTry Open Source Project
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

package map;

import java.util.*;

public class Leetcode170TwoSumIIIDataStructureDesign {
  /*
  space O(N)
   */
  private List<Integer> l = new ArrayList();
  boolean sorted = false;

  // O(1) time
  public void add(int number) {
    l.add(number);
    sorted = false;
  }

  // O(NlogN) time
  public boolean find(int value) {
    if (!sorted) {
      Collections.sort(l);
      sorted = true;
    }
    Integer[] A = new Integer[l.size()];
    l.toArray(A);
    int l = 0, r = A.length - 1;
    while (l < r) {
      int sum = A[l] + A[r];
      if (A[l] + A[r] == value) return true;
      else if (sum < value) l++;
      else r--;
    }
    return false;
  }
}

class TwoSum {
  // space O(N)
  Map<Integer, Integer> map;

  public TwoSum() {
    map = new HashMap();
  }

  // O(1) time
  public void add(int number) {
    map.put(number, map.getOrDefault(number, 0) + 1);
  }

  // O(N) time
  public boolean find(int value) {
    for (Map.Entry<Integer, Integer> e : map.entrySet()) {
      int k = e.getKey();
      int f = e.getValue();
      int c = value - k;
      if (k == c && f > 1 || k != c && map.containsKey(c)) return true;
    }
    return false;
  }
}
