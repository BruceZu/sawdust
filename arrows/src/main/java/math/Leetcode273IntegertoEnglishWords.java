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

import java.util.HashMap;
import java.util.Map;

public class Leetcode273IntegertoEnglishWords {

  /*
  O(N) time O(1) space
  Idea: separate number by 3 length from right to left
  Note:
  - Add level name when l>=1 and current 3 level value is not 0
  - if num is 0 return 'Zero' idrectly
  - always check if current under parsed level, d__, d_, d is 0?
  - add string operation always has a advanced " ", the result remove the left most " "
  */
  public String numberToWords(int num) {
    // '0 <= num <= 2^31 - 1'
    // max  2,147,483,647
    // 'a non-negative integer num'
    // TODO: corner cases validation
    Map<Integer, String> level = new HashMap();
    level.put(1, "Thousand");
    level.put(2, "Million");
    level.put(3, "Billion");

    Map<Integer, String> map = new HashMap();
    map.put(1, "One");
    map.put(2, "Two");
    map.put(3, "Three");
    map.put(4, "Four");
    map.put(5, "Five");
    map.put(6, "Six");
    map.put(7, "Seven");
    map.put(8, "Eight");
    map.put(9, "Nine");
    map.put(10, "Ten");
    map.put(11, "Eleven");
    map.put(12, "Twelve");
    map.put(13, "Thirteen");
    map.put(14, "Fourteen");
    map.put(15, "Fifteen");
    map.put(16, "Sixteen");
    map.put(17, "Seventeen");
    map.put(18, "Eighteen");
    map.put(19, "Nineteen");
    map.put(20, "Twenty");
    map.put(30, "Thirty");
    map.put(40, "Forty");
    map.put(50, "Fifty");
    map.put(60, "Sixty");
    map.put(70, "Seventy");
    map.put(80, "Eighty");
    map.put(90, "Ninety");
    if (num == 0) return "Zero";
    // each time append operation will add a advance " ".
    String result = "";
    int l = 0;
    while (num != 0) {
      int r = num % 1000;
      StringBuilder sb = new StringBuilder();

      // parse current level start----------
      int h = r / 100;
      if (h != 0) sb.append(" " + map.get(h) + " Hundred");
      int t = r % 100;
      if (t != 0) { // if 2 digital is 0 then need not do anything
        if (t <= 20) sb.append(" " + map.get(t));
        else {
          int d = t % 10, dd = t - d;
          if (dd != 0) sb.append(" " + map.get(dd));
          if (d != 0) sb.append(" " + map.get(d));
        }
      }
      if (r != 0 && l != 0) sb.append(" " + level.get(l)); // decided by r and l
      // parse current level done----------
      result = sb + result;
      // next
      num /= 1000;
      l++;
    }
    return result.substring(1);
  }
}
