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

public class Leetcode1103DistributeCandiestoPeople {
  public int[] distributeCandies(int c, int p) {
    int[] r = new int[p];
    for (int i = 0; c > 0; i++) {
      r[i % p] += Math.min(i + 1, c); // +=
      c -= (i + 1);
    }
    return r;
  }
}
