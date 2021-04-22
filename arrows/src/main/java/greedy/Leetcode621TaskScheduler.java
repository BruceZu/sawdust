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

package greedy;

public class Leetcode621TaskScheduler {
  /*
  Task Scheduler
  The key is to find out number of left idles after arrangement
  But don't need actually arrange them.
  Answer = idles + number of tasks.

  After arrangement
  1> left idles > 0
  2> left idles = 0

  Find out the number 'mfn' of all tasks with max frequency `mf`:
   - if `mfn` -1 >= n:
     then left idles = 0 because other tasks can be distributed into the intervals,
     as their frequency  < `mf`.
   - `mfn` -1 < n:
     There are idles need distribute other tasks into them:
       if `otherTasks` >= available idles number `holes`: no idles left.
           all otherTasks are distributed into the intervals
       if `otherTasks`  < `holes`. There are idles left.
           int this scenario the least number of units of times that the CPU will
           take to finish all the given tasks:
                   holes - otherTasks + total
              or:  intervals * (n + 1) + mfn
              or:  holes + mfn * mf
    O(N) time, Need not sort. Only need to know the max frequency and number of it
    O(1) space.
   */

  public static int leastInterval(char[] tasks, int n) {
    /*
    TODO: corner cases validation

    1 <= task.length <= 104
    tasks[i] is upper-case English letter.
    The integer n is in the range [0, 100].
     */
    int[] f = new int[26];
    for (int t : tasks) f[t - 'A']++;
    int mf = 0, mfn = 0; // Max frequency, number of tasks with max frequency
    for (int i = 25; i >= 0; i--) if (f[i] > mf) mf = f[i];
    for (int i = 25; i >= 0; i--) if (f[i] == mf) mfn++;
    int total = tasks.length; // Total tasks
    if (mfn - 1 >= n) return total; // Available idles = 0
    int intervals = mf - 1;
    int holes = intervals * (n - (mfn - 1)); // Available idles > 0
    int otherTasks = total - mfn * mf; // tasks whose frequency < max frequency
    if (holes <= otherTasks) return total; // left idles = 0
    //  left idles >=1
    return holes - otherTasks + total;
    // or:  intervals * (n + 1) + mfn
    // or:  holes + mfn * mf
  }
  // no comments version
  public static int leastInterval2(char[] tasks, int n) {
    int[] f = new int[26];
    for (int t : tasks) f[t - 'A']++;
    int mf = 0, mfn = 0; // Max frequency, number of tasks with max frequency
    for (int i = 25; i >= 0; i--) if (f[i] > mf) mf = f[i];
    for (int i = 25; i >= 0; i--) if (f[i] == mf) mfn++;
    if (mfn - 1 >= n) return tasks.length;
    int holes = (mf - 1) * (n - (mfn - 1));
    int otherTasks = tasks.length - mfn * mf;
    if (holes <= otherTasks) return tasks.length;
    return holes - otherTasks + tasks.length;
  }
}
