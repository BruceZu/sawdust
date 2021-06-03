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

import java.util.Arrays;

public class Leetcode1838FrequencyoftheMostFrequentElement {
  /*
   No smart solution:
   Just sort it and try from one side.
   Here is from the last element of the sorted A[]
   with l and r pointer and try to use the K.
   O(1) space
  */
  public int maxFrequency(int[] A, int k) {
    int a = 1; // at lest 1
    Arrays.sort(A); // O(NlogN) time
    // O(N) time
    int r = A.length - 1, l = r;
    for (; r > 0; r--) {
      for (; l >= 0 && k >= A[r] - A[l]; --l) k -= A[r] - A[l];
      a = Math.max(a, r - l);
      k += (A[r] - A[r - 1]) * (r - l - 1);
    }
    return a;
  }
}
