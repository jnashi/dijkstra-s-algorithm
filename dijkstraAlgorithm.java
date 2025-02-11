
import java.util.Scanner;
import java.io.*;


public class dijkstraAlgorithm {
    public static class Edges {
        int targetVertex;
        int weighting;

        //construction worker
        public Edges(int targetVertex, int weight) {
            this.targetVertex = targetVertex;
            this.weighting = weight;
        }
    }

    public static void daMainImplementation(int numVertices, Edges[][] graph, int startV, FileWriter writer) {
        int[] distanceofVertex = new int[numVertices + 1];
        int[] prevVertex = new int[numVertices + 1];
        boolean[] processed = new boolean[numVertices +1];

        for (int i = 1; i <= numVertices; i++) {
            distanceofVertex[i] = Integer.MAX_VALUE;
            prevVertex[i] = -1;
        }
        distanceofVertex[startV] = 0;

        for (int count = 1; count <= numVertices; count++) {

            int minDistance = Integer.MAX_VALUE;
            int current = -1;
            for (int i = 1; i <= numVertices; i++) {
                if (!processed[i] && distanceofVertex[i] < minDistance) {
                    minDistance = distanceofVertex[i];
                    current = i;
                }
            }

            if (current == -1) {
                break;
            }

            processed[current] = true;

            for (int j = 0; j < graph[current].length; j++) {
                Edges edge = graph[current][j];
                if (edge != null) {
                    int neighbor = edge.targetVertex;
                    //calculation
                    if (!processed[neighbor]) {
                        int cost = distanceofVertex[current] + edge.weighting;
                        //finding if there is a shorter path to the best friend
                        if (cost < distanceofVertex[neighbor]) {
                            distanceofVertex[neighbor] = cost;
                            prevVertex[neighbor] = current;
                        }
                    }
                }
            }
        }

        try {
            // Writinng the number of vetrices.
            writer.write(numVertices + "\n");

            for (int i = 1; i <= numVertices; i++) {
                writer.write(i + " ");
                // When the vertex is unreachable
                if (distanceofVertex[i] == 0) {
                    writer.write("-1 ");
                } else {
                    writer.write(distanceofVertex[i] + " ");
                }

                writer.write(prevVertex[i] + "\n");

            }
        } catch (IOException e) {
            System.out.println("Error on da file.");
            e.printStackTrace();
        }
    }



    private static boolean daValues(Edges[][] graph, int vertex, Edges edge) {
        for (int i = 0; i < graph[vertex].length; i++) {
            if (graph[vertex][i] == null) {
                graph[vertex][i] = edge;
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        try {
            // Read input from a file
            Scanner scanner = new Scanner(new File("cop3503-dijkstra-input.txt."));
            //write intro file
            FileWriter writer = new FileWriter("cop3503-dijkstra-output-Nashi-Julien.txt");

            int numVertices = scanner.nextInt();

            int startV = scanner.nextInt();

            int numEdges = scanner.nextInt();

            Edges[][] graph = new Edges[numVertices + 1][numVertices];

            // Read the edges
            for (int i = 0; i < numEdges; i++) {
                int u = scanner.nextInt();

                int v = scanner.nextInt();

                int weight = scanner.nextInt();

                daValues(graph, u, new Edges(v, weight));
                daValues(graph, v, new Edges(u, weight));
            }

            //vititing stuff happens
            daMainImplementation(numVertices, graph, startV, writer);

            scanner.close();
            writer.close();
        } catch (IOException e) {
            System.out.println("error on da files.");
            e.printStackTrace();
        }


    }
}


