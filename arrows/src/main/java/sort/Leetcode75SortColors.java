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

package sort;

public class Leetcode75SortColors {
  /*
   Idea:
    -  l: next insert index for color 0,
       r: is next insert index for color 2,
       i: is current index, after processing it is color 1.
    -  stop when i > r
   O(N）Time, O(1) space
  */
  public void sortColors(int[] A) {
    int l = 0, r = A.length - 1, i = 0;
    while (i <= r) { // stop when i>r
      if (A[i] == 0) {
        swap(A, i++, l++);
      } else if (A[i] == 2) {
        swap(A, i, r--); // Note: r--, i stay here
      } else {
        i++;
      }
    }
  }

  private void swap(int[] A, int i, int j) {
    if (A[i] != A[j]) {
      int tmp = A[i] ^ A[j];
      A[j] = tmp ^ A[j];
      A[i] = tmp ^ A[i];
    }
  }
}
