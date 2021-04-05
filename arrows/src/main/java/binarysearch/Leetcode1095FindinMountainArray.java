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

interface MountainArray {
  int get(int index);

  int length();
}

public class Leetcode1095FindinMountainArray {
  // O(logN)
  public int findInMountainArray(int target, MountainArray A) {
    /*
       A.length >= 3
       i with 0 < i < A.length - 1 such that:

        A[0] < A[1] < ... A[i-1] < A[i]
        A[i] > A[i+1] > ... > A[A.length - 1]


    3 <= mountain_arr.length() <= 10000
    0 <= target <= 10^9
    0 <= mountain_arr.get(index) <= 10^9

    */

    /*
    Idea:
    no '=', so this means it is strictly monotonic ascending or descending
    with binary search find the peak can be abstract out as a private method
    the left 2 binary search can also be abstracted out as a private method with a
    boolean parameter isAscending
    */

    int N = A.length();
    int l = 0, r = N - 1;

    // use values at index mid and mid+1 to check current location is ascending or descending
    // to make sure mid+1 exist so need not use l<=r while condition, instead use l<r which then
    // require r is checked index, r=m, not r=m-1,
    // thus loop stop when l=r-1, at the time r is known not the peak and only
    // l is left so it make sure l is the peak index
    while (l < r) {
      int m = (l + r) / 2;
      int mv = A.get(m);
      int nv = A.get(m + 1);
      // mv==nv never happen in mountain array
      if (mv < nv) // ascending
      l = m + 1;
      else r = m; // r is known index
    }
    int p = l; // peak Index
    if (A.get(p) == target) return p;
    // For getting the minimum index, firstly start left part, then right part
    // check left part. it is ascending
    l = 0;
    r = p - 1; // all are unknown
    while (l <= r) {
      int m = (l + r) >> 1;
      int mv = A.get(m);
      if (mv == target) return m;
      else if (mv > target) r = m - 1;
      else l = m + 1;
    }
    // no found
    // check right part it is descending
    l = p + 1;
    r = N - 1; // all are unknown
    while (l <= r) {
      int m = (l + r) >> 1;
      int mv = A.get(m);
      if (mv == target) return m;
      else if (mv > target) l = m + 1;
      else r = m - 1;
    }
    // not found
    return -1;
  }
}
