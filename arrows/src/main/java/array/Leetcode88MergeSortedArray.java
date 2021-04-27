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

package array;

/** @see <a href="">leetcode</> */

/** @see <a href="https://leetcode.com/problems/merge-sorted-array/">leetcode</> */
public class Leetcode88MergeSortedArray {
  public static void merge2(int[] A, int m, int[] B, int n) {
    /*

     nums1.length == m + n
     nums2.length == n
     0 <= m, n <= 200
     1 <= m + n <= 200
     -10^9 <= nums1[i], nums2[i] <= 10^9
    */
    // TODO: check corner cases
    int l = m - 1, r = n - 1, i = m + n - 1;
    while (l >= 0 || r >= 0) {
      if (l >= 0 && r >= 0) {
        if (A[l] > B[r]) A[i--] = A[l--]; // select the bigger one
        else A[i--] = B[r--];
        continue;
      }
      // both index l and i  >=0. delete this code block will be endless loop
      if (l >= 0) break;

      if (r >= 0) {
        A[i--] = B[r--];
        continue;
      }
    }
  }

  public void merge(int[] A, int m, int[] B, int n) {
    int l = m - 1, r = n - 1, i = m + n - 1;
    while (l >= 0 && r >= 0) A[i--] = A[l] > B[r] ? A[l--] : B[r--];
    while (r >= 0) A[i--] = B[r--];
  }
}
