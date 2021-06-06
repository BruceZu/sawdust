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

public class Leetcode157ReadNCharactersGivenRead4 {

  class Reader4 {
    int read4(char[] buf) {
      return 0; // todo
    }
  }

  class Solution extends Reader4 {
    /*
     Given a file, you can only read the file using a given method read4,
     implement a method to read n characters.

     About read4 method:
     - in the parent class Reader4
     - reads four consecutive characters from file,
       writes those characters into the buffer array buf4.
        int read4(char[] buf4);
     - The return value is the number of actual characters read.
     - read4() has its own file pointer, much like FILE *fp in C.
    E.g.:
       File file("abcde"); // File is "abcde", initially file pointer (fp) points to 'a'
       char[] buf4 = new char[4]; // Create buffer with enough space to store characters
       read4(buf4); // read4 returns 4. Now buf4 = "abcd", fp points to 'e'
       read4(buf4); // read4 returns 1. Now buf4 = "e", fp points to end of file
       read4(buf4); // read4 returns 0. Now buf4 = "", fp points to end of file

    ask: reads n characters from file and store it
         in the buffer array buf.
         Consider
          - that you cannot manipulate file directly.
          - The file is only accessible for read4 but not for read.
          - The read function will only be called once for each test case.
          - You may assume the destination buffer array, buf, is
            guaranteed to have enough space for storing n characters.
         The return value is the number of actual characters read.
     Idea:
      in a loop:
      - read
      - then check: source reached end or got enough, then stop and return.
    */
    public int read(char[] buf, int n) {
      if (n <= 0) return 0;
      char[] b = new char[4]; // buffer for read4()
      int r = 0; // return value of read4
      int s = 0; // char size in buf
      while (true) {
        r = read4(b);
        int l = Math.min(r, n - s); // left char length need/able to copy
        for (int i = 0; i < l; i++) buf[s++] = b[i]; // i: index of buffer of read4
        if (r < 4 || s == n) return s; // source reached or got enough
      }
    }
  }
}
