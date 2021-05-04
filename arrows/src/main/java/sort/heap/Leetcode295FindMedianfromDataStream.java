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

import java.util.PriorityQueue;

/**
 * <pre>
 *     <a href="https://leetcode.com/problems/find-median-from-data-stream/?tab=Description">LeetCode</a>
 */
public class Leetcode295FindMedianfromDataStream {
    class MedianFinder {
        /**
         * initialize your data structure here.
         */
        private PriorityQueue<Integer> top;
        private PriorityQueue<Integer> bottom;

        public MedianFinder() {
            top = new PriorityQueue<>((a, b) -> -a.compareTo(b));
            bottom = new PriorityQueue<>();
        }

        // o(lgN)
        public void addNum(int num) {
            if (top.size() <= bottom.size()) { // let top size >= bottom
                top.offer(num);
            } else {
                bottom.offer(num);
            }
            if (!bottom.isEmpty()) {
                while (top.peek() > bottom.peek()) {
                    bottom.offer(top.poll());
                    top.offer(bottom.poll());
                }
            }
        }

        public double findMedian() {
            if ((top.size() + bottom.size()) % 2 == 0) {
                return (top.peek() * 1L + bottom.peek()) * 0.5;
            }
            return top.peek();
        }
    }
}

/**
 * Your MedianFinder object will be instantiated and called as such:
 * MedianFinder obj = new MedianFinder();
 * obj.addNum(num);
 * double param_2 = obj.findMedian();
 */
class MedianFinder {
    PriorityQueue<Integer> small = new PriorityQueue();
    PriorityQueue<Integer> bigger = new PriorityQueue<>((a, b) -> -a.compareTo(b));

    public void addNum(int num) {
        bigger.offer(num); // guarantee the order
        small.offer(bigger.poll());
        if (bigger.size() < small.size()) { //keep size >= small' size
            bigger.offer(small.poll());
        }
    }

    public double findMedian() {
        if (bigger.size() == small.size()) return (bigger.peek() + small.peek()) / 2.0;
        else return bigger.peek();
    }
}