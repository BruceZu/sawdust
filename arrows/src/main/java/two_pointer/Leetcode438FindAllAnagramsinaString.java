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

package two_pointer;

import java.util.ArrayList;
import java.util.List;

public class Leetcode438FindAllAnagramsinaString {
  public static List<Integer> findAnagrams2(String s, String p) {
    /*
    1 <= s.length, p.length <= 3 * 104
    s and p consist of lowercase English letters.
    TODO: checking cases:
     s: null, "",
     p: null, "",
     s or p  not only has lowercase English letters
    */

    /*
      Idea:
      Note: Rolling hash does not work for this question which only check whether
      the sub-string is target string's anagrams, or it only require the
      sub-string has the same entries of `char : frequency`. The order of
      char in the sub-string does not matter. The target p can be parsed into
      many `char : frequency` entries and kept in a array int[26] or a map.

      When slide the window:
      - for the step in/out char:
        - if it is the char in p then increase/decrease its frequency,
          - if the the char frequency changed in valid scope
            `v= map.get(char)` : 0<->1, ..., v-1<->v
            then valid length `l`  changed one accordingly.
       If the valid length `l=== p.length()` then current window
       is an answer, Because
      - when invalid char step in/out, it does not change the valid length
        value
      - when valid char does not move in/out its valid scope, it does not
        affect the valid length value
      So valid length value is only cumulated by valid char's valid length.
      When the valid length `l=== p.length()` It guarantee there existing
      just required `char : frequency`  entries in the window whose length
      is `l` and no other invalid char in the window.
    O(N) time, O(1) space. N is length of S. space is decided by the number
      of lowercase English letter 26.
     */
    int N = p.length();
    int[] P =
        new int[26]; // in `p`, each char's valid length ,`a` is 97, `z` is 122;`A` is 65, `Z` is 90
    for (int i = 0; i < p.length(); i++) P[p.charAt(i) - 'a']++;
    int l = 0; // valid length of valid char in sliding window
    int[] W = new int[26]; // valid char frequency in current window;
    List<Integer> r = new ArrayList();
    for (int i = 0; i < s.length(); i++) {
      int c = s.charAt(i) - 'a'; // new char in slide window right side
      // before the char step in the sliding window
      if (P[c] != 0) { // valid char
        if (0 <= W[c] && W[c] <= P[c] - 1) l++; // valid char's valid length change before increase
        W[c]++;
      }
      int start = i - N; // step out char
      if (start >= 0) {
        c = s.charAt(start) - 'a';
        if (P[c] != 0) { // valid char
          if (1 <= W[c] && W[c] <= P[c]) l--; // valid char's valid length change before decrease
          W[c]--;
        }
      }
      if (l == N) r.add(start + 1); // Note: start index
    }
    return r;
  }
  // In the above solution W can be saved if express the current window
  // status,valid char length, via counteracting the P
  // to make code concise, all keep invalid char length which will
  // never great than -1. Thus N and l can be replaced with only
  // on variable L meaning required left valid length.
  // Note: Although related code is concise, This does not improve
  // the algorithm time and space complexity
  public static List<Integer> findAnagrams(String s, String p) {
    int[] R = new int[26];
    char[] chars = p.toCharArray();
    for (char c : chars) {
      R[c - 'a']++;
    }
    int L = p.length();
    List<Integer> r = new ArrayList<>();
    for (int i = 0; i < s.length(); i++) {
      int c = s.charAt(i) - 'a';
      if (R[c] >= 1) L--;
      R[c]--; // step in char: always ++
      int start = i - p.length();
      if (start >= 0) {
        c = s.charAt(start) - 'a';
        if (R[c] >= 0) L++;
        R[c]++; // step out char: always --;
      }
      if (L == 0) r.add(start + 1);
    }
    return r;
  }
}
