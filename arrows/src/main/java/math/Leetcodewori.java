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

public class Leetcodewori {
  // Refer https://en.wikipedia.org/wiki/Modular_exponentiation#Pseudocode
  // Assume:
  // - (modulus - 1) * (modulus - 1) does not overflow long.MAX_VALUE
  // - b * b does not overflow long.MAX_VALUE
  // - r * b does not overflow long.MAX_VALUE
  // b: 0 < base.
  // e: 0 <= exponent
  // m: 0 < modulus
  long modpow(long b, int e, int m) {
    if (m == 1) return 0;
    if(e==0||b==1) return 1;

    b %= m;

    long r = 1;
    while (e > 0) {
      if ((e & 1) == 1) r = r * b % m;
      e = e >> 1; // so it is O(loge)
      b = b * b % m;
    }
    return r;
  }

  // O（logN)
  // Refer hint 1-4
  public int maxNiceDivisors(int pf) { // is the primeFactors
    /*
    1 <= primeFactors <= 10^9
    */
    int mod = 1000000007;
    // Note
    // 2147483647 = Integer.Max_value
    //           < (1000000007-1)^2=1e18
    //          < Long.Max_value =
    // 9,223,372,036,854,775,807
    int[] st = new int[] {0, 1, 2, 3, 4, 6};
    return pf < 5 ? pf : (int) (modpow(3, pf / 3 - 1, mod) * st[3 + pf % 3] % mod);
  }
}
