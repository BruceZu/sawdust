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

package graph.directed_graphs.topological_sort;

import java.util.*;

/** <a href="https://leetcode.com/problems/course-schedule/?tab=Description">LeetCode</a> */
public class Leetcode207CourseSchedule {

  /**
   * This problem is equivalent to finding if a cycle exists in a directed graph.
   * <pre>
   * If a cycle exists, no topological ordering exists and
   * therefore it will be impossible to take all courses.
   * https://www.coursera.org/specializations/algorithms
   * https://en.wikipedia.org/wiki/Topological_sorting#Algorithms
   *
   * array keeps the edges with relation of N:1, 1:N
   *
   * assume there is not duplicated edge
   * assume course id is number, start from 0.
   *    "There are a total of n courses you have to take, labeled from 0 to n - 1."
   */

  // O(V + E)
  public static boolean canFinish__BFS_V_E(int numCourses, int[][] prerequisites) {
    List<Integer>[] iToFollowings = new List[numCourses];
    int[] preNumbersOf = new int[numCourses];
    // O(E)
    for (int[] iToPre : prerequisites) {
      int preCourse = iToPre[1];
      int course = iToPre[0];
      List<Integer> followings = iToFollowings[preCourse];
      if (followings == null) {
        followings = new LinkedList<>();
        iToFollowings[preCourse] = followings;
      }
      followings.add(course);
      preNumbersOf[course]++;
    }

    Queue<Integer> queue = new LinkedList<>();
    for (int i = 0; i < numCourses; i++) {
      if (preNumbersOf[i] == 0) queue.offer(i);
    }
    int count = 0;
    // O(V+E)
    while (!queue.isEmpty()) {
      int course = queue.poll();
      count++;
      List<Integer> followings = iToFollowings[course];
      if (followings == null) continue;
      for (int follow : followings) {
        preNumbersOf[follow]--;
        if (preNumbersOf[follow] == 0) queue.offer(follow);
      }
    }
    return count == numCourses;
  }

  // ----------------------------------------------------------------------------------------------------------------
  public static boolean canFinish_DFS(int numCourses, int[][] prerequisites) {
    List<Integer>[] followsOf = new ArrayList[numCourses];
    for (int i = 0; i < numCourses; i++) { // O(V)
      followsOf[i] = new ArrayList();
    }

    for (int[] iToPre : prerequisites) { // O(E)
      followsOf[iToPre[1]].add(iToPre[0]);
    }

    boolean[] isInCurVisitedPath = new boolean[numCourses];
    //  boolean[] visited = new boolean[numCourses]; // performance improvement.
    //  may using one variable with value as 0: unvisited, 1: visiting, 2:visited
    for (int course = 0; course < numCourses; course++) { // DFS  O(V+E)
      if (hasCircle(course, followsOf, /* visited, */ isInCurVisitedPath)) {
        return false;
      }
    }
    return true;
  }

  private static boolean hasCircle(int i, List<Integer>[] out, boolean[] visited) {
    if (visited[i]) return true;
    visited[i] = true;
    for (int o : out[i]) {
      if (hasCircle(o, out, visited)) return true;
    }
    visited[i] = false;
    return false;
  }

  // ----------------------------------------------------------------------------------------------------------------
  public static void main(String[] args) {
    int numCourse = 4;
    int[][] pres = new int[][] {{1, 0}, {2, 1}, {1, 3}, {0, 2}};
    System.out.println(canFinish_DFS(numCourse, pres));

    numCourse = 4;
    pres = new int[][] {{0, 1}, {1, 2}, {2, 3}, {3, 1}};
    System.out.println(canFinish_DFS(numCourse, pres));

    numCourse = 2;
    pres = new int[][] {{0, 1}, {1, 0}};
    System.out.println(canFinish_DFS(numCourse, pres));
  }
}
