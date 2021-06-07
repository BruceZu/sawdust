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

package stack;

public class Leetcode921MinimumAddtoMakeParenthesesValid {
  /*
   Given a parentheses string, return the minimum
    number of parentheses we must add to make the resulting string valid.
   s.length <= 1000
   s only consists of '(' and ')' characters.

   Idea:
     A string is valid if number of '(' and ')' are same
     and during the calculating process, number of '(' - number of ')'
     has non-negative value;
  */
  public int minAddToMakeValid(String S) {
    int r = 0, l = 0;  // r: redundant ), l: redundant (
    for (int i = 0; i < S.length(); ++i) {
      l += S.charAt(i) == '(' ? 1 : -1;
      // It is guaranteed bal >= -1
      if (l == -1) {
        r++; //
        l++; //  back to balanced 0
      }
    }

    return l + r;
  }
}
