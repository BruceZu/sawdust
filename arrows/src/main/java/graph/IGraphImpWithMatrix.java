//  Copyright 2019 The KeepTry Open Source Project
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

package graph;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/** O(|V|^2) */
class IGraphImpWithMatrix implements IGraph {

    int[][] g;
    // the vertex in MST vertexTo where the
    // shortest edge exits
    int vertexTo[];
    // the shortest edge vertexTo MST
    int shortestEdgeTo[];
    boolean inMST[];
    int mstVerTexCount = 0;

    public IGraphImpWithMatrix(int[][] g) {
        this.g = g;
        vertexTo = new int[g.length];
        Arrays.fill(vertexTo, -1);

        shortestEdgeTo = new int[g.length];
        inMST = new boolean[g.length];
        Arrays.fill(inMST, false);
    }

    @Override
    public void initVertexDistanceStatus() {
        Arrays.fill(shortestEdgeTo, Integer.MAX_VALUE);
        shortestEdgeTo[0] = 0;
    }

    @Override
    public boolean left() {
        return mstVerTexCount < g.length;
    }

    @Override
    public IVertex selectVertex() {
        mstVerTexCount++;
        int min = Integer.MAX_VALUE;
        int nextV = -1;

        // select out the shortest edge in the cut, relax a vertex.
        for (int i = 0; i < g.length; i++) {
            if (!inMST[i] && shortestEdgeTo[i] < min) {
                min = shortestEdgeTo[i];
                nextV = i;
            }
        }

        inMST[nextV] = true;
        final int selectedVetextId = nextV;

        return new IVertex() {
            @Override
            public Map<IVertex, Integer> getNeighborWeighMap() {
                throw new NotImplementedException();
            }

            @Override
            public int getShortestDistanceToI() {
                throw new NotImplementedException();
            }

            @Override
            public void setShortestDistanceWith(int v) {
                throw new NotImplementedException();
            }

            @Override
            public IVertex getVertexToI() {
                throw new NotImplementedException();
            }

            @Override
            public void setVertexToI(IVertex v) {
                throw new NotImplementedException();
            }

            @Override
            public int getId() {
                return selectedVetextId;
            }

            @Override
            public String getName() {
                throw new NotImplementedException();
            }
        };
    }

    @Override
    public BiFunction<IVertex, IVertex, Integer> getDistCalculator() {
        return null;
    }

    @Override
    public void updateCutWith(IVertex v, BiFunction distCalculator) {
        int i = v.getId();
        for (int j = 0; j < g.length; j++) {
            if (!inMST[j]
                    && g[i][j] != 0 /* there is edge */
                    && g[i][j] < shortestEdgeTo[j] /* now the v contributes
                      the shortest edge vertexTo MST to the vertex i which is not in MST */) {
                shortestEdgeTo[j] = g[i][j];
                vertexTo[j] = i;
            }
        }
    }

    @Override
    public Set<Edge> getMst() {
        Set<Edge> r = new HashSet<>(g.length);
        // Note it is from 1 not 0
        // because the vertex with index 0 is the start vertex
        // and no vertex as its 'in'

        for (int i = 1; i < g.length; i++) {
            r.add(new Edge(vertexTo[i], i, g[vertexTo[i]][i]));
        }

        return r;
    }
}
