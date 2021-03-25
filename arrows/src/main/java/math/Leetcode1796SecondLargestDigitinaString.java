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

public class Leetcode1796SecondLargestDigitinaString {
  public int secondHighest(String s) {
    /*
    1 <= s.length <= 500
    s consists of only lowercase English letters and/or digits.
     */
    /*
    Idea:
     - Character.isDigit(c)
     - assume max always > second, or second should always < max
     */
    int max = -1, scend = -1;
    for (char c : s.toCharArray()) {
      if (Character.isDigit(c)) {
        int num = c - '0';
        if (num > max) {
          scend = max;
          max = c - '0';
        } else if (num > scend && num < max) { // second should always < max
          // assume max always > second
          scend = num;
        }
      }
    }
    return scend;
  }
}
