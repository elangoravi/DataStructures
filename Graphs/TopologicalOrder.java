package Graphs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* Topological sorting for Directed Acyclic Graph (DAG) is a linear ordering of vertices 
 * such that for every directed edge u-v, vertex u comes before v in the ordering. 
 * Topological Sorting for a graph is not possible if the graph is not a DAG*/

public class TopologicalOrder {

	Map<Integer, List<Integer>> adjList;
	boolean visited[];
	List<Integer> order;
	int V;

	TopologicalOrder(int V, int[][] Edges) {
		this.V = V;
		adjList = new HashMap<>();
		visited = new boolean[V];
		order = new ArrayList<>();
		while (--V >= 0)
			adjList.put(V, new ArrayList<>());
		for (int[] e : Edges)
			adjList.get(e[0]).add(e[1]);
	}

	public int[] getTopologicalOrder() {
		for (int i = 0; i < V; i++)
			if (!visited[i])
				DFS(i);
		Collections.reverse(order);
		return order.stream().mapToInt(Integer::intValue).toArray();
	}

	private void DFS(int n) {
		visited[n] = true;
		for (int neighbour : adjList.get(n))
			if (!visited[neighbour])
				DFS(neighbour);
		order.add(n);
	}

	public static void main(String[] args) {
		int edges[][] = { { 5, 2 }, { 5, 0 }, { 4, 0 }, { 4, 1 }, { 2, 3 }, { 3, 1 } };
		System.out.println();
		int[] result = new TopologicalOrder(6, edges).getTopologicalOrder();
		for (int c : result) {
			System.out.print(c + " ");
		}
	}

}
