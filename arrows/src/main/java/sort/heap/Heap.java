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

/**
 * <pre>
 *      e
 *    e   e
 *  e e   e e
 *
 *  parent index = i
 *  left child index = 2*i + 1
 *  right child index = 2*i + 2
 *
 *  T[]:
 *  ( e )( e e ) ( e e e e ) ( e e e  e e  e  e  e )
 *    0    1 2     3 4 5 6     7 8 9 10 11 12 13 14
 *
 * @param <T>
 */
public class Heap<T extends Comparable> {
    private final static int LIMITED_SIZE = Integer.MAX_VALUE >> 2;
    private T[] data;
    int nextInsertIndex = 0;

    private void enlargeSize() {
        if (nextInsertIndex >= LIMITED_SIZE) {
            throw new ArrayIndexOutOfBoundsException("reach the limit of heap size " + LIMITED_SIZE);
        }
        int enlargedSize = Math.min(LIMITED_SIZE, nextInsertIndex << 2);
        T[] enlarged = (T[]) new Comparable[enlargedSize];
        System.arraycopy(data, 0, enlarged, 0, nextInsertIndex);
        data = enlarged;
    }

    public Heap() {
        data = (T[]) new Comparable[16];
    }

    public Heap(int size) {
        data = (T[]) new Comparable[size];
    }

    /**
     * - check data size
     * - append the e to the end of data
     * - sort
     * -- compare with parent, keep swap if(p less than I and it is max, p greater or same as I and it is min).
     * -- till condition is not match or to the root of the head, the index is 0;
     * -  return the max/min
     *
     * @param e
     * @param maxAtTop order by ascending order, root is the max one, or descending order with mix at root
     * @return max/min element of heap
     */
    public T add(T e, boolean maxAtTop) {
        if (nextInsertIndex == data.length) {
            enlargeSize();
        }

        data[nextInsertIndex++] = e;
        int eIndex = nextInsertIndex - 1;

        while (true) {
            int parentIndex = (eIndex & 1) == 1 ? (eIndex - 1) / 2 : (eIndex - 2) / 2;
            if (parentIndex == -1) {
                break;
            }

            T p = data[parentIndex];
            T I = data[eIndex];
            boolean pIsSmall = p.compareTo(I) < 0;
            boolean pIsBig = p.compareTo(I) > 0;
            if (maxAtTop && pIsSmall || !maxAtTop && pIsBig) {
                T tmp = p;
                data[parentIndex] = I;
                data[eIndex] = p;
                eIndex = parentIndex;
                continue;
            }
            break; // same as parent or be in order as expected
        }
        return data[0];
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (i < nextInsertIndex - 1) {
            sb.append(data[i++]).append(", ");
        }
        sb.append(data[i++]);
        return sb.toString();
    }

    public T add(T[] ar, boolean max) {
        for (int i = 0; i < ar.length; i++) {
            add(ar[i], max);
        }
        return data[0];
    }

    /**
     * Delete the root
     * plug in the empty seat of root by the end one.
     * sink till has not child(s) or to where cannot sink again
     *
     * @return the deleted root
     */

    public T delete(boolean max) {
        if (nextInsertIndex == 0) {
            return null;
        }
        T re = data[0];
        data[0] = data[nextInsertIndex - 1];
        data[nextInsertIndex - 1] = null;
        nextInsertIndex--;

        int IIndex = 0;
        while (true) {
            int lIndex = 2 * IIndex + 1;
            int rIndex = 2 * IIndex + 2;
            boolean hasL = nextInsertIndex > lIndex;
            boolean hasR = nextInsertIndex > rIndex;
            if (!hasL) {
                return re;
            }
            int indexCompare = -1;
            if (hasL && !hasR) {
                indexCompare = lIndex;
            } else if (hasL && hasR) {
                T left = data[lIndex];
                T right = data[rIndex];
                if (max) {
                    indexCompare = left.compareTo(right) > 0 ? lIndex : rIndex;
                } else {
                    indexCompare = left.compareTo(right) < 0 ? lIndex : rIndex;
                }
            }

            boolean IAmLess = data[IIndex].compareTo(data[indexCompare]) < 0;
            boolean IAmGreat = data[IIndex].compareTo(data[indexCompare]) > 0;

            boolean needSwap = max && IAmLess || !max && IAmGreat;
            if (needSwap) {
                T tmp = data[IIndex];
                data[IIndex] = data[indexCompare];
                data[indexCompare] = tmp;
                IIndex = indexCompare;
                continue;
            }
            return re;
        }
    }
}

