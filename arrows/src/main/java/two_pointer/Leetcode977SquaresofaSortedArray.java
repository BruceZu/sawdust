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

package two_pointer;

public class Leetcode977SquaresofaSortedArray {
  // O(N) time and space
  public int[] sortedSquares(int[] A) {
    int N = A.length;
    int[] a = new int[N];
    int l = 0, r = N - 1;
    int i = N - 1;
    while (i >= 0) {
      int v;
      if (Math.abs(A[l]) < Math.abs(A[r])) {
        v = A[r];
        r--;
      } else {
        v = A[l];
        l++;
      }
      a[i--] = v * v;
    }
    return a;
  }
}
