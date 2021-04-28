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

package array;

import java.util.LinkedList;
import java.util.List;

public class FindAllMissingIntervals {
  /*
  input: [2,3] len: N, sorted, no dup, 0 <= x <= K = 99
  output: "0,1,4-99"


  missing 0,1 => 0,1,...
  missing 0,1,2 => 0-2,...

  E.g.: K=99,  [4,5,8]
  return ? -> 0-3,6,7,9-99
  */

  // O(N) time
  private void addToResult(int l, int r, List<String> re) {
    if (r - l > 1) re.add(l + "-" + r);
    else {
      re.add(l + "");
      if (r != l) re.add(r + "");
    }
  }

  String[] printMissingNumbers(int[] A, int K) {
    List<String> re = new LinkedList();
    for (int i = 0; i < A.length; i++) {
      if (i == 0)
        if (A[i] == 0) continue;
        else addToResult(0, A[i] - 1, re);

      if (A[i - 1] != A[i] - 1) addToResult(A[i - 1], A[i] - 1, re);
    }
    if (A[A.length - 1] != K) addToResult(A[A.length - 1], K - 1, re);

    return re.toArray(new String[re.size()]);
  }
}
