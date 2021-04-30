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

package binarysearch;

import math.Leetcode29DivideTwoIntegers;
import org.junit.Assert;
import org.junit.Test;

public class Leetcode29DivideTwoIntegersTest {
    @Test(timeout = 30000l, expected = Test.None.class)
    public void test() {
        Assert.assertEquals(Leetcode29DivideTwoIntegers.divide(0, 5), 0);
        Assert.assertEquals(Leetcode29DivideTwoIntegers.divide(2, 0), Integer.MAX_VALUE);

        Assert.assertEquals(Leetcode29DivideTwoIntegers.divide(-4, 5), 0);
        Assert.assertEquals(Leetcode29DivideTwoIntegers.divide(9, -5), -1);

        Assert.assertEquals(Leetcode29DivideTwoIntegers.divide(2147483647, 1), 2147483647);
        Assert.assertEquals(Leetcode29DivideTwoIntegers.divide(-1010369383, -2147483648), 0);
        Assert.assertEquals(Leetcode29DivideTwoIntegers.divide(-2147483648, -1), 2147483647);
        Assert.assertEquals(Leetcode29DivideTwoIntegers.divide(-2147483648, 2), -1073741824);
        Assert.assertEquals(Leetcode29DivideTwoIntegers.divide(2147483647, 3), 715827882);
        Assert.assertEquals(Leetcode29DivideTwoIntegers.divide(-2147483648, 2147483647), -1);
        Assert.assertEquals(Leetcode29DivideTwoIntegers.divide(-2147483648, -3), 715827882);
    }
}
