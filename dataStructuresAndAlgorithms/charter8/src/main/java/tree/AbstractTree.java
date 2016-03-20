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

package tree;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public abstract class AbstractTree<T extends TreeNode<T, E>, E> implements Tree<T, E> {

    private int childrenMaxDepth(T n, int ancs /* number of ancestors */) {

        int maxChildDepth = 0;
        for (T c : n.<E>getChildren()) {
            if (!isLeaf(c)) {
                maxChildDepth = Math.max(maxChildDepth, childrenMaxDepth(c, ancs + 1));
            }
            // skip leaf as it is not the child with max depth
        }

        return maxChildDepth == 0 ? ancs : maxChildDepth;
    }

    @Override
    public boolean isLeaf(T n) {
        return n.getChildren() == null || n.childrenSize() == 0;
    }

    // Tree' height :
    //  - if the tree is empty or a leaf: 0
    //  - the maximum of the depth of its nodes
    // O(N)
    @Override
    public int height() {
        T r = root();
        if (r == null || isLeaf(r)) {
            return 0;
        }
        return childrenMaxDepth(r, 1);
    }

    // Tree' height :
    //   - if root is empty or a leaf: 0
    //   - else: 1 + max(the height of subtrees).
    // O(N)
    @Override
    public int height(T n) {
        if (n == null || isLeaf(n)) {
            return 0;
        }
        int subTreeHeight = 0;
        for (T c : n.<E>getChildren()) {
            if (!isLeaf(c)) {
                subTreeHeight = Math.max(subTreeHeight, height(c));
            }
            // skip leaf as it is not the child with max depth
        }
        return 1 + subTreeHeight;
    }

    // The depth of a node: the number of ancestors of node, other than node itself.
    @Override
    public int depth(T n) {
        if (n.getParent() == null) {
            return 0;
        }
        return 1 + depth(n.getParent());
    }

    private void breadthFirstPrint(Queue<T> cs /*children*/) {
        T n = cs.poll();
        if (n == null) {
            return;
        }
        System.out.println(n.getElement().toString());
        Iterator<T> ite = n.getChildren().iterator();
        while (ite.hasNext()) {
            cs.offer(ite.next());
        }
        breadthFirstPrint(cs);
    }

    public void breadthFirstPrint2() {
        int size = size();
        if (size() == 0) {
            return;
        }
        Queue<T> children = new LinkedList();
        // or use a array with size length, and 2 cursors.
        children.add(root());
        breadthFirstPrint(children);
    }

    @Override
    public Iterator<T> iteratorBreadthFirstOrder() {
        return new Iterator<T>() {
            // todo: fail fast
            private Object[] children;
            private int cursor;
            private int insertIndex;

            private Iterator<T> init() {
                int size = size();

                children = new Object[size];
                children[0] = root();
                cursor = -1;
                insertIndex = 1;
                return this;
            }

            @Override
            public boolean hasNext() {
                return size() != 0 && cursor != size() - 1; // check by size
            }

            @Override
            public T next() {
                T r = (T) children[++cursor];
                Iterator<T> ite = r.getChildren().iterator();
                while (ite.hasNext()) {
                    children[insertIndex++] = ite.next();
                }
                return r;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        }.init();
    }
}
