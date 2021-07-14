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

package string;

import java.util.*;

public class Leetcode937ReorderDatainLogFiles {
  /*
        Each log is a space-delimited string of words, where the first word is the identifier.
        There are two types of logs:

        Letter-logs: All words (except the identifier) consist of lowercase English letters.
        Digit-logs: All words (except the identifier) consist of digits.

        Reorder these logs so that:

        The letter-logs come before all digit-logs.
        The letter-logs are sorted lexicographically by their contents. If their contents are the same, then sort them lexicographically by their identifiers.
        The digit-logs maintain their relative ordering.

    Return the final order of the logs.

        1 <= logs.length <= 100
        3 <= logs[i].length <= 100
        All the tokens of logs[i] are separated by a single space.
        logs[i] is guaranteed to have an identifier and at least one word after the identifier.
  */
  /*
  Idea:
    need to know quicksort is not stable. But Java version quicksort in Arrays.sort() is stable

     1> "Stable sort algorithms sort repeated elements in
     the same order that they appear in the input."
     https://en.wikipedia.org/wiki/Sorting_algorithm#Stability

     2> Java Arrays.sort() documentary:
     "the algorithm used by sort(Object[]) does not have to be a MergeSort,
     but it does have to be stable."
     https://docs.oracle.com/javase/8/docs/api/java/util/Arrays.html
     https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/Arrays.html

     2-1> sort arrays of primitives:
      "The sorting algorithm is a Dual-Pivot Quicksort by
      Vladimir Yaroslavskiy, Jon Bentley, and Joshua Bloch. This algorithm offers
      O(n log(n)) performance on many data sets that cause other quicksorts to degrade
      to quadratic performance, and is typically faster than traditional (one-pivot)
      Quicksort implementations."
      https://docs.oracle.com/javase/8/docs/api/java/util/Arrays.html#sort-byte:A-
     2-2>sorting arrays of objects is done using Timsort
      "Sorts the specified array of objects into ascending order,
       according to the natural ordering of its elements.
       All elements in the array must implement the Comparable interface.
       Furthermore, all elements in the array must be mutually comparable
       (that is, e1.compareTo(e2) must not throw a ClassCastException for any elements
        e1 and e2 in the array).
       This sort is guaranteed to be stable: equal elements will not be reordered
        as a result of the sort.
       Implementation note: This implementation is a stable, adaptive, iterative mergesort
       that requires far fewer than nlg(n) comparisons when the input array is partially
       sorted, while offering the performance of a traditional mergesort when the input
       array is randomly ordered. If the input array is nearly sorted, the implementation
       requires approximately n comparisons.
       Temporary storage requirements vary from a small constant for nearly sorted input arrays
       to n/2 object references for randomly ordered input arrays.
       The implementation takes equal advantage of ascending and descending order in its
       input array, and can take advantage of ascending and descending order in different
       parts of the the same input array.
       It is well-suited to merging two or more sorted arrays: simply concatenate the arrays
       and sort the resulting array.
       The implementation was adapted from Tim Peters's list sort for Python ( TimSort ).
       It uses techniques from Peter McIlroy's "Optimistic Sorting and Information
       Theoretic Complexity", in Proceedings of the Fourth Annual ACM-SIAM Symposium on
       Discrete Algorithms, pp 467-474, January 1993"

     2-3> sort(T[] a, Comparator<? super T> c)
      "Temporary storage requirements vary from a small constant for nearly sorted
       input arrays to n/2 object references for randomly ordered input
       arrays."
       TimSort Worst-case space: O(N)

     O(L⋅N⋅log N) time L is the maximum length of a single log
     O(L⋅N) space. in Java Arrays.sort(T[] a, Comparator<? super T> c)
                   is TimSort whose worst-case space: O(N)
   */

  public static String[] reorderLogFiles(String[] logs) {
    Arrays.sort(
        logs,
        (l1, l2) -> {
          char e1 = l1.charAt(l1.length() - 1);
          char e2 = l2.charAt(l2.length() - 1);
          boolean isD1 = e1 <= '9' && '0' <= e1;
          boolean isD2 = e2 <= '9' && '0' <= e2;
          if (!isD1 && !isD2) {
            int i1 = l1.indexOf(" "), i2 = l2.indexOf(" ");
            String ID1 = l1.substring(0, i1), ID2 = l2.substring(0, i2);
            if (l1.substring(i1).equals(l2.substring(i2))) return ID1.compareTo(ID2);
            return l1.substring(i1).compareTo(l2.substring(i2));
          } else if (isD1 && isD2) {
            return 0;
          } else if (isD1) return 1; // note here: digit > string
          else return -1;
        });
    return logs;
  }
}
