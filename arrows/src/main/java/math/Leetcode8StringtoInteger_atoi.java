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

package math;

public class Leetcode8StringtoInteger_atoi {

  // O(N) time O(1) space
  public static int myAtoi2(String s) {
    /*
    0 <= s.length <= 200
    s consists of English letters (lower-case and upper-case),
      digits (0-9), '  ', '+', '-', and '.'.

    Note:
    'Read in next the characters until the next non-digit character
     or the end of the input is reached. The rest of the string is ignored.'
     This means only part leading digit. if the digit is not the leading one,
     ignore it and return 0.

    */
    int N = s.length();
    int i = 0;
    while (i < N && Character.isWhitespace(s.charAt(i))) i++;
    if (i == N) return 0;

    // Assume current position is only one '+' or  one '-' or one digit
    // it is not leading number.
    if (!(Character.isDigit(s.charAt(i)) || s.charAt(i) == '+' || s.charAt(i) == '-'))
      return 0; // no leading digit
    // '+' or '-' or digit
    boolean isNegtive = false;
    if (s.charAt(i) == '+' || s.charAt(i) == '-') {
      if (s.charAt(i) == '-') isNegtive = true;
      i++; // Note: to move a step into index==N or expected digit area or character area
    }
    // now index may be N or at any char
    // if it is not at digit it is not leading number
    if (i == N || !(Character.isDigit(s.charAt(i)))) return 0;

    // digit
    // there may be some leading '0's
    long n = 0;
    while (i < N && Character.isDigit(s.charAt(i))) {
      n = 10l * n + s.charAt(i) - '0';
      if (n > Integer.MAX_VALUE && !isNegtive) return Integer.MAX_VALUE;
      // Note here: without the 1l* the right part  Integer.MAX_VALUE + 1 will be Integer.MIN_VALUE
      if (n > 1l * Integer.MAX_VALUE + 1 && isNegtive) return Integer.MIN_VALUE;
      i++;
    }
    int result = (int) n;
    return isNegtive ? -1 * result : result;
  }

  // no comments version
  public static int myAtoi(String s) {
    int N = s.length();
    int i = 0;
    while (i < N && Character.isWhitespace(s.charAt(i))) i++;
    if (i == N) return 0;
    if (!(Character.isDigit(s.charAt(i)) || s.charAt(i) == '+' || s.charAt(i) == '-')) return 0;
    boolean isNegtive = false;
    if (s.charAt(i) == '+' || s.charAt(i) == '-') {
      if (s.charAt(i) == '-') isNegtive = true;
      i++;
    }
    if (i == N || !(Character.isDigit(s.charAt(i)))) return 0;
    long n = 0;
    while (i < N && Character.isDigit(s.charAt(i))) {
      n = 10l * n + s.charAt(i) - '0';
      if (n > Integer.MAX_VALUE && !isNegtive) return Integer.MAX_VALUE;
      if (n > 1l * Integer.MAX_VALUE + 1 && isNegtive) return Integer.MIN_VALUE;
      i++;
    }
    int result = (int) n;
    return isNegtive ? -1 * result : result;
  }
  // Test----------------------------------------------------------------------
  public static void main(String[] args) {
    System.out.println(myAtoi("  0000000000012345678") == 12345678);
    System.out.println(myAtoi("20000000000000000000") == Integer.MAX_VALUE);
    System.out.println(myAtoi("+") == 0);
    System.out.println(myAtoi("+-12") == 0);
    System.out.println(myAtoi("   -42") == -42);
  }
}
