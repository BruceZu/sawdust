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

public class Leetcode1089DuplicateZeros {
  /*
  duplicate each occurrence of zero, shifting the remaining elements to the right.
  Note that elements beyond the length of the original array are not written.
  Do the above modifications to the input array in place.

  */

  public static void duplicateZeros(int[] arr) {
    /*
    1 <= arr.length <= 10000
    0 <= arr[i] <= 9
    */
    int N = arr.length;
    int j = N - 1; // virtual index of extended array
    for (int i = 0; i < N; i++) {
      if (arr[i] == 0) j++;
    }

    for (int i = N - 1; i >= 0; j--, i--) {
      if (j < N) {
        arr[j] = arr[i];
        if (arr[i] == 0) arr[--j] = 0;
      } else {
        if (arr[i] == 0) {
          --j; // The assumption 'j < arr.length' may be changed now and need save value
          if (j < N) arr[j] = 0; // Note here
        }
      }
    }
  }

  //
  public static void duplicateZeros2(int[] arr) {
    int N = arr.length;
    int j = N - 1; // virtual index of extended array
    for (int i = 0; i < N; i++) {
      if (arr[i] == 0) j++;
    }

    for (int i = N - 1; i >= 0; j--, i--) {
      if (j < N) arr[j] = arr[i];
      if (arr[i] == 0 && --j < N) arr[j] = 0;
    }
  }

  public static void main(String[] args) {
    duplicateZeros(new int[] {8, 4, 5, 0, 0, 0, 0, 7});
    duplicateZeros(new int[] {1, 0, 2, 3, 0, 4, 5, 0});
  }
}
