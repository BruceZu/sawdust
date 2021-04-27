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

package binarysearch;

public class Leetcode278FirstBadVersion {
  private boolean isBadVersion(int m) {
    return true;
  }
  // implement ----------------------------------------------------------------
  public int firstBadVersion(int n) {
    // 1 <= bad <= n <= 2^31 - 1
    int l = 1, r = n; // l and r are unchecked index
    while (l <= r) { // both are unchecked so need check when l==r  (1)
      int m = (l + r) >>> 1;
      if (isBadVersion(m)) r = m - 1; // (2)
      else l = m + 1; // always move a further step (3)
    }
    // with agorithm that - let both l and m move a further step (2)(3) and
    //                    - check index when l==r (1)
    // now after the loop is end we sure
    // 1> all index in valid index scope are checked.
    // 2> l and r possible position and relationship:
    //         r,l
    //    When taking in account the index validation, there are 2 possible status:
    //    <A> Both of them ever moved, the last step in loop their position is
    //		     r
    //		     l
    //		  ooo?xx
    //
    //	  	  Now out of the loop: the position and related value will be in
    // 		  either status depends on value at l/r
    //	      watch in both status, the common observation:
    //		     r decide the scope of  [r+1, ...] is 1 area and l=r+1 now
    //		     l decide the scope of  [..., l-1] is 0 area and r=l-1 now
    //  	     ---------------
    //				     r
    //				      l
    //				  ooooxx
    //				     ?==o
    //			 ---------------
    //				    r
    //				     l
    //				  oooxxx
    //				     ?==x
    //			 ---------------
    //    <B> Either of them has never moved
    //		  - r has never moved
    //				  [... r] l
    //				   ooooo
    //		  - l has never moved
    //				  r [l  ...]
    //				     xxxx
    //
    //  	  As l or r may be out of valid scope:
    //		   [...r]l : l is still unchecked and out of valid scope
    //		             only l decide the scope of  [..., l-1] is 0 area and r=l-1 now
    //		  r[l...]  : r is still unchecked and out of valid scope
    //                   only r decide the scope of  [r+1, ...] is 1 area and l=r+1 now
    //
    //        So Generally need check if l or r may is out of valid index scope.
    //        In this case need to check whether l out of valid index scope
    //
    // ` 1 <= bad <= n ` means sure badVesion existing in valid index scope [1, n].
    //   need not to check whether l is still in valid index scope
    return l;
  }

  public int firstBadVersion2(int n) {
    // 1 <= bad <= n <= 2^31 - 1
    int l = 1, r = n; // l and r are unchecked index
    while (l <= r) {
      int m = (l + r) >>> 1;
      if (isBadVersion(m)) r = m - 1;
      else l = m + 1;
    }
    // ` 1 <= bad <= n ` means sure badVesion existing
    // in [1, n]. else
    // else l or r may be out of valid scope: l may be n+1 or r may be 0
    //  r ] l : l is still unchecked and out of valid scope
    //  r [ l : r is still unchecked and out of valid scope
    return l;
  }
}
