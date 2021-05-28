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
import java.util.List;

/*
  with segment tree(ST)
  O(NlogN) time, O(N) space,
  N is length of A
*/
public class Leetcode315CountofSmallerNumbersAfterSelf2 {
  public List<Integer> countSmaller(int[] A) {
    int[] sorted = A.clone();
    int[] map = new int[A.length];
    Arrays.sort(sorted); // O(nlogn)
    for (int i = 0; i < A.length; i++) { // O(nlogn)
      map[i] = Arrays.binarySearch(sorted, A[i]);
    }
    // map[] is index mapping, to have A[i]==sorted[map[i]]

    int[] ST = new int[2 * A.length];
    // build with zero value array with length of L
    int L = A.length;

    Integer[] a = new Integer[A.length];
    for (int i = A.length - 1; i >= 0; i--) {
      a[i] = sum(map[i] - 1, ST, L);
      update(map[i], ST, L);
    }
    return Arrays.asList(a);
  }

  /*
  Sum of index range [0,i] of original flat array x
  by ST.
  O(logN) time, O(1）space
  */
  private int sum(int i, int[] ST, int L) {
    int l = L, r = L + i;
    int a = 0;
    while (l <= r) {
      if ((l & 1) == 1) a += ST[l++];
      if ((r & 1) == 0) a += ST[r--];
      l >>>= 1;
      r >>>= 1;
    }
    return a;
  }

  /*
    Increase 1 at index i of the original flat array x
    updating ST accordingly
    O(logN) time, O(1）space
  */
  private void update(int i, int[] ST, int L) {
    int idx = i + L;
    ST[idx] += 1; // delta is 1;
    while (idx >= 2) {
      ST[idx >>> 1] = ST[idx] + ST[idx ^ 1];
      idx >>>= 1;
    }
  }
}
