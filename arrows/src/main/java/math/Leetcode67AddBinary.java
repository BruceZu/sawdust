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

public class Leetcode67AddBinary {
  public String addBinary(String a, String b) {
    /*
        1 <= a.length, b.length <= 104 (defend this assumption)
        a and b consist only of '0' or '1' characters.(Note)
        Each string does not contain leading zeros except for the zero itself. (Note)
    */
    // TODO: check corner cases;
    int c = 0; // carry
    int i = a.length() - 1, j = b.length() - 1;
    StringBuilder r = new StringBuilder();
    while (i >= 0 || j >= 0 || c == 1) {
      if (i >= 0) c += a.charAt(i--) - '0';
      if (j >= 0) c += b.charAt(j--) - '0';
      r.append(c % 2);
      c /= 2;
    }
    return r.reverse().toString();
  }
}
