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

package math;

public class Leetcode1093StatisticsfromaLargeSample {

  public static double[] sampleStats1(int[] count) {

    /*
    a large sample of integers in the range [0, 255]
    sample is so large, it is represented by an array count where count[k]
    is the number of times that k appears in the sample.
        count.length == 256
        0 <= count[i] <= 10^9
        1 <= sum(count) <= 10^9
        The mode of the sample that count represents is unique.

        Calculate the following statistics:


      mean: The average of the sample,
      median:
           odd number of elements,  the median is the middle element once the sample is sorted.
           even number of elements, then the median is the average of the two middle elements
                                    once the sample is sorted.
      mode: The number that appears the most in the sample. It is guaranteed to be unique.

       Return the statistics of the sample as an array of floating-point numbers
        [minimum, maximum, mean, median, mode].
       Answers within 10-5 of the actual answer will be accepted.
    */
    int N = count.length;
    double min = Integer.MAX_VALUE, max = 0, sum = 0, mode = 0;
    int cnt = 0;
    for (int i = 0; i < N; i++) {
      if (count[i] == 0) continue;
      if (min == Integer.MAX_VALUE) min = i;
      max = i;
      sum += 1d * i * count[i]; // Note here it can be native integer without the 1d*
      cnt += count[i]; // Note here
      if (count[i] > count[(int) mode]) mode = i;
    }
    double mean = sum / (1d * cnt);
    // calculate median
    int leftEvenMedianIdx = cnt / 2; // 1 based
    int oddMedianIdx = (cnt + 1) / 2; // 1 based
    int curCnt = 0;
    double median = 0;
    for (int i = 0; i < N; i++) {
      if (count[i] == 0) continue;
      curCnt += count[i];
      if ((cnt & 1) == 1 && curCnt >= oddMedianIdx) {
        median = i;
        break;
      }
      if ((cnt & 1) == 0) {
        if (curCnt >= leftEvenMedianIdx && median == 0)
          median = i; // Note here assign value only once
        if (curCnt >= leftEvenMedianIdx + 1) {
          median = (median + i) / 2;
          break;
        }
      }
    }
    return new double[] {min, max, mean, median, mode};
  }

  // ---------------------------------------------------------------------------
  /*
  left median and right median for odd and even total counts
    int l = (cnt + 1) / 2; // 1 based
    int r = (cnt + 2) / 2; // 1 based
   */
  public static double[] sampleStats(int[] count) {
    int N = count.length;
    double min = Integer.MAX_VALUE, max = 0, sum = 0, mode = 0;
    int cnt = 0;
    for (int i = 0; i < N; i++) {
      if (count[i] == 0) continue;
      if (min == Integer.MAX_VALUE) min = i;
      max = i;
      sum += 1d * i * count[i]; // Note here it can be native integer without the 1d*
      cnt += count[i]; // Note here
      if (count[i] > count[(int) mode]) mode = i;
    }
    double mean = sum / (1d * cnt);
    // calculate median
    int l = (cnt + 1) / 2; // 1 based
    int r = (cnt + 2) / 2; // 1 based
    int curCnt = 0;
    double median = 0;
    for (int i = 0; i < N; i++) {
      if (count[i] == 0) continue;
      curCnt += count[i];
      if (curCnt >= l && median == 0) median = i; // Note here assign value only once
      if (curCnt >= r) {
        median = (median + i) / 2;
        break;
      }
    }
    return new double[] {min, max, mean, median, mode};
  }
}
