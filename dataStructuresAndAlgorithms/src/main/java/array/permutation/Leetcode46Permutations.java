//  Copyright 2016 The Sawdust Open Source Project
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

package array.permutation;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * Given a collection of distinct numbers, return all possible permutations.
 *
 * For example,
 * [1,2,3] have the following permutations:
 * [
 * [1,2,3],
 * [1,3,2],
 * [2,1,3],
 * [2,3,1],
 * [3,1,2],
 * [3,2,1]
 * ]
 *
 *  Tags Backtracking
 *  Similar Problems
 *  (M) Next Permutation
 *  (M) Permutations II
 *  (M) Permutation Sequence
 *  (M) Combinations
 *
 * Note:
 *   0  This is for * distinct * numbers
 *   1  current choice scope is depends on pre
 *   2  back-tracing need restore original status when step back:
 *         delete before return
 *         delete after call
 *   3 List methods:
 *     --  2 remove():
 *              remove(int index);
 *              remove(Object o);
 *     --  contains(): need check if current one is selected.
 *   4 Improve:
 *       a. use a boolean[] to check current choice is selected or not
 *       b. return directly once a permutation is finished.
 *       c. all parameters to be variables, easy to read.
 *
 * @see <a href = "https://leetcode.com/problems/permutations/">leetcode</a>
 */
public class Leetcode46Permutations {
    private List<List<Integer>> r;
    private List p;
    private int[] ms;
    private boolean[] selected;

    private void permute3() {
        if (p.size() == ms.length) {
            r.add(new ArrayList(p));
            return;
        }

        for (int i = 0; i < ms.length; i++) {
            if (selected[i] != true) {
                p.add(ms[i]);//  -->
                selected[i] = true;//  -->

                permute3();

                p.remove(p.size() - 1); // <--
                selected[i] = false; // <--
            }
        }
    }


    /**
     * back-tracing
     * Big O: runtime O(N!), space O(N!)
     */
    public List<List<Integer>> permute3(int[] in) {
        if (in == null) {
            return null;
        }
        ms = in;
        r = new ArrayList();
        p = new ArrayList(ms.length);
        selected = new boolean[ms.length];

        permute3();
        return r;
    }

    // ---------------------------------------------------------
    /**
     * <pre>
     *      1 2 3
     * rotate:
     *     1 2 3
     *           1 2 3
     *                  start = end
     *           rotate
     *
     *           1 3 2
     *                  start = end
     *           rotate
     *           1 2 3
     *     rotate
     *     2 3 1
     *           2 3 1
     *                  start = end
     *           rotate
     *           2 1 3
     *                 start = end
     *           rotate
     *           2 3 1
     *     rotate
     *     3 1 2
     *           3 1 2
     *                  start = end
     *           rotate
     *           3 2 1
     *                  start = end
     *           rotate
     *           3 1 2
     *    rotate
     *    1 2 3
     *    the result is
     *      [1, 2, 3]
     *      [1, 3, 2]
     *      [2, 3, 1]
     *      [2, 1, 3]
     *      [3, 1, 2]
     *      [3, 2, 1]
     *
     *  look down to see the choices of fist , second, .... number of permutations. like a Permutation Lock.
     *
     *  Big O: run time O(N!), space  O(N!)
     */
    private void rotateNextChoice(int si, int ei) {
        int siv = ms[si];
        for (int i = si; i < ei; i++) {
            ms[i] = ms[i + 1];
        }
        ms[ei] = siv;
    }

    /**
     * <pre>
     * Note:
     *   1 Arrays.asList(ms) can not work; it will wrap a []
     *   2 choice need -- each loop
     *   3 the loop only about choices, not affect next recursive which always is 'si +1'
     *
     * @param si start index
     * @param ei end index
     */
    private void pNextNumber(int si, int ei) {
        if (si == ei) {
            List<Integer> p = new ArrayList(ms.length);
            for (int i = 0; i < ms.length; i++) {
                p.add(ms[i]);
            }
            r.add(p);
            return;
        }
        int choices = ei - si + 1;
        while (choices-- >= 1) {
            pNextNumber(si + 1, ei);
            rotateNextChoice(si, ei);
        }
    }

    /**
     * <pre>
     * 10 minutes
     * Note:
     *   input check null
     *   it is length -1, not length.
     */
    public List<List<Integer>> permute2(int[] in) {
        if (in == null) {
            return null;
        }
        ms = in;
        r = new ArrayList();
        pNextNumber(0, ms.length - 1);
        return r;
    }

    // ---------------------------------------------------------
    /**
     * <pre>
     * Note:
     * 2 swaps:
     * fist swap: choices of current number depends on pre
     * without the last swap:
     *      [[1,2,3],[1,3,2],
     *      [3,1,2],[3,2,1],   3 get back 1 then 2 push 1 to the last
     *      [1,2,3],[1,3,2]]   now 1 is duplicated
     *
     * for array with duplicated element. this approach can not work.
     * e.g.: see {@link array.permutation.Leetcode47PermutationsII#permuteUnique(int[])}
     *   1 1 2 2
     *   ->  1 2 1 2 -> 1 2 2 1
     *   ->  1 2 2 1
     *
     * for the case [1, 2, 3]
     * Result is
     *      [1, 2, 3]
     *      [1, 3, 2]
     *      [2, 1, 3]
     *      [2, 3, 1]
     *      [3, 2, 1]
     *      [3, 1, 2]
     *
     *
     *  Note:
     *  <1>
     *   before swap need check i!=j;
     *   Here the nums is distinct numbers:
     *   i == j means
     *   ms[i]^=ms[i]  // ms[i] ==0
     *   ms[i]^=ms[i]  // ms[i] ==0
     *   ms[i]^=ms[i]  // ms[i] ==0
     *
     *   So if the nums is not distinct numbers.
     *   Do not use ^= operation.
     *
     *   <2> if use while loop make sure the variable not affect the actions in loop;
     *
     *     it is ok:
     *      int choice = si;
     *      while (choice <=ei){  // wrong while (choice++ <=ei){
     *          swap(si, choice);
     *          pNextNum(si + 1, ei);
     *          swap(si, choice);
     *          choice ++;
     *      }
     * }
     */
    private void swap(int i, int j) {
        if (i != j) {
            ms[i] ^= ms[j];
            ms[j] ^= ms[i];
            ms[i] ^= ms[j];
        }
    }

    private void pNextNum(int si, int ei) {
        if (si == ei) {
            List p = new ArrayList(ms.length);
            for (int i = 0; i < ms.length; i++) {
                p.add(ms[i]);
            }
            r.add(p);
            return;
        }

        for (int curChoice = si; curChoice <= ei; curChoice++) {
            swap(si, curChoice);
            pNextNum(si + 1, ei);
            swap(si, curChoice);
        }
    }

    public List<List<Integer>> permute(int[] in) {
        // 15:03
        if (in == null) {
            return null;
        }
        ms = in;
        r = new ArrayList();
        pNextNum(0, ms.length - 1);

        return r;
    }
}
