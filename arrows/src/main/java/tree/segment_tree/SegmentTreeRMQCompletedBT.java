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
build ST with completed tree
ST in this case keeps value not index of original flat array A[]
ST need at most storage L+ N - 1 in total
N = 2^{ceil(log^L)}
L is the length of original flat array A[]
To make ST's index to be 1-based index for  concise code
so the length of ST at most L + N
If current node index is idx then
- left child index is 2 * idx
- right child index is 2 * idx+1
*/
class SegmentTreeRMQCompletedBT {
  // original flat array A[] length;
  private final BiFunction<Integer, Integer, Integer> f;
  private final int[] ST; // 1-based index
  private final int L;

  SegmentTreeRMQCompletedBT(int[] A) {
    f = (a, b) -> Math.min(a, b);
    L = A.length;
    int N = (int) Math.pow(2, (int) Math.ceil(Math.log(L) / Math.log(2)));
    ST = new int[L + N];
    build(A);
  }

  /*
  O(L + N) time
   */
  private void build(int idx, int l, int r, int[] A) {
    if (l == r) {
      ST[idx] = A[l]; // Leaf node will have a single element
    } else {
      int mid = l + r >>> 1;
      build(2 * idx, l, mid, A);
      build(2 * idx + 1, mid + 1, r, A);

      ST[idx] = f.apply(ST[2 * idx], ST[2 * idx + 1]);
    }
  }

  private void build(int[] A) {
    build(0, 0, A.length - 1, A);
  }

  /*
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
    } else {
      int m = l + r >>> 1;
      if (i <= m) update(2 * idx, l, m, i, v);
      else update(2 * idx + 1, m + 1, r, i, v);
      ST[idx] = f.apply(ST[2 * idx], ST[2 * idx + 1]);
    }
  }

  public void update(int i, int v) {
    update(1, 0, L - 1, i, v);
  }

  /*
  l,r,x,y are index of original flat array A[]
  Assume [l, r] contains [x,y]
  ST[idx] covered index range[l, r]
  query the min/max/sum/... of elements in the index range[x,y] in A[]
  Idea:
  [l, r] and [x,y] form 4 status:  EMPTY, IN_MIDDLE, SIDE, FULL.
  - EMPTY -> end;
  - FULL  -> binary search: find or result is not here.
  - MIDDLE---cut---> EMPTY, MIDDLE || SIDE
  - SIDE     ---cut---> SIDE, EMPTY || SIDE, FULL

   Start status can be any status.
   O(logL) time.
  */
  int query(int idx, int l, int r, int x, int y) {
    //  [l ,r]  are all outside [x,y]
    // default value for not applied index range[l,r]
    // for sum: 0
    // for min: Integer.MAX_VALUE
    // for max: Integer.MIN_VALUE,
    if (y < l || r < x) return Integer.MAX_VALUE; // EMPTY

    // [l ,r]  are all inside [x,y]
    if (x <= l && r <= y) return ST[idx]; // FULL

    //  MIDDLE or SIDE, need cut
    int m = x + y >>> 1;
    return f.apply(query(2 * idx, l, m, x, y), query(2 * idx + 1, m + 1, r, x, y));
  }
}
