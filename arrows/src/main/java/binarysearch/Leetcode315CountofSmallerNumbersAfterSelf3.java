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
  with  Merge sort
  O(NlogN) time, O(N) space,
  N is length of A
*/
public class Leetcode315CountofSmallerNumbersAfterSelf3 {
  //  Merge sort with tracking of those right-to-left jumps
  static class N {
    int v, i;

    N(int v, int i) {
      this.v = v;
      this.i = i;
    }
  }

  public static List<Integer> countSmaller(int[] nums) {
    N[] A = new N[nums.length];
    for (int i = 0; i < nums.length; i++) A[i] = new N(nums[i], i);

    // Not use int[] r, because result required type is List<Integer>,
    // but Integer[] r need initial value to avoid value is null pointer
    Integer[] r = new Integer[nums.length];
    Arrays.fill(r, 0);
    mergesort(A, 0, A.length - 1, new N[A.length], r);
    return Arrays.asList(r);
  }

  /*
   Idea:
    have to calculate result during the sorting process
     - to handle the duplicated number
     - the status is dynamic
     - Observation: The smaller elements on the right of
      a number will jump from its right to its left during
      the ascending sorting process.
   O(NlogN) time
  */
  private static void mergesort(N[] A, int s, int e, N[] T, Integer[] a) {
    if (s == e) return;
    int m = s + e >>> 1;
    mergesort(A, s, m, T, a);
    mergesort(A, m + 1, e, T, a);
    System.arraycopy(A, s, T, s, e - s + 1);

    int i = s, j = m + 1, k = s;
    while (i <= m || j <= e) {
      if (j == e + 1 || i <= m && T[i].v <= T[j].v) {
        A[k] = T[i++];
        a[A[k].i] += j - (m + 1);
        // cumulate the sum of smaller ones on the right of A[K].v  during sorting
        k++;
      } else A[k++] = T[j++];
    }
  }
}
