//  Copyright 2017 The keepTry Open Source Project
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

public class LeetCode50Power {
  /*
      -100.0 < x < 100.0
      -2^31 <= n <= 2^31-1 (Note!)
      -104 <= x^n <= 104
  */
  /*
  Test cases:
    b=0;
    p=0;
    b=1;
    p=1;
    b is negative integer
    p is Integer.MIN_VALUE
  */

  // O( $log^N$) time and O(1) space
  // https://en.wikipedia.org/wiki/Exponentiation_by_squaring
  public double pow(double b, int n) {
    long p = n; // why? reason is `p = -p;`
    if (b == 0) return 0;
    if (p == 0) return 1;
    if (b == 1) return 1;

    if (p < 0) {
      b = 1 / b;
      p = -p;
      /*
      In Java for the Integer type the Integer.MIN_VALUE = -2147483648
       -(-2147483648) is still -2147483648.
      Because Integer.MAX_VALUE is 2147483647
      2147483648=2147483647+1 is -2147483648 not 2147483648
      `p = -p; ` does not apply to Integer.MIN_VALUE
      this is why `long p=n` at start;
      */

    }
    double r = 1;
    while (p >= 1) {
      if ((p & 1) == 1) r *= b;
      b *= b;
      p >>>= 1;
    }
    return r;
  }
  // recursion
  // O(logN) time and space
  public static double myPow(double x, int n) {
    if (x == 0) return 0;
    if (n == 0) return 1;
    if (n < 0) {
      x = 1 / x;
      n = -n;
    }
    // must use >>> not >> thus
    // Integer.MIN_VALUE>>1 is -1073741824
    // Integer.MIN_VALUE>>>1 is 1073741824 it is same result of
    // 2147483648/2
    double r = myPow(x, n >>> 1);
    r *= r;
    if ((n & 1) == 1) r *= x;
    return r;
  }
}
