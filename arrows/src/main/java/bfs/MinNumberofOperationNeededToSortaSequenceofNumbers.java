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

package bfs;

import java.util.*;

public class MinNumberofOperationNeededToSortaSequenceofNumbers {
  // Facebook | Min number of operation needed to sort a sequence of numbers
  /*
  Given an integer N, and a permutation, P of the integers from 1 to N,
  denoted as (a_1, a_2, ..., a_N), rearrange the elements of the permutation
  into increasing order, repeatedly making the following operation:
  Select a sub-portion of the permutation, (a_i, ..., a_j), and reverse its order.

  Your goal is to compute the minimum number of such operations
  required to return the permutation to increasing order.
  */

  // BFS
  // Note:  greedy algorithm does not guarantee the smallest number of reversals
  //        see http://www.cs.unc.edu/~prins/Classes/555/Media/Lec06.pdf
  private static void reverse(int[] arr, int l, int r) {
    // reverse array [i,...,j]
    while (l < r) {
      int xor = arr[l] ^ arr[r];
      arr[l++] = xor ^ arr[l];
      arr[r--] = xor ^ arr[r];
    }
  }

  private static boolean isSorted(int[] arr) {
    for (int i = 0; i < arr.length; i++) {
      if (arr[i] != i + 1) return false;
    }
    return true;
  }

  static int minOperations(int[] arr) {
    int operations = 0;
    if (arr == null || arr.length == 0 || isSorted(arr)) return operations;
    Set<String> visited = new HashSet<>();
    visited.add(Arrays.toString(arr));

    Queue<int[]> queue = new LinkedList<>();
    queue.add(arr);
    while (!queue.isEmpty()) {
      operations++;
      int n = queue.size();
      for (int i = 0; i < n; i++) {
        int[] cur = queue.poll();
        for (int l = 0; l < cur.length; l++) {
          for (int r = l + 1; r < cur.length; r++) {
            int[] next = Arrays.copyOf(cur, cur.length);
            reverse(next, l, r);
            String key = Arrays.toString(next);
            if (visited.contains(key)) continue;
            if (isSorted(next)) return operations;
            visited.add(key);
            queue.add(next);
          }
        }
      }
    }
    return -1; // not reach here
  }

  // --------------------------------------------------------------------------
  public static void main(String[] args) {
    System.out.println(minOperations(new int[] {3, 1, 2}) == 2);
    System.out.println(minOperations(new int[] {4, 3, 1, 2}) == 2);
    System.out.println(minOperations(new int[] {6, 1, 2, 3, 4, 5}) == 2);
  }
}
