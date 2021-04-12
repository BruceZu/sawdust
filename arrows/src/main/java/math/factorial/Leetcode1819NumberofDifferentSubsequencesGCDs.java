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

package math.factorial;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Leetcode1819NumberofDifferentSubsequencesGCDs {
  public static int countDifferentSubsequenceGCDs(int[] nums) {
    /* TODO: corner cases checking
      1 <= nums.length <= 10^5
      1 <= nums[i] <= 2 * 10^5

    The idea comes from hints:
    1. The order property of sequence does not make any sense here.
       Actually, the condition should be sub-combinations with size [1, nums.length]
    2. when the nums length is very big checking all sequences and figure out
       their GCDs will cause Time Limit Exceeded.
       the hints mean check each GCD candidate [1, max of nums] to see if it has
       a related sequence. No magic the logic as: for a candidate number n
       <a> Find out all numbers in nums whose divisors include n by trying
           x=n, n*1, n*2, while x < max of nums
           to check whether x is contained in the nums.
           ***** This approach is the crucial part. *****

       <b> Whether these numbers' GCD is n by the rule:
           `If there is such subsequence, then all of it will be divisible by
            x. Moreover, if you divide each number in the subsequence by x,
            then the gcd of the resulting numbers will be 1.`

     Time O(max{N, M*logM}).
     N, M: the length, max one of nums.
     Without taking gcd() into account.
    */
    Set<Integer> set = new HashSet();
    int max = Integer.MIN_VALUE;
    int minGcd = 0;
    // Time O(N)
    for (int i = 0; i < nums.length; i++) {
      set.add(nums[i]);
      max = Math.max(max, nums[i]);
      minGcd = gcd(minGcd, nums[i]);
    }
    int gcds = 1; // minGcd
    /*
     Time depends on the number of (g,i) pairs.
     let M is the max one of nums
     number of (g,i) pairs
     = M + M/2 + M/3+ ...+ 1
     = M(1 + 1/2 + 1/3 + ... + 1/M)
     According to 'Harmonic series'
     < M*lnM
     Time is O(M*logM)
    */
    // g is a candidate GCD of some sequence
    for (int g = minGcd + 1; g <= max; g++) {
      if (set.contains(g)) {
        gcds++;
        continue;
      }
      int gcd = 0, i = 1;
      while (g * i <= max) {
        if (set.contains(g * i)) {
          gcd = gcd(i, gcd);
          if (gcd == 1) {
            // 'Adding a number to a subsequence cannot increase its gcd. '
            gcds++;
            break;
          }
        }
        i++;
      }
    }
    return gcds;
  }

  static int gcd(int a, int b) {
    while (b != 0) {
      int t = a;
      a = b;
      b = t % b;
    }
    return a;
  }

  // Alternative solution from number[i] divisor view -------------------------------
  // Time O(N*sqrt(M)).
  //  N, M: the length, max one of nums.
  //  Without taking gcd() into account .
  public static int countDifferentSubsequenceGCDs2(int[] nums) {
    // index is the divisor, value is GCD of all founded numbers in nums whose
    // divisors include the index
    // O(N) time
    int[] gcd = new int[Arrays.stream(nums).max().getAsInt() + 1];
    // Time O(N*sqr(M))
    for (int n : nums) {
      // Find all divisors of nums[i]
      for (int d = 1; d * d <= n; ++d) {
        if (n % d == 0) {
          int d1 = d;
          int d2 = n / d;
          gcd[d1] = gcd(n, gcd[d1]);
          gcd[d2] = gcd(n, gcd[d2]);
        }
      }
    }
    int r = 0;
    // Time O(M) M is the max one of nums
    for (int d = 1; d < gcd.length; d++) if (gcd[d] == d) r++;
    return r;
  }
  // test ---------------------------------------------------------------------
  public static void main(String[] args) {
    int[] array = new int[] {6, 10, 3};
    System.out.println(countDifferentSubsequenceGCDs(array) == 5);
    System.out.println(countDifferentSubsequenceGCDs2(array) == 5);
    array = new int[] {5, 15, 40, 5, 6};
    System.out.println(countDifferentSubsequenceGCDs(array) == 7);
    System.out.println(countDifferentSubsequenceGCDs2(array) == 7);
  }
}
