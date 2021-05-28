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

package binarysearch;

import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

/*
 with TreeSet
 t.headSet(A[i]).size()
 O(n^2) time
 Time Limit Exceeded
*/
public class Leetcode315CountofSmallerNumbersAfterSelf5 {

  /*
    Traverse the array backwards, meanwhile build a self balanced BST (AVL, RB-tree)
    with comparator (a, b) -> (a >= b) ? 1 : -1).
  */
  public static List<Integer> countSmaller2(int[] A) {
    Integer[] r = new Integer[A.length];
    TreeSet<Integer> t = new TreeSet<>((a, b) -> a <= b ? -1 : 1);
    for (int i = A.length - 1; i >= 0; i--) {
      t.add(A[i]); // A[i] is the a in comparator;
      r[i] = t.headSet(A[i]).size(); // O(n) A[i] is the a in comparator;
      // set.headSet(e) is O(1),  but SortedSet<E>.size() is O(N) or O(1)
    }
    return Arrays.asList(r);
  }
}
