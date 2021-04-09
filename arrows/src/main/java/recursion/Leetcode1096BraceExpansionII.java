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

package recursion;

import java.util.*;

public class Leetcode1096BraceExpansionII {
  /*
   Example 1:

   Input: "{a,b}{c,{d,e}}"
   Output: ["ac","ad","ae","bc","bd","be"]

   Example 2:

   Input: "{{a,z},a{b,c},{ab,z}}"
   Output: ["a","ab","ac","z"]
   Explanation: Each distinct word is written only once in the final answer.


  1 <= expression.length <= 60
  expression[i] consists of '{', '}', ','or lowercase English letters.
  The given expression represents a set of words based on the grammar
  given in the description.
  */

  /*
  Idea comes from Hint
  'You can write helper methods to parse the next "chunk" of the expression.
   If you see eg. "a", the answer is just the set {a}.
   If you see "{", you parse until you complete the "}"
   (the number of { and } seen are equal) and that becomes a chunk that you
   find where the appropriate commas are, and parse each individual expression
   between the commas.'

   1. handler nested braces with recursion: what is nested braces?
      {{ then the first { is the start, how to find the end: keep only `}` find `}` then
      cut one `{`. For nested braces, keep only the outer braces, inner ones should be
      expanded in the result.
   2. now all brace pair has not nested inner brace pair
      2.1  handle all are `}{` `a{` and `}a` relation: no `, before `{` and after `}`
      2.2  now there can only existing   `},{` relation

   */
  public static List<String> braceExpansionII2(String expression) {
    String expanded = expand(expression);
    String[] pieces = expanded.split(",");
    Arrays.sort(pieces);
    List<String> r = new ArrayList<>();
    for (String p : pieces) {
      r.add(p);
    }
    return r;
  }

  // expand str to make it not contain brace anymore according to 3 rules
  private static String expand(String str) {
    // 1. expand nested expressions,
    Stack<Integer> li = new Stack<>(); // keep left braces index
    int start = 0;
    int nestedStart = -1;
    StringBuilder noNest = new StringBuilder();
    for (int i = 0; i < str.length(); i++) {
      if (str.charAt(i) == '{') {
        if (li.size() == 1 && nestedStart == -1) {
          // find nested brace pair
          nestedStart = li.peek();
        }
        li.push(i);
      }
      if (str.charAt(i) == '}') {
        li.pop();
        if (li.size() == 0 && nestedStart != -1) {
          // find the end the current nested pairs, expand the inner content between braces
          noNest.append(str.substring(start, nestedStart + 1));
          noNest.append(expand(str.substring(nestedStart + 1, i)));
          nestedStart = -1;
          start = i;
        }
      }
    }
    if (noNest.length() > 0) { // note: nested braces does not always exist
      noNest.append(str.substring(start));
      str = noNest.toString();
    }
    // no nested braces
    // 2. expand concatenate expressions,
    // - `a{`, `}{`
    // - `}a` in this case it can be
    //  -- `}a,`
    //  -- `}a{`
    int i = 0;
    while (i < str.length()) {
      if (str.charAt(i) == '{' && i - 1 >= 0 && str.charAt(i - 1) != ',') {
        char searchBackFor = '{';
        if (str.charAt(i - 1) != '}') searchBackFor = ',';
        int from = i;
        while (from >= 0 && str.charAt(from) != searchBackFor) from--;
        if (searchBackFor == ',') from++;
        int end = i;
        while (str.charAt(end) != '}') end++;
        StringBuilder newStr = new StringBuilder();
        newStr.append(str.substring(0, from));
        newStr.append(multiple(str.substring(from, end + 1)));
        newStr.append(str.substring(end + 1));
        str = newStr.toString();
        // i = 0;
        continue;
      }
      if (str.charAt(i) == '}' && i + 1 < str.length() && str.charAt(i + 1) != ',') {
        int from = i - 1;
        while (from >= 0 && str.charAt(from) != '{') from--;
        int end = i + 1;
        if (str.charAt(i + 1) == '{') {
          while (end < str.length() && str.charAt(end) != '}') end++;
        } else {
          while (end < str.length() && str.charAt(end) != ',' && str.charAt(end) != '{') end++;
          end--;
        }
        StringBuilder newStr = new StringBuilder();
        newStr.append(str.substring(0, from));
        newStr.append(multiple(str.substring(from, end + 1)));
        newStr.append(str.substring(end + 1));
        str = newStr.toString();
        // i = 0;
        continue;
      }
      i++;
    }

    // 3. expand union expressions
    String[] pieces = str.split(",");
    Set<String> uni = new HashSet<>();
    for (String p : pieces) {
      if (p.startsWith("{")) p = p.substring(1);
      if (p.endsWith("}")) p = p.substring(0, p.length() - 1);
      uni.add(p);
    }

    StringBuilder sb = new StringBuilder();
    for (String u : uni) {
      sb.append(u).append(",");
    }
    String r = sb.toString();
    return r.substring(0, r.length() - 1);
  }
  // `{}{}` or `ab{}` or `{}ab`
  // return `{a,b,c}` because result can be a port in the continue multiple
  private static String multiple(String str) {
    if (str.startsWith("{")) str = str.substring(1, str.length());
    if (str.endsWith("}")) str = str.substring(0, str.length() - 1);

    String[] two = str.split("}\\{");
    if (two.length == 1) {
      two = str.split("\\{");
    }
    if (two.length == 1) {
      two = str.split("}");
    }

    String[] l = two[0].split(",");
    String[] r = two[1].split(",");
    Set<String> uni = new HashSet<>();
    for (int i = 0; i < l.length; i++) {
      for (int j = 0; j < r.length; j++) {
        uni.add(l[i] + r[j]);
      }
    }
    StringBuilder con = new StringBuilder();
    con.append("{");
    for (String cur : uni) {
      con.append(cur).append(",");
    }
    String re = con.toString();
    return re.substring(0, re.length() - 1) + "}";
  }

  // Improved version ---------------------------------------------------------
  /*
    If expand() return a set
      - need not keep braces pair for its result
      - save a lot of code for expressing the result in a string
      - even un-nested brace pair become a set,
    In this way, all brace pairs become a set no matter it is nested or not.
    So if the current char is
      -  letter: take continued letters as a set.
      - `{` or`}`. process tm together, the result is also a set
    Then the relations left between these sets are `union` or `concatenate` and
    the latter one should be handled firstly as its precedence is higher than union expression.

    Once without nested brace pairs and all brace pairs are set no matter it is nested
    brace pairs or not. Left work is to handle the relationship between sets.

    The crucial part is how to recognize and handle them?
     According to the rules: If the current char is `,` it is a union relationship
     between left set and right set. If there is no `,` between 2 neighbor sets
     then these 2 sets have concatenating relationship.
     So for given  2 or more sets with concatenating relationship sure they are between

     -  two `,`
     - START and `,`
     - `,` and END
     - START and END
    expression string START: index -1
    expression string END: index expression.length()
    Solution:
      Keep 2 variables `unioned` and `pre` the latter one is used to concatenate
      with the current set.

    - once a set is got, by default it should concatenate with the previous
      set `pre` if it exists.
    - If the current char is `,` or reach end of string it means the left set if it exists will have
      no more set to concatenate with and so now it can union with the
      unioned one  `unioned`if it exists.

      O(N) if not take in account the concatenate() and `unioned.addAll(pre)`;
  */
  public static List<String> braceExpansionII(String expression) {
    List<String> r = new ArrayList<>(setOf(expression));
    Collections.sort(r);
    return r;
  }

  private static Set<String> setOf(String str) {
    int N = str.length();
    Set<String> unioned = null;
    Set<String> pre = null;
    int i = 0;
    while (true) {
      if (i == str.length() || str.charAt(i) == ',') {
        if (unioned == null) unioned = pre;
        else unioned.addAll(pre);
        if (i == str.length()) return unioned;
        pre = null;
        i++;
        continue;
      }
      if (Character.isLetter(str.charAt(i))) {
        int start = i;
        while (i < N && Character.isLetter(str.charAt(i))) i++;
        String curStr = str.substring(start, i);
        Set<String> curSet = new HashSet<>();
        curSet.add(curStr);
        if (pre == null) pre = curSet;
        else pre = concatenate(pre, curSet);
        continue;
      }
      if (str.charAt(i) == '{') {
        int inLevel = 0;
        int start = i;
        while (!(str.charAt(i) == '}' && inLevel == 1)) {
          if (str.charAt(i) == '{') inLevel++;
          if (str.charAt(i) == '}') inLevel--;
          i++;
        }
        Set<String> curSet = setOf(str.substring(start + 1, i));
        if (pre == null) pre = curSet;
        else pre = concatenate(pre, curSet);
        i++;
        continue;
      }
    }
  }

  private static Set<String> concatenate(Set<String> pre, Set<String> cur) {
    if (pre == null || pre.size() == 0) return cur;
    Set<String> r = new HashSet<String>();
    for (String p : pre) {
      for (String c : cur) {
        r.add(p + c);
      }
    }
    return r;
  }

  // Other solution with stack  ----------------------------------------------------------
  public static List<String> braceExpansionII3(String expression) {
    Stack<Object> stk = new Stack();
    stk.add(new HashSet<>()); // used by default for concatenate
    char[] exp = expression.toCharArray();
    for (char c : exp) {
      switch (c) {
        case '{': // begin a new set
          stk.push(c); // used for checking the boundary of union
          stk.push(new HashSet<>()); //  used by default for concatenate
          continue;
        case '}': // union all values until "{"
          Set<String> tmp = new HashSet<>();
          while (!(stk.peek() instanceof Character && ((Character) stk.peek()) == '{')) {
            tmp.addAll((Set<String>) stk.pop());
          }
          stk.pop(); // pop the "{"
          stk.push(concatenate((Set<String>) stk.pop(), tmp));
          continue;
        case ',':
          stk.push(new HashSet<>()); // used by default for concatenate
          continue;
        default: // regular letter
          Set<String> letter = new HashSet<>();
          letter.add("" + c); // "ab"  =  "a" concatenate "b"
          stk.push(concatenate((Set<String>) stk.pop(), letter));
      }
    }
    Set<String> tmp = new HashSet<>();
    while (!stk.isEmpty()) {
      tmp.addAll((Set<String>) stk.pop());
    }
    List<String> r = new ArrayList<>(tmp);
    Collections.sort(r);
    return r;
  }

  // test ---------------------------------------------------------------------
  public static void main(String[] args) {
    String t = "ab,cd,{ef,{gh,lm}}";
    System.out.println(braceExpansionII3(t));
    System.out.println(braceExpansionII2(t));
    System.out.println(braceExpansionII(t));
    t = "{ab,cd}{ef,{gh,lm}}";
    System.out.println(braceExpansionII3(t));
    System.out.println(braceExpansionII2(t));
    System.out.println(braceExpansionII(t));
    t = "{a,b}{c,{d,e}}";
    System.out.println(braceExpansionII3(t));
    System.out.println(braceExpansionII2(t));
    System.out.println(braceExpansionII(t));
    t = "{{a,z},a{b,c},{ab,z}}";
    System.out.println(braceExpansionII3(t));
    System.out.println(braceExpansionII2(t));
    System.out.println(braceExpansionII(t));
    t = "{a,b}c{d,e}f";
    System.out.println(braceExpansionII3(t));
    System.out.println(braceExpansionII2(t));
    System.out.println(braceExpansionII(t));
  }
}
