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

package doublepoint;

public class Leetcode42TrappingRainWater {
  public int trap(int[] height) {
    /*
    n == height.length
    0 <= n <= 3 * 10^4
    0 <= height[i] <= 10^5
    */
    // TODO check corner cases

    // l and r are no calculated index so l==r still need calculate
    int l = 0, r = height.length - 1, result = 0;
    // lh: left hight in [0, i-1], rh: right height in [r+1, length-1]
    int lh = 0, rh = 0;
    while (l <= r) { // l and r are no calculated index so l==r still need calculate
      /*
      update lh or rh, update result, one step ahead/back
      O(N) time O(1) space
      */
      if (lh < rh) {
        lh = Math.max(lh, height[l]);
        result += lh - height[l];
        l++;
      } else {
        rh = Math.max(rh, height[r]);
        result += rh - height[r];
        r--;
      }
    }
    return result;
  }
}
