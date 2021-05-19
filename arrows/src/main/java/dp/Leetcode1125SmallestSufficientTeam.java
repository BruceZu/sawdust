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

package dp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Leetcode1125SmallestSufficientTeam {

  /*

     1 <= req_skills.length <= 16
     1 <= req_skills[i].length <= 16
     req_skills[i] consists of lowercase English letters.
     All the strings of req_skills are unique.
     1 <= people.length <= 60
     0 <= people[i].length <= 16
     1 <= people[i][j].length <= 16
     people[i][j] consists of lowercase English letters.
     All the strings of people[i] are unique.
     Every skill in people[i] is a skill in req_skills.
     It is guaranteed a sufficient team exists.

   Idea:
    check
      - null
      - 'All the strings of people[i] are unique'
      - 'All the strings of req_skills are unique'
      - 'Every skill in people[i] is a skill in req_skills'
    Assume: It is guaranteed a sufficient team exists.
    represent the required and people's skills with a number in binary (base 2).
    `Map<Integer,List<Integer>>  dp= new HashMap<>()`
     key: integer whose binary represents the skill set,
     value: the `sufficient team` of the smallest possible size,
            represented by the index of each person in any order.
     initial with 0: empty list;
     then loop each people's skill:
       loop each valid skill set:
         v=valid skill|| current people skill
         if the v is a valid skill or it is and existing valid skill but have smaller
         size of required people then update the dp.
    Note:
    1. ConcurrentModificationException when modify map during loop it
       So use  `List<Integer>[] dp = new List[1 << r.length];`
       to replace  `Map<Integer,List<Integer>>  dp= new HashMap<>()`
    2. `(int)Math.pow(2,r.length)` is  1<< r.length;
       but `(int)Math.pow(2,r.length)-1` is `(1 << r.length) - 1`
    3. List<Integer> can not be convert to int[] directly
  */

  /**
   * @param r: required skills
   * @param ps: people's skill
   * @return
   */
  public int[] smallestSufficientTeam(String[] r, List<List<String>> ps) {
    Map<String, Integer> str_i = new HashMap<>();
    for (int i = 0; i < r.length; i++) str_i.put(r[i], i); // O(M) time M is required skills number
    List<Integer>[] dp = new List[1 << r.length]; // O(R) space, R is 2^M
    dp[0] = new ArrayList<>(); // the only valid one

    // O(N*2^M) time
    for (int i = 0; i < ps.size(); i++) {
      int skill = 0;
      // LinkedList: get(i) O(N), ArrayList: get(i) O(1)
      // Assume it is ArrayList
      // O(N）N is the people skills number.
      for (String s : ps.get(i)) skill |= 1 << str_i.get(s);
      for (int j = 0; j < dp.length; j++) {
        if (dp[j] == null) continue;
        int c = j | skill; // combine
        if (dp[c] == null || (dp[c].size() > dp[j].size() + 1)) {
          List<Integer> v = new ArrayList<>(dp[j]);
          v.add(i);
          dp[c] = v;
        }
      }
    }
    List<Integer> l = dp[(1 << r.length) - 1];
    int[] a = new int[l.size()];
    for (int i = 0; i < a.length; i++) a[i] = l.get(i); // int and Integer
    return a;
  }
}
