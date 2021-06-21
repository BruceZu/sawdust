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

/*
 Ask:  a array of integers numbers sorted in non-decreasing order
       Return the indices of the two numbers (1-indexed) as an integer array answer of size 2,
       where 1 <= answer[0] < answer[1] <= numbers.length.
       they add up to a specific target number

       Assume here is exactly one solution.
       You may not use the same element twice.

 2 points
 O(N) time O(1) space
*/
public class Leetcode167TwoSumIIinputArraySorted {
  public int[] twoSum(int[] numbers, int target) {
    int l = 0, r = numbers.length - 1;
    while (l < r) {
      int sum = numbers[l] + numbers[r];
      if (sum == target) {
        return new int[] {l + 1, r + 1};
      } else if (sum < target) l++;
      else r--;
    }
    return null; // not reach here
  }
}
