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

package math.permutation;

public class Leetcode1079LetterTilePossibilities {
  public int numTilePossibilities(String tiles) {
    /*
     1 <= tiles.length <= 7
    */
    /*
    Idea: concern:
    - how to calculate all permutaiton with all kinds of length
    - how to avoid duplicated result
    */
    int[] fre = new int[26];
    for (int i = 0; i < tiles.length(); i++) {
      // "tiles consists of uppercase English letters."
      fre[tiles.charAt(i) - 'A']++;
    }
    return dfs(fre);
  }
  // O(?) time
  private int dfs(int[] fre) {
    int res = 0;
    for (int i = 0; i < fre.length; i++) {
      // each letter use only once for current number position avoid duplicated result
      if (fre[i] == 0) continue;
      res++; // result with current length.
      fre[i]--;
      res += dfs(fre);
      fre[i]++;
    }
    return res;
  }
}
