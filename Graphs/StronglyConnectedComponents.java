/**
 * An implementation of Tarjan's Strongly Connected Components algorithm using an adjacency list.
 *
 * Time complexity: O(V+E)
 *
 * PS: William Fiset
 */
package Graphs;

import java.util.*;
import static java.lang.Math.min;

public class StronglyConnectedComponents {

	private int V;
	private List<List<Integer>> graph;

	private int sccCount, id;
	private boolean[] onStack;
	private int[] ids, low;
	private Deque<Integer> stack;

	private static final int UNVISITED = -1;

	public StronglyConnectedComponents(List<List<Integer>> graph) {
		if (graph == null)
			throw new IllegalArgumentException("Graph cannot be null");
		V = graph.size();
		this.graph = graph;

	}

	/**
	 * Get the connected components of this graph. If two indexes have the same
	 * value then they're in the same SCC.
	 * 
	 * @return low link array
	 */
	public int[] getSccs() {
		solve();
		return low;
	}

	/**
	 * Returns the number of strongly connected components in the graph.
	 */
	public int sccCount() {
		return sccCount;
	}

	/**
	 * Do a DFS on all vertices
	 */
	public void solve() {
		ids = new int[V];
		low = new int[V];
		onStack = new boolean[V];
		stack = new ArrayDeque<>();
		Arrays.fill(ids, UNVISITED);

		for (int i = 0; i < V; i++)
			if (ids[i] == UNVISITED)
				dfs(i);
	}

	/**
	 * Main Circuit
	 * 
	 * @param Visiting vertex
	 */
	private void dfs(int at) {
		stack.push(at);
		onStack[at] = true;
		ids[at] = low[at] = id++;

		for (int to : graph.get(at)) {
			if (ids[to] == UNVISITED) {
				dfs(to);
				low[at] = min(low[at], low[to]);
			} else if (onStack[to]) {
				low[at] = min(low[at], ids[to]); // deliberate to check with ids and not low- link as in original paper
				// Note that v.lowlink := min(v.lowlink, w.index) is the correct way to update
				// v.lowlink if w is on stack. Because w is on the stack already, (v, w) is a
				// back-edge in the DFS tree and therefore w is not in the subtree of v. Because
				// v.lowlink takes into account nodes reachable only through the nodes in the
				// subtree of v we must stop at w and use w.index instead of w.lowlink.
			}
		}

		// On recursive callback, if we're at the root node (start of SCC)
		// empty the seen stack until back to root.
		if (ids[at] == low[at]) {
			for (int node = stack.pop();; node = stack.pop()) {
				onStack[node] = false;
				low[node] = ids[at];
				if (node == at)
					break;
			}
			sccCount++;
		}
	}

	/**
	 * Creates an adjacency list with V vertices
	 * 
	 * @param Number of Vertices
	 * @return graph object
	 */
	public static List<List<Integer>> createGraph(int V) {
		List<List<Integer>> graph = new ArrayList<>();
		for (int i = 0; i < V; i++)
			graph.add(new ArrayList<>());
		return graph;
	}

	/**
	 * Adds a directed edge from node 'from' to node 'to'
	 * 
	 * @param graph object
	 * @param from  vertex
	 * @param to    vertex
	 */
	public static void addEdge(List<List<Integer>> graph, int from, int to) {
		graph.get(from).add(to);
	}

	public static void main(String[] args) {

		int n = 8;
		List<List<Integer>> graph = createGraph(n);

		addEdge(graph, 6, 0);
		addEdge(graph, 6, 2);
		addEdge(graph, 3, 4);
		addEdge(graph, 6, 4);
		addEdge(graph, 2, 0);
		addEdge(graph, 0, 1);
		addEdge(graph, 4, 5);
		addEdge(graph, 5, 6);
		addEdge(graph, 3, 7);
		addEdge(graph, 7, 5);
		addEdge(graph, 1, 2);
		addEdge(graph, 7, 3);
		addEdge(graph, 5, 0);

		StronglyConnectedComponents solver = new StronglyConnectedComponents(graph);

		int[] sccs = solver.getSccs();
		Map<Integer, List<Integer>> multimap = new HashMap<>();
		for (int i = 0; i < n; i++) {
			if (!multimap.containsKey(sccs[i]))
				multimap.put(sccs[i], new ArrayList<>());
			multimap.get(sccs[i]).add(i);
		}

		// Prints: // Number of Strongly Connected Components: 3
		// Nodes: [0, 1, 2] form a Strongly Connected Component.
		// Nodes: [3, 7] form a Strongly Connected Component.
		// Nodes: [4, 5, 6] form a Strongly Connected Component.
		System.out.printf("Number of Strongly Connected Components: %d\n", solver.sccCount());
		for (List<Integer> scc : multimap.values()) {
			System.out.println("Nodes: " + scc + " form a Strongly Connected Component.");
		}

	}
}
