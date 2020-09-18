/**
 * Finds all articulation points on an undirected graph.
 *
 * Time Complexity : O(V+E) , where V - vertex and E - edge.
 */

package Graphs;

import static java.lang.Math.min;

import java.util.ArrayList;
import java.util.List;

public class BridgesAndArticulationPoints {

	private int n, id, rootNodeOutcomingEdgeCount;
	private int[] low, ids;
	private boolean solved;
	private boolean[] visited, isArticulationPoint;
	private List<List<Integer>> graph;
	private List<Integer> bridges;

	public BridgesAndArticulationPoints(List<List<Integer>> graph) {
		if (graph == null)
			throw new IllegalArgumentException();
		this.graph = graph;
		n = graph.size();
	}

	public boolean[] findArticulationPoints() {
		return isArticulationPoint;
	}

	// Returns a list of pairs of nodes indicating which nodes form bridges.
	// The returned list is always of even length and indexes (2*i, 2*i+1) form a
	// pair. For example, nodes at indexes (0, 1) are a pair, (2, 3) are another
	// pair, etc...
	public List<Integer> findBridges() {
		if (solved)
			return bridges;

		low = new int[n]; // Low link values
		ids = new int[n]; // Nodes ids
		visited = new boolean[n];
		isArticulationPoint = new boolean[n];

		bridges = new ArrayList<>();

		// Finds all bridges in the graph across various connected components.
		for (int i = 0; i < n; i++)
			if (!visited[i]) {
				rootNodeOutcomingEdgeCount = 0;
				dfs(i, i, -1, bridges);
				isArticulationPoint[i] = rootNodeOutcomingEdgeCount > 1;
			}

		solved = true;
		return bridges;
	}

	private void dfs(int root, int at, int parent, List<Integer> bridges) {

		if (root == parent)
			rootNodeOutcomingEdgeCount++;

		visited[at] = true;
		low[at] = ids[at] = id++;

		for (Integer to : graph.get(at)) {
			if (to == parent)
				continue;
			if (!visited[to]) {
				dfs(root, to, at, bridges);
				low[at] = min(low[at], low[to]);
				if (ids[at] < low[to]) { // Add bridge and articulation point found via bridge (either of the vertex in
											// a bridge should be an articulation point connecting two SCCs)
					bridges.add(at);
					bridges.add(to);
					isArticulationPoint[at] = true;
				}
				if (ids[at] == low[to]) // Articulation point found via cycles. Technically this condition can be
										// linked with the previous condition for bridges.
					isArticulationPoint[at] = true;
			} else {
				low[at] = min(low[at], ids[to]);
			}
		}
	}

	// Initialize graph with 'n' nodes.
	public static List<List<Integer>> createGraph(int n) {
		List<List<Integer>> graph = new ArrayList<>();
		for (int i = 0; i < n; i++)
			graph.add(new ArrayList<>());
		return graph;
	}

	// Add undirected edge to graph.
	public static void addEdge(List<List<Integer>> graph, int from, int to) {
		graph.get(from).add(to);
		graph.get(to).add(from);
	}

	////////////////////////////////////////////////////
	// Example usage: //
	////////////////////////////////////////////////////

	public static void main(String[] args) {

		int n = 9;
		List<List<Integer>> graph = createGraph(n);

		addEdge(graph, 0, 1);
		addEdge(graph, 0, 2);
		addEdge(graph, 1, 2);
		addEdge(graph, 2, 3);
		addEdge(graph, 3, 4);
		addEdge(graph, 2, 5);
		addEdge(graph, 5, 6);
		addEdge(graph, 6, 7);
		addEdge(graph, 7, 8);
		addEdge(graph, 8, 5);

		BridgesAndArticulationPoints solver = new BridgesAndArticulationPoints(graph);
		List<Integer> bridges = solver.findBridges();

		// Prints:
		// Bridge between nodes: 3 and 4
		// Bridge between nodes: 2 and 3
		// Bridge between nodes: 2 and 5
		for (int i = 0; i < bridges.size() / 2; i++) {
			int node1 = bridges.get(2 * i);
			int node2 = bridges.get(2 * i + 1);
			System.out.printf("Bridge between nodes: %d and %d\n", node1, node2);
		}

		boolean[] isArticulationPoint = solver.findArticulationPoints();

		// Prints:
		// Node 2 is an articulation
		// Node 3 is an articulation
		// Node 5 is an articulation
		for (int i = 0; i < n; i++)
			if (isArticulationPoint[i])
				System.out.printf("Node %d is an articulation\n", i);
	}
}
