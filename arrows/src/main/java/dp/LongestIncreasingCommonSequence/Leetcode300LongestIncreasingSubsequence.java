//  Copyright 2018 The KeepTry Open Source Project
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

package dp.LongestIncreasingCommonSequence;

import java.util.Arrays;

public class Leetcode300LongestIncreasingSubsequence {

  /*
  Idea:
  Just like locate right seat in country cinema to make sure
  not hiding other to watch the film.
  smaller one should stand before the taller one(replace)
  tallest one should stand alone(append)
  */
  // O(NlogN) time and O(N) space
  static int lengthOfLISOriginal(int A[]) {
    if (A == null || A.length == 0) return 0;
    int[] a = new int[A.length];
    int size;

    a[0] = A[0];
    size = 1;
    for (int n : A) { // O(n)
      // if (A[i - 1] == n) continue; // continued duplicated number, performance
      if (n < a[0]) a[0] = n; // replace. Note here: not insert
      else if (n > a[size - 1]) a[size++] = n; // apend
      else { // find and replace.
        // sure not n is in the scope [a[0], a[size-1]]
        int il = Arrays.binarySearch(a, 0, size, n);
        if (il < 0) il = -(il + 1);
        a[il] = n;
      }
    }
    return size;
  }

  public int lengthOfLIS(int[] nums) {
    int[] a = new int[nums.length];
    int size = 0;
    for (int n : nums) {
      int i = Arrays.binarySearch(a, 0, size, n);
      if (i < 0) i = -(i + 1);
      a[i] = n;
      if (i == size) size++;
    }
    return size;
  }
}
