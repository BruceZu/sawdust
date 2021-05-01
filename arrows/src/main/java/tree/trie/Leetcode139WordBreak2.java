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

import java.util.Arrays;
import java.util.List;

public class Leetcode139WordBreak2 {
    static public class TrieNode {
        boolean hasDicWordEndHere;
        TrieNode[] nextChars;

        public TrieNode() {
            hasDicWordEndHere = false;
            nextChars = new TrieNode[128];
        }
    }

    static public void addWord(TrieNode root, String S) {
        for (int i = 0; i < S.length(); i++) {
            int ichar = (int) S.charAt(i);
            if (root.nextChars[ichar] == null) {
                root.nextChars[ichar] = new TrieNode();
            }
            root = root.nextChars[ichar];
        }
        root.hasDicWordEndHere = true;
    }

    static public boolean wordBreak(String s, List<String> wordDict) {
        TrieNode root = new TrieNode(), node;
        for (String dic : wordDict) {
            addWord(root, dic);
        }

        char[] chars = s.toCharArray();
        boolean[] isAbleToBreakStrTo = new boolean[chars.length + 1];
        isAbleToBreakStrTo[chars.length] = true;

        for (int from = chars.length - 1; from >= 0; from--) {
            node = root;
            for (int j = from; node != null && j < chars.length; j++) {
                node = node.nextChars[(int) chars[j]];
                if (node != null && node.hasDicWordEndHere && isAbleToBreakStrTo[j + 1]) {
                    isAbleToBreakStrTo[from] = true;
                    break;
                }
            }
        }
        return isAbleToBreakStrTo[0];
    }

    public static void main(String[] args) {
        wordBreak("aaaaaaa", Arrays.asList("aaaa", "aa"));
    }

}
