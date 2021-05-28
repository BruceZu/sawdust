//  Copyright 2016 The Sawdust Open Source Project
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

package sort;

import common_lib.Common;
import common_lib.Merger;

public class MergeSortNoRecursion {
  private static Merger merger = new Common();

  public MergeSortNoRecursion(Merger merger) {
    this.merger = merger;
  }

  /**
   * Merge sort an array without recursion. no thread safe
   * <pre>
   * Idea:
   * 1>   decide the size of sub array from 1, then 2, 4 ,8, 16 .... while the size <= arr.length()
   * 2>   with each size, loop arr from left:
   *   21>     calculate the index of 2 sub arrays: [l, mid] and [mid+1, r]
   *   22>     merge them in sort
   * <a href="http://softwareengineering.stackexchange.com/questions/297160/why-is-mergesort-olog-n">O(NLOGN)</a>
   */
  // O(NlogN) runtime, O(N) space.
  public static <T extends Comparable<T>> void mergeSortNoRecursion(T[] A) {
    if (A == null || A.length <= 1) return;
    Comparable[] T = new Comparable[A.length]; // space O(N)
    int L = 1; // sorted window length
    // O(NlogN)
    while (L < A.length) {
      int s = 0, m = s + L - 1; // first end
      int e; // end index
      // [s, m] and [m+1, e]
      while (m < A.length - 1) {
        e = s + 2 * L - 1;
        e = Math.min(e, A.length - 1);
        merger.mergeInsort(A, s, m, e, T);
        // 2*L width window are sorted in ascending order

        s = s + 2 * L;
        m = s + L - 1;
      }

      L = L << 1;
    }
  }
}
