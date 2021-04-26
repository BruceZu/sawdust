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
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class Leetcode56MergeInterval {
  public int[][] merge(int[][] intervals) {
    /*
    1 <= intervals.length <= 10^4
    intervals[i].length == 2
    0 <= starti <= endi <= 10^4
     */
    // TODO: check corner cases
    /*


    O(nlogn) time
    O(n) space
     Note:
     - update the cur interval right boundary as
          max{itself right boundary, next's right bundary}
     -  cur is left then no more interval is left;
     -  List.toArray(new int[r.size()][])
       */

    Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));
    int[] cur = intervals[0];
    List<int[]> r = new LinkedList<>();
    for (int i = 1; i < intervals.length; i++) {
      if (cur[1] < intervals[i][0]) {
        r.add(cur);
        cur = intervals[i];
      } else {
        cur[1] = Math.max(cur[1], intervals[i][1]);
      }
    }
    r.add(cur); // the cur is left
    return r.toArray(new int[r.size()][]);
  }
}
