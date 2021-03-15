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

public class Leetcode1780CheckifNumberisaSumofPowersofThree {
  public static boolean checkPowersOfThree(int n) {
    /*
    '1 <= n <= 10^7'
    'Given an integer n, return true if it is possible to represent
     n as the sum of distinct powers of three. Otherwise, return false.'

    Idea:
     note the 'distinct'. 'powers of three'
     Just assume answer is true then the right behavior will be no 2 appearing
     to represent n in a 3 based number.

     ..., 27, 9, 3, 1
     ----------------
     ...,  2, 2, 2, 2
     ...,  1, 1, 1, 1
     ...,  0, 0, 0, 0

     for any 3^i there is only 0 or 1 options
     from left to right to check
     - 0 : means it should be   0 <= number <     3^i
     - 1 : means it should be 3^i <= number < 2 * 3^i
     */

    int N = (int) (Math.log(n) / Math.log(3));
    // O(log_3^n)
    for (int i = N; i >= 0; i--) {
      int p3i = (int) Math.pow(3, i);
      if (n >= p3i) {
        n -= p3i;
        if (n > p3i) return false;
      }
    }
    return n == 0;
  }

  public static void main(String[] args) {
    System.out.println(checkPowersOfThree(12));
  }
}
