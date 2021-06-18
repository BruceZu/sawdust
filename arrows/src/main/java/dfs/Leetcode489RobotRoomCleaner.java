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

package dfs;

import java.util.HashSet;
import java.util.Set;

public class Leetcode489RobotRoomCleaner {
  interface Robot {
    // Returns true if the cell in front is open and robot moves into the cell.
    // Returns false if the cell in front is blocked and robot stays in the current cell.
    public boolean move();

    public void turnLeft();

    public void turnRight();

    public void clean();
  }
  // Solution -----------------------------------------------------------------
  /*
        1.all cells marked as 1 will be accessible by the robot.
        2.The initial direction of the robot will be facing up
        3.require: clean the entire room
        Thinking:
        how to know entire room has all been cleaned: try 4 direction and back to original point

       Idea:
        use recursion: clean un-visited cell and try 4 direction,
        at last back to the specified location with a back direction of original direction.
       skill:
       -  for each move(), need to know the move direction,
          tracking the location and direction of move is for updating visited cells
       -  during try 4 direction: after try, back to 'current location and direction' before move

       when we should step back from current recursion if we can not move further or
       all direction has been tried

       O(N−M) time and space, N is cell number, M is number of 0/obstacles cells
  */

  int N = 0, W = 1, S = 2, E = 3;

  public void cleanRoom(Robot r) {
    dfs(r, 0, 0, N, new HashSet());
  }

  private void dfs(Robot r, int ox, int oy, int od, Set<String> v) {
    String k = ox + "-" + oy;
    if (!v.contains(k)) {
      r.clean();
      v.add(k);

      for (int i = 1; i <= 4; i++) {
        r.turnLeft();
        if (r.move()) {
          int x = ox, y = oy, d = (od + i) % 4;
          if (d == N) y++;
          if (d == W) x--;
          if (d == S) y--;
          if (d == E) x++;
          dfs(r, x, y, d, v);
          r.move();
          r.turnLeft();
          r.turnLeft();
        }
      }
    }
    r.turnLeft();
    r.turnLeft();
  }
}
