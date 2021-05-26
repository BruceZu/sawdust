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

package tree.segment_tree;

import java.util.function.BiFunction;

/*
 build ST with completed tree. All operation are bottom-up
*/
public class SegmentTreeRMQ {
  private final int[] ST;
  private final int L;
  private final BiFunction<Integer, Integer, Integer> f;

  // O(L) time
  public SegmentTreeRMQ(int[] A) {
    f = (a, b) -> Math.min(a, b); // for RMQ
    L = A.length;
    ST = new int[2 * L];
    // keep value not index of A[]
    for (int i = 0; i < L; i++) ST[i + L] = A[i];
    for (int i = L - 1; i >= 1; i--) ST[i] = f.apply(ST[i << 1], ST[i << 1 | 1]);
  }

  // O(logL) time
  public void update(int i, int v) {
    i = L + i;
    ST[i] = v;
    while (i > 1) {
      ST[i >>> 1] = f.apply(ST[i], ST[i ^ 1]);
      i >>>= 1;
    }
  }
  // O(logL) time
  int query(int l, int r) { // on index range [l, r] in original flat array A[]
    l += L;
    r += L;
    // initial answer a with
    // for sum: 0
    // for min: Integer.MAX_VALUE
    // for max: Integer.MIN_VALUE,
    int a = Integer.MAX_VALUE; // for RMQ
    while (l <= r) {
      if ((l & 1) == 1) a = f.apply(a, ST[l++]);
      if ((r & 1) == 0) a = f.apply(a, ST[r--]);
      l >>>= 1;
      r >>>= 1;
    }
    return a;
  }
}
