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

package binarysearch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Leetcode1044LongestDuplicateSubstring {
  // O(N * logN) time
  public static String longestDupSubstring(String s) {
    /*
     2 <= s.length <= 3 * 104
     s consists of lowercase English letters.
    */
    int N = s.length();
    int M = 8191; // Mersenne prime
    int[] power = new int[N]; //  (base ^ i) % M
    for (int i = 0; i < N; ++i) {
      power[i] = (i == 0) ? 1 : (power[i - 1] * 26) % M;
    }
    int dupLen = -1, start = -1;
    // Binary search
    int l = 1, r = N - 1;
    while (l <= r) {
      int m = r + l >> 1;
      int begin = rollinHash(s, m, power, M);
      if (begin > 0) {
        dupLen = m;
        start = begin;
        l = m + 1;
      } else {
        r = m - 1;
      }
    }
    return start < 0 ? "" : s.substring(start, start + dupLen);
  }

  // return the start index of one duplicate 'len' length substring, else -1
  static int rollinHash(String s, int len, int[] power, int M) {
    int N = s.length(), h = 0;
    for (int i = 0; i < len; ++i) {
      h = (h * 26 + (s.charAt(i) - 'a')) % M;
    }
    List<Integer> inds = new ArrayList<Integer>();
    inds.add(0);

    // hash and related 'len' length substring start indexes
    Map<Integer, List<Integer>> hi = new HashMap<>();
    hi.put(h, inds);

    for (int i = len; i < N; i++) {
      int rmc = s.charAt(i - len) - 'a', addc = s.charAt(i) - 'a';
      // Assume M > base=26 and M > (char-'a')
      h = h + M - rmc * power[len - 1] % M;
      h = (h % M * 26 % M + addc) % M;
      int start = i - len + 1;
      if (!hi.containsKey(h)) {
        inds = new ArrayList();
        inds.add(start);
        hi.put(h, inds);
      } else {
        inds = hi.get(h);
        String iStr = s.substring(start, start + len);
        for (int idx : inds) {
          if (s.substring(idx, idx + len).equals(iStr)) return start;
        }
        inds.add(start);
        hi.put(h, inds);
      }
    }
    return -1;
  }

  public static void main(String[] args) {
    System.out.println(longestDupSubstring("banana"));
  }
}
