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

public class Leetcode1041RobotBoundedInCircle {
  /*
     infinite plane,
     a robot initially stands at (0, 0) and faces north.
     three instructions:
        "G": go straight 1 unit;
        "L": turn 90 degrees to the left;
        "R": turn 90 degrees to the right.

    The robot performs the instructions given in order, and repeats them forever.

    Return true if and only if there exists a circle in the plane such that
    the robot never leaves the circle.
  */
  /*
  Math:
   At the end of the first cycle if the robot
   - back to original location or
   - doesn't back to original location and does not face the same direction as the initial status
   Then that's the limit cycle trajectory.

  O(N) time, O(1) space
   */
  // N:0, W:1,S:2,E:3
  public boolean isRobotBounded2(String s) {
    int d = 0, x = 0, y = 0;
    for (int i = 0; i < s.length(); i++) {
      char c = s.charAt(i);
      if (c == 'G') {
        if (d == 0) y++;
        if (d == 1) x--;
        if (d == 2) y--;
        if (d == 3) x++;
      }
      if (c == 'L') d = (d + 1) % 4;
      if (c == 'R') d = (d + 3) % 4;
    }
    return d != 0 || x == 0 && y == 0;
  }
}
