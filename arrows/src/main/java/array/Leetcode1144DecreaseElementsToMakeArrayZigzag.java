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

package array;

public class Leetcode1144DecreaseElementsToMakeArrayZigzag {
  /*
   a move consists of choosing any element and decreasing it by 1.
   Note: only decreasing
   left, i, right
      2, 1, 2
      2, 1, 0
      0, 1, 2
      1, 2, 1
   O(N) time, O(1) space
  */
  public int movesToMakeZigzag(int[] A) {
    int even = 0, odd = 0, l = 0, r = 0;
    for (int i = 0; i < A.length; i++) {
      l = i == 0 ? Integer.MAX_VALUE : A[i - 1];
      r = i == A.length - 1 ? Integer.MAX_VALUE : A[i + 1];
      int m = Math.max(0, A[i] - Math.min(l, r) + 1);
      if ((i & 1) == 1) odd += m;
      else even += m;
    }
    return Math.min(odd, even);
  }
}
