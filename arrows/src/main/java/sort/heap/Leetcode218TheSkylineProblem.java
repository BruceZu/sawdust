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

package sort.heap;

import java.util.*;

public class Leetcode218TheSkylineProblem {
  /*
  Note:
  - How does the each building represented:
    a triplet of integers [x, y, h],
      h is its height.
      0 ≤ x, y ≤ INT_MAX, 0 < h ≤ INT_MAX, and y - x > 0.

  - How does the skyline defined:
   - a list of "key points" sorted by their
     x-coordinate in the form [[x1,y1],[x2,y2],...]. Each key point is the left
     endpoint of some horizontal segment in the skyline except the last point in the list.
     Note: some point is created by crossed edges, not comes from one of building
     itself vertex point.
   - the last key point, where the rightmost building ends,
     is merely used to mark the termination of the skyline,
     and always has zero height.
   - There must be no consecutive horizontal lines of equal height in the output skyline

   The number of buildings in the range [0, 10000].
   The input list is already sorted in ascending order by the left x position Li.
   The output list must be sorted by the x position.

   Tags
     Binary Indexed Tree
     Segment Tree
     Heap
     Divide and Conquer
   */

  /*
  Idea:
    TODO: checking buildings null, empty
    If there is only one building: result skyline is left top vertex point and right bottom vertex point
    According to the definition of skyline.
     (a). when building's vertex points on scan line are all of right top vertical:
        should select none of them as skyline,
        so a solution for this is to let the value of building top right vertex's y coordinate be a negative
        value, thus on any scan line no matter the point is top-left of top-right vertex point, the
        point of skyline always determine it y coordinate value max{yi, 0}.

       Ask 5: sort these top left and top right vertex in ascending order or descending order when
              their x coordinate has the same value, or on the same scan line?
       Answer 5: from the Answer 4, it is descending order to make sure when on a scan line
                 - there are more left top vertex: only the one with the biggest value of
                   height on the skyline.
                 - there are more right top vertex: make sure none of these normal right top vertex
                   are on the skyline, instead (x,0) will be the on the skyline as the end point.
                   According to the definition of skyline, any building's right top vertex will not
                   be on skyline which only include normal left top vertex or cross edges created point.
                 - this works for scenario where current scan line has 1 or more left top vertex and
                   1 or more right top vertex.
     (b). Solution in （a） can not handle case
         - where 2 buildings with same height level
         - where on the scan line there are points created by building crossed edges.

        Ask 1: how to recognize the point created by crossed edge on current scan line?
        Answer 1: from the Answer 4: what can be seen on how this kind of point created and how
                  to handle them:
                  scan line move from normal vertex to normal vertex. For points in current scan line:

                  If current normal vertex is a left top vertex and find its height is great
                  than (>) current highest height which is of some building which covering all other
                  point on the  current scan line, then current left top vertex is of the skyline, any
                  point created by this left top vertex related edge with other buildings are
                  ignored. (Else if its height is <= current highest height, itself will be ignored)

                  If current normal vertex is a right top vertex and find his height is the same
                  as the current highest height ( as a right top vertex itself will never have a height
                  great than the current highest height which only be changed by some left top vertex point),
                  then it invalid the highest height and in this case, need take the cross edge crated
                  pint (x, new current highest height) as skyline only when
                      the new current highest height < just invalided one
                  ( Else current normal right top vertex's height is <= current highest height, itself will
                    be ignored)

        Ask 2: how to decide the sign of the y coordinate of these crossed edges created point?
        Answer 2: need not to define its sign which is only used for normal vertex point for sorting them
                  in descending order, here assume top right vertex's y is marked as negative.
                  And the crossed edges created point is created and handled on the fly thus
                  need not to keep them. Refer to Answer 1.
        Ask 3: how compared them with normal vertex point.
        Answer 3: need not compared them with other point. They are created only in need, before it
                  is created it is already be decided of skyline and need not compare with other anymore.
                  Refer to Answer 1 & 2.

     (c). Note on any scan line: only need to select at more one point as skyline:
        If current vertex has a new higher height or it has the same height compared with previous
        scan line's points' highest height, then from current scan line one point will be of skyline
         - in the first case: current point is a left top vertex point and is chosen of sky line
         - in the later case: current point is a top right vertex point, take care here only when
           the new current highest height < just invalided one can we make a cross edge crated
           pint (x, new current highest height) as skyline
        Else no point of current scan line will be of skyline
        Note: the pint with current highest height is always normal point, not point created
              by crossed edges.

        Ask 4: how to know the highest height of some building that cover all current points on
               current scan line?
        Answer 4: use a heap (PriorityQueue) to select it and keep updating it during scan line moving
               from left to right in thus way:
               The height is decided by the left top vertex of the top building and be invalid
               by the same building's right top vertex point. During the scan line move from left to
               right a building's left top vertex always is scanned firstly than its right top vertex.
               Any left top vertex should be put in heap, as at anytime later it could be the highest
               height, it will be removed by the related right top vertex of the same building.
               The remove operation is not O(1), instead It is O(H).

    Summary:
      Algorithm and data structure:
      - data structure: get left top point and right top point from a given building and mark the
        normal right top point's y
      coordinate value as negative.
      - Algorithm: sort them by x in ascending, y in descending and handle them one by one just
        like a scan line and on each position, the scan line only select out at most one point to be
        of skyline.
      - data structure: max heap (PriorityQueue) top keep the highest height: meaning
                        - with the biggest value, a positive integer, on the current scan line
                        - and its building covering other points on the same scan line.
                          according to skyline definition, any building right top vertex will never be
                          of skyline. So
                          any left top vertex on the scan lien is including in the scan line relation
                              [x]
                          any right top vertex on the scan line is excluding in the scan line relation.
                              (x)
                        initial with a 0 number. horizontal level height
      - Algorithm: Check current point height, it is |y|:
                   > heap top: the current point is a top-left vertex and the highest one, y is positive.
                               head.add(y).
                               itself is of skype line: (x,y)
                   = heap top: current point is a top right vertex, or y is negative.
                               invalid/end the same building left top vertex height by remove heap top
                               now the highest height <= current point height.
                               Only when highest height < current point height to create a crossed edge
                               created point(x, new heap top) as skyline to avoid adding duplicate
                              skyline point for 2 buildings having the same highest height

                   < heap top: this vertex is not one skyline as it is covered by the highest height related
                               building.
                               - if y is negative, it is  a top-right vertex: need invalid/end its building
                                 height, provided by the related top left vertex of the same build, as a
                                 candidate for highest height by remove |y| from the max heap, O(N) time
                               - else it is a top-left vertex, need to add its height to the max heap as it
                                 may become active height later.

  O(NlogN) time used to sort the 2 top vertex point of each building. Assume use
           a customized heap who can provide O(1) time remove(object) operation
  O(N) space
  */

  public static List<List<Integer>> getSkyline(int[][] buildings) {
    List<int[]> ps = new ArrayList<>(buildings.length * 2);
    for (int[] b : buildings) {
      ps.add(new int[] {b[0], b[2]});
      ps.add(new int[] {b[1], -b[2]});
    }
    Collections.sort(
        ps,
        (a, b) -> {
          if (a[0] != b[0]) return a[0] - b[0]; // ascending order for x
          return b[1] - a[1]; // descending order for y
        });

    // max heap, top keep the highest height of current scan line
    Queue<Integer> h = new PriorityQueue((Comparator<Integer>) (o1, o2) -> o2 - o1);
    h.offer(0); // Note it is max heap,  need a comparator
    // and provide a initial highest height 0  for the first and last point.
    List<List<Integer>> r =
        new LinkedList<>(); // the skyline point is required to kept in a list<Integer> with size 2
    for (int[] p : ps) {
      if (p[1] > 0 && p[1] > h.peek()) {
        h.offer(p[1]);
        r.add(Arrays.asList(p[0], p[1]));
        continue;
      }
      if (p[1] < 0 && -p[1] == h.peek()) {
        h.remove(-p[1]); // O(H) used to find the location of he target to be removed
        if (h.peek() < -p[1]) r.add(Arrays.asList(p[0], h.peek())); // note here
        // the condition
        continue;
      }
      // be covered by highest height related building
      if (p[1] > 0) h.offer(p[1]); // a top left vertex point
      else h.remove(-p[1]);
    }
    return r;
  }
}
