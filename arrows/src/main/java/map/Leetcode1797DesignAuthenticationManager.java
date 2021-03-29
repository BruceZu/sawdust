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

import java.util.HashMap;
import java.util.Map;

public class Leetcode1797DesignAuthenticationManager {}

class AuthenticationManager {
  /*

     1 <= timeToLive <= 108
     1 <= currentTime <= 108
     1 <= tokenId.length <= 5
     tokenId consists only of lowercase letters.
     All calls to generate will contain unique values of tokenId.
     The values of currentTime across all the function calls will be strictly increasing.
     At most 2000 calls will be made to all functions combined.

  This is session scenario

   Note:
    1> Need not use LinkedHashMap to keep session entry ordered by access time
       any session touched by countUnexpiredTokens will be reordered to the end
       of the double linked list.
    2> Note java.util.ConcurrentModificationException
   */
  private Map<String, Integer> sessions = new HashMap<>();
  private int sessionPeriod;

  public AuthenticationManager(int timeToLive) {
    sessionPeriod = timeToLive;
  }

  public void generate(String tokenId, int currentTime) {
    sessions.put(tokenId, currentTime);
  }

  public void renew(String tokenId, int currentTime) {
    Integer lastAccessTime = sessions.get(tokenId);
    if (lastAccessTime != null) {
      if (lastAccessTime + sessionPeriod <= currentTime) sessions.remove(tokenId);
      else sessions.put(tokenId, currentTime);
    }
  }

  public int countUnexpiredTokens(int currentTime) {
    sessions.entrySet().removeIf(e -> e.getValue() + sessionPeriod <= currentTime);
    return sessions.size();
  }

  /**
   * Your AuthenticationManager object will be instantiated and called as such:
   * AuthenticationManager obj = new AuthenticationManager(timeToLive);
   * obj.generate(tokenId,currentTime); obj.renew(tokenId,currentTime); int param_3 =
   * obj.countUnexpiredTokens(currentTime);
   */
}
