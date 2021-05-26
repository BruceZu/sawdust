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
/* build ST with perfect tree -------------------------------------------------
Basic
  like the trick of heap sort.
  N = 2^{ceil(log^L)}
  E.g. for a original flat array A[] with length 5
      Build ST with a perfect binary tree:
      N = 2^{ceil(log^L)}=8 is `0b 1000`
      other nodes number in total is `0b 100`+`0b 10`+`0b 1` =`0b 111`
      So the ST tree need  2 * N -1= 8 +  `0b 111` = 15 nodes.
      While 2 BITs only need 2*(L+1)= 12

  index of ST is 1-based, so in total the length is 2*N. ST[0] will not be used.
  i is the index of ST[], ST[i] children index is  i<<1, i<<1 ^ 1  or i << 1 | 1
  ST[i] sibling index is i^1;
  ST[i] parent index i >>> 1;

  L is the length of the original flat array A[]
  L is also the number of leaf nodes

  N is the number of leaf nodes of perfect tree
  N = 2^{ceil(log^L)}
  N is also the 1-based index of A[0] in ST[];
  N-1 is the number of the no-leaf nodes

  function f can be min/max/sum/greatest common divisor in a index range.
  ST works with any associative operation
*/
public class SegmentTreeRMQPerfectBT {
  private final int[] ST;
  private final int N;
  private final BiFunction<Integer, Integer, Integer> f;

  // O(N) time
  public SegmentTreeRMQPerfectBT(int[] A) {
    f = (a, b) -> Math.min(a, b);
    int L = A.length;
    N = (int) Math.pow(2, (int) Math.ceil(Math.log(L) / Math.log(2)));
    ST = new int[N << 1];

    for (int i = N; i < N << 1; i++) {
      // for Range min Query  ST[i]= Integer.MAX_VALUE;
      // for Range MAX Query  ST[i]= Integer.MIN_VALUE;
      // for Range SUM Query  ST[i]= 0;
      ST[i] = Integer.MAX_VALUE;
    }
    for (int i = 0; i < L; i++) ST[i + N] = A[i];
    // bottom-up
    for (int i = N - 1; i >= 1; i--) ST[i] = f.apply(ST[i << 1], ST[i << 1 | 1]);
  }

  /* bottom-up
    after update A[] on index i with new value v
    update ST[] accordingly
    O(logN)
    This reevaluation is correct because operation is associative.
    code is not suitable if operation is not commutative.
  */
  public void update(int i, int v) {
    int idx = N + i;
    ST[idx] = v;
    while (idx > 1) {
      ST[idx >>> 1] = f.apply(ST[idx], ST[idx ^ 1]);
      idx >>>= 1;
    }
  }

  /* top-down
   Query min/max/sum for the index range [x, y] of elements in original array A[]  .
   l,r,x,y are integer and are 0-based index of A[].

   BT[idx] covers index range [l, r] of elements in A[]
   BT[1] covers index range of elements in original array A[] is [0, N-1], NOT [0, L-1]
   r-l+1 is always a power of 2.
   Leaf nodes: BT[N], ..., BT[N+L-1] keep value of A[0],...,A[L-1]

   BT[idx] child:
   - BT[2*idx] covers index range    [l,   m] of A[]
   - BT[2*idx +1] covers index range [m+1, r]
     m = l+r >>>1;

   [x,y] is split into at most two part per level.
   [x,y] and [l,r] has 4 status

   O(logN) time
  */
  private int queryTopDown(int idx, int l, int r, int x, int y) {
    if (x > y) return 0; // EMPTY
    if (l == x && r == y) return ST[idx]; // FULL
    // MIDDLE || SIDE: need cut
    int m = l + r >>> 1;
    // default value of not applied index range d
    // for sum: 0
    // for min: Integer.MAX_VALUE
    // for max: Integer.MIN_VALUE
    int d = Integer.MAX_VALUE;
    return f.apply(
        // the [x,y] is mutate
        x <= m ? queryTopDown(idx << 1, l, m, x, (Math.min(m, y))) : d,
        y > m ? queryTopDown(idx << 1 ^ 1, (m + 1), r, (Math.max(x, (m + 1))), y) : d);
  }

  /*
    the min/max/sum in the index range [x,y] in the original flat array A[]
  */
  public int queryTopDown(int x, int y) {
    return queryTopDown(1, 0, N - 1, x, y);
  }

  /* bottom-up
   based on 1-based index
   at each level:
   - the index of the fist element of ST[] is power of 2. even number
   - each pair element with even, odd index has the same parent ST node.
   for the l index:
     if l is odd then need count its value before move it right with 1 step and jump to parent index from there.
     means result will cover current BS[l] but not cover its parent. it is right child of its parent node
     else result will cover its parent and ignore it.
   for the r index:
    if r is even then need count its value before move it left with 1 step and jump to parent index from there.
    means result will cover current BS[r] but not cover its parent. it is left child of its parent node
    else result will cover its parent and ignore it.

   stop the loop when l>r;
   when l==r:
     if it is odd: r will pick it up, l will ignore it.
     if it is even:  l will pick it up, r will ignore it.
   If operation is idempotent (E.g. minimum), both if-statements can be omitted
   and rewrite body of the for-loop in a single line:
   a = min(a, min(a[L], a[R])); https://codeforces.com/blog/entry/1256
  */
  public int query(int l, int r) { // on index range [l, r] in original flat array A[]
    l += N;
    r += N;
    // initial answer a with
    // for sum: 0
    // for min: Integer.MAX_VALUE
    // for max: Integer.MIN_VALUE,
    int a = Integer.MAX_VALUE;
    while (l <= r) {
      if ((l & 1) == 1) a = f.apply(a, ST[l++]);
      if ((r & 1) == 0) a = f.apply(a, ST[r--]);
      l >>>= 1;
      r >>>= 1;
    }
    return a;
  }
}
