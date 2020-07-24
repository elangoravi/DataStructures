package Graphs;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

public class FordFulkerson {
	int vertices;
	int[][] graph;
	int maxFlow;
	ArrayList<String> cutSet;

	FordFulkerson(int vertices, int[][] graph) {
		this.vertices = vertices;
		this.graph = graph;
		this.cutSet = new ArrayList<>();
	}

	void findMaxFlow(int source, int sink) {
		maxFlow = 0;

		// Residual graph
		int[][] residual = new int[vertices][vertices];
		for (int i = 0; i < vertices; i++) {
			for (int j = 0; j < vertices; j++) {
				residual[i][j] = graph[i][j];
			}
		}

		int[] parent = new int[vertices];
		while (BFS(residual, source, sink, parent)) {
			int flowCapacity = Integer.MAX_VALUE;
			int t = sink;
			while (t != source) {
				int s = parent[t];
				flowCapacity = Math.min(flowCapacity, residual[s][t]);
				t = s;
			}
			t = sink;
			while (t != source) {
				int s = parent[t];
				residual[s][t] -= flowCapacity;
				residual[t][s] += flowCapacity;
				t = s;
			}
			maxFlow += flowCapacity;
		}

		// Minimum Cut:
		boolean visited[] = new boolean[vertices];
		DFS(residual, source, visited);
		for (int i = 0; i < vertices; i++) {
			for (int j = 0; j < vertices; j++) {
				if (graph[i][j] > 0 && visited[i] && !visited[j]) {
					cutSet.add(i + "-" + j);
				}
			}
		}
	}

	boolean BFS(int[][] residual, int source, int dest, int[] parent) {
		boolean[] visited = new boolean[vertices];
		parent[source] = -1;

		Deque<Integer> q = new ArrayDeque<>();
		q.offer(source);
		visited[source] = true;

		while (!q.isEmpty()) {
			int u = q.poll();
			for (int v = 0; v < vertices; v++) {
				if (!visited[v] && residual[u][v] > 0) {
					visited[v] = true;
					q.offer(v);
					parent[v] = u;
				}
			}
		}
		return visited[dest];
	}

	private void DFS(int[][] residual, int source, boolean[] visited) {
		visited[source] = true;
		for (int i = 0; i < vertices; i++) {
			if (!visited[i] && residual[source][i] > 0) {
				DFS(residual, i, visited);
			}
		}
	}

	public static void main(String[] args) {
		int vertices = 3;
		int[][] graph = { { 0, 10, 8, 0, 0, 0 }, { 0, 0, 5, 5, 0, 0 }, { 0, 4, 0, 0, 10, 0 }, { 0, 0, 9, 0, 10, 3 },
				{ 0, 0, 0, 6, 0, 14 }, { 0, 0, 0, 0, 0, 0 } };
		FordFulkerson obj = new FordFulkerson(vertices, graph);
		obj.findMaxFlow(0, 2);
		System.out.println("Maximum Flow :" + obj.maxFlow);
		System.out.println("Min Cut :" + obj.cutSet);

		// Output:
		// Maximum Flow :13
		// Min Cut :[0-2, 1-2]
	}

}
