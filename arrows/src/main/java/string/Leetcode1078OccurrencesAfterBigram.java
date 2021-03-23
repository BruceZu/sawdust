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

package string;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Leetcode1078OccurrencesAfterBigram {
  public static String[] findOcurrences(String text, String first, String second) {
    /*

        1 <= text.length <= 1000
        text consists of space separated words, where each word consists of lowercase English letters.
        1 <= first.length, second.length <= 10
        first and second consist of lowercase English letters.

    */
    // O(?) time
    String[] ws = text.split(" ");
    List<String> result = new ArrayList();
    // O(?) time
    for (int i = 2; i < ws.length; i++) {
      // all are lower case
      if (ws[i - 2].equals(first) && ws[i - 1].equals(second)) result.add(ws[i]);
    }
    String[] r = new String[result.size()];
    return result.toArray(r);
  }
}
