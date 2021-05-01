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

package map;

import java.util.HashMap;
import java.util.Map;

public class Leetcode311SparseMatrixMultiplication {
  // O(M*N*K) time
  public int[][] multiplyForceWay(int[][] A, int[][] B) {
    int M = A.length, K = A[0].length, N = B[0].length;
    int[][] x = new int[M][N];
    for (int i = 0; i < M; i++) {
      for (int j = 0; j < N; j++) {
        for (int k = 0; k < K; k++) {
          if (A[i][k] != 0 && B[k][j] != 0) x[i][j] += A[i][k] * B[k][j];
        }
      }
    }
    return x;
  }

  public int[][] multiply(int[][] mat1, int[][] mat2) {
    /*
    m == mat1.length
    k == mat1[i].length == mat2.length
    n == mat2[i].length
    1 <= m, n, k <= 100
    -100 <= mat1[i][j], mat2[i][j] <= 100

    TODO：   checking test cases:
    - null of either of mat1 and mat2,
    - mat1[0] and mat2.length should be same
    - value range of each element
    Idea:
     1  sparse matrices-->
      "To save space and running time it is critical to only store the nonzero elements."
      convert both matrices to
         Map<Integer, Map<Integer, Integer>> data structure
                row index : (column index of current row,column cell: value)
             column index : (row    index of current row,column cell: value)
      It need O(M*k+k*N) time, the benefit is in the next dot multiplication:
       - the rows and lines with only zeros value are ignored automatically
       - the zero cell is ignored too
     2 calculate multiplication
      O(M'*N'*K') time.
      M' is number of no-zero values row,
      N' is number of no-zero values columns,
      K' is number of max{no zero values in each row}
    */

    int M = mat1.length, K = mat2.length, N = mat2[0].length;
    Map<Integer, Map<Integer, Integer>> rs = new HashMap();
    for (int i = 0; i < M; i++) {
      Map<Integer, Integer> t = new HashMap();
      for (int j = 0; j < K; j++) if (mat1[i][j] != 0) t.put(j, mat1[i][j]);
      if (!t.isEmpty()) rs.put(i, t);
    }
    Map<Integer, Map<Integer, Integer>> cs = new HashMap();
    for (int j = 0; j < N; j++) {
      Map<Integer, Integer> t = new HashMap();
      for (int i = 0; i < K; i++) if (mat2[i][j] != 0) t.put(i, mat2[i][j]);
      if (!t.isEmpty()) cs.put(j, t);
    }
    // multiplication
    int[][] a = new int[M][N];
    for (Map.Entry<Integer, Map<Integer, Integer>> r : rs.entrySet()) {
      for (Map.Entry<Integer, Map<Integer, Integer>> c : cs.entrySet()) {
        int x = 0;
        // improvement: select the one of current 2 pair set with small size.
        for (int index : r.getValue().keySet()) {
          if (c.getValue().keySet().contains(index))
            x += r.getValue().get(index) * c.getValue().get(index);
        }
        a[r.getKey()][c.getKey()] = x;
      }
    }
    return a;
  }
}
