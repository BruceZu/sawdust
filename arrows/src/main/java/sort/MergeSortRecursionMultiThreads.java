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

package sort;

import common_lib.Common;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import static common_lib.Common.divide;

public class MergeSortRecursionMultiThreads {
  /**
   * Merge sort an array in recursion with multi-threads
   * <pre>
   * Idea:
   * 1> divide into 2 sub arrays
   * 2> apply divide-merge in sort logical recursively on 2 sub arrays
   * 3> merge them into one in sort
   *
   * Note, when arr == null || arr.length <= 1, then
   * stop start
   * or
   * stop recursion and return to wait merge in sort directly
   *
   */
  private static AtomicInteger numberOfThreads = new AtomicInteger();

  public static <T extends Comparable<T>> void mergeSort(T[] A) {
    // Input check
    if (A == null || A.length <= 1) return;

    // 1 Divide into 2 halves
    final Comparable[][] hs = divide(A);

    // 2 Sort each halves
    final CountDownLatch cd = new CountDownLatch(2);
    new Thread(
            () -> {
              mergeSort(hs[0]);
              numberOfThreads.getAndAdd(1);
              cd.countDown();
            })
        .start();
    new Thread(
            () -> {
              mergeSort(hs[1]);
              numberOfThreads.getAndAdd(1);
              cd.countDown();
            })
        .start();

    // 3 Merge them back into one.
    try {
      cd.await(); // Simple than join() and CyclicBarrier
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    Common.mergeInsort(hs[0], hs[1], A);
  }

  public static void main(String[] args) {
    mergeSort(new Integer[] {3, 6, 8, 6, 1, 2, 90, 44});
    System.out.println(numberOfThreads.get());
  }
}
