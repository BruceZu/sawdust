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

package binarysearch;

import java.util.PriorityQueue;

public class Leetcode774MinimizeMaxDistancetoGasStation {
  /*
  You should add k new gas stations. You can add the stations anywhere on the x-axis,
  and not necessarily on an integer position.
  Let penalty() be the maximum distance between adjacent gas stations
  after adding the k new stations.
  Return the smallest possible value of penalty().
  Answers within 10^-6 of the actual answer will be accepted.

    10 <= stations.length <= 2000
    0 <= stations[i] <= 10^8
    stations is sorted in a strictly increasing order.
    1 <= k <= 10^6
  Idea:
    the original bus station location is integer.
    the new bug station location may be real number with a infinite circle decimal

    max heap: O(min{NlogN, KlogN}）time and O(N) space. Time Limit Exceeded
   */
  public double minmaxGasDistOriginalIdea(int[] stations, int K) {
    int N = stations.length;
    PriorityQueue<int[]> q =
        new PriorityQueue<>((a, b) -> (double) b[0] / b[1] < (double) a[0] / a[1] ? -1 : 1);
    for (int i = 0; i < N - 1; ++i) q.add(new int[] {stations[i + 1] - stations[i], 1});

    for (int k = 0; k < K; ++k) {
      int[] n = q.poll();
      n[1]++;
      // insert one to share the interval. not to cut the interval into 2 parts.
      // It maybe be > 2 parts if some new station has already been inserted in
      // this interval.
      q.add(n);
    }

    int[] n = q.poll();
    return (double) n[0] / n[1];
  }

  /*
   Idea:
     binary search
     - initial value l = 0 and r = 1e8 is the search space of smallest possible value of max distance of
       any adjacent 2 gas stations. this search space is monotonic and binary search can be applied here
     - try a value in the search space calculated by binary search to see with it(Double)
       how many new bus stations (Integer) can be added in total in a manner with which for any 2 existing
       adjacent gas stations the new gas station(s) are evenly allocated in the interval.
     - what we want to get from the predicate after comparing the possible added new station
       number with K is only the heuristic hint which can let us know we should make the next distance
       value used to try to be smaller or bigger than the current one.
       thus continue trying till step in the a accepted shrink search space and stop there.
       This is binary search character in real number search space.
       So handle the scenario of
         new gas station number n and K:
         n < K  : current value is bigger that target one. try a smaller value
         n > K  : current value is smaller that target one. try a bigger value
         how about  n == K, let l=mid or let r=mid? and why?
             Note: the first distance value used to try is (0+max)/2
             for any interval of 2 adjacent existing gas stations,
             when n == K happen for the first time the last distance value
             is a bigger one that the one which firstly make n == K happen.
             at that time if use l=mid, then the following distance value will
             be bigger the current one again as before. but at the time
             when n == K happens for the first time, even the value of n
             is expected but the distance value may be not the one which can
             evenly allocate the new gas stations. in this case still need tuning
             it to be smaller in next step without increasing the number of n
             because the required distance is the one who will make smallest possible
             value of penalty(). for this reason use r=mid.
             E.g. stations=[0,1] ,K=1.

    'Answers within 10^-6 of the actual answer will be accepted.'
     means: the search space shrink to where any 2 value's diff is < 10^-6
           then the search can terminate and any answer in that search space is
           acceptable.

      O(NlogW) time. W is the value of the last element value of station.
      O(1) space
  */
  public static double minmaxGasDist(int[] stations, int K) {
    // Check null, k is positive and stations is sorted in a strictly increasing order
    double l = 0, r = stations[stations.length - 1];
    while (r - l > 1e-6) { // when search space is real number.
      double d = (l + r) / 2.0;

      int n = 0; // new gas stations. it is integer
      for (int i = 0; i < stations.length - 1; i++) {
        // n += (int) ((stations[i + 1] - stations[i]) / d);
        n += Math.ceil((stations[i + 1] - stations[i]) / d) - 1; // same with above line when
        // the above value's fraction is not 0.
      }

      if (n < K) r = d;
      else if (n > K) l = d;
      else if (n == K) r = d;
    }
    return l;
  }
}
