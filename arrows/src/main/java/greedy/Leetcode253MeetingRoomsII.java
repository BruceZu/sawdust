//  Copyright 2016 The Sawdust Open Source Project
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

package greedy;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class Leetcode253MeetingRoomsII {
  /*
  Use a min heap of end time: to keep end time used to judge if
  there is free room compared with current meeting start time
  Min heap of end time:
     1. top's time is the one which can finished earlier than any other meeting,
     thus provide free room.
     2. size is the room number
  O(NlogN) time and O(N）space
  */
  public int minMeetingRooms(int[][] A) {
    if (A == null || A.length == 0) return 0;
    Arrays.sort(A, Comparator.comparingInt(a -> a[0])); // Sort by start time
    Queue<Integer> q = new PriorityQueue<>(A.length, Comparator.comparingInt(a -> a));
    q.add(A[0][1]); // end time
    for (int i = 1; i < A.length; i++) {
      if (q.peek() <= A[i][0]) q.poll();
      q.add(A[i][1]); // overlap: no free room available, then need allocate a room
    }
    return q.size();
  }
  // Alternative ------------------------------------------------------------
  //  O(NlogN) time and O(N）space
  /*
    Input: intervals = [[0,30],[5,10],[15,20]]
    Output: 2                10  20  30
                        0  5   15
    Input: intervals = [[7,10],[2,4]]
    Output: 1            4   10
                      2    7
  */
  public int minMeetingRooms2(int[][] A) {
    if (A == null || A.length == 0) return 0;
    int[] s = new int[A.length];
    int[] e = new int[A.length];
    for (int i = 0; i < A.length; i++) {
      s[i] = A[i][0];
      e[i] = A[i][1];
    }
    Arrays.sort(e);
    Arrays.sort(s);
    int i = 0, j = 0, r = 0;
    while (i < A.length) {
      if (e[j] <= s[i]) j++; // there is a free room
      else r++;
      i += 1;
    }

    return r;
  }
  // Alternative ------------------------------------------------------------
  // O(M) M is the max value of end
  public static int minMeetingRooms3(int[][] A) {
    int l = 0;
    for (int[] i : A) l = Math.max(l, i[1]);

    int[] t = new int[l + 1];
    for (int[] i : A) {
      t[i[0]]++;
      t[i[1]]--;
    }
    int max = 0;
    int s = 0;
    for (int i : t) {
      s += i;
      max = Math.max(max, s);
    }
    return max;
  }

  /* --------------------------------------------------------------------------
   Question: one room, calculate the max meetings it can hold
   Idea: sort the meetings with end time
         always select the meeting which finished firstly than any other
         once it is selected then any meeting whose start time is < the end
         time of selected meeting will can not be arranged.

   O(NlogN) time, O(1) extra space
  */
  public static int maxMeetings(int[][] A) {
    Arrays.sort(A, Comparator.comparingInt(a -> a[1]));
    int e = A[0][1];
    int r = 1;
    for (int i = 1; i < A.length; i++) {
      if (e < A[i][0]) {
        r++;
        e = A[i][1];
      }
    }
    return r;
  }
}
