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

package tree.trie;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Leetcode1803CountPairsWithXORinaRange {
  /*
  A nice pair is a pair (i, j) where 0 <= i < j < nums.length and
  low <= (nums[i] XOR nums[j]) <= high.
  return the number of nice pairs.

  1 <= nums.length <= 2 * 10^4
  1 <= nums[i]     <= 2 * 10^4
  1 <= low <= high <= 2 * 10^4
   */

  /*
  Idea:
  The question can be converted into calculating the number of nice pairs whose
  XOR result is less than the target
  number 'x'.
  Generally, a solution is to create a Bitwise Tries
   (https://en.wikipedia.org/wiki/Trie#Bitwise_tries)
  to group array elements whose index < current index based on their
  binary(base 2) representation.
  In each node keep the counts of the number whose binary representation mapped
  path in tries crosses this node.  Then find all elements in the tries able to
  form nice pair with the current number 'a' in this way:
    Note
      - Start from the high bit to lower bit, or from dumb root to leaf as this
        is also the order used to create the tire.
      - Positive integer a ^ b = xor then  b = a ^ xor.
      - Generally the x's binary representation looks like
          0000100100100   (x)
        its length is 1 + log(2 * 10^4) as '1 <= nums[i] <= 2 * 10^4'.
        Binary representations of xor, a and candidate of b in tries also has
        the same length. It is tries' depth. Because
        "1 <= nums.length <= 2 * 10^4
         1 <= nums[i]     <= 2 * 10^4
         1 <= low <= high <= 2 * 10^4"
        The alternative is to use 1 + log max{array number, low, high) as
        the tries depth.

      - Insight:
        All bits to the left to the first bit 1 in x is 0. The same position's
        bit in xor can be 1 or 0. xor should go along the 0-bit sub-branch.
        Because its bit 1 sub-branch will make xor > x. And no other optional
        bit value less than 0 is available.(base 2). So in this case no nice
        pairs can be found.

       For the position of first bit 1 in x, the same position's bit in xor can
       be 1 or 0.
       - If the same position's bit in xor is 0 then the 2 numbers that make the
         xor are a nice pair.
         With above x as example:
           0000100100100   (x)    x >> a few steps till x is:  00001 (x')
           00000????????   (xor)  xor >> same steps to be:     00000 (x'-1)
         Even all ? bit be 1, xor is still less than x.
         The counts of pairs = counts of  a multiply counts of b = a ^ xor

       - If the same position's bit in xor is 1, same with that of x, then at
         present no way to find nice pair and, but chance can appear in next bit
         so go ahead along this way

        Actually for any left bit 0 or 1 of x, above rule can be applied and
        repeated.
        For the left position of bit 1:
          0000100100100   (x)    x >> a few steps till x is:  00001001 (x')
          00001000?????   (xor)  xor >> same steps to be:     00001000 (x'-1)

          0000100100100   (x)    x >> a few steps till x is:  00001001001 (x')
          00001001000??   (xor)  xor >> same steps to be:     00001001000 (x'-1)

        After that add the current number in the trie and continue to handle the
        left number in the given array in the same way.

  Generally
    -1- Only at the position with bit 1 in x, can xor have a chance to form nice
        pair.
    -2- At any time, all bits to the left to the current bit in x are the same
        as that of xor. Note the expected xor is invisible, only x and a are at
        hand. The xor is the expected number less than the target number x.
    -3- The bit-wise trie here looks like a form of a binary tree except for its
        dumb root which is only used to refer to 2 sub-branches, or numbers
        starting with 0 or 1.
    -4- Calculations at each position with bit 1 in x for nice pairs number is
        independent. That means the calculating order can be started from the
        low-level bit to the high-level bit, or from right to left of x and xor
        if replace the bit-wise tries with a map (number: counts).
    -5- 'A nice pair is a pair (i, j) where 0 <= i < j < nums.length and
        low <= (nums[i] XOR nums[j]) <= high.'
        This only means pair (i, j) and pair (j, i) are the same.
        E.g. if array is 1, 1, 2, 2  and a=1, b=2 a^b < x, then it is 4 pairs
        not 2 pairs.
        If we calculate it in this way, then the index of a and b is independent.
        when with a to find b, result=4 pairs is found.
        when with b to find a, 4 pairs are found again, result=8.
        As (i,j) and (j,i) is ame pair. So let result=result/2.
    With 2 independent described in -4- and -5-, the tries can be replaced by a
    map (number: counts).

    Runtime:
     N is array length,
     M1= max{ numbers in array}.
     M2 = max{low, high+1}
     M = max{M1, M2},

   - Solution with Ties:
     It depends on how to decide the depth of Tries:
       O(N*(1+logM)) = O(N*logM)
       In this case the max limit is 2 * 10^4 of number, low and high
       so max value of 1+logM is 15, 2^0~2^14

   - Solution with map:
     It depends on the average of arrays numbers value.
     worse case:
     When array number are all great than M2 and each num[i] are distinct even
     after num[i] > O(max{N, N*log(M2)}  = O(N*log(M2))

     better case:
     When the average of arrays numbers value is very small, this makes the number
     of the keys of the map (number: counts) shrink fast and can be ignorable:
       O(max{N, log(M2)}
   */
  // Solution with Tries -------------------------------------------------------
  static class Trie {
    Trie[] t = new Trie[2];
    int cnt = 0;

    void insert(int a, int i) {
      if (i >= 0) {
        int A = a >> i & 1;
        t[A] = t[A] == null ? new Trie() : t[A];
        t[A].cnt++;
        t[A].insert(a, i - 1);
      }
    }

    /*
      TRIE, A, XOR, X column is the bit of Trie, a, xor and x at the same position.

      TRIE  A  XOR    X
      -------------------------------------------
       1    1   0     1  nice pairs exits
       0    0   0     1  nice pairs exits

       0    1   1     1  go ahead
       1    0   1     1  go ahead
       1    1   0     0  go ahead
       0    0   0     0  go ahead

       0    1   1     0  no nice pairs, ignorable.
       1    0   1     0  no nice pairs, ignorable.
      -------------------------------------------

     Also need check if current Trie has 0-bit/1-bit sub branch
    */
    int nicePairsLessThan(int a, int x, int i) {
      int A = a >> i & 1, X = x >> i & 1;
      int nicePairs = X == 1 ? (t[A] != null ? t[A].cnt : 0) : 0; // Note the operator precedence
      return nicePairs + (t[A ^ X] != null ? t[A ^ X].nicePairsLessThan(a, x, i - 1) : 0);
    }
  }

  static int countPairs(int[] nums, int low, int high) {
    int max = Math.max(Math.max(Arrays.stream(nums).max().getAsInt(), low), high + 1);
    int d = (int) (Math.log(max) / (Math.log(2))); // Depth of Tries

    Trie tries = new Trie();
    int r = 0;
    for (int a : nums) {
      r += tries.nicePairsLessThan(a, high + 1, d) - tries.nicePairsLessThan(a, low, d);
      tries.insert(a, d);
    }
    return r;
  }

  // Solution with map ---------------------------------------------------------
  public static int countPairs2(int[] nums, int low, int high) {
    return countsLessThan(nums, high + 1) - countsLessThan(nums, low);
  }

  private static int countsLessThan(int[] nums, int x) {
    Map<Integer, Integer> m = new HashMap<>(), m2;
    for (int n : nums) m.put(n, m.getOrDefault(n, 0) + 1);
    int r = 0;
    while (x > 0) {
      m2 = new HashMap<>();
      for (int a : m.keySet()) {
        int counts = m.get(a), b = (x - 1) ^ a;
        if ((x & 1) == 1) r += counts * m.getOrDefault(b, 0);
        m2.put(a >> 1, m2.getOrDefault(a >> 1, 0) + counts);
      }
      m = m2;
      x >>= 1;
    }
    return r / 2;
  }
  // test ---------------------------------------------------------
  public static void main(String[] args) throws FileNotFoundException {
    // array length:20000
    Scanner scanner = new Scanner(new File("/tmp/number.txt"));
    scanner.useDelimiter(",");
    List<Integer> list = new ArrayList<>();
    while (scanner.hasNextInt()) {
      list.add(scanner.nextInt());
    }
    int[] array = list.stream().mapToInt(i -> i).toArray();
    int low = 7, high = 7;
    System.out.println(countPairs(array, low, high) == 100000000);

    array = new int[] {1, 4, 2, 7};
    low = 2;
    high = 6;
    System.out.println(countPairs(array, low, high) == 6);
    System.out.println(countPairs2(array, low, high) == 6);

    array = new int[] {1, 1, 2, 2};
    System.out.println(countPairs(array, low, high) == 4);
    System.out.println(countPairs2(array, low, high) == 4);
  }
}
