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

package binarysearch;

public class Leetcode493ReversePairs4 {
   static class BST { // binary search tree
        final int v;
        int l_sum; // number moving over the node to its left, or left sub tree nodes account.

        BST l, r;

        public BST(int v) {
            this.v = v;
        }
    }
    //  toward right to count node whose value is equal or less than target value
    //  keep the count number of left subtree nodes.
    //  Access and build BST
    //  O(nlogn) if it is balance tree
    //  loop
    //  Time Limit Exceeded
    static public int reversePairs(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        BST root = new BST(nums[nums.length - 1]);
        int totalPairNum = 0;
        for (int i = nums.length - 2; i >= 0; i--) {
            totalPairNum += towardRightCountEqualLessThanFrom(root,
                    (int) (Math.floor((nums[i] - 1) * 0.5d))); // pros: do not need long
            insertToBSTFrom(root, nums[i]);
        }

        return totalPairNum;
    }

    static private int towardRightCountEqualLessThanFrom(BST root, int v) {
        int result = 0;
        while (true) {
            if (root == null) {
                return result;
            }
            if (v == root.v) {
                return result + root.l_sum + 1;
            }

            if (v < root.v) {
                root = root.l;
            } else {
                result += root.l_sum + 1;
                root = root.r;
            }
        }
    }

    static private void insertToBSTFrom(BST root, int cur) {
        while (true) {
            if (cur <= root.v) {
                root.l_sum++;
                if (root.l == null) {
                    root.l = new BST(cur);
                    break;
                }
                root = root.l;
            } else {
                if (root.r == null) {
                    root.r = new BST(cur);
                    break;
                }
                root = root.r;
            }
        }
    }
}
