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

package math;

import java.util.PriorityQueue;

public class Leetcode1792MaximumAveragePassRatio {

  /*
  1 <= classes.length <= 105
  classes[i].length == 2
  1 <= passi <= totali <= 105
  1 <= extraStudents <= 105

  1> compare A
       1/2=0.50  <   3/5=0.60
       ----------------------
       2/3=0.66,     4/6=0.66
      diff=0.16,    diff=0.06

       2/3-1/2 = 10/60
       4/6-3/5 =  4/60
       The delta is not same

     compare B
       2/3=0.66  >  3/5=0.60
       ---------------------
       3/4=0.75     4/6=0.66
      diff=0.09    diff=0.06

       3/4-2/3 = 5/60
       4/6-3/5 = 4/60
       The delta is not same

      y(x)=(x+a)/(x+a+b), a and b are positive integer.
      when x-> MAX, y(x) -> 1
      when x1 < x2
      There is
       y(x1) < y(x2) and
       y(x1+d) - y(x1) > y(x2+d) - y(x2)
  2> (x+1)/(y+1)-x/y=(y-x)/(y*(y+1))= D/(y*(y+1)), D=(y-x), y>x
  3> double and int arithmetic in Java
   */

  // O(max{C*logC,S*logC}) C is classes number,  S is extra students number
  public static double maxAverageRatio(int[][] classes, int extraStudents) {
    PriorityQueue<double[]> maxDiff = new PriorityQueue<>((a, b) -> b[2] - a[2] > 0 ? 1 : -1);
    double score = 0;
    // O(C*logC)
    for (int[] e : classes) {
      maxDiff.offer(
          new double[] {e[0], e[1], 1d * (e[1] - e[0]) / (e[1] * (e[1] + 1)), e[1] - e[0]});
      score += 1d * e[0] / e[1]; // They are still integer, so need change to double by 1d * them
    }
    // O(S*logC)
    while (extraStudents-- > 0) {
      double[] top = maxDiff.poll();
      score -= top[0] / top[1];
      score += (top[0] + 1) / (top[1] + 1);
      maxDiff.offer(
          new double[] {top[0] + 1, top[1] + 1, top[3] / ((top[1] + 1) * (top[1] + 2)), top[3]});
    }
    return score / classes.length;
  }

  //   Make double arithmetic easy in Java with a diff function
  public static double maxAverageRatio2(int[][] classes, int extraStudents) {
    PriorityQueue<double[]> maxDiff = new PriorityQueue<>((a, b) -> b[2] - a[2] > 0 ? 1 : -1);
    double score = 0;
    // O(C*logC)
    for (int[] e : classes) {
      maxDiff.offer(new double[] {e[0], e[1], diff(e[0], e[1])});
      score += 1d * e[0] / e[1]; // They are still integer, so need change to double by 1d * them
    }
    // O(S*logC)
    while (extraStudents-- > 0) {
      double[] top = maxDiff.poll();
      score -= top[0] / top[1];
      score += (top[0] + 1) / (top[1] + 1);
      maxDiff.offer(new double[] {top[0] + 1, top[1] + 1, diff(top[0] + 1, top[1] + 1)});
    }
    return score / classes.length;
  }

  private static double diff(double a, double b) {
    return (a + 1) / (b + 1) - a / b;
  }
  // Basic idea
  public static double maxAverageRatio1(int[][] classes, int extraStudents) {
    PriorityQueue<int[]> maxDiff =
        new PriorityQueue<>(
            (a, b) -> {
              double end =
                  ((b[0] + 1d) / (b[1] + 1d) - 1d * b[0] / b[1])
                      - ((a[0] + 1d) / (a[1] + 1d) - 1d * a[0] / a[1]);
              if (end == 0) return 0;
              return end > 0 ? 1 : -1;
            });
    double score = 0d;
    for (int[] e : classes) {
      maxDiff.offer(e);
      score += 1d * e[0] / e[1];
    }
    while (extraStudents-- > 0) {
      int[] top = maxDiff.poll();
      score -= 1d * top[0] / top[1];
      score += (top[0] + 1d) / (top[1] + 1d);
      maxDiff.offer(new int[] {top[0] + 1, top[1] + 1});
    }
    return score / classes.length;
  }
}
