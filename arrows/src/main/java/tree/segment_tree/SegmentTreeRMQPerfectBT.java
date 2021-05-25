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
 make use of the key trick of heap sort.
 important parameter N = 2^{ceil(log^L)}
 - with it to to calculate the storage
 - with it to calculate each no leaf node covered interval bound of min/max/sum ..
 E.g. for a original flat array A[] with length 5
      then take it as a perfect binary tree then the leaf node number is
      N = 2^{ceil(log^L)}=8 is `0b 1000`
      other nodes number in total is `0b 100`+`0b 10`+`0b 1` =`0b 111`
      The ST tree in total need  2 * N -1= 8 +  `0b 111` = 15 nodes.
       While 2 BITs only need 2*(L+1)= 12

 ST is build with a perfect binary tree in this class
*/

/*
  index of ST is 1-based to make code a bit easier
  - E.g. i is the index of ST[],  ST[i] children index is  i<<1, i<<1 ^ 1  or i << 1 | 1

  L is the length of the original flat array A[]
  L is also the number of leaf nodes

  N is the number of leaf nodes of perfect tree
  N = 2^{ceil(log^L)}
  N is also the 1-based index of A[0] in ST[];
  N-1 is the number of the no-leaf nodes

  in total the perfect tree need 2*N-1 elements.
  Let ST is 1-based index array, so in total the length of
  ST is 2*N. ST[0] will not be used.

  for a given node ST[idx]
  - its parent index p in ST of node is idx/2
  - its left child node index is 2*idx;
  - its right child node index is 2*idx+1

  function f can be min/max/sum/greatest common divisor in  a range.
  ST works with any associative operation.

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
    ST = new int[2 * N];
    // initial value for set()
    for (int i = N; i < 2 * N; i++) {
      // for Range Minimum Query
      ST[i] = Integer.MAX_VALUE;
      // for Range MAX Query  ST[i]= Integer.MIN_VALUE;
      // for Range SUM Query  ST[i]= 0;
    }
    for (int i = 0; i < L; i++) ST[i + N] = A[i];
    for (int i = N - 1; i >= 1; i--) ST[i] = ST[i << 1] + ST[i << 1 | 1];
  }

  /*
    after update A[] on index i with new value v
    update ST[] accordingly
    O(logN)
    This reevaluation is correct because operation is associative.
    Please note that this code is not suitable if operation is not commutative.
  */
  public void set(int i, int v) {
    int idx = N + i;
    ST[idx] = v;
    while (idx > 1) {
      ST[idx >>> 1] = f.apply(ST[idx], ST[idx ^ 1]);
      idx >>>= 1;
    }
  }

  /*
   This function is to query min/max/sum for the index range of elements in original
   array A[]:  [x, y].
   idx is 1-based index of ST[],
   l,r,x,y are integer and are 0-based index of original flat array A[].
   interval [l, r] contains interval [x, y].

   BT[idx] covers index range of elements in original array A[] is [l, r]
   E.g.:
   BT[1] covers index range of elements in original array A[] is [0, N-1],
   Note: it is NOT [0, L-1] And  r-l+1 always is *** a power of 2 ***.
   Leaf nodes: BT[N], ..., BT[N+L-1] covers the index range of elements in original
   array A[]: [0,0], ...,[L-1, L-1]

   idx is used to get
   - the index  2*idx    of left child node which covers original array A[] index range  [l, m]
   - the index  2*idx +1 of right child node which covers original array A[] index range [m+1, r]
   m = l+ (r-l)/2

   [x,y] is split into at most two per level where one node has only 2 children.
   2*logN ->  O(logN) time
  */
  private int queryTopDown(int idx, int l, int r, int x, int y) {
    if (l == x && r == y) return ST[idx];
    int m = l + r >>> 1; /* split [l,r] into [l,m] [m+1,r] */
    // default value of not applied index range d:
    // for sum: 0
    // for min: Integer.MAX_VALUE
    // for max: Integer.MIN_VALUE,
    int d = Integer.MAX_VALUE;
    return f.apply(
        x <= m ? queryTopDown(2 * idx, l, m, x, (Math.min(m, y))) : d,
        y > m ? queryTopDown(2 * idx + 1, (m + 1), r, (Math.max(x, (m + 1))), y) : d);
  }
  /*
  return the min/max/sum in the index range [x,y] in
  the original flat array A[]
  */
  public int queryTopDown(int x, int y) {
    return queryTopDown(1, 0, N - 1, x, y);
  }
  /*
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
  int queryBottomUp(int l, int r) { // on index range [l, r] in original flat array A[]
    l += N;
    r += N;
    // initial answer a with
    // for sum: 0
    // for min: Integer.MAX_VALUE
    // for max: Integer.MIN_VALUE,
    int a = Integer.MAX_VALUE;
    while (l <= r) {
      if ((l & 1) == 1) f.apply(a, ST[l++]);
      if ((r & 1) == 0) f.apply(a, ST[r--]);
      l >>>= 1;
      r >>>= 1;
    }
    return a;
  }
}
