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

import java.util.Arrays;

public class Leetcode238ProductOfArrayExceptSelf {
  /*
    O(N）time, O(N） space
   */
  public int[] productExceptSelf(int[] A) {
    if (A == null) return null;
    int N = A.length;

    int[] a = new int[A.length];
    Arrays.fill(a, 1);
    int l = 1, r = 1;

    for (int i = 0; i < N; i++) {
      a[i] *= l; // note it is *= not =
      l *= A[i];

      a[N - 1 - i] *= r;
      r *= A[N - 1 - i];
    }
    return a;
  }
}
