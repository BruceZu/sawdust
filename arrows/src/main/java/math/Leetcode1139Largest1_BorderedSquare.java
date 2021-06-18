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

public class Leetcode1139Largest1_BorderedSquare {
  /*
  return 0 if such a subgrid doesn't exist in the grid.
  Note:
     select the right array from  vertical and horizontal during
     checking the left and top edge length of current square.
  O(M*N) space,  O(M*N*L) time
  M=grid.length,N=grid[0].length, L is min{ max value of v, max value of h}
  */

  public int largest1BorderedSquare(int[][] grid) {
    int M = grid.length, N = grid[0].length;
    int[][] v = new int[M][N], h = new int[M][N];
    for (int i = 0; i < M; i++) {
      for (int j = 0; j < N; j++) {
        if (grid[i][j] == 1) {
          v[i][j] = i == 0 ? 1 : v[i - 1][j] + 1;
          h[i][j] = j == 0 ? 1 : h[i][j - 1] + 1;
        }
      }
    }
    int l = 0; // return 0  if such a subgrid doesn't exist in the grid.
    for (int i = M - 1; i >= 0; i--) {
      for (int j = N - 1; j >= 0; j--) {
        int min = Math.min(v[i][j], h[i][j]);
        while (min > l) {
          if (v[i][j - min + 1] >= min && h[i - min + 1][j] >= min) l = min;
          min--;
        }
      }
    }
    return l * l;
  }
}
