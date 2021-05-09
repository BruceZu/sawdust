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

package sort.interval;

public class Leetcode1109CorporateFlightBookings {

  /*
   There are n flights that are labeled from 1 to n.

   bookings[i] = [firsti, lasti, seatsi] represents a booking
   for flights firsti through lasti (inclusive) with seatsi seats reserved
   for each flight in the range.

   Return an array answer of length n, where answer[i]
   is the total number of seats reserved for flight i.

   1 <= n <= 2 * 104
   1 <= bookings.length <= 2 * 104
   bookings[i].length == 3
   1 <= firsti <= lasti <= n
   1 <= seatsi <= 104

  TODO: check null, 0
  O(n) time and space
  */
  public int[] corpFlightBookings(int[][] bookings, int n) {
    int[] res = new int[n];
    for (int[] v : bookings) {
      res[v[0] - 1] += v[2];
      if (v[1] < n) res[v[1]] -= v[2];
    }
    for (int i = 1; i < n; ++i) res[i] += res[i - 1];
    return res;
  }
}
