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

package tree.trie;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Leetcode211AddandSearchWordDatastructuredesign {
  /*
   `word may contain dots '.' where dots can be matched with any letter.`

  1 <= word.length <= 500
  word in addWord consists lower-case English letters.
  word in search consist of  '.' or lower-case English letters.
  At most 50000 calls will be made to addWord and search.
   */

  /*
  Test cases:
    defend cases:
    set null    get ""
    set 'abc'   get null
    set "亚洲"   get "ABC"
    set "ABC"   get "亚洲"

    general:
      set ""      get "","a"
      set "...."  get "abcd"
   */

  /* -------------------------------------------------------------------------
  Idea:
    `word may contain dots '.' where dots can be matched with any letter.`
    This means HaskMap does not work.
    with `Map<Integer, Set<String>> `:
      cons:  hash collision, space
  */
  class WordDictionaryIntuitive {
    Map<Integer, Set<String>> d;

    public WordDictionaryIntuitive() {
      d = new HashMap(); // did not consider hash collision
    }

    public void addWord(String w) {
      d.computeIfAbsent(w.length(), k -> new HashSet<>()).add(w);
    }

    public boolean search(String word) {
      int l = word.length();
      if (d.containsKey(l)) {
        for (String w : d.get(l)) { // O(N) N is the number of words
          int i = 0;
          while (i < l && (w.charAt(i) == word.charAt(i) || word.charAt(i) == '.')) i++; // O(l)
          if (i == l) return true;
        }
      }
      return false;
    }
  }
  /* -------------------------------------------------------------------------
   Trie implement
     Trie could use less space compared to hashmap.
   keep only child value via map<child value, node> variable,
      itself value is keep in parent map variable
  */

  class TNode<T> {
    boolean isWordEnd = false;
    Map<T, TNode> map = new HashMap();

    // alternative of search(String word, TNode<Character> n)
    public boolean find(String w) {
      if (w.isEmpty()) return isWordEnd;
      char c = w.charAt(0);
      if (c != '.')
        if (map.containsKey(c)) {
          return map.get(c).find(w.substring(1));
        } else return false;
      // current char is '.'
      for (TNode<T> next : map.values()) {
        boolean found = next.find(w.substring(1));
        if (found) return true;
      }
      return false;
    }
  }

  /** Initialize your data structure here. */
  class WordDictionary {
    TNode root;

    public WordDictionary() {
      root = new TNode();
    }

    // L is under searched word length
    // O(L) time and space(in worst case)
    // defend cases:
    // TODO: check 'word in addWord consists lower-case English letters.`
    //       check null;
    public void addWord(String word) {
      TNode<Character> n = root;
      for (Character c : word.toCharArray()) {
        n = n.map.computeIfAbsent(c, k -> new TNode());
      }
      n.isWordEnd = true;
    }

    // L is under searched word length
    // O(L) or O(26^L) time for word containing "."
    // O(1) or O(L) space used in recursion stack for word containing ".",
    // defend cases:
    // TODO: check 'word in addWord consists lower-case English letters.`
    //       check null;
    // return search(word, root);
    public boolean search(String word) {
      return root.find(word);
    }

    /*
    `if (c == '.')` is special logic for Leetcode 211
     Need check let all next, not only next, node to check left chars
     Need recursion here.
       - Trie Node can provide a search function: search(string w): stack call
         It is easy to read, but when word does not contain '.' the space complexity
         is not as good as loop function in Directory class
       - current class provide a search function: search(string w, TNode<Character> node)
          -- this also need to know current char index, thus to get left substring before
             call recursion function
     */
    private boolean search(String word, TNode<Character> n) {
      int i = 0; // used to next recursion to get get substring
      for (Character c : word.toCharArray()) {
        if (n.map.containsKey(c)) {
          n = n.map.get(c);
        } else {
          if (c == '.') {
            for (TNode next : n.map.values()) {
              boolean found = search(word.substring(i + 1), next);
              if (found) return true;
            }
          }
          return false;
        }
        i++;
      }
      return n.isWordEnd;
    }
  }
}
