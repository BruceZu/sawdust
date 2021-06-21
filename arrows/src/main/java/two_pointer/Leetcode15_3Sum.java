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

/*
Ask:
 Given an integer array nums, return all the triplets
 [nums[i], nums[j], nums[k]] such that
 i != j, i != k, and j != k,
 and nums[i] + nums[j] + nums[k] == 0.

 Not duplicate triplets.

 Input: nums = [0,0,0]
 Output: [[0,0,0]]

 Input: nums = [-1,0,1,2,-1,-4]
 Output: [[-1,-1,2],[-1,0,1]]

 Input: nums = []
 Output: []

 Input: nums = [0]
 Output: []

 0 <= nums.length <= 3000
 -10^5 <= nums[i] <= 10^5
 */
public class Leetcode15_3Sum {
  // sort + 2 pointers O(N^2)   time, O(1) space, not count the result.
  public List<List<Integer>> threeSum(int[] a) {
    List<List<Integer>> re = new ArrayList<>();
    if (a == null || a.length <= 2) return re;
    Arrays.sort(a);
    for (int i = 0; i < a.length; i++) {
      if (i > 0 && a[i] == a[i - 1]) continue;
      int l = i + 1, r = a.length - 1;
      while (l < r) {
        int sum = a[l] + a[r] + a[i];
        if (sum == 0) {
          re.add(Arrays.asList(a[i], a[l], a[r]));
          while (l < r && a[l] == a[l + 1]) l++;
          l++;
          while (l < r && a[r] == a[r - 1]) r--;
          r--;
        } else if (sum < 0) l++;
        else r--;
      }
    }
    return re;
  }

  /*
  ----------------------------------------------------------------------------
  binarySearch
  Idea: sort + binary search for 2sum
  find 2 sum from [L, R]
  find the right index idx in[L+1 ,R] to match sum=A[L]+A[idx]
  move one side in linear time: depends on the duplicate number,
                               worse case is O(N^2): [2,2,2,2,2] target is 4
  move the other side with binary search: logN! time, O(NlogN) time.
  O(1) space, not count the result.
  */
  public List<List<Integer>> threeSumBS(int[] a) {
    int N = a.length;
    List<List<Integer>> re = new ArrayList<>();
    Arrays.sort(a);
    for (int i = 0; i <= N - 3; i++) {
      if (i > 0 && a[i] == a[i - 1]) continue;
      int L = i + 1, R = N - 1;
      while (L < R) {
        int r = Arrays.binarySearch(a, L + 1, R + 1, 0 - a[i] - a[L]);
        if (r >= 0) {
          re.add(Arrays.asList(a[i], a[L], a[r]));
          R = r;
        } else R = ~r - 1;
        while (L < R && a[L] == a[L + 1]) L++;
        L++;
      }
    }
    return re;
  }

  /*
   ----------------------------------------------------------------------------
   sort + set for 2sum
        [0,1,1,2]  target: 2
   skill:  if (!r.isEmpty() && r.get(r.size() - 1).get(1) == a[i]) continue;

   O(N^2) time, O(N) space.
  */
  public List<List<Integer>> threeSumSet(int[] a) {
    List<List<Integer>> r = new LinkedList();
    if (a == null || a.length <= 2) return r;
    Arrays.sort(a);
    int N = a.length;
    for (int i = 0; i < N; i++) {
      if (i > 0 && a[i] == a[i - 1]) continue;
      for (List<Integer> let : sum2(a, i + 1, 0 - a[i])) {
        List<Integer> tmp = new LinkedList();
        tmp.add(a[i]);
        tmp.addAll(let);
        r.add(tmp);
      }
    }
    return r;
  }

  private List<List<Integer>> sum2(int[] a, int from, int T) {
    Set<Integer> s = new HashSet<>();
    List<List<Integer>> r = new LinkedList<>();
    for (int i = from; i < a.length; i++) {
      if (!r.isEmpty() && r.get(r.size() - 1).get(1) == a[i]) continue;
      if (s.contains(T - a[i])) r.add(Arrays.asList(T - a[i], a[i]));
      s.add(a[i]);
    }
    return r;
  }
}
