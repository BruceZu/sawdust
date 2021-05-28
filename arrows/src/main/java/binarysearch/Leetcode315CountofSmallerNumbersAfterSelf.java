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

package binarysearch;

import java.util.*;

public class Leetcode315CountofSmallerNumbersAfterSelf {

  /*
  Skill:
  - if binary search result x is a negative number
    then the index should be got by -(x+1) = -x-1 = (~x+1)-1 = ~x
  - duplicated number will be take as not same (a, b) -> a >= b ? 1 : -1
      when a == b, b is insert at the index of a, a is moved back.
    to make the result of   Collections.binarySearch(l, A[i], (a, b) -> a >= b ? 1 : -1)
    always be negative
  Idea: intuitive
  log(N!) = O(NlogN) time
  */
  public static List<Integer> countSmaller(int[] A) {
    Integer[] r = new Integer[A.length];
    List<Integer> l = new ArrayList<>(); // is a sorted list
    for (int i = A.length - 1; i >= 0; i--) {
      r[i] = ~Collections.binarySearch(l, A[i], (a, b) -> a >= b ? 1 : -1);
      l.add(r[i], A[i]); // O(1) for ArrayList.
    }
    return Arrays.asList(r);
  }
}
