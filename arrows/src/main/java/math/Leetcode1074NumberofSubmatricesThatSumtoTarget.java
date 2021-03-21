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

import java.util.HashMap;
import java.util.Map;

public class Leetcode1074NumberofSubmatricesThatSumtoTarget {
  // basic idea
  public static int numSubmatrixSumTarget2(int[][] matrix, int target) {
    /*
    Given a matrix and a target, return the number of non-empty submatrices that sum to target.

    1 <= matrix.length <= 100
    1 <= matrix[0].length <= 100
    -1000 <= matrix[i] <= 1000
    -10^8 <= target <= 10^8
     */

    /*
    Idea: prefix sum for calculating a_b=0_b-0_a for sub array and sub matrix
     */

    int M = matrix.length, N = matrix[0].length;
    // variable preSum can be saved and change matrix to preSum. But it is not good habit
    int[][] preSum = new int[M][N];
    for (int r = 0; r < M; r++) {
      preSum[r][0] = matrix[r][0];
      for (int c = 1; c < N; c++) {
        preSum[r][c] = matrix[r][c] + preSum[r][c - 1];
      }
    }
    int counts = 0;
    for (int cl = 0; cl < N; cl++) {
      for (int cr = cl; cr < N; cr++) {
        // sub matrix width is decided by [cl, cr]
        int matrixSum = 0;
        Map<Integer, Integer> sumCounts = new HashMap<>();
        // for calculating sub matrix sum by
        //   a_b=0_b-0_a where a_b is the target value and when 0_b is just the target value
        //   here provide a virtual sub matix with sum is 0.
        sumCounts.put(0, 1);
        for (int r = 0; r < M; r++) {
          int rowSum = preSum[r][cr] - (cl == 0 ? 0 : preSum[r][cl - 1]);
          matrixSum += rowSum;
          counts += sumCounts.getOrDefault(matrixSum - target, 0);
          sumCounts.put(matrixSum, sumCounts.getOrDefault(matrixSum, 0) + 1);
        }
      }
    }
    return counts;
  }
  // no comments version and use matrix itself as presum
  public static int numSubmatrixSumTarget(int[][] A, int target) {
    int M = A.length, N = A[0].length;
    for (int m = 0; m < M; m++) for (int c = 1; c < N; c++) A[m][c] = A[m][c] + A[m][c - 1];
    int res = 0;
    for (int l = 0; l < N; l++) {
      for (int r = l; r < N; r++) {
        int sum = 0;
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, 1);
        for (int m = 0; m < M; m++) {
          sum += A[m][r] - (l == 0 ? 0 : A[m][l - 1]);
          res += map.getOrDefault(sum - target, 0);
          map.put(sum, map.getOrDefault(sum, 0) + 1);
        }
      }
    }
    return res;
  }

  public static void main(String[] args) {
    System.out.println(
        numSubmatrixSumTarget(
                new int[][] {
                  new int[] {1, -1},
                  new int[] {-1, 1}
                },
                0)
            == 5);
  }
}
