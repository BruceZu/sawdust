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

package sort;

import java.util.*;

public class Leetcode1152AnalyzeUserWebsiteVisitPattern {
  /*
   This question statement is not clear:
   Understand it with 3 examples:
    1> Input:
    username =  ["joe"," joe",  "joe",   "james","james","james","james","mary", "mary","mary"],
    timestamp = [1,      2,     3,       4,       5,      6,      7,      8,     9,      10],
    website =   ["home","about","career","home", "cart",  "maps", "home", "home","about","career"]

    Output: ["home","about","career"]

    Explanation:
    The 3-sequence ("home", "about", "career") was visited by 2 users: joe and mary
    james visited 4 websites: "home", "cart",  "maps", "home"
    there are 4 possible 3-sequences as:
    The 3-sequence ("home", "cart", "maps")
    The 3-sequence ("home", "cart", "home")
    The 3-sequence ("home", "maps", "home")
    The 3-sequence ("cart", "maps", "home")
    each one is visited by one user, james only.


    2> Input:
      ["u1","u1","u1","u2","u2","u2"]
      [ 1,   2,   3,   4,   5,   6]
      ["a", "b", "a", "a", "b", "c"]
      Output:  ["a"," b", "a"]

      Explanation:
         ["a"," b", "a"] was visited by 1 user: u1
         ["a", "b", "c"] was visited by 1 user: u2

       The lexicographically smallest is  ["a"," b", "a"]

     3> Input:
        ["dowg",    "dowg",   "dowg"]
        [158931262, 562600350, 148438945]
        ["y",      "loedo",   "y"]

        Output:  ["y","y","loedo"]


      3 <= N = username.length = timestamp.length = website.length <= 50
      1 <= username[i].length <= 10
      0 <= timestamp[i] <= 10^9
      1 <= website[i].length <= 10
      Both username[i] and website[i] contain only lowercase characters.
      It is guaranteed that there is at least one user who visited at least 3 websites.
      No user visits two websites at the same time.

    A 3-sequence is a list of websites of length 3 sorted
    in ascending order by the time of their visits.
    ( The websites in a 3-sequence are not necessarily distinct. )

    Find the 3-sequence visited by the largest number of users.
    If there is more than one solution, return the
    lexicographically smallest such 3-sequence.
  */

  /*
   Idea: 1 from the given 3 arrays get each user's website visiting records sorted by timestamp
           user (1) | time:website(n)
         2 then get all possible 3-seq(1) | visiting users(n)
         3 sort 3-seq in users number descending order + name lexicographically order

   Note:
   - the given timestamp array is not sorted
     'No user visits two websites at the same time'.  But "10" < "8" < "9", need convert to
         number
   - 'Both username[i] and website[i] contain only lowercase characters.'

  O(N^3) time, space.
    - '1 <= username[i].length <= 10'
    - 'It is guaranteed that there is at least one user who visited at least 3 websites'
   */

  public List<String> mostVisitedPattern(String[] username, int[] timestamp, String[] website) {
    int N = username.length;
    Map<String, Map<Integer, String>> utw = new HashMap();
    for (int i = 0; i < N; i++) {
      utw.putIfAbsent(username[i], new HashMap());
      utw.get(username[i]).put(timestamp[i], website[i]);
    }

    Map<String, Set<String>> n = new HashMap();
    int rn = 0;
    String r = "";
    for (Map.Entry<String, Map<Integer, String>> e : utw.entrySet()) { // .entrySet()
      String u = e.getKey();
      Map.Entry<Integer, String>[] tw = new Map.Entry[e.getValue().size()]; // new without <>
      e.getValue().entrySet().toArray(tw);
      Arrays.sort(tw, Comparator.comparingInt(a -> a.getKey()));

      for (int x = 0; x <= tw.length - 3; x++) {
        for (int y = x + 1; y <= tw.length - 2; y++) { // y=x+1
          for (int z = y + 1; z <= tw.length - 1; z++) {
            String k = tw[x].getValue() + " " + tw[y].getValue() + " " + tw[z].getValue();
            n.putIfAbsent(k, new HashSet());
            n.get(k).add(u);

            if (n.get(k).size() > rn) {
              rn = n.get(k).size();
              r = k;
            } else if (n.get(k).size() == rn && k.compareTo(r) < 0) r = k;
          }
        }
      }
    }
    return Arrays.asList(r.split(" "));
  }
}
