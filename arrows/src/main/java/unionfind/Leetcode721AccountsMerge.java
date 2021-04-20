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

package unionfind;

import java.util.*;

public class Leetcode721AccountsMerge {
  /*

   1 <= accounts.length <= 1000
   2 <= accounts[i].length <= 10
   1 <= accounts[i][j] <= 30
   accounts[i][0] consists of English letters.
   accounts[i][j] (for j > 0) is a valid email.

  */

  /*
  Note:
  1. Current account may union a few previous distinguish accounts together by
     having their email(s).
  2. Do not put the name into connection between email and account ID
  */

  // Time and Space: O(AlogA) if with rank.
  // A=sum(ai), and ai is the length of accounts[i]
  public List<List<String>> accountsMerge(List<List<String>> accounts) {
    Map<String, Integer> con = new HashMap<>();
    Map<Integer, Integer> union = new HashMap<>();

    int id = 0; // distinguish accounts with same name
    Map<Integer, String> IdToName = new HashMap<>();
    for (List<String> list : accounts) {
      String name = "";
      for (String e : list) {
        if (name == "") {
          name = e;
          union.put(id, id);
          continue;
        }
        if (con.containsKey(e)) union(id, con.get(e), union); // union
        con.put(e, id);
      }

      IdToName.put(id, name);
      id++;
    }
    // distinguish accounts
    Map<Integer, List<String>> da = new HashMap<>();
    for (Map.Entry<String, Integer> c : con.entrySet()) {
      da.computeIfAbsent(find(c.getValue(), union), i -> new ArrayList<>()).add(c.getKey());
    }
    List<List<String>> re = new ArrayList<>();
    for (Map.Entry<Integer, List<String>> a : da.entrySet()) {
      List<String> emails = a.getValue();
      Collections.sort(emails); // O(AlogA)
      emails.add(0, IdToName.get(a.getKey()));
      re.add(emails);
    }
    return re;
  }

  private int find(int cur, Map<Integer, Integer> union) {
    if (union.get(cur) == cur) return cur;
    union.put(cur, find(union.get(cur), union));
    return union.get(cur);
  }

  private void union(int cur, int old, Map<Integer, Integer> union) {
    int rootCur = find(cur, union);
    int rootOld = find(old, union);
    // without take in account the rank
    union.put(rootCur, rootOld);
  }
}
