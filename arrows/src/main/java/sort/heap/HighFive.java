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

/**
 * <a href="https://discuss.leetcode.com/topic/61493/five-score-average"> LeetCode interview</a>
 */
public class HighFive {
    class Result {
        int id;
        int score;

        Result(int i, int s) {
            id = i;
            score = s;
        }
    }

    public Map<Integer, Integer> topFives(List<Result> studentScores) {
        Map<Integer, Queue<Result>> studentAndScores = new HashMap<>();
        for (Result idScore : studentScores) {
            Queue<Result> q = null;
            if ((q = studentAndScores.get(idScore.id)) == null)
                studentAndScores.put(idScore.id, q = new PriorityQueue<>((a, b) -> b.score - a.score));

            q.offer(idScore);
        }
        Map<Integer, Integer> highFives = new HashMap<>();
        for (Map.Entry<Integer, Queue<Result>> r : studentAndScores.entrySet()) {
            int avg = 0;
            Queue<Result> scores = r.getValue();
            for (int i = 0; i < 5; i++) {// count 5
                avg += scores.poll().score;
            }
            highFives.put(r.getKey(), avg / 5);
        }
        return highFives;
    }


    // O(logn!)?
    public Map<Integer, Integer> topFives_(List<Result> studentScores) {
        Queue<Result> sss = new PriorityQueue<>((o1, o2) -> {
            if (o1.id != o2.id) {
                return o1.id - o2.id;
            } else {
                return o2.score - o1.score;
            }
        });
        for (Result r : studentScores) {
            sss.offer(r);
        }

        Map<Integer, Integer> result = new HashMap();

        int curStudentId = sss.peek().id;
        int addedSize = 0;
        int avg = 0;
        while (!sss.isEmpty()) { // get one and check one each time
            if (curStudentId == sss.peek().id) {
                Result cur = sss.poll();
                if (addedSize <= 4) {
                    avg += cur.score;
                    addedSize++;
                    if (addedSize == 5) {
                        result.put(curStudentId, avg / 5);
                    }
                }
            } else {
                curStudentId = sss.peek().id;
                addedSize = 0;
                avg = 0;
            }
        }
        return result;
    }

//    // -------------------------------------------------------------------------------------
//    @Test
//    public void test() {
//        Map<Integer, Integer> expected = new HashMap<Integer, Integer>() {
//            {
//                put(1, 80);
//                put(2, 80);
//                put(3, 100);
//                put(4, 90);
//            }
//        };
//        List<Result> test = Arrays.asList(
//                new Result(1, 90),
//                new Result(1, 70),
//                new Result(1, 60),
//                new Result(2, 60),
//                new Result(2, 100),
//                new Result(2, 80),
//                new Result(2, 90),
//                new Result(2, 70),
//                new Result(2, 40),
//                new Result(3, 100),
//                new Result(3, 100),
//                new Result(3, 100),
//                new Result(3, 100),
//                new Result(3, 100),
//                new Result(3, 40),
//                new Result(4, 90),
//                new Result(4, 90),
//                new Result(4, 90),
//                new Result(4, 90),
//                new Result(4, 90),
//                new Result(4, 40),
//                new Result(4, 20),
//                new Result(1, 100),
//                new Result(1, 80));
//        assertEquals(expected, topFives_(test));
//        assertEquals(expected, topFives(test));
//    }
}
