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

package cache;

import java.util.LinkedHashMap;
import java.util.Map;

public class Leetcode146LRU_LinkedHashMap {
  // get and put must each run in O(1) average time complexity.
  class LRUCache extends LinkedHashMap {
    private int cap = 0;

    public LRUCache(int capacity) {
      super(capacity, 0.75f, true);
      this.cap = capacity;
    }

    protected boolean removeEldestEntry(Map.Entry e) {
      return size() > cap;
    }
    // int get(int key) Return the value of the key if the key exists, otherwise return -1.
    public int get(int key) {
      return (int) super.getOrDefault(key, -1);
    }

    public void put(int key, int value) {
      super.put(key, value);
    }
  }
}
