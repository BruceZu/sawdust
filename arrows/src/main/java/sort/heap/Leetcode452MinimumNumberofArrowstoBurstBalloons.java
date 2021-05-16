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

package sort.heap;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;

public class Leetcode452MinimumNumberofArrowstoBurstBalloons {
  /*
   1 <= points.length <= 104
   points[i].length == 2
   -2^31 <= xstart < xend <= 2^31 - 1
  Idea 1:
    sort with x_start + x_end with min heap
    for each one polled from the top of min heap
    for current bolloom which needs a arrow, try best to share  the arrow
    with those following one who has overlap with current one:
    their start<= current one's end
  O(NlogN) time and O(N) space
  Idea 2:
   sort in place is still okay
   */
  public static int findMinArrowShots(int[][] points) {
    if (points == null) return 0;
    Queue<int[]> q =
        new PriorityQueue<>(
            //  -2^31 <= xstart < xend <= 2^31 - 1
            (a, b) -> {
              if (a[0] == b[0]) {
                if (a[1] == b[1]) return 0;
                if (a[1] < b[1]) return -1;
                return 1;
              } else {
                if (a[1] < b[1]) return -1;
                return 1;
              }
            });
    // use end to order is still okay
    //    Queue<int[]> q =
    //        new PriorityQueue<>(
    //            (a, b) -> {
    //              if (a[1] == b[1]) return 0;
    //              if (a[1] < b[1]) return -1;
    //              return 1;
    //            });
    for (int[] i : points) {
      q.offer(i);
    }
    int r = 1;
    int[] i = q.poll();
    while (!q.isEmpty()) {
      if (q.peek()[0] <= i[1]) {
        q.poll();
      } else {
        r++;
        i = q.poll();
      }
    }
    return r;
  }

  /*
  sort by end:
   */
  public int findMinArrowShots2(int[][] points) {
    if (points.length == 0) return 0;
    Arrays.sort(
        points,
        (o1, o2) -> {
          if (o1[1] == o2[1]) return 0;
          if (o1[1] < o2[1]) return -1;
          return 1;
        });

    int r = 1;
    int End = points[0][1];
    for (int[] p : points) {
      int s = p[0], e = p[1];
      if (End < s) {
        r++;
        End = e;
      }
    }
    return r;
  }
}
