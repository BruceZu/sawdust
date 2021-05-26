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
  build ST with completed tree, But ST itself is a full balanced binary tree.
  ST in this case keeps value, not index of value in original flat array A[]
  ST need at most storage N + N - 1
  N = 2^{ceil(log^L)}
  L is the length of original flat array A[]
  ST is 1-based index, the length is 2 * N
  Why isn't it L + N?
      L   N   L+N   index   issue
      12  16  28    0~27    ST[14](9,10) need left child  ST[28](9,9)
      13, 16  29    0~28    ST[14](    ) need right child ST[29](11,11)
      20, 32  52    0~51    ST[28](    ) need left child  ST[56](15,15)
  A[i] child index is 2 * i, 2 * i^1
*/
class SegmentTreeRMQCompletedBT {
  private final BiFunction<Integer, Integer, Integer> f;
  private final int[] ST; // 1-based index
  private final int L;

  SegmentTreeRMQCompletedBT(int[] A) {
    f = (a, b) -> Math.min(a, b);
    L = A.length;
    int N = (int) Math.pow(2, (int) Math.ceil(Math.log(L) / Math.log(2)));
    ST = new int[2 * N];
    build(A);
  }

  private void build(int[] A) {
    build(1, 0, L - 1, A);
  }

  // top-down, O(L) time
  private void build(int idx, int l, int r, int[] A) {
    if (l == r) {
      ST[idx] = A[l]; // Leaf node
      return;
    }
    int m = l + r >>> 1;
    build(2 * idx, l, m, A);
    build(2 * idx + 1, m + 1, r, A);
    ST[idx] = f.apply(ST[2 * idx], ST[2 * idx + 1]); // merge
  }

  /* top-down
  l, r, i are index of original flat array A[]
  Assume i in [l, r]
  v is A[i]
  idx is the index of ST[]
  ST[idx] covered the index range [l,r] in A[]
  This function update ST[] when A[i] is updated to value v;
  O(logL)
   */
  private void update(int idx, int l, int r, int i, int v) {
    if (l == r) {
      ST[idx] = v; // leaf node
      return;
    }
    int m = l + r >>> 1;
    if (i <= m) update(2 * idx, l, m, i, v);
    else update(2 * idx + 1, m + 1, r, i, v);
    ST[idx] = f.apply(ST[2 * idx], ST[2 * idx + 1]);
  }

  public void update(int i, int v) {
    update(1, 0, L - 1, i, v);
  }

  /* top-down
  l,r,x,y are index of original flat array A[]
  Assume [l, r] contains [x,y]
  ST[idx] covered index range[l, r]
  query the min/max/sum/... of elements in the index range[x,y] in A[]
  Idea:
  [l, r] and [x,y] form 4 status:  EMPTY, IN_MIDDLE, SIDE, FULL.
  - EMPTY -> end;
  - FULL  -> binary search: find or result is not here.
             looking for only node(s) that match exactly with the queried range
  - MIDDLE ---cut---> EMPTY, MIDDLE || SIDE
  - SIDE   ---cut---> SIDE, EMPTY   || SIDE, FULL

   Start status can be any status.
   O(logL) time.
   Note: [x,y] keep immutate
  */
  private int query(int idx, int l, int r, int x, int y) {
    //  [l ,r]  are all outside [x,y]
    // default value for not applied index range[l,r]
    // for sum: 0
    // for min: Integer.MAX_VALUE
    // for max: Integer.MIN_VALUE,
    if (y < l || r < x) return Integer.MAX_VALUE; // EMPTY
    if (x <= l && r <= y) return ST[idx]; // FULL
    // MIDDLE or SIDE, need cut
    int m = l + r >>> 1;
    return f.apply(query(2 * idx, l, m, x, y), query(2 * idx + 1, m + 1, r, x, y));
  }

  public int query(int x, int y) {
    return query(1, 0, L - 1, x, y);
  }
}
