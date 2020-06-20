package Graphs;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

class Node {
	int value;
	int weight;

	Node(int n, int weight) {
		this.value = n;
		this.weight = weight;
	}
}

public class Dijkstra {

	List<List<Node>> adjList;

	Dijkstra() {
		this.adjList = new ArrayList<>();
	}

	PriorityQueue<Node> pq = new PriorityQueue<>((n1, n2) -> (n1.weight - n2.weight));

	public int getShortestPath(int source, int dest) {
		int[] dist = new int[adjList.size() + 1];
		for (int i = 0; i < adjList.size() + 1; i++) {
			dist[i] = Integer.MAX_VALUE;
		}
		pq.add(new Node(source, 0));
		dist[source] = 0;
		while (!pq.isEmpty()) {
			Node n = pq.poll(); // get the node with least weight
			for (Node neighbour : adjList.get(n.value)) {
				if (dist[neighbour.value] > (dist[n.value] + neighbour.weight)) {
					dist[neighbour.value] = dist[n.value] + neighbour.weight;
					pq.offer(new Node(neighbour.value, dist[neighbour.value]));
				}
			}
		}
		return dist[dest] == Integer.MAX_VALUE ? -1 : dist[dest];
	}

	public static void main(String[] args) {
		Dijkstra obj = new Dijkstra();
		// create 4 nodes
		for (int i = 0; i < 4; i++)
			obj.adjList.add(new ArrayList<>());
		obj.adjList.get(0).add(new Node(1, 1));
		obj.adjList.get(0).add(new Node(2, 4));
		obj.adjList.get(0).add(new Node(3, 6));
		obj.adjList.get(1).add(new Node(3, 2));
		obj.adjList.get(1).add(new Node(2, 1));
		obj.adjList.get(2).add(new Node(3, 5));
		System.out.println(obj.getShortestPath(0, 3));
	}

}
