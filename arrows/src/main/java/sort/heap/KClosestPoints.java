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

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * <pre>
 * Find the K closest points to the origin in a 2D plane,
 * given an array containing N points.
 */
public class KClosestPoints {

    public class Point {
        public int x;
        public int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    /**
     * Quick Sort     Average performance O(n log n)
     * Quick Select   Average performance O(n)
     * @see sort.Leetcode215KthLargestElementinanArray3
     * Heap of k size: O(nlogk)
     */
    public List<Point> findKClosest(Point[] pOf, int k) {
        Queue<Point> pq = new PriorityQueue<>(
                (a, b) -> (b.x * b.x + b.y * b.y) - (a.x * a.x + a.y * a.y));

        for (int i = 0; i < pOf.length; i++) {
            if (i < k)
                pq.offer(pOf[i]);
            else {
                Point head = pq.peek();
                if ((pOf[i].x * pOf[i].x + pOf[i].y * pOf[i].y) < (head.x * head.x + head.y * head.y)) {
                    pq.poll();
                    pq.offer(pOf[i]);
                }
            }
        }

        List<Point> r = new ArrayList<>();
        while (!pq.isEmpty())
            r.add(pq.poll());

        return r;
    }
}
