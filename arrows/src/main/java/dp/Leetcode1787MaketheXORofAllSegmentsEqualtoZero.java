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

package dp;

import java.util.Arrays;
/*
  1 <= k <= nums.length <= 2000
   0 <=nums[i] < 2^10,

Note:
1> It is possible the best solution changes [0, k-1] position(s)'s number in the
first k numbers to minimize changes for all other positions.
2> For the same reason, any position's number can be changed to be [0, 1024).
*/

public class Leetcode1787MaketheXORofAllSegmentsEqualtoZero {
  // Basic idea: O(K*R^2) R is the range of number[i], it is 1024 here. Time Limit Exceeded
  public static int minChanges1(int[] nums, int k) {
    int N = nums.length;
    int[] nm = new int[k]; // nm[i] The number of group number 0~k-1
    int[][] fre =
        new int[k][1024]; // int[i][j]  frequency of number j in group i. '0 <=nums[i] < 2^10=1024'
    for (int i = 0; i < N; i++) {
      fre[i % k][nums[i]]++;
      nm[i % k]++;
    }
    // dp[i][j] is the minimum changes of pre i groups to make
    // the result of XOR of number[0]~number[i] is j
    // '0 <=nums[i] < 2^10=1024' this means xor value range will be in [0, 1024)
    // transform equation is
    //        dp[current group][xor] =
    //        Math.min(dp[current group][xor],
    //                 dp[current group-1][xor^alternative number] +
    //                   nm[current group] - fre[current group][xor^alternative number]);
    int[][] dp = new int[k + 1][1024];
    for (int i = 0; i <= k; i++) Arrays.fill(dp[i], N);
    // Not use Integer.MAX_VALUE: see transform equation
    /*
       For the group 0 dp[0][j] is N, except dp[0][0] is 0.
       Because dp[0][j] is used only for calculate group 1
       where for any alternative of nums[0], [0,1024), it has not other previous numbers to do XOR operation with it.
       So for the xor value j of dp[1][j], the nums[0] only have to select j,
          dp[1][j] = nm[0] - fre[0][j];
       Here in nm[g] and fre[g][] the group g is 0 index based
       For making it uniform with the translation equation
         dp[g][xor] = Math.min(dp[g][xor], dp[g - 1][px] + nm[g - 1] - fre[g - 1][al]);
       Let dp[0][j^j=0] = 0, then
         dp[1][j] = dp[0][j^j=0] + nm[0] - fre[0][j]);
       Let dp[0][other number than 0] be N to achieve an effect to ignore these alternative numbers
    */
    dp[0][0] = 0;
    for (int g = 1; g <= k; g++) {
      for (int x = 0; x < 1024; x++)
        for (int al = 0; al < 1024; al++) // alternative of num[g]
        dp[g][x] = Math.min(dp[g][x], dp[g - 1][x ^ al] + nm[g - 1] - fre[g - 1][al]);
    }
    return dp[k][0];
  }
  // Improvement version ->O(N*R),R is the range of number[i], it is 1024 here.
  public static int minChanges2(int[] nums, int k) {
    int N = nums.length;
    int[] nm = new int[k];
    int[][] fre = new int[k][1024];
    for (int i = 0; i < N; i++) {
      fre[i % k][nums[i]]++;
      nm[i % k]++;
    }

    int[][] dp = new int[k + 1][1024];
    for (int i = 0; i <= k; i++) Arrays.fill(dp[i], N);

    dp[0][0] = 0;
    /*
    '0 <=nums[i] < 2^10 = 1024' means
    For xor value x in dp[g][x], let px = x ^ number.
    As x in [0, 1024), number is in [0, 1024).
    So px is in [0, 1024) and for a given x the relationship
    between number and px is bijective.
    So when fre[g - 1][al] is 0, the transform equation
        dp[g][x] = Math.min(dp[g][x], dp[g - 1][px] + nm[g - 1] - fre[g - 1][al]);
    becomes:
       dp[g][x] = Math.min(dp[g][x], dp[g - 1][px]) + nm[g - 1];
    For any alternative number of num[g-1], here g is in[1,k] and i in num[i] is in [0,k-1],
    if the number is not in group g-1 then the fre[g - 1][al] is 0.
    So the
       dp[g][x] = Math.min(dp[g][x], dp[g - 1][px]) + nm[g - 1]
    can be applied to the alternative number not in group g-1
    As  nm[g - 1] - fre[g - 1][al] < nm[g - 1]
    This means the
      dp[g][x] = Math.min(dp[g][x], dp[g - 1][px]) + nm[g - 1];
    Can also apply to the number in group g-1 to form a inner loop 1, and then
    handle the number in group g-1 with original transform equation in another
    inner loop 2. And the overlap and running order of these 2 loops does not
    affect result.
    The inner loop 1 can even be took as dp[g][x] initial value.
    */
    int preDPmin = 0;
    for (int g = 1; g <= k; g++) {
      for (int x = 0; x < 1024; x++) { // Inner loop 1
        dp[g][x] = Math.min(dp[g][x], preDPmin + nm[g - 1]);
      }
      for (int x = 0; x < 1024; x++) { // Inner loop 2
        for (int i = g - 1; i < N; i += k) // Note i=g-1, nums is 0 index based.
        dp[g][x] = Math.min(dp[g][x], dp[g - 1][x ^ nums[i]] + nm[g - 1] - fre[g - 1][nums[i]]);
      }
      int curMin = N;
      for (int x = 0; x < 1024; x++) {
        curMin = Math.min(curMin, dp[g][x]);
      }
      preDPmin = curMin;
    }
    return dp[k][0];
  }

  // Concise version. O(N*R),R is the range of number[i], it is 1024 here.
  // 2 dimension dp-> 1 dimension thus all variable is 0 index based now
  public static int minChanges3(int[] nums, int k) {
    int N = nums.length;
    int[] nm = new int[k];
    int[][] fre = new int[k][1024];
    for (int i = 0; i < N; i++) {
      fre[i % k][nums[i]]++;
      nm[i % k]++;
    }
    int[] dp = new int[1024];
    Arrays.fill(dp, N);
    dp[0] = 0;
    int min = 0;
    for (int g = 0; g < k; g++) {
      int[] curDP = new int[1024];
      Arrays.fill(curDP, min + nm[g]);
      int curMin = N;
      for (int x = 0; x < 1024; x++) {
        for (int i = g; i < N; i += k)
          curDP[x] = Math.min(curDP[x], dp[x ^ nums[i]] + nm[g] - fre[g][nums[i]]);
        curMin = Math.min(curMin, curDP[x]);
      }
      min = curMin;
      dp = curDP;
    }
    return dp[0];
  }

  // Last version. O(N*R),R is the range of number[i], it is 1024 here.
  // min -> max logic. thus save the nm[] variable
  public static int minChanges(int[] nums, int k) {
    int N = nums.length;
    int[][] fre = new int[k][1024];
    for (int i = 0; i < N; i++) {
      fre[i % k][nums[i]]++;
    }
    int[] dp = new int[1024];
    Arrays.fill(dp, -N);
    dp[0] = 0;
    int max = 0;
    for (int g = 0; g < k; g++) {
      int[] DP = new int[1024];
      Arrays.fill(DP, max);
      int MAX = 0;
      for (int x = 0; x < 1024; x++) {
        for (int i = g; i < N; i += k) DP[x] = Math.max(DP[x], dp[x ^ nums[i]] + fre[g][nums[i]]);
        MAX = Math.max(MAX, DP[x]);
      }
      max = MAX;
      dp = DP;
    }
    return N - dp[0];
  }

  public static void main(String[] args) {
    System.out.println(minChanges(new int[] {3, 4, 5, 2, 1, 7, 3, 4, 7}, 3) == 3);
    System.out.println(minChanges(new int[] {1, 2, 4, 1, 2, 5, 1, 2, 6}, 3) == 3);
    System.out.println(minChanges(new int[] {1, 2, 0, 3, 0}, 1) == 3);
    int[] number =
        new int[] {
          67, 101, 73, 239, 200, 79, 137, 0, 65, 145, 0, 51, 244, 234, 1, 229, 198, 133, 241, 178,
          158, 111, 39, 164, 203, 145, 127, 113, 103, 248, 87, 199, 202, 4, 36, 19, 141, 141, 58,
          188, 31, 253, 223, 151, 88, 36, 174, 242, 1, 113, 217, 114, 233, 40, 221, 212, 218, 142,
          135, 206, 133, 216, 90, 13, 169, 108, 218, 89, 104, 82, 162, 247, 34, 222, 13, 80, 183,
          139, 230, 182, 114, 88, 95, 102, 175, 148, 150, 110, 189, 10, 104, 23, 86, 34, 95, 158,
          227, 159, 147, 0, 249, 96, 157, 224, 33, 150, 61, 130, 25, 229, 173, 217, 35, 86, 220, 63,
          26, 216, 148, 82, 103, 206, 23, 28, 17, 146, 117, 158, 153, 64, 230, 150, 255, 208, 168,
          137, 7, 219, 56, 7, 199, 95, 61, 78, 20, 122, 227, 189, 109, 86, 181, 24, 4, 160, 244,
          122, 79, 57, 63, 173, 49, 44, 14, 145, 129, 38, 163, 240, 38, 252, 190, 239, 180, 18, 211,
          23, 57, 177, 206, 140, 160, 171, 63, 120, 191, 3, 126, 139, 213, 88, 39, 67, 122, 67, 210,
          157, 119, 92, 85, 152, 195, 151, 167, 199, 128, 132, 221, 23, 11, 225, 231, 159, 133, 21,
          152, 52, 49, 46, 76, 112, 146, 10, 77
        };
    System.out.println(minChanges(number, 75) == 147);
    System.out.println(minChanges2(number, 75) == 147);

    number =
        new int[] {
          97, 138, 238, 167, 15, 36, 23, 173, 237, 9, 213, 121, 38, 169, 115, 155, 76, 165, 44, 169,
          214, 213, 45, 9, 213, 124, 40, 17, 173, 10, 192, 109, 185, 128, 72, 217, 11, 243, 183,
          241, 214, 128, 174, 224, 77, 183, 114, 97, 211, 194, 80, 0, 246, 68, 30, 56, 47, 254, 238,
          37, 53, 236, 225, 237, 54, 47, 148, 73, 5, 81, 196, 57, 154, 201, 203, 109, 59, 182, 33,
          30, 2, 111, 117, 226, 100, 253, 63, 190, 104, 225, 253, 102, 39, 185, 140, 107, 97, 21,
          28, 158, 161, 172, 116, 226, 206, 33, 100, 221, 19, 81, 42, 198, 175, 168, 216, 192, 105,
          26, 187, 229, 31, 147, 73, 133, 135, 45, 83, 69, 57, 173, 205, 144, 88, 191, 120, 246,
          240, 213, 18, 157, 81, 204, 190, 26, 19, 164, 11, 29, 105, 170, 86, 121, 38, 55, 90, 159,
          146, 219, 166, 11, 195, 205, 168, 81, 195, 201, 124, 57, 80, 96, 162, 234, 5, 199, 94, 91,
          71, 232, 121, 242, 15, 118, 25, 202, 20, 133, 146, 149, 136, 111, 63, 231, 170, 10, 128,
          31, 18, 98, 1, 139, 7, 18, 50, 147, 106, 84, 121, 221, 236, 242, 192, 138, 138, 133, 35,
          1, 1, 98, 27, 140
        };
    System.out.println(minChanges(number, 35) == 182);
    System.out.println(minChanges2(number, 35) == 182);
  }
}
