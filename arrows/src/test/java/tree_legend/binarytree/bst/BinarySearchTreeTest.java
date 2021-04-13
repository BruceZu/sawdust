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

package tree_legend.binarytree.bst;

import org.junit.Assert;
import org.junit.Test;

public class BinarySearchTreeTest {
    @Test(timeout =200L, expected = Test.None.class)
    public void test() {
        BinarySearchTree bst = new BinarySearchTreeImplement<>();
        bst.add('b');
        bst.add('c');
        bst.add('a');
        bst.add('a');
        bst.add('c');
        bst.add('b');
        bst.add('A');
        bst.add('z');
        bst.add('m');
        bst.add('n');
        bst.delete('m');
        bst.delete('n');
        bst.delete('a');
        bst.delete('b');
        bst.delete('c');
        bst.delete('c');
        bst.delete('A');
        bst.delete('Z');
        bst.delete('z');
        bst.delete('b');
        bst.delete('a');
        //bst.drawing();
        Assert.assertEquals(0, bst.size());
    }
}
