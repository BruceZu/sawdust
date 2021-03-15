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

public class Leetcode1053PreviousPermutationWithOneSwap {
  /*
  ' positive integers arr (not necessarily distinct),
    return the lexicographically largest permutation that
    is smaller than arr, that can be made with exactly one swap'
  So need to know 4 rule:
   - swap only happen when arr[i] > arr[j], j>i
   - from right to check
   - swap arr[i] with the max{arr[j]} where j>i and arr[j]< arr[i]
   - if there are more Indices matching rule 3th, then use the one with smaller index
  */
  public static int[] prevPermOpt1(int[] arr) {
    /*
       1 <= arr.length <= 104
       1 <= arr[i] <= 104

    */

    int i = arr.length - 1;
    // O(N) time
    while (i - 1 >= 0 && arr[i - 1] <= arr[i]) i--;
    if (i == 0) return arr; // ' If it cannot be done, then return the same array.'
    int j = i, target = j;
    // O(N) time
    while (j < arr.length && arr[j] < arr[i - 1]) {
      if (arr[j] != arr[j - 1]) target = j;
      j++;
    }
    int x = arr[i - 1] ^ arr[target];
    arr[i - 1] = x ^ arr[i - 1];
    arr[target] = x ^ arr[target];
    return arr;
  }

  public static void main(String[] args) {
    System.out.println(Arrays.toString(prevPermOpt1(new int[] {3, 1, 1, 3})));
    System.out.println(Arrays.toString(prevPermOpt1(new int[] {3, 2, 1})));
  }
}
