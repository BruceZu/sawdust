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

import java.util.ArrayList;
import java.util.List;

public class Leetcode1156SwapForLongestRepeatedCharacterSubstring {
  /*
  swap two of the characters in the string.
  Find the length of the longest substring with repeated characters.

  1 <= text.length <= 20000
  text consist of lowercase English characters only.

  Input: text = "abcdef"
  Output: 1

  Input: text = "aaaaa"
  Output: 5

  Input: text = "aaabbaaa"
  Output: 4

  Input: text = "aaabaaa"
  Output: 6

  Input: text = "ababa"
  Output: 3

  Input: text = "aabaabwreaaa"
  Output: 5

  Input: text ="abbaa"
  Output: 3

  Input: text = "aabaaabaaaba"
  Output: 7

  Input: text = "bbababaaaa"
  Output: 6
  */
  /*
  look the string text as a string composed by groups, each group has 1 or more same character(s)
  for current group g which contains char x:
  - if (a) existing a group g2 right to g1 which is right to current g: g, g1, g2. And
       (b) the index of last char x of g + 2 =the index of the first char of g2, or g1 is one char group. And
       (c) g and g2 has the same char x.
       swap two of the characters: one is char of g1, the other depends on the count of char x in string
       then got a length of substring with repeated characters of x
        -    count of g + count of g2 +1
        - or count of g + count of g2

  - else:
      there is not existing g2, in other word: g or g1 is the last group of string: "[..,g,g1]", or "[....g]"
      there is g2 but g1 is not one char:  ...g1,[bb],g2...
      there is g2 but g2 has not the same char with g: [...xx,b,yy,...]
      then:
       swap two of the characters: one is the first char of g1, the other depends on the count of char x in string
       then got a length of substring with repeated characters of x
        -    count of g +1
        - or count of g

  O(n) time and space
  */
  public static int maxRepOpt1(String s) {
    int N = s.length(), a = 1;
    int[] c = new int[26];
    for (int i = 0; i < N; i++) {
      int idx = s.charAt(i) - 'a';
      c[idx]++;
      a = Math.max(a, c[idx]);
    }
    if (a == 1) return 1;
    a = 1;
    List<int[]> gs = new ArrayList(N);
    int l = 0;
    while (l < N) {
      int r = l;
      char lc = s.charAt(l);
      while (r < N && s.charAt(r) == lc) r++;
      gs.add(new int[] {l, r - 1});
      l = r;
    }
    for (int i = 0; i < gs.size(); i++) {
      int[] g = gs.get(i);
      int n = g[1] - g[0] + 1;
      char ch = s.charAt(g[0]);
      if (i + 2 < gs.size() && g[1] + 2 == gs.get(i + 2)[0] && ch == s.charAt(gs.get(i + 2)[0])) {
        int[] g2 = gs.get(i + 2);
        int both = n + g2[1] - g2[0] + 1;
        a = Math.max(a, both + (c[ch - 'a'] > both ? 1 : 0));

      } else a = Math.max(a, n + (c[ch - 'a'] > n ? 1 : 0));
    }
    return a;
  }
  /*
   sliding window to detect the longest substring (with up to one different character).
   O(N) time, right pointer at most 4*N operation.
   O(1) space
  */
  public static int maxRepOpt1_(String s) {
    int N = s.length();
    int[] c = new int[26]; //  text consist of lowercase English characters only.
    for (int i = 0; i < N; i++) c[s.charAt(i) - 'a']++;
    int l = 0, a = 1, idx = -1;
    while (l < N) {
      int r = l;
      while (true) {
        while (r + 1 < N && s.charAt(r) == s.charAt(r + 1)) r++;
        if (idx == -1 && r + 2 < N && s.charAt(r) == s.charAt(r + 2)) {
          idx = r + 1;
          r = r + 2;
          continue;
        }
        int n = r - l + 1;
        if (idx != -1) {
          if (c[s.charAt(r) - 'a'] >= n) a = Math.max(a, n);
          else a = Math.max(a, n - 1);
          l = idx; // crucial part
          idx = -1;
        } else {
          if (c[s.charAt(r) - 'a'] > n) a = Math.max(a, n + 1);
          else a = Math.max(a, n);
          l = r + 1;
        }
        break;
      }
    }
    return a;
  }
  // improve sliding window idea by merging 2 scenarios into one
  public static int maxRepOpt1__(String s) {
    int N = s.length(), a = 1;
    int[] c = new int[26];
    for (int i = 0; i < N; i++) {
      int idx = s.charAt(i) - 'a';
      c[idx]++;
      a = Math.max(a, c[idx]);
    }
    if (a == 1) return 1;
    a = 1;
    int l = 0;
    while (l < N) {
      int r = l, sep = -1;
      char lc = s.charAt(l);
      while (r < N && s.charAt(r) == lc) r++;
      sep = r;
      r++;
      while (r < N && s.charAt(r) == lc) r++;
      int n = r - l - 1;
      a = Math.max(a, c[lc - 'a'] > n ? n + 1 : n);
      l = sep;
    }
    return a;
  }
}
