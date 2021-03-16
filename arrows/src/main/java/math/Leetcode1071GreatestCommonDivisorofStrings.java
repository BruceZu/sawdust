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

import java.math.BigInteger;

public class Leetcode1071GreatestCommonDivisorofStrings {
  // Basic idea
  public String gcdOfStrings1(String str1, String str2) {
    /*
    For two strings s and t, we say "t divides s" if and only if
      s = t + ... + t  (t concatenated with itself 1 or more times)
    Given two strings str1 and str2, return the largest string x
    such that x divides both str1 and str2.

        1 <= str1.length <= 1000
        1 <= str2.length <= 1000
        str1 and str2 consist of English uppercase letters.
     */
    /*
    Idea:
    Let M is str1 length, N is str2 length.
    Greatest Common Divisor gcd(M,N) of two integers M and N is the
    largest integer that divides both M and N.
     M % gcd(M,N) == 0
     N % gcd(M,N) == 0
    If e is any integer which divides M and N, then e divides gcd(M,N)
     M % e == 0
     N % e == 0
     gcd(M,N) % e == 0
    Refer to  https://web.ma.utexas.edu/users/rodin/343K/GCD.pdf
    or <<Algebra Abstract amd Concrete, Stressing Symmetry, 2nd Edition>> by Frederick Goodman

    This means firstly try gcd(M,N) size prefix of str1 ans str2.
    If
         it works it is the answer.
    Else no answer.
         Because assume answer exists and it is e size prefix of str1 ans str2, then
         as gcd(M,N) % e == 0, so gcd(M,N) size prefix of str1 ans str2 should works.
         A contradictory result appears.
     */

    int M = str1.length(), N = str2.length();
    int gcd = BigInteger.valueOf(M).gcd(BigInteger.valueOf(N)).intValue();

    int i = 0, j = 0;
    // O(max{M,N})
    while (i < M || j < N) {
      for (int l = 0; l < gcd; l++) {
        if (i != M && str1.charAt(i + l) != str1.charAt(l)) return "";
        if (j != N && str2.charAt(j + l) != str1.charAt(l)) return "";
      }
      if (i != M) i += gcd;
      if (j != N) j += gcd;
    }

    return str1.substring(0, gcd);
  }
  // O(M+N) make it simple
  public String gcdOfStrings(String str1, String str2) {
    int M = str1.length(), N = str2.length();
    int gcd = BigInteger.valueOf(M).gcd(BigInteger.valueOf(N)).intValue();
    return (str1 + str2).equals(str2 + str1) ? str1.substring(0, gcd) : "";
  }
}
