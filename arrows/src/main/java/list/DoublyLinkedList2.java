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

package list;


import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

//TODO:  support multi threads access concurrently.
public class DoublyLinkedList2<E> implements MyLinkedList {
    private class Node<E> {
        private E e;
        private Node<E> p /*prev*/;
        private Node<E> n /*next*/;

        private Node(E e, Node<E> prev, Node<E> next) {
            this.e = e;
            this.p = prev;
            this.n = next;
        }

        private Node(E e) {
            this.e = e;
        }

        private Node() {
        }
    }

    private final Node<E> es /*endSentinel*/ = new Node<E>();
    private final Node<E> hs /*headSentinel*/= new Node<E>();
    private int size;

    private int endNodeIndex() {
        if (isEmpty()) {
            throw new IndexOutOfBoundsException("This list is empty");
        }
        return size - 1;
    }

    private int checkPositionIndex(int positionIndex) {
        if (isEmpty()) {
            throw new IndexOutOfBoundsException("This list is empty");
        }
        if (positionIndex < 0 || endNodeIndex() < positionIndex) {
            throw new IndexOutOfBoundsException("Index is wrong ");
        }
        return positionIndex;
    }

    private Node<E> nodeOf(int index) {
        checkPositionIndex(index);
        Node<E> current;
        if (index < size() / 2) {
            int i = 0;
            current = hs.n;
            while (i != index) {
                current = current.n;
                i++;
            }
            return current;
        }
        int i = endNodeIndex();
        current = es.p;
        while (i != index) {
            current = current.p;
            i--;
        }
        return current;
    }

    private void addBetween(@NotNull(value = "") E e,
                            @NotNull(value = "") Node p,
                            @NotNull(value = "") Node n) {
        Node<E> it = new Node<E>(e);
        p.n = it;
        it.n = n;
        n.p = it;
        it.p = p;
        size++;
    }

    private void deleteBetween(@NotNull(value = "") Node p,
                               @NotNull(value = "") Node n) {
        p.n = n;
        n.p = p;
        size--;
    }

    private E delete(int itsIndex,
                     @NotNull(value = "") Node<E> it) {
        checkPositionIndex(itsIndex);
        deleteBetween(it.p, it.n);
        it.p = it.n = null;
        return it.e;
    }

    public DoublyLinkedList2() {
        hs.n = es;
        es.p = hs;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    @Deprecated
    public int size2() {
        int size = 0;
        Node<E> i = hs;
        while (i.n != es) {
            size++;
            i = i.n;
        }
        return size;
    }

    public boolean hasOnlyOneElement() {
        return size == 1;
    }

    public MyLinkedList addBeforeHead(@NotNull(value = "") Object e) {
        addBetween((E) e, hs, hs.n);
        return this;
    }

    public DoublyLinkedList2 appendAfterEnd(@NotNull(value = "") Object e) {
        addBetween((E) e, es.p, es);
        return this;
    }

    public DoublyLinkedList2 addBefore(@NotNull(value = "") Object e, int index) {
        checkPositionIndex(index);
        Node<E> i = nodeOf(index);
        addBetween((E) e, i.p, i);
        return this;
    }

    public DoublyLinkedList2 addAfter(@NotNull(value = "") Object e, int index) {
        checkPositionIndex(index);
        Node<E> i = nodeOf(index);
        addBetween((E) e, i, i.n);
        return this;
    }

    public E deleteHead() {
        return delete(0, hs.n);
    }

    public E deleteEnd() {
        return delete(endNodeIndex(), es.p);
    }

    public E delete(int index) {
        Node<E> it = nodeOf(index);
        return delete(index, it);
    }

    public E update(int index, @NotNull(value = "") Object e) {
        Node<E> n = nodeOf(index);
        E re = n.e;
        n.e = (E) e;
        return re;
    }

    public E updateHead(@NotNull(value = "") Object e) {
        E re = hs.n.e;
        hs.n.e = (E) e;
        return re;
    }

    public E updateEnd(@NotNull(value = "") Object e) {
        E re = es.p.e;
        es.p.e = (E) e;
        return re;
    }

    public E getHead() {
        if (isEmpty()) {
            return null;
        }
        return hs.n.e;
    }

    public E getEnd() {
        if (isEmpty()) {
            return null;
        }
        return es.p.e;
    }

    public E get(int index) {
        checkPositionIndex(index);
        return nodeOf(index).e;
    }

    /**
     * Finding the middle node with header and trailer sentinels by “link hopping,”
     * and without relying on explicit knowledge of the size of the list.
     * In the case of an even number of nodes, report the node slightly left of
     * center as the “middle.”
     *
     * @return
     */
    @Deprecated
    public E getMiddle() {
        Node<E> l = hs, r = es;
        while (true) {
            if (l.n == r) {
                return l.e;
            }
            l = l.n;
            if (l.n == r) {
                return l.e;
            }
            r = r.p;
            if (l.n == r) {
                return l.e;
            }
        }
    }

    public E getMiddle2() {
        int size = size();
        Node<E> iNode = hs.n;
        for (int i = 0; i < (size % 2 == 0 ? size / 2 - 1 : size / 2); i++) {
            iNode = iNode.n;
        }
        return iNode.e;
    }

    @Override
    public void clean() {
        if (size() == 0) {
            return;
        }
        size = 0;
        hs.n = es;
        es.p = hs;
    }

    @Override
    public MyLinkedList clone() {
        DoublyLinkedList2 r = new DoublyLinkedList2();
        Node<E> i = hs.n;
        while (i != es) {
            E e = i.e;
            E eClone;
            if (e instanceof Cloneable) {
                try {
                    if (e instanceof Object[])
                        eClone = (E) ((Object[]) e).clone();
                    else if (e instanceof byte[])
                        eClone = (E) ((byte[]) e).clone();
                    else if (e instanceof short[])
                        eClone = (E) ((short[]) e).clone();
                    else if (e instanceof int[])
                        eClone = (E) ((int[]) e).clone();
                    else if (e instanceof long[])
                        eClone = (E) ((long[]) e).clone();
                    else if (e instanceof char[])
                        eClone = (E) ((char[]) e).clone();
                    else if (e instanceof float[])
                        eClone = (E) ((float[]) e).clone();
                    else if (e instanceof double[])
                        eClone = (E) ((double[]) e).clone();
                    else if (e instanceof boolean[])
                        eClone = (E) ((boolean[]) e).clone();
                    else
                        eClone = (E) e.getClass().getDeclaredMethod("clone").invoke(e);
                    r.appendAfterEnd(eClone);
                } catch (NoSuchMethodException |
                        IllegalAccessException |
                        InvocationTargetException ex) {
                    r.appendAfterEnd(e);
                }
            } else {
                r.appendAfterEnd(e);
            }
            i = i.n;
        }
        return r;
    }

    @Override
    public boolean equals(Object it) {
        if (it == null) {
            return false;
        }
        if (it == this) {
            return true;
        }
        if (!(it instanceof DoublyLinkedList2) || this.size() != ((DoublyLinkedList2) it).size()) {
            return false;
        }
        if (size() == ((DoublyLinkedList2) it).size() && size() == 0) {
            return true;
        }
        Node<E> i = this.hs.n;
        Node<E> j = ((DoublyLinkedList2<E>) it).hs.n;
        while (i != this.es) {
            E e1 = i.e;
            E e2 = j.e;

            if (e1 == e2) {
                i = i.n;
                j = j.n;
                continue;
            }
            if (e1 == null) {
                return false;
            }

            boolean eq;

            if (e1 instanceof Object[] && e2 instanceof Object[])
                eq = Arrays.deepEquals((Object[]) e1, (Object[]) e2);
            else if (e1 instanceof byte[] && e2 instanceof byte[])
                eq = Arrays.equals((byte[]) e1, (byte[]) e2);
            else if (e1 instanceof short[] && e2 instanceof short[])
                eq = Arrays.equals((short[]) e1, (short[]) e2);
            else if (e1 instanceof int[] && e2 instanceof int[])
                eq = Arrays.equals((int[]) e1, (int[]) e2);
            else if (e1 instanceof long[] && e2 instanceof long[])
                eq = Arrays.equals((long[]) e1, (long[]) e2);
            else if (e1 instanceof char[] && e2 instanceof char[])
                eq = Arrays.equals((char[]) e1, (char[]) e2);
            else if (e1 instanceof float[] && e2 instanceof float[])
                eq = Arrays.equals((float[]) e1, (float[]) e2);
            else if (e1 instanceof double[] && e2 instanceof double[])
                eq = Arrays.equals((double[]) e1, (double[]) e2);
            else if (e1 instanceof boolean[] && e2 instanceof boolean[])
                eq = Arrays.equals((boolean[]) e1, (boolean[]) e2);
            else
                eq = e1.equals(e2);

            if (!eq)
                return false;
            i = i.n;
            j = j.n;
        }
        return true;
    }
}
