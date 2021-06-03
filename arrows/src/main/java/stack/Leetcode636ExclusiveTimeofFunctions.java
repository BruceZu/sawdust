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

package stack;

import java.util.List;
import java.util.Stack;

public class Leetcode636ExclusiveTimeofFunctions {
  public static int[] exclusiveTime(int n, List<String> logs) {
    /*
       `
       1 <= n <= 100
       1 <= logs.length <= 500
       0 <= function_id < n
       0 <= timestamp <= 109
       No two start events will happen at the same timestamp.
       No two end events will happen at the same timestamp.
       Each function has an "end" log for each "start" log.
       `
       This means: 1 CPU.
       TODO: corner cases validation
    */
    /*
       single-threaded CPU
       Function calls are stored in a call stack
       log message formatted as a string
           "{function_id}:{"start" | "end"}:{timestamp}"
       started at the beginning of timestamp x
       ended at the end of timestamp x

    Idea
        - CPU will not have idle time.
        - CPU runs task at timestamp-start and stop at timestamp-end: so
          even run and stop a task at the same timestamp the used time is 1.
        - for any time interval need to know: who is running and what the interval-start is
         "0:start:0", "2:start:2", "2:end:5", "1:start:7", "1:end:7", "0:end:8"

         task ID  :  0       2               2   1   1   0
         start/end : [       [               ]   [   ]   ]
         time      : 0   1   2   3   4   5   6   7   8   9
        running task 0------02--------------20--01---10--0
      Note:
        In the above case, at time interval [6, 7):
        The running task is 0 which started at time point 6
        after its previous task 2 ends at time point 5.
        From the view of task 1: its previous task is 2 in the log sequence order from which no way to
        know who was running before task 1 starts.
      So need a stack:
        Each time interval has a start time and has a task running in it.
        - use stack  `t`'s top keep current running task ID,
        - use start `s` keep current time interval start time
        Update them at each log entry and calculate the just finished time interval for the task who
        was running in it.
      when it is a start log:
         if there is some function was running before current function: l[0] starts, so need to
         calculate the time interval for that function which should stop at the end of the time
         point: Integer.parseInt(l[2])-1, so the time interval is Integer.parseInt(l[2]) - s;
      els if it is end log:
         end log makes sure there is already a `start` log: and the interval is:
          Integer.parseInt(l[2]) + 1 -s

      O(N) time, O(N) space.
     */

    int[] r = new int[n];
    Stack<Integer> t = new Stack<>(); // previous function ID
    int s = 0; // start time
    for (String log : logs) {
      String[] l = log.split(":");
      if (l[1].equals("start")) {
        if (!t.isEmpty()) r[t.peek()] += Integer.parseInt(l[2]) - s;
        // update current task and start time
        t.push(Integer.parseInt(l[0]));
        s = Integer.parseInt(l[2]);
      } else {
        r[t.peek()] += Integer.parseInt(l[2]) + 1 - s;
        // update current task and start time
        t.pop();
        if (!t.isEmpty()) s = Integer.parseInt(l[2]) + 1;
        // CPU has no idle time, update the start time for the running task of t.peek()
      }
    }
    return r;
  }
}
