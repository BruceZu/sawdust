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

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class MergeSortRecursionMultiThreads4 {
  private static Merger merger = new Common();

  private static class DivideMergeInSortAction extends RecursiveAction {
    private final Comparable[] A, T;
    private final int l, r;

    @Override
    protected void compute() {
      // Input check, threshold
      if (l == r) return;
      int m = l + r >>> 1;
      invokeAll(
          new DivideMergeInSortAction(A, l, m, T), new DivideMergeInSortAction(A, m + 1, r, T));

      merger.mergeInsort(A, l, m, r, T);
    }

    public DivideMergeInSortAction(Comparable[] A, int l, int r, Comparable[] T) {
      this.A = A;
      this.l = l;
      this.r = r;
      this.T = T;
    }
  }

  public static <T extends Comparable<T>> void mergeSort(T[] arr) {
    // Input check
    if (arr == null || arr.length <= 1) { // note: arr may be empty array: {}
      return;
    }
    ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
    forkJoinPool.invoke(
        new DivideMergeInSortAction(arr, 0, arr.length - 1, new Comparable[arr.length]));
  }
}
