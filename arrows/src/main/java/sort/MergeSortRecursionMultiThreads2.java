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
import common_lib.Merger;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MergeSortRecursionMultiThreads2<T extends Comparable<T>> {
  private static Merger merger = new Common();

  // Runnable is enough, do not need Callable as each thread only work on its elements scope of arr
  private static class DivideMergeInSort implements Runnable {
    private Comparable[] A, T;
    private int l, r;

    @Override
    public void run() {
      // stop divide
      if (l == r) return;
      int m = l + r >>> 1;
      try {
        Future<?> f = executor.submit(new DivideMergeInSort(A, l, m, T));
        Future<?> f2 = executor.submit(new DivideMergeInSort(A, m + 1, r, T));
        f.get();
        f2.get();
      } catch (InterruptedException | ExecutionException e) {
        e.printStackTrace();
      }

      merger.mergeInsort(A, l, m, r, T);
    }

    public DivideMergeInSort(Comparable[] A, int l, int r, Comparable[] T) {
      this.A = A;
      this.l = l;
      this.r = r;
      this.T = T;
    }
  }

  private static ExecutorService executor;

  public static <T extends Comparable<T>> void mergeSort(T[] A) {
    // Input check, note: arr may be empty array: {}
    if (A == null || A.length <= 1) return;
    try {
      // https://www.mathsisfun.com/algebra/sequences-sums-geometric.html
      executor =
          Executors.newFixedThreadPool(
              2 * A.length - 2 /*Runtime.getRuntime().availableProcessors();*/);
      executor.submit(new DivideMergeInSort(A, 0, A.length - 1, new Comparable[A.length])).get();

    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    } finally {
      executor.shutdown();
    }
  }
}
