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

public class Leetcode1353MaximumNumberofEventsThatCanBeAttended {

  /*
    1 <= events.length <= 10^5
    events[i].length == 2
    1 <= startDayi <= endDayi <= 10^5

    Idea:
    1> sort the meetings with start day + end day in ascending order
    2> figure out the day scope of [1, max] possible to attend a meeting
    3> for each day: if there is available meetings, then use a min heap keeping
       the end day of each meeting. to select out the earlier closed one
       to attend, the one
        - will end earlier than any others.
        - end day is today day or after today. or not finished yet
        - has started.
       those not be selected meetings, has started, are left in the
       min heap and will take part in the next day's selection.
  Note: put all meetings in min heap does not work
      e.g. [[1,2],[1,2],[3,3],[1,5],[1,5]]
           --
           --
           -----
           -----
             --
             --
      the [3,3] is covered by [1,5],[1,5]
      another case [1,5][1,5][3,4][3,4]
        -----
        -----
          --
          --
   O(NlogN) time and space
   */
  public static int maxEvents(int[][] A) {
    if (A == null) return 0;
    Arrays.sort(A, (a, b) -> (a[0] == b[0]) ? a[1] - b[1] : a[0] - b[0]); // O(NlogN)
    int D = 0;
    for (int[] e : A) D = Math.max(D, e[1]); // O(N）

    int r = 0, N = A.length, i = 0; // i is the index of meetings
    Queue<Integer> e = new PriorityQueue(); // min heap of meeting's end day
    // O(NlogN) for each meeting it will in and out only one time.
    for (int d = 1; d <= D; d++) { // d is today.
      while (i < N && A[i][0] == d) e.offer(A[i++][1]); // new candidates
      while (!e.isEmpty() && e.peek() < d) e.poll(); // old candidate but has finished meeting
      if (!e.isEmpty()) { // select the one will close firstly
        e.poll();
        r++;
      }
    }
    return r;
  }
}
