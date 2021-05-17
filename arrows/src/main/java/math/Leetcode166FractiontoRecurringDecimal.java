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

import java.util.HashMap;
import java.util.Map;

public class Leetcode166FractiontoRecurringDecimal {
  // n: numerator; d: denominator
  public String fractionToDecimal(int n, int d) {
    if (n == 0) {
      return "0";
    }
    // f: fraction, the result;
    StringBuilder f = new StringBuilder();
    // If either one is negative (not both)
    if (n < 0 ^ d < 0) f.append("-");

    // Convert to Long or else abs(-2147483648) overflows
    // N: dividend; D: divisor; r: remainder
    long N = Math.abs(Long.valueOf(n));
    long D = Math.abs(Long.valueOf(d));
    f.append(String.valueOf(N / D));
    long r = N % D;
    if (r == 0) {
      return f.toString();
    }
    f.append(".");
    // reminder and its index in the result string of fraction
    Map<Long, Integer> map = new HashMap<>();
    while (r != 0) {
      if (map.containsKey(r)) {
        f.insert(map.get(r), "(");
        f.append(")");
        break; // fraction part is infinite circle decimal
      }
      map.put(r, f.length());
      r *= 10;
      f.append(String.valueOf(r / D));
      r %= D;
    }
    return f.toString(); // fraction part is not infinite circle decimal
  }
}
