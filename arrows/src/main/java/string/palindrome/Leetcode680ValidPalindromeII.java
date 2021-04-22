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

package string.palindrome;

public class Leetcode680ValidPalindromeII {
  /*
  Idea:
  check from both sides if some pair is not same
  then need try sub array [l+1, r] and [l, r-1]. any of them
  is palindrome then the string is palindrome

  Case:   ececabbacec
          ececabbace  is not palindrome
           cecabbacec is a palindrome

   */
  public static boolean validPalindrome(String s) {
    // TODO: corner cases validation
    // note: j does not start from s.length()
    for (int l = 0, r = s.length() - 1; l < r; l++, r--) {
      if (s.charAt(l) != s.charAt(r)) return isPalindrome(s, l + 1, r) || isPalindrome(s, l, r - 1);
    }
    return true;
  }

  // O(N) time O(1) space.
  private static boolean isPalindrome(String s, int l, int r) {
    while (l < r) {
      if (s.charAt(l++) != s.charAt(r--)) return false;
    }
    return true;
  }
}
