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

package backtracing;

import java.util.ArrayList;
import java.util.List;

/*
    0 <= digits.length <= 4
    digits[i] is a digit in the range ['2', '9'].


Idea:
 prepare a map: digit-> string
 if use Array as map:  index = digit[i] - '0'

O(L^N) time and space
 N is the length of digits.
 L is  the maximum value length in the hash map, it is 4
*/
public class Leetcode17LetterCombinationsofaPhoneNumber {
  private static String[] map =
      new String[] {"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};

  private List<String> r = new ArrayList<>();
  private String d;

  public List<String> letterCombinations(String digits) {
    // TODO check null
    if (digits.length() == 0) return r;

    d = digits;
    backtrack(0, new StringBuilder());
    return r;
  }

  private void backtrack(int i, StringBuilder a) {
    if (a.length() == d.length()) {
      r.add(a.toString());
      return;
    }

    String possibleLetters = map[d.charAt(i) - '0'];
    for (char c : possibleLetters.toCharArray()) {
      a.append(c);
      backtrack(i + 1, a);
      a.deleteCharAt(a.length() - 1);
    }
  }
}
