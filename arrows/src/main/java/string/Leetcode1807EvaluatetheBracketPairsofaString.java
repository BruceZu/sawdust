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
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Leetcode1807EvaluatetheBracketPairsofaString {
  /*
  Assume:
  -1 with each pair containing a non-empty key.
  -2 key may not have value in the knowledge
  -3 s consists of lowercase English letters and round brackets '(' and ')'.
  -4 Every open bracket '(' in s will have a corresponding close bracket ')'.
  -5 There will not be any nested bracket pairs in s.
  -6 keyi and valuei consist of lowercase English letters.
  -7 Each keyi in knowledge is unique.

   1 <= s.length <= 105
   0 <= knowledge.length <= 105
   knowledge[i].length == 2
   1 <= keyi.length, valuei.length <= 10

   Followup:
   -  complementary character
   -  nested brackets
   -  Each keyi in knowledge is not unique.
   -  Every open bracket '(' in s will have a corresponding close bracket ')'.

   Note:
   - "(" and ")" need escape
   -
  */

  // O（ma{M,N}) M is the length of s and N is the total length of knowledge keys
  public static String evaluate2(String s, List<List<String>> knowledge) {
    Map<String, String> d =
        knowledge.stream().collect(Collectors.toMap(l -> l.get(0), l -> l.get(1)));
    StringBuilder r = new StringBuilder();
    String[] splits = s.split("\\(");
    r.append(splits[0]);
    for (int i = 1; i < splits.length; i++) {
      String[] oneOrtwo = splits[i].split("\\)");
      // Note: split result may have only the left part
      r.append(d.getOrDefault(oneOrtwo[0], "?")).append(oneOrtwo.length == 2 ? oneOrtwo[1] : "");
    }
    return r.toString();
  }

  // O（ma{M,N}) M is the length of s and N is the total length of knowledge keys
  public static String evaluate(String s, List<List<String>> knowledge) {
    Map<String, String> d =
        knowledge.stream().collect(Collectors.toMap(l -> l.get(0), l -> l.get(1)));
    StringBuilder r = new StringBuilder();
    StringBuilder key = new StringBuilder();
    boolean rr = true;
    for (int i = 0; i < s.length(); i++) {
      char c = s.charAt(i);
      if (c == '(') {
        rr = false;
        continue;
      } else if (c == ')') {
        r.append(d.getOrDefault(key.toString(), "?"));
        key = new StringBuilder();
        rr = true;
        continue;
      }
      if (rr) {
        r.append(c);
      } else {
        key.append(c);
      }
    }
    return r.toString();
  }
}
