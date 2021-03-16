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

package string;

public class Leetcode1790CheckifOneStringSwapCanMakeStringsEqual {
  public boolean areAlmostEqual(String s1, String s2) {
    /*
     Return true if it is possible to make both strings equal by
     performing at most one string swap on exactly one of the strings.
     Otherwise, return false.

    1 <= s1.length, s2.length <= 100
    s1.length == s2.length
    s1 and s2 consist of only lowercase English letters.
    */
    int N = s1.length();
    int pre = -1;
    int diffNum = 0;
    for (int i = 0; i < N; i++) {
      if (s1.charAt(i) != s2.charAt(i)) {
        if (diffNum == 0) {
          pre = i;
        } else if (diffNum == 1) {
          if (s1.charAt(pre) != s2.charAt(i) || s2.charAt(pre) != s1.charAt(i)) return false;
        } else if (diffNum == 2) {
          return false;
        }
        diffNum++;
      }
    }
    return diffNum == 0 || diffNum == 2;
  }
}
