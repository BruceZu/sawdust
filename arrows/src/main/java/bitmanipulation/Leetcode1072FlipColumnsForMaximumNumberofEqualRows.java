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

package bitmanipulation;

import java.util.HashMap;
import java.util.Map;

public class Leetcode1072FlipColumnsForMaximumNumberofEqualRows {
  public int maxEqualRowsAfterFlips(int[][] matrix) {
    /*
    m == matrix.length
    n == matrix[i].length
    1 <= m, n <= 300
    matrix[i][j] is either 0 or 1.

    You can choose any number of columns in the matrix and flip every cell in that column
    (i.e., Change the value of the cell from 0 to 1 or vice versa).
    Return the maximum number of rows that have all values equal afterome number of flips.
    Example 1:  [[0,1],[1,1]]
    Output: 1
    Example 2:  [[0,1],[1,0]]
    Output: 2
    Example 3:  [[0,0,0],[0,0,1],[1,1,0]]
    Output: 2
    */
    /*
    Idea:
    For a given row[i], 0<=i<m, row[j], 0<=j<m and j!=i, if either of them can have
    all values equal after some number of flips, then
     row[i]==row[j]  <1> or
     row[i]^row[j] == 111...111 <2> (xor result is a row full of '1')

    Go further, in case<2> row[j] can turn to row[i] by flipping each column of row[j]
    IF assume row[i][0] is 0, then question is convert into:
     1> flipping each column of each row if row[i][0] is not '0',
     2> count the frequency of each row.
     The biggest number of frequencies is the answer.
     */

    // O(M*N), int M = matrix.length, N = matrix[0].length;
    int answer = 0;
    Map<String, Integer> frequency = new HashMap<>();
    for (int[] row : matrix) {
      StringBuilder rowStr = new StringBuilder();
      for (int c : row) {
        if (row[0] == 1) {
          rowStr.append(c == 1 ? 0 : 1);
        } else rowStr.append(c);
      }
      String key = rowStr.toString();
      int value = frequency.getOrDefault(key, 0) + 1;
      frequency.put(key, value);
      answer = Math.max(answer, value);
    }
    return answer;
  }

  public static void main(String[] args) {
    System.out.println( Integer.MAX_VALUE);
  }
}
