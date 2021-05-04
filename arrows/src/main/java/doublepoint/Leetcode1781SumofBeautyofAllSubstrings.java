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

package doublepoint;

public class Leetcode1781SumofBeautyofAllSubstrings {
  // O(N^2)
  public static int beautySum(String s) {
    /*
    The beauty of a string is the difference in frequencies
    between the most frequent and least frequent characters.
       For example, the beauty of "abaacc" is 3 - 1 = 2.
    Given a string s, return the sum of beauty of all of its substrings.
       1 <= s.length <= 500
       s consists of only lowercase English letters.

    Idea:
      1. The substring length >= 3
      2. for all substring with length >=3 reuse
         calculating the frequent of each characters by sliding window
         whose left side is fixed and only the right side keep extending
    */
    int N = s.length();
    int sum = 0;
    for (int i = 0; i < N; i++) {
      // only lowercase English letters.
      int[] fre = new int[26];
      // 1 <= s.length <= 500;
      int[] count = new int[500]; // count[f] is the number of char with same frequency number f
      count[0] = 26; // initial value when window has not any char
      int min = 1, max = 1;
      for (int j = i; j < N; j++) {
        int n = s.charAt(j) - 'a';
        // before add current char frequency
        count[fre[n]]--;
        if (count[fre[n]] == 0 && min == fre[n]) min = fre[n] + 1;
        // add
        fre[n]++;
        // after add
        count[fre[n]]++;
        if (fre[n] > max) max = fre[n];
        if (fre[n] == 1 && min != 1) min = 1;
        //
        sum += max - min;
      }
    }
    return sum;
  }
}
