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

package dp;

import java.util.List;

/*
Question
 Given a string s and a dictionary of strings wordDict,
 return true if s can be segmented into a space-separated
 sequence of one or more dictionary words.
 Note that the same word in the dictionary may be reused multiple
      times in the segmentation.


    1 <= s.length <= 300
    1 <= wordDict.length <= 1000
    1 <= wordDict[i].length <= 20
    s and wordDict[i] consist of only lowercase English letters.
    All the strings of wordDict are unique.

 */
public class Leetcode139WordBreak {

  /*
  O(M*L*N) time.
  N is the string length,
  M: Directory word number
  L: is max length of word in Directory
  O(N) space
  */
  public boolean wordBreak2(String s, List<String> d) {
    boolean[] dp = new boolean[s.length() + 1]; // dp[j]: status of sub string [0,j)
    dp[0] = true;

    for (int j = 1; j <= s.length(); j++) {
      for (String w : d) {
        int i = j - w.length();
        if (i >= 0 && dp[i] && s.substring(i, j).equals(w)) {
          dp[j] = true;
          break;
        }
      }
    }
    return dp[s.length()];
  }
  // -- with Trie -------------------------------------------------------------
  public static class T {
    boolean isEnd;
    T[] nexts;

    public T() {
      isEnd = false;
      nexts = new T[128];
    }
  }

  public static void addWord(T root, String S) {
    for (int i = 0; i < S.length(); i++) {
      int ci = (int) S.charAt(i);
      if (root.nexts[ci] == null) {
        root.nexts[ci] = new T();
      }
      root = root.nexts[ci];
    }
    root.isEnd = true;
  }
  /*
  Idea
     convert directory to a Tire in O(M*L) time
     dp[i]: true/false for index range [i, Length-1]
     back forward loop string s, because words in Trie is left-right/top-down
     for calculating dp[s.length()-1], need dp[s.length()] be true, which require
     dp length is s.length() + 1.

  O(max{L*N,M*L}) time.
  N is the string length,
  M: Directory word number
  L: is max length of word in Directory
  O(M*L) space
  */
  public boolean wordBreak(String s, List<String> d) {
    // TODO check null
    T root = new T(), node;
    for (String w : d) addWord(root, w); // O(M*L) time

    boolean[] dp = new boolean[s.length() + 1];
    dp[s.length()] = true;

    for (int i = s.length() - 1; i >= 0; i--) {
      T n = root;
      for (int j = i; j < s.length() && n.nexts[(int) s.charAt(j)] != null; j++) {
        n = n.nexts[(int) s.charAt(j)];
        if (n.isEnd && dp[j + 1]) {
          dp[i] = true;
          break;
        }
      }
    }
    return dp[0];
  }
}
