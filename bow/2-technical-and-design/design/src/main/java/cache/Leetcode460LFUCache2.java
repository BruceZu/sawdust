//  Copyright 2017 The keepTry Open Source Project
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

package cache;

import java.util.HashMap;
import java.util.LinkedHashSet;

public class Leetcode460LFUCache2 {
  // compared with Leetcode460LFUCache.java: use LinkedHashSet to keep LRU in frequency node
  static class LFU {
    // frequency node
    static class Fode {
      public int freq = 0;
      public LinkedHashSet<Integer> keysSet = null; // LRU

      public Fode prev = null, next = null;

      public Fode(int freq, Fode prev, Fode next) {
        this.freq = freq;
        keysSet = new LinkedHashSet();

        this.prev = prev;
        this.next = next;

        prev.next = this;
        next.prev = this;
      }

      private Fode() { // for sentinel only
        this.freq = -1;
      }
    }

    private Fode s; // centinel Smaller Side
    private Fode b;
    private int capacity;

    private HashMap<Integer, Integer> kv = null; // key to value
    private HashMap<Integer, Fode> kf = null; // key to frequency node

    private void unlink(Fode node) {
      Fode p = node.prev;
      Fode n = node.next;

      p.next = n;
      n.prev = p;

      node.prev = node.next = null;
    }

    private void increaseFrequency(int k) {
      Fode f = kf.get(k);
      if (f.next.freq != f.freq + 1) {
        new Fode(f.freq + 1, f, f.next);
      }

      f.keysSet.remove(k);
      f.next.keysSet.add(k);

      kf.put(k, f.next);

      if (f.keysSet.isEmpty()) unlink(f);
    }

    // remove the least recent one of lest frequency
    // and update freqList:
    // after remove if the preNode's list is empty, unlink this preNode
    private void removeOne() {
      int k = 0;
      for (int key : s.next.keysSet) {
        k = key; // first one is the one to be removed key O(1)
        break;
      }

      s.next.keysSet.remove(k);
      kv.remove(k);
      kf.remove(k);

      if (s.next.keysSet.isEmpty()) unlink(s.next);
    }

    // insert new key-value to preNode with freq=0
    // if the preNode does not exist, create it.
    private void insertNewKV(int k, int v) {
      if (s.next.freq != 0) {
        new Fode(0, s, s.next);
      }
      s.next.keysSet.add(k);
      kf.put(k, s.next);
      kv.put(k, v);
    }

    public LFU(int capacity) {
      this.capacity = capacity;

      kf = new HashMap();
      s = new Fode();
      b = new Fode();
      s.next = b;
      b.prev = s;

      kv = new HashMap();
    }

    public int get(int key) {
      Integer v = kv.get(key);
      if (v != null) {
        increaseFrequency(key);
        return v;
      }
      return -1;
    }

    public void put(int k, int v) {
      if (capacity == 0) return;
      Integer o = kv.get(k);
      // update
      if (o != null) {
        kv.put(k, v);
        increaseFrequency(k);
        return;
      }
      // add
      if (kv.size() == this.capacity) removeOne();
      // add to freNode with frequency =0;
      insertNewKV(k, v);
    }
  }
}
