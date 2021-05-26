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

public class Leetcode307RangeSumQueryMutable {
  // ----- ST with completed binary tree --------------------------------------
  // ----- BS its self is a full balanced binary tree -------------------------
  static class NumArray3 {
    private final BiFunction<Integer, Integer, Integer> f;
    private final int[] ST; // 1-based index
    private final int L;

    public NumArray3(int[] A) {
      f = (a, b) -> a + b;
      L = A.length;
      int N = (int) Math.pow(2, (int) Math.ceil(Math.log(L) / Math.log(2)));
      ST = new int[2 * N]; // L+N is not enough e.g. L=15000, N=16384.
      build(A);
    }

    private void build(int[] A) {
      build(1, 0, L - 1, A);
    }

    private void build(int idx, int l, int r, int[] A) {
      if (l == r) {
        ST[idx] = A[l];
        return;
      }
      int m = l + r >>> 1;
      build(2 * idx, l, m, A);
      build(2 * idx + 1, m + 1, r, A);
      ST[idx] = f.apply(ST[2 * idx], ST[2 * idx + 1]);
    }

    private void update(int idx, int l, int r, int i, int v) {
      if (l == r) {
        ST[idx] = v;
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

    private int sumRange(int idx, int l, int r, int x, int y) {
      // default value for not applied index range[l,r]
      // for sum: 0
      // for min: Integer.MAX_VALUE
      // for max: Integer.MIN_VALUE,
      if (y < l || r < x) return 0; // EMPTY
      if (x <= l && r <= y) return ST[idx]; // FULL
      // MIDDLE or SIDE, need cut
      int m = l + r >>> 1;
      return f.apply(sumRange(2 * idx, l, m, x, y), sumRange(2 * idx + 1, m + 1, r, x, y));
    }

    public int sumRange(int x, int y) {
      return sumRange(1, 0, L - 1, x, y);
    }
  }
  // -----------------------------------ST with perfect tree-------------------
  static class NumArray2 {
    private final int[] ST;
    private final int N;
    private final BiFunction<Integer, Integer, Integer> f;

    public NumArray2(int[] A) {
      f = (a, b) -> a + b;
      int L = A.length;
      N = (int) Math.pow(2, (int) Math.ceil(Math.log(L) / Math.log(2)));
      ST = new int[2 * N];
      for (int i = N; i < 2 * N; i++) {
        ST[i] = 0; // this is default value.Add this loop just for reminder
        // for Range min Query  ST[i]= Integer.MAX_VALUE;
        // for Range MAX Query  ST[i]= Integer.MIN_VALUE;
        // for Range SUM Query  ST[i]= 0;
      }
      for (int i = 0; i < L; i++) ST[i + N] = A[i];
      for (int i = N - 1; i >= 1; i--) ST[i] = f.apply(ST[i << 1], ST[i << 1 | 1]);
    }

    public void update(int i, int v) {
      int idx = N + i;
      ST[idx] = v;
      while (idx > 1) {
        ST[idx >>> 1] = f.apply(ST[idx], ST[idx ^ 1]);
        idx >>>= 1;
      }
    }

    private int sumRange(int idx, int l, int r, int x, int y) {
      if (x > y) return 0; // EMPTY
      if (l == x && r == y) return ST[idx]; // FULL
      int m = l + r >>> 1;
      // MIDDLE || SIDE
      // default value of not applied index range d:
      // for sum: 0
      // for min: Integer.MAX_VALUE
      // for max: Integer.MIN_VALUE
      int d = 0;
      return f.apply(
          x <= m ? sumRange(2 * idx, l, m, x, (Math.min(m, y))) : d,
          y > m ? sumRange(2 * idx + 1, (m + 1), r, (Math.max(x, (m + 1))), y) : d);
    }

    public int sumRange(int x, int y) {
      return sumRange(1, 0, N - 1, x, y);
    }

    int sumRange2(int l, int r) {
      l += N;
      r += N;
      // initial answer a with
      // for sum: 0
      // for min: Integer.MAX_VALUE
      // for max: Integer.MIN_VALUE,
      int a = 0;
      while (l <= r) {
        if ((l & 1) == 1) a = f.apply(a, ST[l++]);
        if ((r & 1) == 0) a = f.apply(a, ST[r--]);
        l >>>= 1;
        r >>>= 1;
      }
      return a;
    }
  }
  // --------------------------- ST with completed binary tree ----------------
  /*
   build ST with completed tree. all operation are bottom-up
  */
  static class NumArray1 {
    private final int[] ST;
    private final int L;
    private final BiFunction<Integer, Integer, Integer> f;

    // O(L) time
    public NumArray1(int[] A) {
      f = (a, b) -> a + b; // for RMQ
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
    int sumRange(int l, int r) { // on index range [l, r] in original flat array A[]
      l += L;
      r += L;
      // initial answer a with
      // for sum: 0
      // for min: Integer.MAX_VALUE
      // for max: Integer.MIN_VALUE,
      int a = 0;
      while (l <= r) {
        if ((l & 1) == 1) a = f.apply(a, ST[l++]);
        if ((r & 1) == 0) a = f.apply(a, ST[r--]);
        l >>>= 1;
        r >>>= 1;
      }
      return a;
    }
  }
  // ---------------------------    BIT  --------------------------------------
  static class NumArray {
    private final int[] t;
    private int[] A;

    NumArray(int[] A) {
      t = new int[A.length + 1];
      this.A = A;
      build(A);
    }

    // O(N）
    private void build(int[] A) {
      for (int i = 0; i < A.length; i++) {
        int idx = i + 1;
        t[idx] += A[i]; // +=, not =, BIT for prefix sum.
        int next = idx + (idx & -idx);
        if (next <= A.length) t[next] += t[idx]; // update parent, +=, not =; it is  t[idx], not A[i]
      }
    }

    private void add(int idx, int delta) {
      while (idx < t.length) {
        t[idx] += delta; // BIT for prefix sum.
        idx += idx & -idx;
      }
    }

    /*
    update A[i]=new_v
    accordingly update BIT[]
    require operation support inverse,
    because need delta; BIT[] only know add operation
    update A[i] == A[i]+ (new_v -A[i]); the delta can be negative or positive
    */
    public void update(int i, int new_v) {
      int delta = new_v - A[i];
      A[i] = new_v;
      add(i + 1, delta);
    }

    // BIT for prefix sum.
    private int sumRange(int i) {
      int s = 0;
      // Note
      int idx = i + 1;
      while (idx >= 1) {
        s += t[idx]; // This is a BIT for prefix sum.
        idx -= idx & -idx;
      }
      return s;
    }

    // in index scope [l,r] of original flat array A[]
    public int sumRange(int l, int r) {
      return sumRange(r) - sumRange(l - 1);
    }
  }
}
