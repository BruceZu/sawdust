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

package doublepoint;

import java.util.*;

public class Leetcode18_4Sum {
  /*
   ask: return an array of all the unique quadruplets [nums[a], nums[b], nums[c], nums[d]],
        such that:
        0 <= a, b, c, d < n // index
        a, b, c, and d are distinct.
        nums[a] + nums[b] + nums[c] + nums[d] == target
       You may return the answer in any order.

   Idea:
    sort array firstly, because it is based on sort-> unique quadruplets
    Two pointers for 2 sum,
    it is possible there is duplicated elements
    e.g.: [2,2,2,2,2] k=8

   k is int as -10^9 <= target <= 10^9
   and it is not MAX and MIN.

   nums is int array as -10^9 <= nums[i] <= 10^9
   cases:
       nums=null return null;

       nums [2,2,2,2,2] k=8
       Output: [[2,2,2]]
       nums = [1,0,-1,0,-2,2], target = 0
          sort[-2, -1, 0, 0, 1, 2]
       Output: [[-2,-1,1,2],[-2,0,0,2],[-1,0,0,1]]

  O(N^3) time, O(1) space
  Easy fault:
    - no sort,
    - no skip duplicate number
    - 2-pointers: forget do further l++ or r-- after skip duplicate number after find a sum
  */
  public List<List<Integer>> fourSum(int[] A, int T) {
    if (A == null) return null;
    Arrays.sort(A);
    int N = A.length;
    List<List<Integer>> a = new LinkedList();
    for (int i = 0; i < N; i++) {
      if (i > 0 && A[i] == A[i - 1]) continue;
      for (int j = i + 1; j < N; j++) {
        if (j > i + 1 && A[j] == A[j - 1]) continue;
        int l = j + 1, r = N - 1;
        while (l < r) {
          int sum = A[i] + A[j] + A[l] + A[r];
          if (sum == T) {
            a.add(Arrays.asList(A[i], A[j], A[l], A[r]));
            while (l < r && A[l] == A[l + 1]) l++;
            l++;
            while (l < r && A[r] == A[r - 1]) r--;
            r--;
          } else if (sum < T) l++;
          else r--;
        }
      }
    }
    return a;
  }
  // k sum --------------------------------------------------------------------
  /*
  K sum: try each A[i] with k-1 sum in [i+1,N-1]
         i is N then return empty list
         k==1 it is two sum:
              find the last 2 numbers. E.g. 4 sum: find the 3rd dnd the 4th.
              [ 2,2,  2, 2, 2], avoid duplicated, the last 2, not the second is skilled.
              wrong:   if (i > start && A[i] == A[i - 1]) continue;
              right:   if(!a.isEmpty() && a.get(a.size()-1).get(1) ==A[i]) continue;
                       it is based on the sorted array, so need sort the array
         Don't change the returned list in loop
   O(N^(K-1)) time, O(N) space used in call stack

   Easy fault:
   - no sort;
   - not skip duplicated number
  */
  public List<List<Integer>> fourSum2(int[] A, int T) {
    if (A == null) return null;
    List<List<Integer>> a = new LinkedList();
    Arrays.sort(A);
    return ksum(A, 0, T, 4);
  }

  private List<List<Integer>> ksum(int[] A, int from, int T, int k) {
    List<List<Integer>> a = new LinkedList();
    if (from == A.length || A[from] * k > T || A[A.length - 1] * k < T) return a;
    if (k == 2) return sum2(A, from, T);
    for (int i = from; i < A.length; i++) {
      if (i > from && A[i] == A[i - 1]) continue;
      for (List<Integer> let : ksum(A, i + 1, T - A[i], k - 1)) {
        List<Integer> tmp = new LinkedList();
        tmp.add(A[i]);
        tmp.addAll(let);
        a.add(tmp);
      }
    }
    return a;
  }

  private List<List<Integer>> sum2(int[] A, int from, int T) {
    List<List<Integer>> a = new LinkedList();
    Set<Integer> s = new HashSet();
    for (int i = from; i < A.length; i++) {
      if (!a.isEmpty() && a.get(a.size() - 1).get(1) == A[i]) continue;
      if (s.contains(T - A[i])) a.add(Arrays.asList(T - A[i], A[i]));
      s.add(A[i]);
    }
    return a;
  }
}
