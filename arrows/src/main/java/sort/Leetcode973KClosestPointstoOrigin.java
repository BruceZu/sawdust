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

public class Leetcode973KClosestPointstoOrigin {
  public int[][] kClosest(int[][] points, int k) {
    PriorityQueue<int[]> q =
        new PriorityQueue<>(
            // need cost to int
            // and 10* for the value less than 1 before casting to int
            // or remove the sqrt step to save all of these
            (a, b) -> a[0] * a[0] + a[1] * a[1] - b[0] * b[0] - b[1] * b[1]);
    for (int[] p : points) {
      q.offer(p);
    }
    int[][] r = new int[k][];
    while (k > 0) {
      r[--k] = q.poll();
    }
    return r;
  }
  // use ArrayList sort directly
  public int[][] kClosest2(int[][] points, int k) {
    Arrays.sort(points, (a, b) -> a[0] * a[0] + a[1] * a[1] - b[0] * b[0] - b[1] * b[1]);
    return Arrays.copyOfRange(points, 0, k);
  }
}
