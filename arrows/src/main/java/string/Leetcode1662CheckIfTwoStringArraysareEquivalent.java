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

public class Leetcode1662CheckIfTwoStringArraysareEquivalent {

  public boolean arrayStringsAreEqual(String[] word1, String[] word2) {
    /*
        1 <= word1.length, word2.length <= 10^3
        1 <= word1[i].length, word2[i].length <= 10^3
        1 <= sum(word1[i].length), sum(word2[i].length) <= 10^3
        word1[i] and word2[i] consist of lowercase letters.
    */
    /*
    Note:
     `consist of lowercase letters.`
     */
    int i = 0, j = 0, I = 0, J = 0;
    while (I < word1.length && J < word2.length) {
      if (word1[I].charAt(i) != word2[J].charAt(j)) return false;
      i++;
      j++;
      i %= word1[I].length(); // Note it is word1[I].length() not I.
      I = i == 0 ? I + 1 : I;
      j %= word2[J].length();
      J = j == 0 ? J + 1 : J;
    }
    return I == word1.length && J == word2.length && i == 0 && j == 0;
  }
}
