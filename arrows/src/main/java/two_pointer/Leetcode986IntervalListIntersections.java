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

import java.util.ArrayList;
import java.util.List;

public class Leetcode986IntervalListIntersections {
  // O(min{M,N}) M and N is the length of firstList  and secondList
  public int[][] intervalIntersection(int[][] firstList, int[][] secondList) {
    /*
    TODO: corner cases checking

    0 <= firstList.length, secondList.length <= 1000
    firstList.length + secondList.length >= 1
    0 <= start_i < end_i <= 10^9
    end_i < start_i+1
    0 <= start_j < end_j <= 10^9
    end_j < start_j+1
    A closed interval [a, b] (with a < b) denotes the
    set of real numbers x with a <= x <= b.
    The intersection of two closed intervals is a set of
    real numbers that are either empty or represented as a
    closed interval. For example, the intersection of [1, 3] and [2, 4] is [2, 3].

    Each list of intervals is pairwise disjoint and in sorted order.
    Return the intersection of these two interval lists.
    Idea:

    O(min{M,N}) M and N is the length of firstList  and secondList
    */

    int i = 0, j = 0; // is is index of s; j is index of f
    List<int[]> l = new ArrayList<>();
    while (i < firstList.length && j < secondList.length) {
      int[] f = firstList[i];
      int[] s = secondList[j];
      // Each list of intervals is pairwise disjoint and in sorted order.
      // So the way to know if there is Intersection is:
      int maxl = Math.max(s[0], f[0]), minr = Math.min(s[1], f[1]);
      if (maxl <= minr) {
        l.add(new int[] {maxl, minr});
      }
      // now need to decide which one(s) is discard for next step checking
      // at least one will be discarded
      if (s[1] < f[1]) j++;
      else if (f[1] < s[1]) i++;
      else {
        i++;
        j++;
      }
    }

    int[][] r = new int[l.size()][];
    i = 0;
    for (int[] inter : l) {
      r[i++] = inter;
    }
    return r;
  }
}
