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

package greedy;

public class Leetcode1147LongestChunkedPalindromeDecomposition {
  /*

    1 <= text.length <= 1000
    text consists only of lowercase English characters.

    subtexti is a non-empty string.

    split it to k substrings (subtext1, subtext2, ..., subtextk)
    subtext1 + subtext2 + ... + subtextk == text
    subtexti == subtextk - i + 1 for all valid values of i
    (i.e., 1 <= i <= k).

    Return the largest possible value of k.

    Input: text = "ghi abcdef hello adam hello abcdef ghi"
    Output: 7

    Input: text = "merchant"
    Output: 1

    Input: text = "a nt a pre za tep za pre a nt a"
    Output: 11


    Input: text = "aaa"
    Output: 3

    Input: text = "elvto elvto"
    Output: 2

    Input: text = "ab"
    Output: 1
  */
  /*
  Idea: greedy for 'return the largest possible value of k'.
  2 pointers l and r
  greedy: compare 2 substring [l, i] and [ii, r] once find a char same as char at index r
  search char at index r before the index of half which is index of (right) median

  when l is at index half and r is     at index half too, so half itself is a substring, text is odd length
  when l is at index half and r is not at index half, then text is even length.

  O(N) time if take substring comparison as O(1) time, but it is not.
  O(1) space
  */
  public int longestDecomposition(String text) {
    int k = 0, l = 0, r = text.length() - 1, half = text.length() >>> 1;
    while (l < half) {
      int i = l;
      while (true) {
        while (i < half && text.charAt(i) != text.charAt(r)) i++;
        if (i == half) return 2 * k + 1;
        else {
          int ii = r - (i - l);
          if (text.substring(l, i + 1).equals(text.substring(ii, r + 1))) {
            k++;
            l = i + 1;
            r = ii - 1;
            break;
          } else i++; // need a while(true) loop to continue searching
        }
      }
    }
    if (r == half) return 2 * k + 1; // now l == half
    return 2 * k;
  }

  /*
  cons: slow compared with above solution
  pros: need not care the length of string is odd or even
  */
  public int longestDecomposition2(String text) {
    String l = "", r = "";
    int k = 0;
    for (int i = 0; i < text.length(); i++) {
      l = l + text.charAt(i);
      r = text.charAt(text.length() - 1 - i) + r;
      if (l.equals(r)) {
        k++; // calculated by one side substring number
        l = "";
        r = "";
      }
    }
    return k;
  }
}
