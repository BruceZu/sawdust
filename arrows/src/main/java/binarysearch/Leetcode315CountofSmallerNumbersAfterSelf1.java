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

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/*
  with binary index tree (BIT)
  O(NlogN) time, O(N) space,
  N is length of A
*/
public class Leetcode315CountofSmallerNumbersAfterSelf1 {
  /*
     the way to get index mapping:
     original array's duplicated number's index in sorted array is wrong but
     all duplicated number's index are all same,
     so it does not matter in Leetcode 315

  O(NlogN)
  */
  public List<Integer> countSmaller(int[] A) {
    if (A == null || A.length == 0) return new LinkedList();

    int[] sort = A.clone();
    // O(NlogN)
    Arrays.sort(sort);
    for (int i = 0; i < A.length; i++) {
      A[i] = Arrays.binarySearch(sort, A[i]);
    }

    // Now A[i] is a map: original array index -> index in sorted array
    int[] BIT = new int[A.length + 1];
    Integer[] r = new Integer[A.length];
    for (int i = A.length - 1; i >= 0; i--) { // O(nlogn)
      update(A[i], BIT);
      r[i] = query(A[i] - 1, BIT); // [0,indexInFlatArray)
    }
    return Arrays.asList(r);
  }

  private int query(int i, int[] BIT) {
    int idx = i + 1;
    int sum = 0; // [0,idx]
    while (idx > 0) {
      sum += BIT[idx];
      idx -= idx & (-idx);
    }
    return sum;
  }

  private void update(int i, int[] BIT) {
    int idx = i + 1;
    while (idx < BIT.length) {
      BIT[idx]++; // add 1
      idx += idx & (-idx);
    }
  }
}
