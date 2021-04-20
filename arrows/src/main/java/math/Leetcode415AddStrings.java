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

public class Leetcode415AddStrings {
  public static String addStrings(String num1, String num2) {
    /*
    TODO: corner cases checking

      1 <= num1.length, num2.length <= 104
      num1 and num2 consist of only digits.
      num1 and num2 don't have any leading zeros except for the zero itself.
      */

    int i = num1.length() - 1, j = num2.length() - 1, carry = 0; // Note: start from str length-1.
    StringBuilder r = new StringBuilder();
    // O(N) N is the length of longer string
    while (i >= 0 || j >= 0 || carry != 0) { // note: also need check carry value. it is 1 or 0
      if (i >= 0) carry += Integer.valueOf(num1.charAt(i--) - '0'); // note: the i-- and j--
      if (j >= 0) carry += Integer.valueOf(num2.charAt(j--) - '0'); // note: need -'0'
      r.append("" + carry % 10);
      carry /= 10;
    }
    return r.reverse().toString();
  }
}
