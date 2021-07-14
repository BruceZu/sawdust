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

package math;

import java.util.Arrays;

public class Leetcode1465MaximumAreaofaPieceofCakeAfterHorizontalandVerticalCuts {
  /*

    2 <= h, w <= 10^9
    1 <= horizontalCuts.length <= min(h - 1, 10^5)
    1 <= verticalCuts.length <= min(w - 1, 10^5)
    1 <= horizontalCuts[i] < h
    1 <= verticalCuts[i] < w
    All the elements in horizontalCuts are distinct.
    All the elements in verticalCuts are distinct.

  Return the maximum area of a piece of cake after you cut at each horizontal and vertical position provided in the arrays horizontalCuts and verticalCuts.
  Since the answer can be a large number, return this modulo 10^9 + 7.

  Input: h = 5, w = 4, horizontalCuts = [1,2,4], verticalCuts = [1,3]
  Output: 4

  Input: h = 5, w = 4, horizontalCuts = [3,1], verticalCuts = [1]
  Output: 6

  Input: h = 5, w = 4, horizontalCuts = [3], verticalCuts = [3]
  Output: 9

  Input: h = 1000000000, w = 1000000000, horizontalCuts = [2], verticalCuts = [2]
  Output: 81

  */
  /*
  Idea:
  use long type for max distance of consecutive elements for horizontal cuts and vertical cuts.
  O(N⋅log(N)+M⋅log(M)) time
  O(1) space
  */
  static int m = 1000000007;

  public static int maxArea(int h, int w, int[] hs, int[] vs) {
    Arrays.sort(hs);
    Arrays.sort(vs);
    long hm = hs[0], vm = vs[0];

    for (int i = 1; i < hs.length; i++) hm = Math.max(hm, hs[i] - hs[i - 1]);
    hm = Math.max(hm, h - hs[hs.length - 1]);

    for (int i = 1; i < vs.length; i++) vm = Math.max(vm, vs[i] - vs[i - 1]);
    vm = Math.max(vm, w - vs[vs.length - 1]);
    return (int) ((vm % m * (hm % m)) % m);
  }
}
