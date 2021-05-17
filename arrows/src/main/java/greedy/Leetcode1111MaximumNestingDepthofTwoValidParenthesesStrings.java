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

package greedy;

public class Leetcode1111MaximumNestingDepthofTwoValidParenthesesStrings {
  /*
   require: only the max(depth(A), depth(B)) is the minimum possible value.
   Idea:
   so separate with the level evenly
  */
  public static int[] maxDepthAfterSplit2(String seq) {
    int N = seq.length();
    int[] r = new int[N];
    int l = 0; // level
    for (int i = 0; i < N; i++) {
      if (seq.charAt(i) == '(') r[i] = ++l & 1;
      else r[i] = l-- & 1;
    }
    return r;
  }
  /*
   Idea:
   use index i replace variable l in the above
   Because the string consists only of "(" and ")"
   and () are always in pair
  */
  public static int[] maxDepthAfterSplit(String s) {
    int N = s.length(), res[] = new int[N];
    for (int i = 0; i < N; ++i) {
      //  `1 - i & 1; ` :
      //  - current ')' and its '(' is separated by pairs of '(' and ')'
      //  i-1 and the index of its '('  has the same attribute of even of odd
      res[i] = s.charAt(i) == '(' ? i & 1 : (1 - i & 1);
    }
    return res;
  }

  public static void main(String[] args) {
    maxDepthAfterSplit("(()())"); //  [0,1,1,1,1,0]
    maxDepthAfterSplit("()(())()"); //  [0,0,0,1,1,0,0,0]
    maxDepthAfterSplit2("(()())"); //  [1,0,0,0,0,1]
    maxDepthAfterSplit2("()(())()"); //  [1,1,1,0,0,1,1,1]
  }
}
