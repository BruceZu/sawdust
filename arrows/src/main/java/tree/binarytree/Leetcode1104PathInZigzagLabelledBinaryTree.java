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

package tree.binarytree;

import java.util.Arrays;
import java.util.List;

public class Leetcode1104PathInZigzagLabelledBinaryTree {
  public List<Integer> pathInZigZagTree(int label) {
    /*
     1 <= label <= 10^6

    Idea from Gauss:  15+8=14+9=13+10=12+11
     - when you are given 14 you want to find the number in normal binary tree 9 which is in the mirror location of 14's location
     - then the parent root value 4 can be figure out by 9/2
     - 8 is the $2^3$ and 15 is $2^4-1$  and $3=log^{14}, 4=3+1$
    So for any node value e.g. 11, its parent node value will be (8+15-11)/2=6
    */

    // current level
    int l = (int) (Math.log(label) / Math.log(2));
    Integer[] r = new Integer[l + 1]; // Integer required by Arrays.asList(r);
    r[l] = label;
    while (l >= 1) {
      // note the bigger one in current level is Math.pow(2, level + 1)-1.  do not lost the tail -1
      r[l - 1] = (int) ((Math.pow(2, l) + Math.pow(2, l + 1) - 1) - r[l]) / 2;
      l--;
    }
    return Arrays.asList(r);
  }
}
