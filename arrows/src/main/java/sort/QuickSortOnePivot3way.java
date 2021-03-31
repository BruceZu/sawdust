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

import static common_lib.Common.*;
import static sort.InsertSort.insertSort;

/**
 * <pre>
 * @see
 *   <br><a href="http://www.sorting-algorithms.com/quick-sort-3-way">
 *      Quick Sort (3 Way Partition)
 *      </a>
 *   <br><a href="http://www.sorting-algorithms.com/static/QuicksortIsOptimal.pdf">
 *      QUICKSORT IS OPTIMAL Robert Sedgewick Jon Bentley
 *      </a>
 *   <br><a href="https://en.wikipedia.org/wiki/Radix_sort">
 *       wiki: Radix sort
 *   </a>
 */
public class QuickSortOnePivot3way {
//    /**
//     * Ascending order.
//     * jumping by pivot initialed at left side with the one starts from the right side.
//     * the items with same value as pivot firstly be allocated to left/right side area
//     * then be moved to the center at the end.
//     *
//     * @param l Left index
//     * @param r Right index
//     * @return Left and right index of center pivot area
//     */
//    private static <T extends Comparable<T>> int[] legencyLocatePivotIndex3Way(T[] arr, int l, int r) {
//        // Improvement
//        initPivotUsingMedianOf3(arr, l, r, (l + r) / 2);
//
//        int lp = l - 1; // right index of left pivots area
//        int rp = r + 1; // left index of right pivots area
//        /**<pre>
//         * meaning of lp and rp:
//         *
//         *  pppp, < p ,  p , > p, pppp
//         *     |                  |
//         *    lp                 rp
//         *
//         *  init value:
//         *
//         *       [p a b c d e f]
//         *     |                 |
//         *    lp                 rp
//         *
//         *  general:
//         *
//         *     p p p, 1, 2, p, 6, p, 17, 18, 19, p p p
//         *         |        |     |              |
//         *         lp           other            rp
//         */
//
//        // index
//        int pI = l, curI = r;
//
//        while (pI != curI) {// jump near
//            // equal
//            if (arr[pI] == arr[curI]) {
//                // allocate found pivot to side area
//                if (curI > pI) {
//                    swap(arr, curI, --rp);
//                } else { // Note: must 'else', else run both of them.
//                    swap(arr, curI, ++lp);
//                }
//            } else if (pI < curI && greatThan(arr[pI], arr[curI])
//                    || pI > curI && lessThan(arr[pI], arr[curI])) {
//                // sort ascending
//                swap(arr, pI, curI);
//                // also swap index variable
//                pI ^= curI;
//                curI ^= pI;
//                pI ^= curI;
//            }
//            curI = pI < curI ? curI - 1 : curI + 1;
//        }
//
//        /**
//         * <pre>
//         *  Now: p, p, p | < p | p | > p | p, p, p
//         *             |         |         |
//         *            lp                   rp
//         */
//
//        // Move both sides pivot value into the center
//        int pr = pI, pl = pI; // center pivots area right index
//        while (lp != l - 1) {
//            swap(arr, --pl, lp--);
//        }
//
//        while (rp != r + 1) {
//            swap(arr, ++pr, rp++);
//        }
//
//        //Now 3 way:
//        //           <p  |  =p  |   >p
//        return new int[]{pl, pr};
//    }

    private static <T extends Comparable<T>> int[] locatePivotIndex3Way(T[] arr, int l, int r) {
        T pivot = arr[getPivotIndexUsingMedianOf3(arr, l, r, (l + r) / 2)];
        int i = l, s = l, g = r; // next smaller index s and next greater index g. compared with pivot value
        // partition in 3 ways:  <pivot | pivots | > pivot
        while (i <= g) {
            if (lessThan(arr[i], pivot)) {
                swap(arr, i, s);
                i++;
                s++;
            } else if (same(pivot, arr[i])) {
                i++;
            } else {
                swap(arr, i, g);
                g--;
            }
        }
        return new int[]{s, g};
    }

    private static <T extends Comparable<T>> void call(T[] arr, int l, int r) {
        // Range check.  Note: java.lang.ArrayIndexOutOfBoundsException
        if (l >= r) {
            return;
        }
        // Improvement: for tiny array using  Insertion sort
        if (arr.length <= 4) {
            insertSort(arr);
            return;
        }

        int[] p = locatePivotIndex3Way(arr, l, r);
        call(arr, l, p[0] - 1); // Note: not 0
        call(arr, p[1] + 1, r); // Note: not arr.length
    }

    /**
     * Idea: <pre>
     * Improves quick sort in presence of duplicate keys.
     * The difference with {@link QuickSortOnePivot2way QuickSortOnePivot2way} is in the
     * {@link #locatePivotIndex3Way(T[] arr, int l, int r) locatePivotIndex3Way} method.
     * <p>
     * where taking in account the equal relation:
     */
    public static <T extends Comparable<T>> void quickSort3Way(T[] arr) {
        // Input check
        if (arr == null || arr.length <= 1) { // Note: arr.length may be 0  and 1
            return;
        }
        call(arr, 0, arr.length - 1);
    }
}
