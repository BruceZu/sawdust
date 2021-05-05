//  Copyright 2017 The keepTry Open Source Project
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

public class Leetcode134GasStation {
  /*
   There are n gas stations along a circular route,
   where the amount of gas at the ith station is gas[i].
   You have a car with an unlimited gas tank and it costs cost[i]
   of gas to travel from the ith station to its next (i + 1)th station.
   You begin the journey with an empty tank at one of the gas stations.

   Given two integer arrays gas and cost, return the starting gas
   station's index if you can travel around the circuit once in the
   clockwise direction, otherwise return -1. If there exists a solution,
   it is guaranteed to be unique。

  Ida:
     If the total number of gas is bigger than the total number of cost.
     There must be a solution.
     which one is the start station?
    */
  public int canCompleteCircuit(int[] gas, int[] cost) {
    int N = gas.length;
    int all = 0; // total tank oil
    int cur = 0; // current tank oil
    int start = 0; // starting_station
    for (int i = 0; i < N; ++i) {
      all += gas[i] - cost[i];
      cur += gas[i] - cost[i];
      if (cur < 0) {
        // couldn't reach the next station
        // pick up the next station as the starting one. and
        // reset  an empty tank. because
        // may be this is not the only station cannot reachable
        // so keep try to find the next one.

        start = i + 1;
        cur = 0;
      }
    }
    return all >= 0 ? start : -1;
  }
}
