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

package two_pointer;

import java.util.*;

/**
 * <pre>
 * Given an array S of n integers, are there elements a, b, c in S such that a + b + c = 0?
 * Find all unique triplets in the array which gives the sum of zero.
 *
 * Note:
 * Elements in a triplet (a,b,c) must be in non-descending order. (ie, a ≤ b ≤ c)
 * The solution set must not contain duplicate triplets.
 * For example, given array S = {-1 0 1 2 -1 -4},
 *
 * A solution set is:
 * (-1, 0, 1)
 * (-1, -1, 2)
 * <a href="https://leetcode.com/problems/3sum/">LeetCode</a>
 */
public class Leetcode15_3Sum {
  /*
    duplicated number:
      unique triplets, but nums[j] can be same as nums[i] print out in order of
      ascending triplets are unique
   3sum -> 2sum:
      sort + 2 pointer O(N^2)
      sort + map   O(N^2). can not work here,
                           as there maybe duplicated element need care the duplicated,
                           keep times, and care the order, the index.
      sort + binary search in 2sum, O(NlogN) ~O(N^2)
  */
  public static void main(String[] args) {
    threeSum(new int[] {-1, 0, 1, 2, -1, -4}); // 2 results
    threeSum(new int[] {3, 0, -2, -1, 1, 2}); // 3 results
  }

  // o(N^2) time
  public static List<List<Integer>> threeSum2(int[] nums) {
    Arrays.sort(nums);
    List<List<Integer>> result = new ArrayList<>();
    for (int i = 0; i < nums.length - 2; i++) {
      if (i == 0 || (i > 0 && nums[i] != nums[i - 1])) {
        int j = i + 1, k = nums.length - 1, sum = 0 - nums[i];
        while (j < k) {
          if (nums[j] + nums[k] == sum) {
            result.add(Arrays.asList(nums[i], nums[j], nums[k]));
            while (j < k && nums[j] == nums[j + 1]) j++;
            while (j < k && nums[k] == nums[k - 1]) k--;
            j++;
            k--;
          } else if (nums[j] + nums[k] < sum) {
            j++;
          } else {
            k--;
          }
        }
      }
    }
    return result;
  }

  // ----------------------  binarySearch --------------------------------------
  public static List<List<Integer>> threeSum(int[] nums) {
    // TODO: input checking
    int N = nums.length;
    List<List<Integer>> result = new ArrayList<>();
    Arrays.sort(nums); // O(NlogN)
    // in better case O(NlogN)
    for (int i = 0; i <= N - 3; i++) {
      if (i != 0 && nums[i - 1] == nums[i]) continue;
      int j = i + 1;
      int k = N; // just for initial

      int r, l;
      while (j < k) {
        l = j + 1;
        r = k - 1;
        if (l > r) break;

        int idx = Arrays.binarySearch(nums, l, r + 1, 0 - nums[i] - nums[j]);
        if (idx >= 0) {
          k = idx;
          result.add(Arrays.asList(nums[i], nums[j], nums[k]));
          k--;
          while (j < k && nums[k] == nums[k + 1]) k--;
        } else {
          k = -idx - 1 - 1; // Not found, greaterIndex is -idx - 1;
        }

        // ----------------------------------------------------
        l = j + 1;
        r = k - 1;
        if (l > r) break;
        idx = Arrays.binarySearch(nums, l, r + 1, 0 - nums[i] - nums[k]);
        if (idx >= 0) {
          j = idx;
          result.add(Arrays.asList(nums[i], nums[j], nums[k]));
          j++;
          while (j < k && nums[j] == nums[j - 1]) j++;
        } else {
          j = -idx - 1;
        }
      }
    }
    return result;
  }
}
