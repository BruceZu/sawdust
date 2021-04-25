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

/**
 * <a href="https://leetcode.com/problems/course-schedule-ii/?tab=Description">LeetCode</a>
 */
public class Leetcode210CourseScheduleii {
    static int WHITE = 0, GREP = 1, BLACK = 2;

    //----------------------------------------- DFS --------------------------------------------------------------
    static private List<Integer>[] converseToFollowings(int numCourses, int[][] prerequisites) {
        List<Integer>[] followings = new ArrayList[numCourses];
        for (int i = 0; i < numCourses; i++) {
            followings[i] = new ArrayList();
        }
        for (int[] edge : prerequisites) {
            int i = edge[0], pre = edge[1];
            followings[pre].add(i);
        }
        return followings;
    }

    static public int[] findOrder_DFS(int numCourses, int[][] prerequisites) {
        List<Integer>[] followings = converseToFollowings(numCourses, prerequisites);
        Deque<Integer> sortedOrder_stack = new ArrayDeque<>(numCourses); // or StringBuilder
        int[] whiteGreyBlack = new int[numCourses];

        // topology sort
        for (int course = 0; course < numCourses; course++) {
            if (DFSTopoSortFromFindCircle(course, followings, whiteGreyBlack, sortedOrder_stack)) {
                return new int[0];
            }
        }
        // result
        int[] r = new int[numCourses];
        int i = 0;
        while (!sortedOrder_stack.isEmpty()) {
            r[i++] = sortedOrder_stack.poll();
        }
        return r;
    }

    static private boolean DFSTopoSortFromFindCircle(int course, List<Integer>[] followings,
                                                     int[] whiteGreyBlack,
                                                     Deque<Integer> sortedOrder_stack) {
        if (whiteGreyBlack[course] == GREP) {
            return true;
        }
        if (whiteGreyBlack[course] == BLACK) {
            return false;
        }

        whiteGreyBlack[course] = GREP;
        for (int follow : followings[course]) {
            if (DFSTopoSortFromFindCircle(follow, followings, whiteGreyBlack, sortedOrder_stack)) {
                return true;
            }
        }
        whiteGreyBlack[course] = BLACK;
        sortedOrder_stack.push(course);
        return false;
    }

    //-------------------------------------------------------------------------------------------------------
    public static void main(String[] args) {
        findOrder_DFS(2, new int[][]{{1, 0}});
        findOrder_BFS(2, new int[][]{{1, 0}});
    }

    //----------------------------------------- BFS --------------------------------------------------------------
    static private void initialFollowingsAndPresNumber(int[][] prerequisites,
                                                       List<Integer>[] followings,
                                                       int[] pres) {
        for (int[] edge : prerequisites) {
            int i = edge[0], pre = edge[1];
            followings[pre].add(i);
            pres[i]++;
        }
    }

    static public int[] findOrder_BFS(int numCourses, int[][] prerequisites) {
        // init graph
        List<Integer>[] followings = new ArrayList[numCourses];
        for (int i = 0; i < numCourses; i++) {
            followings[i] = new ArrayList();
        }
        int[] pres = new int[numCourses];
        initialFollowingsAndPresNumber(prerequisites, followings, pres);

        // prepare topology sort
        Queue<Integer> currents = new LinkedList();
        for (int i = 0; i < numCourses; i++) {
            if (pres[i] == 0) {
                currents.offer(i);
            }
        }

        int[] sortedOrder = new int[numCourses];
        int sortedSize = 0;

        // start topology sort BFS
        while (!currents.isEmpty()) {
            int curCourse = currents.poll();
            sortedOrder[sortedSize++] = curCourse;
            // next Batch cur
            for (int curFollow : followings[curCourse]) {
                pres[curFollow]--;
                if (pres[curFollow] == 0) {
                    currents.offer(curFollow);
                }
            }
        }
        return sortedSize != numCourses ? new int[0] : sortedOrder;
    }
}
