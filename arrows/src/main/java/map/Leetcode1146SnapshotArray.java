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

package map;

import java.util.TreeMap;

public class Leetcode1146SnapshotArray {}

/*
initialization can avoid a lot of  checking null in each step.
*/
class SnapshotArray {

  private TreeMap<Integer, Integer>[] a;
  private int snap;

  public SnapshotArray(int length) {
    snap = 0;
    a = new TreeMap[length];
    // require "Initially, each element equals 0."
    for (int i = 0; i < length; i++) {
      a[i] = new TreeMap();
      a[i].put(snap, 0);
    }
  }

  public void set(int index, int val) {
    a[index].put(snap, val);
  }

  public int snap() {
    return snap++;
  }
  // "0 <= index < length"
  // "0 <= snap_id < (the total number of times we call snap())"
  public int get(int index, int snap_id) {
    return a[index].floorEntry(snap_id).getValue();
  }
}
