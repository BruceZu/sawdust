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

import java.util.*;

public class Leetcode249GroupShiftedStrings {

  public List<List<String>> groupStrings(String[] strings) {
    /*
     1 <= strings.length <= 200
     1 <= strings[i].length <= 50
     strings[i] consists of lowercase English letters. (Note)
    */
    // O(T) time and space T is all character's number in strings
    Map<String, List<String>> map = new HashMap<>();
    for (String s : strings) map.computeIfAbsent(shift(s), k -> new LinkedList<>()).add(s);
    return new ArrayList<>(map.values());
  }

  private String shift(String s) { // shift s to be one starting with `a`
    if (s.charAt(0) == 'a') return s;
    int d1 = s.charAt(0) - 'a';
    StringBuilder sb = new StringBuilder();

    for (int i = 0; i < s.length(); i++) {
      int d = s.charAt(i) - 'a';
      sb.append((char) ('a' + (d >= d1 ? d - d1 : d + (26 - d1)))); // Note it must be char type
    }
    return sb.toString();
  }
}
