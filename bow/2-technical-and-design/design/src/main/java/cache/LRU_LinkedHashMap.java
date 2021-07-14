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

package cache;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRU_LinkedHashMap {

  private Map<Integer, Integer> map;

  public LRU_LinkedHashMap(int capacity) {
    map =
        new LinkedHashMap<Integer, Integer>(16, 0.75f, true) {
          @Override
          protected boolean removeEldestEntry(Map.Entry eldest) {
            return size() > capacity;
          }
        };
  }

  public int get(int key) {
    return map.getOrDefault(key, -1);
  }

  public void set(int key, int value) {
    map.put(key, value);
  }
}
