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

package binarysearch;

public class Leetcode4MedianofTwoSortedArrays {
  /*
  Ask:
    Given two sorted arrays nums1 and nums2 of size m and n respectively,
    return the median of the two sorted arrays.

      nums1.length == m
      nums2.length == n
      0 <= m <= 1000
      0 <= n <= 1000
      1 <= m + n <= 2000
      -10^6 <= nums1[i], nums2[i] <= 10^6


     The overall run time complexity should be O(log (m+n)).

      nums1 = [1,3], nums2 = [2]
      Output: 2.00000

      nums1 = [1,2], nums2 = [3,4]
      Output: 2.50000

      nums1 = [0,0], nums2 = [0,0]
      Output: 0.00000

      nums1 = [], nums2 = [1]
      Output: 1.00000

      nums1 = [2], nums2 = []
      Output: 2.00000

      nums1 = [1,3], nums2 = [2，2，2]
      Output: 2.00000

      nums1 = [], nums2 = []
      Output: this will not happen as '1 <= m + n <= 2000'

      nums1 = null, nums2 = []
      Output: this will not happen as
      ' 0 <= m <= 1000
        0 <= n <= 1000'
  */

  /*---------------------------------------------------------------------------
   no array is null, at least one array has at least one element.
   B[j-1] | B[j]
   A[m-1] | A[m]

   m is index in A, j is index in B.
   m + j = half = M + N >>> 1
   m =  l + r >>>1
   1>.
   l and r is possible index range of m in A, initialed as
   [0,  half or Math.min(half,A.length)] of
   (right) median index of A+B array
   0:
       in the merged A+B the left x elements are all from B
             min | A[0]
       B[half-1] | B[half]
   half or Math.min(half,A.length)
       in the merged A+B the left x elements are all from A
       A[half-1] | A[half]
             min | B[0]
   use 'while (true)' not use 'while (l <= r)' because it the result always can be
   find.
   need not check if (l > r) return -1; because always find the right m and j

   2.
   with m to get j by half:
   m + j = half which is A.length + B.length >>> 1, it is:
               the x in the even length 2*x   of A+B
               the x in the odd  length 2*x+1 of A+B
               (right) median index of merged A+B array

   try m with l and r and binary search
   evaluate the candidate m by j and 4 values in
   B[j-1] | B[j]
   A[m-1] | A[m]
   A, B and merged A+B may have repeated value, so use <= not < in:
     B[j-1]<=A[m] && A[m-1]<=B[j]
  Note
     e.g. A = [3,4], B = []
     Output: 3.5
     M:2, N:0, half:1
     l:0, r:1,
     m:0, j:1, j-1:0 (Note: in   v(int[] X, int i) method use >= in
       `if (i >= X.length) return Integer.MAX_VALUE;`
     A: min|3
     B: max|max
     l:1, r:1,
     m:1, j:0, j-1:-1
     A:   3|4
     B: min|max

   Algorithm pros: need not care which array is short or long
   O(log(M)) time, O(1) space;
  */
  public double findMedianSortedArrays(int[] A, int[] B) {
    // neither array is null and at least one array has at least one element
    int M = A.length, N = B.length, half = M + N >>> 1;
    int l = 0, r = Math.min(M, half);
    int m = 0, j = 0;
    while (l <= r) {
      m = l + r >>> 1;
      j = half - m;
      // A[m-1]|A[m]
      // B[j-1]|B[j]
      if (v(A, m - 1) <= v(B, j) && v(B, j - 1) <= v(A, m)) break;
      if (v(A, m - 1) > v(B, j)) r = m - 1;
      else l = m + 1; // v(B,j-1)>v(A,m))
    }
    if ((M + N & 1) == 1) return Math.min(v(A, m), v(B, j));
    return (Math.max(v(A, m - 1), v(B, j - 1)) + Math.min(v(A, m), v(B, j))) * 0.5;
  }

  private double v(int[] x, int i) {
    if (i < 0) return Integer.MIN_VALUE;
    if (i >= x.length) return Integer.MAX_VALUE;
    return x[i];
  }
  /*---------------------------------------------------------------------------
  Idea:
       median may be 1 for odd(M+N)
       - median may be in A or B
       median may be 2 for even (M+N)
       - left or right median may be in A or B

       median 0-based index: left: m+n-1/2, right: m+n/2
          m=2, n=3, left:2, right: 2
          m=3, n=3, left:2, right: 3
       median 1-based index: left: m+n+1/2, right: m+n+2/2
          m=2, n=3, left:3, right: 3
          m=3, n=3, left:3, right: 4

    where to cut?
    Name the shorter sorted array is B[] with length N, longer one as A[] with length M.
    B have 2*N+1 position to cut,
    (This is not true for the longer array. as its cut position is decided by the cut position in B)
    '|' is the cut position, it can be:
     <a>: between B[i] and B[j] ,  0<= i<j <=N, assume N>=2
       E.g.:
        B[]: [1,2] | max
        A[]:   [3, | 4,5,6]
     <b>: on B[i] itself  0<=i<=N, assume N>=1
       E.g.:
        B[]:   [3]
                | cut on 3 itself, means median is it, this can took as median = (it + it)/2
                                                                        it median it
                                                                        it   |    it
        A[]: [1,| 2]

    use enhanced B: EB and enhance A: EA, then no concern of cut
    within 2 array elements,and need not care the the total length is odd or even
    only cut on EB[i]:
      <a> become:
        EB[]: [#,1,#,2,#] max
                       | cut on '#'

        EA[]:   [#,3,#,4,#,5,#,6,#]
                     | cut on '#'
     <b> become:
       E.g.:
        B[]:   [#,3,#]
                  | cut on 3 itself
                  | cut on '#'
        A[]: [#,1,#,2,#]

     enhanced 2 arrays total length is 2*M+2*N+2
     EA length: 2*M+1
     EB length: 2*N+1
     each cut take one position, left element without cut is 2*M+2*N.
     rule: in EA and EB to keep the total element of left side of 2 cuts is M+N, thus
           the right side of 2 cuts in EA and EB in total is M+N too.
     2 cuts position as:
      EA[i-1] or min , EA[i] with cut, EA[i+1] or max
      EB[j-1] or min , EB[j] with cut, EB[j+1] or max

      Let L1 =EA[i-1] or min,   R1=EA[i+1] or max
          L2 =EB[j-1] or min,   R2=EB[j+1] or max

     try each possible cut position in EB by binary search and adjust the cut position in EB by
     Check   L1|R1
             L2|R2
     accordingly adjust the cut position in EA by keeping the above rule.


     if L1<=R2 and L2<=R1 the cuts is expected that give the median(s) value
     else if L1 > R2:  the expected cutting position in EB on the right of current one
     else if L2 > R1:  the expected cutting position in EB on the left of current one

    index in original array = idx in enhanced array/2 and
     original index:    0     1     2     3
              idx:   0  1  2  3  4  5  6  7  8
    if idx is odd, and it is cut or median is it, then
       L2=B[idx>>1] | R2=B[idx>>1]
    else
      L2=B[idx-1>>1] | R2=B[idx+1>>1]
    E.g.:
    original index       0
                      [# 2 #]
    idx                0 1 2

    original index       0   1
                      [# 1 # 3 #]
    idx                0 1 2 3 4

   E.g.:
            |
     [# 1 # 2 # ]
        |
     [# 3 # 4 # ]


     `double R = (c & 1) == 1 ? B[c >>> 1] : (c == 2 * N) ? Integer.MAX_VALUE : B[c + 1 >>> 1];`
      Because the enhanced array length is odd, last index is even,
      when cut index is even the element value right next to it in original array is
        B[c + 1 >>> 1]
      it is same as
        B[c >>> 1]
      so R = (c == 2 * N) ? Integer.MAX_VALUE : B[c >>> 1];
  */
  double findMedianSortedArrays2(int[] A, int[] B) {
    int M = A.length, N = B.length;
    if (M < N) return findMedianSortedArrays2(B, A);

    int l = 0, r = 2 * N;
    while (l <= r) {
      int c = l + r >>> 1; // cut position index in B
      double L = (c == 0) ? Integer.MIN_VALUE : B[c - 1 >>> 1];
      double R = (c & 1) == 1 ? B[c >>> 1] : (c == 2 * N) ? Integer.MAX_VALUE : B[c + 1 >>> 1];

      int c2 = M + N - c;
      double La = (c2 == 0) ? Integer.MIN_VALUE : A[c2 - 1 >>> 1];
      double Ra = (c2 & 1) == 1 ? A[c2 >>> 1] : (c2 == 2 * M) ? Integer.MAX_VALUE : A[c2 + 1 >>> 1];

      if (La > R) l = c + 1; // binary search  O(logN)
      else if (L > Ra) r = c - 1;
      else return (Math.max(La, L) + Math.min(Ra, R)) / 2;
    }
    return -1;
  }
}
