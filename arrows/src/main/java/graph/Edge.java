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

import org.jetbrains.annotations.NotNull;

public class Edge implements Comparable<Edge> {
    public Integer from, to, w;
    public String fromN, toN;

    public Edge(int vertex1Id, int vertex2Id, int weight) {
        this.fromN = vertex1Id + "";
        this.toN = vertex2Id + "";
        this.from = vertex1Id;
        this.to = vertex2Id;
        this.w = weight;
    }

    public Edge(String vertex1Id, String vertex2Id, int weight) {
        this.fromN = vertex1Id + "";
        this.toN = vertex2Id + "";
        this.w = weight;
    }

    @Override
    public int compareTo(@NotNull Edge o) {
        return this.w - o.w;
    }

    @Override
    public int hashCode() {
        return this.fromN.hashCode() + this.toN.hashCode() + this.w * 31;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof Edge)) return false;
        Edge other = (Edge) o;
        if (this.w != other.w) return false;
        return this.fromN.equals(other.fromN) && this.toN.equals(other.toN)
                || this.fromN.equals(other.toN) && this.toN.equals(other.fromN);
    }

    @Override
    public String toString() {
        return fromN + "-" + toN + ", weight: " + w;
    }
}
