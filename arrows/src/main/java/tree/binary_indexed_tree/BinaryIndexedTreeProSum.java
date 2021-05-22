//  Copyright 2016 The Sawdust Open Source Project
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

package tree.binary_indexed_tree;

import java.util.Arrays;

/*
  https://www.youtube.com/watch?v=v_wj_mOAlig
  https://en.wikipedia.org/wiki/Fenwick_tree
  https://www.topcoder.com/community/data-science/data-science-tutorials/binary-indexed-trees/
  Notes:
    1. BIT array length = array length + 1;
       Because shift operation does not apply to 0.
       BIT[0] is not used, BIT array index is 1 based.
    2. BIT[i] is the value of **f(segment)**, the segment is 1 or few continuous element(s) in the
       original array: A[?],,,A[i-1] and the number of elements and who they are in the segment is
       decided by the index i, the function of f can be sum, xor, product, thus f(segment) is apart
       of the prefix sum or prefix product or prefix xor of A[0],,,A[i-1].
       take BIT for pre sum as example:
       e.g. BST[12]
                BST' index  12 = 0b1100
                last bit‘1’ is 4 = x & (-x)
                So the value of BST[12] is sum of 4 elements of original flat array:
                start from the 12th: array【11】 including.
                BIT[12]=array【11】+array【10】+array【9】+array【8】
                        12th　　　　 11th　　　  10th　　　 9th
        So  BIT[i] keep sum(i & (-i) elements), they are: array[i-i & (-i)],..., array[i-1]
        For BIT[i] the number be covered by it in the original array element is the value
                   of the right most bit of binary i.

       Thus the sum of the original array first 12 members?
       sum of first 12 members = 0b 1100＝0b 1000 + 0b 0100
                                             8　　　　４
                  　   ４　＝　　　  x & (-x)　saved in BST[0b 1100]
                 　    8  ＝　x - ( x & (-x)) saved in BST[0b 1000]

       see sum(i) function: how to collect all segments from different
       element of BIT to calculate sum(i) by shifting from the last index to
       previous index of BST one by one:
       sum(11):
       the pre sum of index scope: 0,...,11 in original array.
       in BIT it starts from idx 12:
       12 = 0b 1100,                   BIT[0b 1100] keeps sum of 4 elements in original array
       x  = x - ( x & (-x)) = 0b 1000; BIT[0b 1000] keeps sum of 8 elements in original array
       x  = x - ( x & (-x)) = 0      ; stop
       sum of first 12 members = BIT[0b 1100]  + BIT[0b 1000]
                               = BIT[12]       + BIT[8]
      in the same way, the pre sum of index scope [0, i] of original array are kept in
      segment(s), the number of the segment(s) is decided by the number of '1' bit in the number i+1.
      and the number of the covered element in each segment is decided by the value of
      that segment related bit '1' in the number i+1. it is a value of exponent of 2
      e.g. sum(12) the first 13 members' sum in original flat array:
      13： 0b1101 have 3 '1' bit, so sum(12) is separated in 3 segment
           0b1101 = 0b1000      +   0b0100    +  0b0001
                    8 elements      4            1
      kept in BIT:  BIT[0b1000]     BIT[0b1100]  BIT[0b1101]
      0b1101 is the first; next x=x-x&(x-1); till x==0;
      runtime O(1ogn)

     <img src="../../../resources/BIT_sum13.png" height="400" width="600">
     <img src="../../../resources/BIT_sum7.png" height="400" width="600">
    3 The hard part is how to convert a given flat array to its BIT
      Building BIT can be done add operation after find the relation between original flat array and its BIT
      It still comes from the idx of BIT.
      array[i] is added a value, the what will happen on idx=i+1 in BIT?
      BIT[idx] should add the value too. is that all?
      the following members in BIT are also need updated, because they
      also keep a segment of elements in the original array that covering this changed one.
      All the index of all these members in BIT are right to that of BIT[idx] and also are
      a value of exponent of 2
      watch a case:
      e.g.:  Change happened on the original array array[12], the 13th member. It is added some value.
     <img src="../../../resources/BIT_add.png" height="400" width="600">
       calculated next index of BIT
              13   0b    1101
         next 14   0b    1110
         next 16   0b   10000
         next 32   0b  100000
         next 64   0b 1000000
       till reach or out of the length of BIT
       runtime is O(1ogn)
       x is current index; next index: x + ( x & (-x))
       Also see this case to know why not affect idx inf BIT if it is not exponent of 2
       if array[27] is changed, in BIT, BIT[28] is affected, next is BIT[32]
       Ob 011100   16 + 8 + 4     = 28
       Ob 011101   16 + 8 + 4 + 1 = 29 depends on the change on BIT[28]
       Ob 011110   16 + 8 + 4 + 2 = 30 depends on the change on BIT[28]
       Ob 011111   16 + 8 + 4 + 3 = 31 depends on the change on BIT[28]
       Ob 100000                    32


    4. usage: based on BIT to figure out any sub array's sum, product and xor in scenario where
       there is a streaming of value update, not changing the array length, and ask for
       any sub array's sum, product and xor
      - sum sum(i, j】 = sum(j) - sum(i);
      - product
      - xor
    5. but not for min/max (using segment tree)

 This is a BIT for keeping pre sum of original array 'A'
 `i` is o based index of original flat array
 'idx' is 1 based index of `bit tree` array
*/
public class BinaryIndexedTreeProSum {
  private final int[] t; // bit tree

  BinaryIndexedTreeProSum(int[] A) {
    t = new int[A.length + 1];
    for (int i = 0; i < A.length; i++) add(i + 1, A[i]);
  }

  public void add(int idx, int delta) {
    while (idx < t.length) { // O(logN)
      t[idx] += delta; // This is a BIT for prefix sum.
      idx += idx & -idx;
    }
  }

  // usage: pre sum
  public int sum(int i) {
    int sum = 0;
    // Note
    int idx = i + 1;
    while (idx >= 1) { // O(logN)
      sum += t[idx]; // This is a BIT for prefix sum.
      idx -= idx & -idx;
    }
    return sum;
  }

  // Original flat array A's subarray of index scope [l,r]
  public int sum(int il, int ir) {
    return sum(ir) - sum(il - 1);
  }

  @Override
  public String toString() {
    return Arrays.toString(t);
  }
}
