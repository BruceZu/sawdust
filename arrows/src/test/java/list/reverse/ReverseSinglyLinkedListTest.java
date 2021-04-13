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

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

// C328
public class ReverseSinglyLinkedListTest {

    @Test(timeout = 100L, expected = Test.None.class)
    public void test() {
        List l = new LinkedList() {{
            add("a");
            add("b");
            add("c");
            add("d");
        }};
        Collections.addAll(l, "A", "B", "C", "D");
        l = ReverseSinglyLinkedList.reverse(l);
        Assert.assertEquals(Arrays.toString(l.toArray()), "[D, C, B, A, d, c, b, a]");
        l = ReverseSinglyLinkedList.reverse2(l);
        Assert.assertEquals(Arrays.toString(l.toArray()), "[a, b, c, d, A, B, C, D]");
    }
}
