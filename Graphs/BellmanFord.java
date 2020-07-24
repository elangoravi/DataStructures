/*
 *  Bellman Ford is a single source shortest path(SSSP) algorithm with ability
 *  to detect negative cycles/weights
 *
 */

package Graphs;

import java.util.Arrays;

class Edge {
	int from;
	int to;
	double cost;

	Edge(int from, int to, double cost) {
		this.from = from;
		this.to = to;
		this.cost = cost;
	}
}

public class BellmanFord {

	public static double[] findShortestPath(Edge[] edgeList, int V, int src) {

		/*
		 * Distance array for the vertices V and fill it with value Positive Infinity
		 */

		double[] dist = new double[V];
		Arrays.fill(dist, Double.POSITIVE_INFINITY);

		// Distance value of source vertex starts with Zero
		dist[src] = 0;

		/*
		 * Only in the worst case does it take V-1 iterations for the Bellman-Ford
		 * algorithm to complete. Another stopping condition is when we're unable to
		 * relax an edge, this means we have reached the optimal solution early.
		 */

		boolean relaxEdge = true;

		// For each vertex, apply relaxation for all the edges

		for (int v = 0; v < V - 1 && relaxEdge; v++) {
			relaxEdge = false;
			for (Edge edge : edgeList) {
				if (dist[edge.to] > (dist[edge.from] + edge.cost)) {
					dist[edge.to] = dist[edge.from] + edge.cost;
					relaxEdge = true;
				}
			}
		}

		/*
		 * Run algorithm a second time to detect which nodes are part of a negative
		 * cycle. A negative cycle has occurred if we can find a better path beyond the
		 * optimal solution.
		 */

		relaxEdge = true;
		for (int v = 0; v < V - 1 && relaxEdge; v++) {
			relaxEdge = false;
			for (Edge edge : edgeList) {
				if (dist[edge.to] > (dist[edge.from] + edge.cost)) {
					dist[edge.to] = Double.NEGATIVE_INFINITY;
					relaxEdge = true;
				}
			}
		}
		return dist;
	}

	public static void main(String[] args) {

		int E = 10, V = 9, start = 0;
		Edge[] edges = new Edge[E];
		edges[0] = new Edge(0, 1, 1);
		edges[1] = new Edge(1, 2, 1);
		edges[2] = new Edge(2, 4, 1);
		edges[3] = new Edge(4, 3, -3);
		edges[4] = new Edge(3, 2, 1);
		edges[5] = new Edge(1, 5, 4);
		edges[6] = new Edge(1, 6, 4);
		edges[7] = new Edge(5, 6, 5);
		edges[8] = new Edge(6, 7, 4);
		edges[9] = new Edge(5, 7, 3);

		double[] dist = BellmanFord.findShortestPath(edges, V, start);

		for (int i = 0; i < V; i++)
			System.out.printf("The cost to get from node %d to %d is %.2f\n", start, i, dist[i]);

		// Output:
		// The cost to get from node 0 to 0 is 0.00
		// The cost to get from node 0 to 1 is 1.00
		// The cost to get from node 0 to 2 is -Infinity
		// The cost to get from node 0 to 3 is -Infinity
		// The cost to get from node 0 to 4 is -Infinity
		// The cost to get from node 0 to 5 is 5.00
		// The cost to get from node 0 to 6 is 5.00
		// The cost to get from node 0 to 7 is 8.00
		// The cost to get from node 0 to 8 is Infinity
	}

}
