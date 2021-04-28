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

public class Leetcode158ReadNCharactersGivenRead4IICallmultipletimes {
  int read4(char[] buf) {
    return 0; // Assume it is implemented
  }
  // implement -----------------------------------------------------------------
  char[] b4 = new char[4];
  int real = 0; //  valid size in current b4 got in the last batch.
  int ri = 0; // next index of b4 to get character if ri<real;

  public int read(char[] buf, int n) {
    /*
     `
     Please remember to RESET your class variables declared in Solution,
     as static/class variables are persisted across multiple test cases.

     You may assume the destination buffer array, buf, is guaranteed to have enough
     space for storing n characters.

     It is guaranteed that in a given test case the same buffer buf is called by read.

     1 <= file.length <= 500
     file consist of English letters and digits.
     1 <= queries.length <= 10
     1 <= queries[i] <= 500`

    */
    /*
       So:
       - need not check the overflow of buf. but each call set its index to be 0

    Note:
     - b4 can return more characters than required! in this case, the left ones
       will be used by next read call
     - read only consumed n number, no more than n even b4 return more.
    */

    int i = 0; // next index of buf ot insert character
    while (i < n) { // make sure not read more than required n number
      if (ri == real) {
        real = read4(b4);
        ri = 0;
      }
      if (real == 0) break;
      buf[i++] = b4[ri++];
    }
    return i;
  }
}
