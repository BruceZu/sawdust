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

package queue;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Stack;

/**
 * <pre>
 * Pros: easy to understand. As have a layer of API of Stack between Queue and basic core Java data structures.
 *       separate the add and remove operation to balance their performance.
 * Cons: maintenance 2 stacks in space. and need move data
 *
 * Assume:
 *      1 null is not allowed.
 *      2 no limit on the class of element, but all elements need be of same class.
 *
 *      1 no limit on the property of element.
 *      2 repeated element is allowed.
 *      3 there is not size limited.
 *
 * Design:
 *      move data: when, who and how many.
 *      depends on special scenarios
 *      1  more offer/add operations
 *      2  more poll/remove, peek/element operations.
 *      3  balance them
 *
 *
 *      before or after offer/add.
 *      before or after poll/remove.
 *      another daemon on scheduled time.
 *
 *      care: size of in, out is empty or not, size of data to move.
 *
 * To make it simple:
 *      before poll or peek. check out, if it is empty,
 *      check in, if it is not empty then move all data from in.
 */
public class QueueWith2Stacks<E> implements Queue, FIFOQueue {
    private Stack<E> in;
    private Stack<E> out;


    public QueueWith2Stacks() {
        in = new Stack<>();
        out = new Stack<>();
    }

    @Override
    public boolean add(Object o) {
        return offer(o);
    }

    @Override
    public boolean offer(Object o) {
        if (o == null) {
            throw new NullPointerException("Null is not allowed");
        }
        in.push((E) o);
        return true;
    }

    @Override
    public Object remove() {
        Object r = poll();
        if (r == null) {
            throw new NoSuchElementException();
        }
        return r;
    }

    @Override
    public Object poll() {
        synchronized (out) {
            if (out.size() != 0) {
                return out.pop();
            }
            synchronized (in) {
                if (in.size() == 0) {
                    return null;
                }
                while (!in.empty()) {
                    out.push(in.pop());
                }
            }
            return out.pop();
        }
    }

    @Override
    public Object element() {
        Object r = poll();
        if (r == null) {
            throw new NoSuchElementException();
        }
        return r;
    }

    @Override
    public Object peek() {
        synchronized (out) {
            if (out.size() != 0) {
                return out.peek();
            }
            synchronized (in) {
                if (in.size() == 0) {
                    return null;
                }
                while (!in.empty()) {
                    out.push(in.pop());
                }
            }
            return out.peek();
        }
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public int size() {
        synchronized (out) {
            synchronized (in) {
                return in.size() + out.size();
            }
        }
    }

    // interfaces of  Collection
    @Override
    public boolean contains(Object o) {
        throw new NotImplementedException();
    }

    @Override
    public boolean remove(Object o) {
        throw new NotImplementedException();
    }

    @Override
    public Iterator iterator() {
        throw new NotImplementedException();
    }

    @Override
    public Object[] toArray() {
        throw new NotImplementedException();
    }

    @Override
    public Object[] toArray(Object[] a) {
        throw new NotImplementedException();
    }

    @Override
    public boolean addAll(Collection c) {
        throw new NotImplementedException();
    }

    @Override
    public void clear() {
        throw new NotImplementedException();
    }

    @Override
    public boolean retainAll(Collection c) {
        throw new NotImplementedException();
    }

    @Override
    public boolean removeAll(Collection c) {
        throw new NotImplementedException();
    }

    @Override
    public boolean containsAll(Collection c) {
        throw new NotImplementedException();
    }

    @Override
    public String toString() {
        synchronized (out) {
            synchronized (in) {
                StringBuilder sb = new StringBuilder();
                if (!out.empty()) {
                    String os = out.toString();
                    sb.append(os.substring(1, os.length() - 1)).reverse();
                }
                if (!in.empty()) {
                    String is = in.toString();
                    if (!out.empty()) {
                        sb.append(", ");
                    }
                    sb.append(is.substring(1, is.length() - 1));
                }
                sb.append("]");
                return "[" + sb.toString();
            }
        }
    }
}
