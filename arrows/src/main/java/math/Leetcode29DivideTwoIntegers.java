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

public class Leetcode29DivideTwoIntegers {
  /*
    "without using multiplication, division, and mod operator.
    Assume we are dealing with an environment that could only store
    integers within the 32-bit signed integer range: [−2^31, 2^31 − 1]"
    So
     - do not use long type and do not use overflow
     - do not use abs which also cause overflow

    "-2^31 <= dividend, divisor <= 2^31 - 1
    divisor != 0"
  */

  /*
   test cases:
   defend:
   1.  divisor: 0
   special:
   1.  one of dividend and divisor is positive, another is negative
   2.  dividend is 0
   3.  -2^31/-1
   4.  -2^31/1,
   5.  -1/-2^31
   general: 3/9,  9/7.2
  */

  /*
  Idea:
  1 check result division/quotient sign
  2 process special cases: dividend or divisor is 0
  3 process Integer.MIN_VALUE
    - divisor is Integer.MIN_VALUE which is always is negative.
    - dividend is Integer.MIN_VALUE:
         consider the value of divisor:  = or > Integer.MIN_VALUE
         return MAX when the divisor is -1 (special case and special requirement).
         return MIN when the divisor is 1 (special case, but the following algorithm
                                          can handle it. The cumulative time is kept
                                          in an int type variable which can keep -2^31.
                                          MAX+1 is MIN and -MIN is still MIN, but this
                                          logic is not intuitive)
         Now the times variable `r`  can work, Algorithm is just:
          - let divisor be a negative number if it is not.
          - cumulate divisor one by one when divisor is a negative number dividend< divisor.
            else check if it == dividend
            the valid times' value.
  4 left cases where dividend or divisor is not Integer.MIN_VALUE, and it is possible
    to convert both values to be negative, thus reuse the above code and logic

  O(dividend/divisor) time and O(1) space
  Improvement:
     - cumulate divisor one by one 'cum=cum+divisor` => cum+=cum
       O(O(logd)*O(log_dr^d)) time and O(1) space, dr is Divisor, d is dividend
     - avoid duplicated calculation of cumulated dr and times by step back
       from the reached largest value of them with binary wise operation/Bit-Shifting
       O(log_dr^d)) time and O(1) space.

  */
  public int divideWithComment(int dividend, int divisor) {
    int d = dividend, dr = divisor;
    boolean isNegtive = false;
    if ((d >>> 31) + (dr >>> 31) == 1) isNegtive = true;
    // 1. 0
    if (dr == 0) return Integer.MAX_VALUE; // 'divisor != 0'
    if (d == 0) return 0; // covered
    // 2. dividend is Integer.MIN_VALUE and divisor is -1, returns 2^31 − 1
    if (dr == Integer.MIN_VALUE) return d == Integer.MIN_VALUE ? 1 : 0; // covered
    if (d == Integer.MIN_VALUE && dr == -1) return Integer.MAX_VALUE;
    if (d == Integer.MIN_VALUE && dr == 1) return Integer.MIN_VALUE; // covered  but overflow

    // 3. convert dividend and divisor to be negative number.
    //    Integer.MIN_VALUE which is always negative
    d = d > 0 ? -d : d;
    dr = dr > 0 ? -dr : dr;
    if (d == dr) return isNegtive ? -1 : 1;
    if (dr < d) return 0;
    // d < dr
    int r = 0; // division/quotient
    int t = 1, c = dr; // times and value of cumulated dr
    while (d <= (c << 1) && (c << 1) < 0) {
      // both are negative, note if d==dr still need run once e.g. d=-3, dr=-1
      t <<= 1; //   O(logN)
      c <<= 1;
    }
    while (d <= dr) { // both are negative.  O(logN)
      r += -t; // use negative value thus r can cover MIN
      d = d - c;
      while (c < d && d <= dr) {
        t >>>= 1;
        c >>= 1; // do not use >>>, instead use >> to take the sign
      }
    }
    return isNegtive ? r : -r;
  }

  public int divideNoComment(int d, int dr) {
    boolean isNegtive = false;
    if ((d >>> 31) + (dr >>> 31) == 1) isNegtive = true;
    if (dr == 0) return Integer.MAX_VALUE;
    if (d == Integer.MIN_VALUE && dr == -1) return Integer.MAX_VALUE;
    d = d > 0 ? -d : d;
    dr = dr > 0 ? -dr : dr;
    if (d == dr) return isNegtive ? -1 : 1;
    if (dr < d) return 0;
    int r = 0;
    int t = 1, c = dr;
    while (d <= (c << 1) && (c << 1) < 0) {
      t <<= 1;
      c <<= 1;
    }
    while (d <= dr) { // both are negative.  O(logN)
      if (d <= c) {
        r += -t; // use negative value thus r can cover MIN
        d = d - c;
      }
      t >>>= 1;
      c >>= 1; // do not use >>>, instead use >> to take the sign as c is negative
    }
    return isNegtive ? r : -r;
  }

  /*
  Idea:
      Binary Long Division
       figure out the first position of dr to align with d and start the binary long division.
       and shift divisor to the right until can't shift it any further
       So also need to know the right time to stop the binary long division.
      O(log_dr^d)) time and O(1) space. dr is Divisor, d is dividend
  */

  public static int divide(int d, int dr) {
    // Special cases: overflow.
    if (d == Integer.MIN_VALUE && dr == -1) return Integer.MAX_VALUE;
    if (d == Integer.MIN_VALUE && dr == 1) return Integer.MIN_VALUE;
    boolean isNegtive = false;
    if ((d >>> 31) + (dr >>> 31) == 1) isNegtive = true;
    if (d > 0) d = -d;
    if (dr > 0) dr = -dr;

    int from = 0;
    while (d <= (dr << 1) && (dr << 1) < 0) {
      from += 1;
      dr += dr;
    }
    int r = 0;
    for (int m = from; m >= 0; m--) {
      if (d <= dr) {
        r += -(1 << m);
        d -= dr;
      }
      dr >>= 1;
    }
    return isNegtive ? r : -r;
  }
}
