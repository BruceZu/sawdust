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

public class Leetcode1094CarPooling {

  // O(max{N,M})
  public boolean carPooling(int[][] trips, int capacity) {
    /*
        trips.length <= 1000
        trips[i].length == 3
        1 <= trips[i][0] <= 100
        0 <= trips[i][1] < trips[i][2] <= 1000
        1 <= capacity <= 100000
    */

    int[] t = new int[1001]; // deafult 0
    // O(N) N is trips length
    for (int[] trip : trips) {
      t[trip[1]] += trip[0];
      t[trip[2]] -= trip[0];
    }

    int realNeed = 0;
    // O(M) M is the max value of trips[i][1] and trips[i][2]
    for (int i : t) {
      realNeed += i;
      if (realNeed > capacity) return false;
    }
    return true;
  }

  // another way is to sort the start time and end time in 2 arrays attached with num_passengers
  // int[][] getin
  // int[][] getDown.
  // sort by time
  // then process both sorted arrays:
  // if it is getin, requiredSeats += getin[1]
  // else it is getDown, requiredSeats -= getin[1]
  // if requiredSeats> capacity return false
  // at last return true
  // this is O(NlogN) Time
}
