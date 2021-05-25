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

package list.reverse;

import list.ListNode;
import list.ListRecursion;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class ReverseListTest {

  //    @Parameterized.Parameters(name = "test {index}")
  //    public static Iterable<int[]> data() {
  //        return Arrays.asList(new int[][]{
  //                {1, 2, 3, 4},
  //                {},
  //                {1, 2}
  //        });
  //    }

  @Parameterized.Parameters(name = "test with {index}")
  public static Collection<Object[]> data() {
    return Arrays.asList(
        new Object[][] {
          {new int[] {1, 2, 3, 4}},
          {new int[] {}},
          {new int[] {1, 2}},
        });
  }

  private int[] originalData;

  public ReverseListTest(int[] array) {
    this.originalData = array;
  }

  @Test(timeout = 10L, expected = Test.None.class)
  public void test() {
    ListNode test;
    if (originalData.length == 0) {
      test = null;
    } else {
      test = new ListNode(originalData[0]);
      for (int i = 1; i < originalData.length; i++) {
        ListRecursion.insertRear(test, originalData[i]);
      }
    }
    Assert.assertEquals(
        Arrays.toString(ListRecursion.toArray(test)), Arrays.toString(originalData));
  }
}
