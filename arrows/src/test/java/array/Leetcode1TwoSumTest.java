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

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

@RunWith(Parameterized.class)
public class Leetcode1TwoSumTest {
    @Parameterized.Parameters(name = " test {index}")
    public static Iterable<int[][]> data() {
        return Arrays.asList(new int[][][]{
                // {test array}, {target, result1 result2}
                {{3, 2, 4}, {6, 1, 2}},
                {{3, 3, 4}, {8, -1, -1}},
                {{8, 0, 8}, {16, 0, 2}},
                {{0, 4, 3, 0}, {0, 0, 3}},
                {{-1, -2, -3, -4, -5}, {-8, 2, 4}},
                {{230, 863, 916, 585, 981, 404, 316, 785, 88, 12, 70, 435,
                        384, 778, 887, 755, 740, 337, 86, 92, 325, 422,
                        815, 650, 920, 125, 277, 336, 221, 847,
                        168, 23, 677, 61, 400, 136, 874, 363, 394, 199,
                        863, 997, 794, 587, 124, 321, 212, 957, 764, 173,
                        314, 422, 927, 783, 930, 282, 306, 506, 44,
                        926, 691, 568, 68, 730, 933, 737, 531, 180, 414,
                        751, 28, 546, 60, 371, 493, 370, 527, 387, 43,
                        541, 13, 457, 328, 227, 652, 365, 430, 803, 59, 858,
                        538, 427, 583, 368, 375, 173, 809, 896, 370, 789},
                        {542, 28, 45}}
        });
    }

    private int[] array;
    private int target;
    private int i, j;

    public Leetcode1TwoSumTest(int[] array, int[] targetAndExpectedResult) {
        this.array = array;
        this.target = targetAndExpectedResult[0];
        this.i = targetAndExpectedResult[1];
        this.j = targetAndExpectedResult[2];
    }
}
