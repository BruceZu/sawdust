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

package tree.heap;

import org.junit.Assert;
import org.junit.Test;
import sort.heap.Heap;

public class TestHeap {
    @Test(timeout = 10l, expected = Test.None.class)
    public void addAndDeleteMaxHeapTest() {
        Heap h = new Heap(30);
        Assert.assertEquals(0, h.add(new Integer[]{1}, true).compareTo((Integer) 1));
        Assert.assertEquals(0, h.add(new Integer[]{2}, true).compareTo((Integer) 2));
        Assert.assertEquals(0, h.add(new Integer[]{3}, true).compareTo((Integer) 3));
        Assert.assertEquals(0, h.add(new Integer[]{4}, true).compareTo((Integer) 4));
        Assert.assertEquals(0, h.add(new Integer[]{4}, true).compareTo((Integer) 4));
        Assert.assertEquals(0, h.add(new Integer[]{5}, true).compareTo((Integer) 5));

        Assert.assertEquals(0, h.delete(true).compareTo((Integer) 5));
        Assert.assertEquals(0, h.delete(true).compareTo((Integer) 4));
        Assert.assertEquals(0, h.delete(true).compareTo((Integer) 4));
        Assert.assertEquals(0, h.delete(true).compareTo((Integer) 3));
        Assert.assertEquals(0, h.delete(true).compareTo((Integer) 2));
        Assert.assertEquals(0, h.delete(true).compareTo((Integer) 1));
    }

    @Test(timeout = 10l, expected = Test.None.class)
    public void addAndDeleteMinHeapTest() {
        Heap h = new Heap(30);
        Assert.assertEquals(0, h.add(new Integer[]{1}, false).compareTo((Integer) 1));
        Assert.assertEquals(0, h.add(new Integer[]{2}, false).compareTo((Integer) 1));
        Assert.assertEquals(0, h.add(new Integer[]{3}, false).compareTo((Integer) 1));
        Assert.assertEquals(0, h.add(new Integer[]{4}, false).compareTo((Integer) 1));
        Assert.assertEquals(0, h.add(new Integer[]{0}, false).compareTo((Integer) 0));

        Assert.assertEquals(0, h.delete(false).compareTo((Integer) 0));
        Assert.assertEquals(0, h.delete(false).compareTo((Integer) 1));
        Assert.assertEquals(0, h.delete(false).compareTo((Integer) 2));
        Assert.assertEquals(0, h.delete(false).compareTo((Integer) 3));
        Assert.assertEquals(0, h.delete(false).compareTo((Integer) 4));
        Assert.assertTrue(h.delete(true) == null);
    }

    @Test(timeout = 10l, expected = Test.None.class)
    public void sizeEnlargeTest() {
        Heap h = new Heap(2);
        Assert.assertEquals(h.add(new Integer[]{1}, true).compareTo((Integer) 1), 0);
        Assert.assertEquals(h.add(new Integer[]{2}, true).compareTo((Integer) 2), 0);
        Assert.assertEquals(h.add(new Integer[]{3}, true).compareTo((Integer) 3), 0);
    }
}
