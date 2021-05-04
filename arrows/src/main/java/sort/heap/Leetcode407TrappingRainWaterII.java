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

package sort.heap;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

/** <a href="https://leetcode.com/problems/trapping-rain-water-ii/?tab=Description">LeetCode</a> */
public class Leetcode407TrappingRainWaterII {
  static class E { // 'class' lower case
    int r, c, h;

    public E(int r, int c, int h) {
      this.r = r;
      this.c = c;
      this.h = h;
    }
  }
  // Get out the current water level cell, BFS to its unvisited neighbors:
  // record visited.
  // if the neighbor is lower,:
  //      sure it will keep water,
  //      assume it is filled water, update its level to current water level.
  // add the neighbors to the bounder circle.
  //
  // improvement: when visited neighbor is lower the current water level cell
  // continue BFS from there  to save the process of put it in circle and fetch it out again.
  public int trapRainWater(int[][] heightMap) {
    // as there is at least 2 element
    if (heightMap == null || heightMap.length < 3 || heightMap[0].length < 3) return 0;
    // at least is is 3x3 matrix
    int m = heightMap.length;
    int n = heightMap[0].length;

    boolean[][] v = new boolean[m][n]; // default false

    Queue<E> q = new PriorityQueue<E>((Comparator<E>) (a, b) -> a.h - b.h);

    int[][] nb4 = new int[][] {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

    // initial q
    for (int i = 0; i < m; i++) {
      q.offer(new E(i, 0, heightMap[i][0]));
      v[i][0] = true;
      q.offer(new E(i, n - 1, heightMap[i][n - 1]));
      v[i][n - 1] = true;
    }
    for (int j = 0; j < n; j++) { // 4 corners are ignorable.
      q.offer(new E(0, j, heightMap[0][j]));
      v[0][j] = true;
      q.offer(new E(m - 1, j, heightMap[m - 1][j]));
      v[m - 1][j] = true;
    }
    // calculate result
    int res = 0;
    while (!q.isEmpty()) {
      E e = q.poll(); // no pop method
      for (int[] nb : nb4) {
        int r = e.r + nb[0], c = e.c + nb[1];
        if (0 <= r && r < m && 0 <= c && c < n && v[r][c] == false) { // valid E
          v[r][c] = true;
          //  there is waters only when cur elevation is lower than the polled one.
          res += Math.max(e.h - heightMap[r][c], 0);
          q.offer(new E(r, c, Math.max(e.h, heightMap[r][c]))); // keep the water level
        }
      }
    }
    return res;
  }
}
