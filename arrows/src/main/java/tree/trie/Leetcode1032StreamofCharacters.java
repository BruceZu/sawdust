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

package tree.trie;

class StreamChecker2 {
  /*
  Implement the StreamChecker class as follows:
  StreamChecker(words): Constructor, init the data structure with the given words.
  query(letter): returns true if and only if for some k >= 1,
  the last k characters queried (in order from oldest to newest,
  including this letter just queried) spell one of the words in the given list.
  */
  class Trie {
    public boolean isWord;
    public Trie[] nexts;

    public Trie() {
      nexts = new Trie[26]; // Note: init it
    }
  }

  private Trie root = new Trie();
  private StringBuilder stream = new StringBuilder();

  private void init(String[] words) {
    for (String w : words) {
      Trie cur = root;
      // Reverse
      // 'Words will only consist of lowercase English letters.'
      // 'Queries will only consist of lowercase English letters.'
      for (int i = w.length() - 1; i >= 0; i--) {
        int idx = w.charAt(i) - 'a';
        if (cur.nexts[idx] == null) cur.nexts[idx] = new Trie();
        cur = cur.nexts[idx];
      }
      cur.isWord = true;
      // End a word
    }
  }

  public StreamChecker2(String[] words) {
    /*
      1 <= words.length <= 2000
      1 <= words[i].length <= 2000
      Words will only consist of lowercase English letters.
      Queries will only consist of lowercase English letters.
      The number of queries is at most 40000.
    */
    init(words);
  }

  public boolean query(char letter) {
    stream.append(letter);
    // The steam can be very long and memory can not hold it
    // updated its length to keep only max(word length) to make it support stream input
    // without end.
    Trie cur = root;
    for (int i = stream.length() - 1; i >= 0; i--) { // Note: do not reverse the steam content.
      int idx = stream.charAt(i) - 'a';
      if (cur.nexts[idx] == null) return false; // So only check max Word Length
      if (cur.nexts[idx].isWord) return true;
      cur = cur.nexts[idx];
    }
    return false; // Never come here
  }
}
// ---------------------------------------------------------------------------------------------------
// no comments version
class StreamChecker {
  class Trie {
    public boolean isWord;
    public Trie[] nexts;

    public Trie() {
      nexts = new Trie[26]; // Note: initial it
    }
  }

  private Trie root = new Trie();
  private StringBuilder stream = new StringBuilder();
  // private int maxWordLength;
  private void init(String[] words) {
    for (String w : words) {
      // maxWordLength=Math.max(w.length(),maxWordLength);
      Trie cur = root;
      for (int i = w.length() - 1; i >= 0; i--) {
        int idx = w.charAt(i) - 'a';
        if (cur.nexts[idx] == null) cur.nexts[idx] = new Trie();
        cur = cur.nexts[idx];
      }
      cur.isWord = true;
    }
  }

  public StreamChecker(String[] words) {
    init(words);
  }

  public boolean query(char letter) {
    stream.append(letter);
    // if (stream.length() > maxWordLength) stream.delete(0, stream.length() - maxWordLength);
    Trie cur = root;
    for (int i = stream.length() - 1; i >= 0; i--) {
      int idx = stream.charAt(i) - 'a';
      if (cur.nexts[idx] == null) return false;
      if (cur.nexts[idx].isWord) return true;
      cur = cur.nexts[idx];
    }
    return false;
  }
}

public class Leetcode1032StreamofCharacters {}
