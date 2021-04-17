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

package bitmanipulation;

public class Leetcode1016BinaryStringWithSubstringsRepresenting1ToN {
  public static boolean queryString(String S, int N) {
    //     1 <= S.length <= 1000
    //            1 <= N <= 10^9
    //
    // T is checking times， it is checking number i in [N to N/2)
    //   generally need not check N/2 if N/2 > 0, special case is N=1, N/2==0
    // JDK String.contains() is O(M*L) time,
    //   M is S length.
    //   L is binary string length of i is 1 + logN or logN
    // So the runtime is T*M*L
    // It is O(N*M*logN) time
    if (S.length() < (Math.log(N) / Math.log(2)) * (N - N / 2)) {
      return false;
    }
    for (int i = N; i > N / 2; --i) if (!S.contains(Integer.toBinaryString(i))) return false;
    return true;
  }

  public static void main(String[] args) {
    System.out.println(queryString("1", 1));
  }
}
