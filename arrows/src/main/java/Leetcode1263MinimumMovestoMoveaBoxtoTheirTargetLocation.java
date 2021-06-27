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

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class Leetcode1263MinimumMovestoMoveaBoxtoTheirTargetLocation {
  /*
  Ask pushes box in a grid of  m x n, ach element is a wall, floor, or a box.
      to the target position 'T'
      rules:
        Player 'S' and can move up, down, left, right if it is a floor (empty cell).
        Floor: '.'
        Wall : '#'
        only one box 'B', one target cell 'T'
        Player 'S' cannot walk through the box.

  Return the minimum number of pushes
  return -1 if it is impossible

     m == grid.length
     n == grid[i].length
     1 <= m <= 20
     1 <= n <= 20
     grid contains only characters '.', '#',  'S' , 'T', 'B'.
     There is only one character 'S', 'B' and 'T' in the grid.


  Idea:
    concern:
    1> how to avoid repeat or endless loop of person walking and box moving
     answer: S can repeat location when B is in different location,
             but for a given fix B location, S should not repeat  walking
             B should not repeat moving.
             need a visited variable to keep the visited status using box and person location
    2> if failed to move to any direction should restore the B to one of original location?
     answer: yeah, only when there is not any direction is possible to move on.
             the one of original location is decided by the priority value of heuristic
             So should keep Box and person location in status.
             heuristic decided by moved steps and manhattan distance.
             so the status include: manhattan distance, moved steps, Box and person location

   Solution:
    find the initial location of B, S, T
    A * search: BFS + priority queue.
                a priority queue keeping status + heuristic = manhattan + steps
                avoid repeating visited status: locations of person and box
      initial status in priority queue
      loop while queue is not empty
        1> if current B is T then return moved steps
        2> if current B is visited return, stop trying from this status
        3> make person + B location as visited
          try a step toward 4 direction of person.
            effective: try to move box a step toward 4 directions if box
                       have space to move & person can walk to the place to push
            details:
            from current status: person try walk a step toward 4 directions if
            person can walk: in grid and no # at next cell
            if the next cell for person to walk is box location and box's next cell
            is allow it to move:
               it is move box
            else
               it is only person walk.
            update related status and add to priority queue.

     out of loop return -1
     Variables:
      target T location [A,B],
      person S location [c,r] or [column, row]
      box B location [a,b]

     Note:
       location [x,y] : [column, row]

    O(M^2*N^2) time and space used by visited and priority queue
  */
  int A, B;
  int[][] d4 = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

  public int minPushBox(char[][] grid) {
    int _a = 0, _b = 0, _x = 0, _y = 0;
    int R = grid.length, C = grid[0].length;
    // O(M*N) time
    for (int i = 0; i < R; i++) {
      for (int j = 0; j < C; j++) {
        char v = grid[i][j];
        if (v == 'T') {
          A = j;
          B = i;
        }
        if (v == 'B') {
          _a = j;
          _b = i;
        }
        if (v == 'S') {
          _x = j; // x: column
          _y = i; // y: rows
        }
      }
    }
    Set<String> v = new HashSet();
    Queue<int[]> q = new PriorityQueue<>((o, oo) -> o[0] + o[1] - oo[0] - oo[1]);
    q.offer(new int[] {Math.abs(_a - A) + Math.abs(_b - B), 0, _x, _y, _a, _b});
    // O(M^2*N^2) time and space used by visited and priority queue
    while (!q.isEmpty()) {
      int[] c = q.poll();
      int a = c[4], b = c[5], steps = c[1];
      if (a == A && b == B) return steps;

      int x = c[2], y = c[3];
      String k = x + "" + y + "" + a + "" + b;
      if (v.contains(k)) continue;
      v.add(k);

      // O(1) time and space
      for (int[] d : d4) {
        int x_ = x + d[0], y_ = y + d[1];
        if (!can(x_, y_, R, C, grid)) continue;
        if (x_ == a && y_ == b) {
          int a_ = a + d[0], b_ = b + d[1];
          if (!can(a_, b_, R, C, grid)) continue;
          q.offer(new int[] {Math.abs(a_ - A) + Math.abs(b_ - B), steps + 1, x_, y_, a_, b_});
        } else q.offer(new int[] {c[0], c[1], x_, y_, a, b});
      }
    }
    return -1;
  }

  // can walk person or move box to location [x,y]
  // O(1) time and space
  private boolean can(int x, int y, int R, int C, char[][] grid) {
    // note it is grid[y][x]
    if (x < 0 || y < 0 || x >= C || y >= R || grid[y][x] == '#') return false;
    return true;
  }
  /*
      grid = [["#","#","#","#","#","#"],
              ["#","T","#","#","#","#"],
              ["#",".",".","B",".","#"],
              ["#",".","#","#",".","#"],
              ["#",".",".",".","S","#"],
              ["#","#","#","#","#","#"]]
      Output: 3

      grid = [["#","#","#","#","#","#"],
              ["#","T","#","#","#","#"],
              ["#",".",".","B",".","#"],
              ["#","#","#","#",".","#"],
              ["#",".",".",".","S","#"],
              ["#","#","#","#","#","#"]]
      Output: -1

      grid = [["#","#","#","#","#","#"],
              ["#","T",".",".","#","#"],
              ["#",".","#","B",".","#"],
              ["#",".",".",".",".","#"],
              ["#",".",".",".","S","#"],
              ["#","#","#","#","#","#"]]
      Output: 5

      grid = [["#","#","#","#","#","#","#"],
              ["#","S","#",".","B","T","#"],
              ["#","#","#","#","#","#","#"]]
      Output: -1
  */
}
