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

package binarysearch;

public class Leetcode1539KthMissingPositiveNumber {
  /*
   Idea: binary search
   1>  no ==. because when it is ==. in case of ..., 107, 119, 120,, ...
       where m is index of 109  have to find out the index of 107.
   2> with
       ```
       while (l <= r) {
       if (A[m] - (1 + m) < k) l = m + 1;
       else r = m - 1;
       }
       ```
      guarantee:
        when the loop is over
        r is the right most index keeping missing number sum < k
        index scope left to r is < relationship, right to l is >= relationship
      no guarantee:
        r and r are still in [0, length-1]

  3>   r+1 is the sum of not missing numbers.
       result is  r + 1 + k
       or A[r] + (k - (A[r] - (r + 1))); // need checking whether r is -1
    */
  // O(logN)
  public static int findKthPositive(int[] A, int k) {
    /*
    TODO:corner cases
    1 <= arr.length <= 1000
    1 <= arr[i] <= 1000
    1 <= k <= 1000
    arr[i] < arr[j] for 1 <= i < j <= arr.length
     */
    int l = 0, r = A.length - 1;
    while (l <= r) {
      int m = (l + r) >> 1;
      if (A[m] - (1 + m) < k) l = m + 1;
      else r = m - 1;
    }
    return r + 1 + k;
  }
}
