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

package matrix;

import java.util.List;

public class Leetcode1428LeftmostColumnwithatLeastaOne {
  interface BinaryMatrix {
    int get(int row, int col);

    List<Integer> dimensions();
  }

  public int leftMostColumnWithOne(BinaryMatrix binaryMatrix) {
    /*
    TODO: corner cases checking

      rows == mat.length
      cols == mat[i].length
      1 <= rows, cols <= 100
      mat[i][j] is either 0 or 1.
      mat[i] is sorted in non-decreasing order.
      */

    // O(N) time, O(1) space
    List<Integer> dimensions = binaryMatrix.dimensions();
    int M = dimensions.get(0), N = dimensions.get(1);
    int j = N - 1;
    for (int i = 0; i < M; i++) {
      while (j >= 0 && binaryMatrix.get(i, j) == 1) j--;
    }
    return j == N - 1 ? -1 : j + 1;
  }
}
