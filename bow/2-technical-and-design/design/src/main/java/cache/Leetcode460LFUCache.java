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

import java.util.*;

class KeyNode {
  int key; // delete this from map
  int val;
  int freq;

  KeyNode next;
  KeyNode pre;

  public KeyNode(int key, int val) {
    this.key = key;
    this.val = val;
    this.freq = 0;
  }

  public KeyNode() {
    // for sentinel only
  }
}

class KeyNodeList { // maintenance the size
  KeyNode sentinelRecentSide; // imagine it as the top side
  KeyNode sentinelOldSide; // down side
  int size;

  public KeyNodeList() {
    sentinelRecentSide = new KeyNode();
    sentinelOldSide = new KeyNode();
    sentinelRecentSide.next = sentinelOldSide;
    sentinelOldSide.pre = sentinelRecentSide;
  }

  public KeyNode addRecentNode(int key, int value) {
    KeyNode recent = new KeyNode(key, value);
    return addRecentNode(recent);
  }

  public KeyNode addRecentNode(KeyNode recent) {
    KeyNode next = sentinelRecentSide.next;

    sentinelRecentSide.next = recent;
    recent.next = next;

    next.pre = recent;
    recent.pre = sentinelRecentSide;
    size++;
    return recent;
  }

  public int removeLeastRecentAndReturnItsKey() {
    KeyNode toRmove = sentinelOldSide.pre;
    KeyNode p = toRmove.pre;

    p.next = sentinelOldSide;
    sentinelOldSide.pre = p;

    toRmove.pre = toRmove.next = null;
    size--;
    return toRmove.key;
  }

  // when it increase the frequency
  public void unlink(KeyNode toRmove) {
    KeyNode pre = toRmove.pre;
    KeyNode next = toRmove.next;

    pre.next = next;
    next.pre = pre;

    toRmove.pre = toRmove.next = null;
    size--;
  }
}

class Fode {
  int freq;
  Fode prev;
  Fode next;
  KeyNodeList list;

  public Fode(int freq, Fode prev, Fode next) {
    this.freq = freq;

    this.prev = prev;
    this.next = next;
    next.prev = this;
    prev.next = this;

    list = new KeyNodeList();
  }

  public Fode() {
    this.freq = -1; // for sentinel only
  }
}

class LFUCache { // maintain a double linked freNode list by itself
  private Map<Integer, Fode> byFre;
  private Fode s; // freSentinel Small Side
  private Fode e;

  private int capacity;
  private Map<Integer, KeyNode> byKey;

  private int unlink(Fode freqNode) {
    Fode pre = freqNode.prev;
    Fode next = freqNode.next;
    pre.next = next;
    next.prev = pre;
    freqNode.prev = freqNode.next = null;
    return freqNode.freq;
  }

  // 11 increase its frequency
  // 12 if there is not frequency node for increased node, create a new frequency Node, add it to
  // list and map
  // 21 unlink this node from current list
  // 22 make is simple, if pre node list is empty then delete this freqNode from list and map.
  //      this is for remove action later. assume single thread.
  // 13 link the node to list with increased frequency at the recent side
  private void increaseFrequency(KeyNode node) {
    Fode f = byFre.get(node.freq);

    node.freq++;
    if (f.next.freq != node.freq) {
      Fode newFreqNode = new Fode(f.freq + 1, f, f.next);
      byFre.put(f.freq + 1, newFreqNode);
    }

    f.list.unlink(node);
    f.next.list.addRecentNode(node);
    // need not update byKay map

    if (f.list.size == 0) {
      unlink(f);
      byFre.remove(f.freq);
    }
  }

  // remove the least recent node from least frequency Node's node list
  // after that if the list is empty, unlink the frequency node.
  private void removeOne() {
    byKey.remove(s.next.list.removeLeastRecentAndReturnItsKey());
    if (s.next.list.size == 0) {
      byFre.remove(unlink(s.next));
    }
  }

  public void insertKeyNode(int key, int val) {
    Fode z = byFre.get(0); // zeroFreqNode

    if (z == null) {
      z = new Fode(0, s, s.next);
      byFre.put(0, z);
    }

    byKey.put(key, z.list.addRecentNode(key, val));
  }

  public LFUCache(int capacity) {
    this.capacity = capacity;

    byFre = new HashMap<>();
    s = new Fode();
    e = new Fode();
    s.next = e;
    e.prev = s;

    byKey = new HashMap<>();
  }

  //  Get the value (will always be positive) of the key if the key exists in the cache, otherwise
  // return -1.
  public int get(int key) {
    if (capacity == 0) return -1;

    KeyNode keyNode = byKey.get(key);
    if (keyNode != null) {
      increaseFrequency(keyNode);
      return keyNode.val;
    }
    return -1;
  }

  /**
   * Update or insert the value if the key is not already present. When the cache reaches its
   * capacity, it should invalidate the least frequently used item before inserting a new item. For
   * the purpose of this problem, when there is a tie (i.e., two or more keys that have the same
   * frequency), the least recently used key would be evicted.
   */
  public void put(int key, int val) {
    if (capacity == 0) return;
    KeyNode keyNode = byKey.get(key);
    // update
    if (keyNode != null) {
      keyNode.val = val;
      increaseFrequency(keyNode);
      return;
    }

    // add
    //    full then delete one firstly
    if (byKey.size() == this.capacity) {
      removeOne();
    }
    // add to freNode with frequency =0;
    insertKeyNode(key, val);
  }
}

public class Leetcode460LFUCache {
  public static void main(String[] args) {
    LFUCache obj = new LFUCache(0);
    obj.put(0, 0);
    obj.get(0);

    obj = new LFUCache(2);
    obj.put(1, 1);
    obj.put(2, 2);
    obj.get(1);
    obj.put(3, 3);
    obj.get(2);
    obj.get(3);
    obj.put(4, 4);
    obj.get(1);
    obj.get(3);
    obj.get(4);
  }
}
