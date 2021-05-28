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

public class Leetcode493ReversePairs3 {
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
    //  Recursion
    //  Time Limit Exceeded
    static public int reversePairs(int[] nums) {
        int totalPairNum = 0;
        BST root = null;
        for (int i = nums.length - 1; i >= 0; i--) {
            totalPairNum += towardRightCountEqualLessThanFrom(root,
                    (int) (Math.floor(  (nums[i] - 1) * 0.5d))); // pros: do not need long
            root = insertToBSTFrom(root, nums[i]);
        }

        return totalPairNum;
    }

    static private BST insertToBSTFrom(BST root, int v) {
        if (root == null) {
            root = new BST(v);
        } else if (v <= root.v) {
            root.l_sum++;
            root.l = insertToBSTFrom(root.l, v);
        } else {
            root.r = insertToBSTFrom(root.r, v);
        }
        return root;
    }

    // count those <= (x-1)/2
    static private int towardRightCountEqualLessThanFrom(BST root, int v) {
        if (root == null) {
            return 0;
        }
        if (v < root.v) {
            return towardRightCountEqualLessThanFrom(root.l, v);
        }
        if (v == root.v) {
            return root.l_sum + 1;
        }
        return root.l_sum + 1 + towardRightCountEqualLessThanFrom(root.r, v);
    }
}
