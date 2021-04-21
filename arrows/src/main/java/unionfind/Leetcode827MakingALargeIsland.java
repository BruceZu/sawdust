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

package unionfind;

import java.util.*;

public class Leetcode827MakingALargeIsland {
  /*
   n == grid.length
   n == grid[i].length
   1 <= n <= 500
   grid[i][j] is either 0 or 1.
  */
  /*
  Note:
  1. The same island can connect to a 0 value cell more than one side .

  Idea like Union-Found:
  Pros:
   1. with updating the input grid it can save space used to mark the connection relation.
   2. find() of union-found is easy to implement with the color number
   3. need not `visited` variable in BFS and DFS
  Cons: have to updating the input grid.

   Steps:
   1. paint all connected 1 value cells with the color Id start from 2.
   2. keep the color ID: size in map
   3. try each 0 value cell

   O(N*M) Time, O(C) space
   M is row number, N is column number.
   C is colour number or island number.
   */
  public int largestIsland(int[][] grid) {
    // TODO: corner cases checking
    // distinguish islands with color
    int M = grid.length, N = grid[0].length;
    Map<Integer, Integer> size = new HashMap<>();
    int colour = 2;
    // painting
    for (int i = 0; i < M; i++) {
      for (int j = 0; j < N; j++) {
        if (grid[i][j] == 1) {
          size.put(colour, paint(i, j, grid, M, N, colour));
          colour++;
        }
      }
    }
    // try largest island from each 0 value cell
    int max = 0;
    for (int i = 0; i < M; i++) {
      for (int j = 0; j < N; j++) {
        if (grid[i][j] == 0) {
          Set<Integer> islands = new HashSet();
          add(i - 1, j, M, N, grid, islands);
          add(i + 1, j, M, N, grid, islands);
          add(i, j - 1, M, N, grid, islands);
          add(i, j + 1, M, N, grid, islands);
          int tmp = 1; // if cell is marked as 1
          for (int c : islands) {
            tmp += size.get(c);
          }
          max = Math.max(max, tmp);
        }
      }
    }
    // note max can be 0 in scenario where there is not any 0 value cell
    return max == 0 ? size.values().stream().max(Comparator.naturalOrder()).get() : max;
  }

  private void add(int i, int j, int M, int N, int[][] grid, Set<Integer> islands) {
    if (0 <= i && i < M && 0 <= j && j < N && grid[i][j] != 0) islands.add(grid[i][j]);
  }
  // Assume cell[r][c] value is 1.
  //  1 Start from cell[r][c] to paint all connected 1 value cell to be colour Id
  //  2 return size of painted cells
  // no `visited` variable and no `level` in BFS
  private int paint(int r, int c, int[][] g, int M, int N, int color) {
    if (r < 0 || r == M || c < 0 || c == N || g[r][c] != 1) return 0;
    // it is 1=1, it can be 0, 1, colourID

    g[r][c] = color;
    return 1
        + paint(r - 1, c, g, M, N, color)
        + paint(r + 1, c, g, M, N, color)
        + paint(r, c - 1, g, M, N, color)
        + paint(r, c + 1, g, M, N, color);
  }
}
