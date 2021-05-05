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

import java.util.Arrays;

public class Leetcode324WiggleSortII {
  /*
   Idea:
   Sort array in ascending and clone it
   working in original array as result array.
   allocate number in sorted array with index -> result array with index :
   - mid-> 0  ->  0,2,...,N-1 (even index)
   - N-1->mid+1 to  1,3,...,N-1(odd index)

   Note: processing the element in sorted array in descending order,
    thus separate the middle part to both side to avoid them become
    neighbor if they have duplicate continuous numbers

  O(NlgN) time and O(N) space
   */
  public static void wiggleSortOriginal(int[] A) {
    int N = A.length;
    Arrays.sort(A); // O(NlgN)
    int[] s = Arrays.copyOf(A, N); // O(N) extra space

    int mi = (N & 1) == 0 ? N / 2 - 1 : N / 2; // (left) median index
    int e = N - 1;

    for (int i = 0; i < N; i += 2, e--, mi--) { // i: index of result array
      A[i] = s[mi]; // from mi -> 0
      if (i + 1 < N) { // existing
        A[i + 1] = s[e]; // from N-1-> mi+1
      }
    }
  }
  // --------------------------------------------------------------------------

  /*
   O(N)runtime and O(1) extra space

   */
  public static void wiggleSort(int[] A) {
    // index of (left) median;
    int m = A.length - 1 >> 1;
    quickSelectlocateKInAscending(A, 0, A.length - 1, m);
    wiggleSortPartitionedArrayIn3ways(A, A[m]);
  }
  // arrange array to make
  //   A[i] <= A[k], i<K;
  //   A[k] <= A[i], k<i
  // assume index k is in [start, end]
  // O(N) time. O(1) space
  public static void quickSelectlocateKInAscending(int[] A, int start, int end, int k) {
    if (k < start || k > end) return;
    int l = start, r = end;
    while (true) { // sure can find kth value
      int p = arrangeInNoDescendingByMidPivotal(A, l, r);
      if (p == k) return;
      else if (p > k) r = p - 1;
      else l = p + 1;
    }
  }
  // select middle element as pivotal value.
  // swap elements in index scope [from, to] to make array in no-descending order
  // in this scope as:
  //    elements < random pivotal value | random pivotal value | elements >= random pivotal value
  // return the random pivotal value index to make sure the index scope of
  // [from, returned index] has values <= A[returned index]
  private static int arrangeInNoDescendingByMidPivotal(int[] A, int from, int to) {
    int p = (from + to) >> 1, pv = A[p];
    swap(A, p, to); // index `to` is for pivot value
    int l = from; // l is for next smaller value < pivot value
    for (int i = from; i <= to - 1; i++) if (A[i] < pv) swap(A, i, l++);
    swap(A, l, to);
    return l;
  }

  private static void swap(int[] nums, int l, int r) {
    if (l != r) {
      int t = nums[l] ^ nums[r];
      nums[l] ^= t;
      nums[r] ^= t;
    }
  }
  /*

   p: median or left median value
   Assume array is in thus status:
   smallers and biggers are separated by median,
   both smallers and biggers need not to be sorted

  Take [4 5 5 6] as example:
  index 0       2(lm)
  index     1           3
  b: index for next bigger number, start from index 1, next is 3 (ascending)
  s: index for smaller bigger number, start from index 2, next is 0 (descending)
  At last, mideans number are left/separated on both sides
  it has the same effect as merging 2 descending ordered sub array
      [mideans, smallers]
      [biggers, middeans]
   */
  public static void wiggleSortPartitionedArrayIn3ways(int[] A, int p) {
    int b = 0; // for next Bigger
    int i = 0;
    int s = A.length - 1; // for next Smaller
    while (i <= s) {
      if (A[wi(i, A)] > p) {
        swap(A, wi(i, A), wi(b, A)); // wiggle order
        i++; // ascending
        b++; // ascending
      } else if (A[wi(i, A)] < p) {
        swap(A, wi(i, A), wi(s, A));
        s--; // descending
      } else i++;
    }
  }

  // For index     0, 1, 2, 3 of Array [4,5,5,6], Length is 4
  // return index: 1, 3; 0, 2. odd index then even index in ascending order
  // It is used in really processing order
  public static int wi(int i, int[] A) {
    return (2 * i + 1) % (A.length | 1);
  }
}
