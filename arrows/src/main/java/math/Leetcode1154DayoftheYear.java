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

public class Leetcode1154DayoftheYear {
  /*
  Leap year:
    y % 4 == 0 && (y % 100 != 0  ||  y % 400 == 0)
  */
  private static int days[] = new int[] {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

  public int dayOfYear(String s) {
    int y = Integer.valueOf(s.substring(0, 4)),
        m = Integer.valueOf(s.substring(5, 7)),
        d = Integer.valueOf(s.substring(8));

    if (m > 2 && y % 4 == 0 && (y % 100 != 0 || y % 400 == 0)) d++;
    while (--m > 0) d += days[m];
    return d;
  }
}
