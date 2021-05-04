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

package sort.heap;

public class Leetcode215KthLargestElementinanArray {
  /*
   Get the value of the number at index ki in the descending ordered array.

  Idea: Comes but not completely same as quick sort idea, see Arrays. sort();
        The difference with quicksort:
         1 only care one side way, the quick sort may need care 3 way with one
           pivot and 5 ways with 2 pivotal
         2. more important. next time only handle half part of the current.
           So time is O(logN) if not use recursion it is O(1) space
           https://en.wikipedia.org/wiki/Quickselect
           ‘instead of recursing into both sides, as in quicksort,
            quickselect only recurses into one side’

  pivotal select:   select middle index number or the last index number;
                    best is to select the one with middle value of numbers:
                       middle index number and 2 both side number.
                     ```Java
                     Random ran = new Random();
                     int pivot_index = left + ran.nextInt(right - left +1);
                     ```

  get the index of pivot in the partitioned array index [from, to] as:
   'any order of number <p, p self one number, any order of number >=p`

  Note:
   - index `l` is the index used to insert into next number < pivotal value
   - index `i` is an unhandled number index after handled its index number >= p
   - put pivot in index `to`. after the loop swap it with the number in  index `l` to
     make the result array in expected status:
      'any order of number <p, p self one number, any order of number >=p`
   - after the loop: [l,to] is number >= p in any order.
     need to switch p with the number in  index `l`:
      -- thus the array is in required status
      'any order of number <p, p self one number, any order of number >=p`
      -- for the case where index  `l` is just the required K index then need to
         return the right number in index l/K.

  O(N) average time. O(N^2) worst-case time;  O(D）space D is call stack depth

  Alternative Idea:
   1. sort the array with Arrays. sort()
       O(NlogN) time and O(1) space
   2. heap sort with PriorityQueue and keep its size is k
       O(NlogK) time and O(k) space
   3  variation of current Idea but find the k-1 index number ins descending order array
   4. replace recursion with loop
       O(N) time. O(1) space
  */
  private int valueOfKinexInAscendingOrder(int[] nums, int start, int end, int K) {
    int l = start, r = end, p = nums[r];
    // O(N)
    for (int i = l; i < r; i++) if (nums[i] < p) swap(nums, l++, i);
    swap(nums, l, r);

    if (l == K) return nums[l];
    else if (l < K) return valueOfKinexInAscendingOrder(nums, l + 1, r, K); // half
    else return valueOfKinexInAscendingOrder(nums, start, l - 1, K); // half
  }

  // no recursion implement ---------------------------------------------------
  public int findKthLargest(int[] nums, int k) {
    // TODO: check corner cases
    //  return valueOfKinexInAscendingOrder(nums, 0, nums.length - 1, nums.length - k);
    int l = 0, r = nums.length - 1, K = k - 1; // index of Kth bigger numb in Descending array
    while (true) {
      int pi = midPivotIndexAfterDescendingGroup(nums, l, r);
      if (pi == K) return nums[pi];
      else if (pi > K) r = pi - 1;
      else l = pi + 1;
    }
  }

  // replace recursion with loop
  private int midPivotIndexAfterDescendingGroup(int[] nums, int from, int to) {
    int pi = (from + to) >> 1, p = nums[pi];
    swap(nums, pi, to);
    int l = from;
    for (int i = from; i < to; i++) if (nums[i] > p) swap(nums, i, l++);
    swap(nums, l, to);
    return l;
  }

  private static void swap(int[] nums, int l, int r) {
    if (l != r) {
      int t = nums[l] ^ nums[r];
      nums[l] ^= t;
      nums[r] ^= t;
    }
  }
}
