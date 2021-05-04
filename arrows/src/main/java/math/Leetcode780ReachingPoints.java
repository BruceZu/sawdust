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

public class Leetcode780ReachingPoints {
  /*
    A move consists of taking a point (x, y) and transforming
    it to either (x, x+y) or (x+y, y).
    Given a starting point (sx, sy) and a target point (tx, ty),
    return True if and only if a sequence of moves exists to
    transform the point (sx, sy) to (tx, ty). Otherwise, return False.

    sx, sy, tx, ty will all be integers in the range [1, 10^9].

  Idea:
   All are positive number

   DFS recursion         -> java.lang.StackOverflowError
   DFS recursion + cache -> java.lang.StackOverflowError
   loop in place         -> Time Limit Exceeded
  */
  public boolean reachingPointsLoopInPlace(int x, int y, int X, int Y) {
    while (X >= x && Y >= y) {
      if (x == X && y == Y) return true;
      if (X > Y) X -= Y; // -> modula
      else Y -= X;
    }
    return false;
  }

  /*
  Idea: improve above method by modula.
  if target X == target Y. it is false.

  O(log(min(target X,target Y))) time
   The analysis comes from Euclidean algorithm,
   and assume the modulo operation can be done in O(1) time.
   also refer
    - "we get that the Euclidean algorithm works in O(logmin(a,b))."
      https://cp-algorithms.com/algebra/euclid-algorithm.html
    - "For having been supplied with a pair of numbers m>n>0,
       it is possible to determine their relative primality by a
       method (Euclid’s algorithm) which requires a number of
       steps proportional to log(n)"
       https://plato.stanford.edu/entries/computational-complexity/
  O(1) space
  */

  public boolean reachingPoints(int x, int y, int X, int Y) {
    while (X >= x && Y >= y) {
      if (X > Y) // X is got by x'+ some times of Y. need improve the if (X > Y) X -= Y;
      if (Y == y) return (X - x) % y == 0; // Y==y
        else X %= Y; // when Y > y: Y is got by y + x'; after this step X is less than Y
      // and be handled in the following code:
      else
      // Y >= X
      if (x == X) return (Y - y) % x == 0;
      else Y %= X; // when X > x
    }
    return false;
  }
}
