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

package sort;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;

public class Leetcode973KClosestPointstoOrigin {
  /*
  You may return the answer in any order.
  The answer is guaranteed to be unique (except for the order that it is in).

  Idea: keep a max heap with size of K
  O(N*longK) time. O(K) space
   */
  public int[][] kClosest(int[][] points, int k) {
    Queue<int[]> q = // max heap
        new PriorityQueue<>((b, a) -> a[0] * a[0] + a[1] * a[1] - b[0] * b[0] - b[1] * b[1]);
    for (int[] p : points) {
      if (q.size() < k) q.offer(p);
      else { // q.size==k
        int[] t = q.peek();
        if (p[0] * p[0] + p[1] * p[1] < t[0] * t[0] + t[1] * t[1]) {
          q.poll();
          q.offer(p);
        }
      }
    }
    int[][] r = new int[k][];
    while (k > 0) {
      r[--k] = q.poll();
    }
    return r;
  }
  // use ArrayList sort directly. O(NlogN) time O(1) space
  public int[][] kClosest2(int[][] points, int k) {
    Arrays.sort(points, (a, b) -> a[0] * a[0] + a[1] * a[1] - b[0] * b[0] - b[1] * b[1]);
    return Arrays.copyOfRange(points, 0, k);
  }
}
