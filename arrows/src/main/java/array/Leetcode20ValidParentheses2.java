//  Copyright 2016 The Sawdust Open Source Project
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

package array;

public class Leetcode20ValidParentheses2 {
  // using a array to act as a stack;
  public boolean isValid1(String str) {
    char[] arr = str.toCharArray();
    char[] st = new char[arr.length];
    int size = 0;
    for (int i = 0; i < arr.length; i++) {
      char c = arr[i];
      if (c == '(' || c == '{' || c == '[') {
        st[size++] = c;
        continue;
      }
      if (size == 0) { // note
        return false;
      }
      size--;
      char poped = st[size];
      if (poped == '(' && c == ')' || poped == '{' && c == '}' || poped == '[' && c == ']') {
        continue;
      }
      return false;
    }
    return size == 0;
  }
}
