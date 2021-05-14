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

package sort;

public class Leetcode1122RelativeSortArray {
  /*
  1 <= arr1.length, arr2.length <= 1000
  0 <= arr1[i], arr2[i] <= 1000
  All the elements of arr2 are distinct.
  Each arr2[i] is in arr1.
  TODO: check
   - arr2: distinct
   - null

  O(N) time and space
   */
  public int[] relativeSortArray(int[] arr1, int[] arr2) {
    int N = arr1.length;
    int[] f = new int[1001];
    for (int i = 0; i < N; i++) f[arr1[i]]++;

    int j = 0;
    int[] r = new int[N];
    for (int i = 0; i < arr2.length; i++) {
      int c = arr2[i];
      int count = f[c];
      while (count-- > 0) r[j++] = c;
      f[arr2[i]] = 0; // will loop again later for left number
    }

    for (int i = 0; i < f.length; i++) { // left number in f not in arr1
      int count = f[i]; // still possible that some left number is duplicated
      while (count-- > 0) r[j++] = i;
    }
    return r;
  }
}
