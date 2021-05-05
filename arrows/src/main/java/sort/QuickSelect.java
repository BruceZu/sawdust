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

import static common_lib.Common.swap;
import static sort.InsertSort.insertSort;

public class QuickSelect {
  /**
   * For 5 or less elements right - left < 5 implement this using insertion sort
   *
   * @param arr
   * @param left index, inclusive.
   * @param right index, inclusive.
   * @return get final index of median or <strong>left</strong> median
   */
  private static int pivot5(int[] arr, int left, int right) {
    insertSort(arr, left, right);
    return left + ((right - left) >> 1);
  }

  /**
   * ---------------------------------------------------------------------------------------------------
   * Select a pivot based on median of medians strategy like the nth_element() int c++.
   * implementation base on intro sort
   *
   * @param arr
   * @param l index, inclusive.
   * @param r index, inclusive.
   * @return the index of median or left median which is as the pivot
   */
  private static int pivotByMedianOfMedians(int[] arr, int l, int r) {
    if (r - l < 5) return pivot5(arr, l, r);

    // otherwise move the medians of five-element subgroups to the first n/5 positions
    int swapTo = l;
    for (int i = l; i <= r; i = i + 5) {
      // get the median of the i'th five-element subgroup
      int indexOfCurMedian = pivot5(arr, i, i + 4 > r ? r : i + 4);
      swap(arr, indexOfCurMedian, swapTo++);
    }
    // compute the median or left median of the n/5 medians-of-five
    return introSelectKth(arr, l, swapTo - 1, l + ((swapTo - 1 - l) >> 1));
  }

  /**
   * move all elements in 3-ways. index: ...... left ............................. right ......
   * value: ...... smaller scope, pivot scope, bigger scope ......
   *
   * @param arr
   * @param left index, inclusive.
   * @param right index, inclusive.
   * @param pivotIndex
   * @return index scope of pivot value, inclusive.
   */
  private static int[] partition(int[] arr, int left, int right, int pivotIndex) {
    int pv = arr[pivotIndex];

    // 3-ways partition
    int swapNextSmallTo = left;
    int swapNextBigTo = right;
    int cur = left;
    while (cur <= swapNextBigTo) {
      if (arr[cur] < pv) {
        swap(arr, cur++, swapNextSmallTo++);
      } else if (arr[cur] == pv) {
        cur++;
      } else {
        swap(arr, cur, swapNextBigTo--);
      }
    }
    return new int[] {swapNextSmallTo, cur - 1};
    //  cur-1 will always be the last index of pivot value
  }

  /**
   * worst case performance O(n), best case performance O(n). Todo: o(1) extra space?? o(lgN)??
   *
   * @param arr
   * @param l index , inclusive.
   * @param r index , inclusive.
   * @param k index
   * @return index of the n-th smallest element of list within left..right inclusive (left <= n <=
   *     right), and make sure it is in its final sorted position with all those <= it is in its
   *     left and in an unsorted order all those >= it is in its right and in an unsorted order.
   */
  public static int introSelectKth(int[] arr, int l, int r, int k) {
    while (l < r) {
      int pivotalIndex = pivotByMedianOfMedians(arr, l, r);
      int[] pivotalIndexScope = partition(arr, l, r, pivotalIndex);
      // now the pivot is in its final sorted position.
      if (pivotalIndexScope[0] <= k && k <= pivotalIndexScope[1]) {
        return k;
      }
      if (k < pivotalIndexScope[0]) {
        r = pivotalIndexScope[0] - 1;
      } else {
        l = pivotalIndexScope[1] + 1;
      }
    }
    // left == right
    return l;
  }
}
