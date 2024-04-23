import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Dijkstra {
    private Set<Integer> vertices;
    private int[][] adjacencyMatrix;
    
    public Dijkstra() {
        vertices = new HashSet<>();
        adjacencyMatrix = new int[0][0];
    }
    
    public void addVertex(int vertex) {
        vertices.add(vertex);
        resizeMatrix();
    }
    
    public void addEdge(int source, int destination, int weight) {
        if (!vertices.contains(source) || !vertices.contains(destination))
        throw new IllegalArgumentException("One or more vertices do not exist.");
        
        adjacencyMatrix[source][destination] = weight;
        adjacencyMatrix[destination][source] = weight;
    }
    
    public void resizeMatrix() {
        int size = vertices.size() + 1;
        int[][] newMatrix = new int[size][size];
        
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            for (int j = 0; j < adjacencyMatrix[i].length; j++) {
                newMatrix[i][j] = adjacencyMatrix[i][j];
            }
        }
        
        adjacencyMatrix = newMatrix;
    }
    
    public void printGraph() {
        for (int vertex : vertices) {
            System.out.print("Vertex " + vertex + " is connected to: ");
            for (int i = 0; i < adjacencyMatrix.length; i++) {
                if (adjacencyMatrix[vertex][i] != 0) {
                    System.out.print("[" + i + ", " + adjacencyMatrix[vertex][i] + "]");
                }
            }
            System.out.println();
        }
    }
    
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("cop3503-asn2-input.txt");
        Scanner scan = new Scanner(file);
        int numVertices, sourceVertex, numEdges, s, d, w;
        Dijkstra graph = new Dijkstra();
        
        
        numVertices = scan.nextInt();
        for(int i = 0; i < numVertices; i++){
            graph.addVertex(i + 1);
        }
        
        sourceVertex = scan.nextInt();
        numEdges = scan.nextInt();
        
        for(int j = 0; j < numEdges; j++){
            s = scan.nextInt();
            d = scan.nextInt();
            w = scan.nextInt();
            graph.addEdge(s, d , w);
        }
        
        
        try{
            FileWriter fileWrite = new FileWriter("cop3503-asn2-output-rowe-omory.txt");
            fileWrite.write(new Integer(numVertices).toString());
            fileWrite.write("\n");
            
            int[][] visit = new int[1][3];
            int[][] find = new int [numVertices][2];
            int[][] visited = new int[numVertices][3];
            int[][] queue = new int[numVertices][3];
            //Algorithm
            
            
            
            int value;
            int topVal;
            int overallWeight;
            int min = 10000000;
            int otherWeight = 0;
            for(int j = 0; j < numVertices-1; j++){
                if(j == 0){
                    value = sourceVertex;
                    visit[0][0] = sourceVertex;
                    visit[0][1] = -1;
                    visit[0][2] = -1;
                    
                    visited[value - 1][0] = sourceVertex;
                    visited[value - 1][1] = -1;
                    visited[value - 1][2] = -1;
                } else {
                    value = visit[0][0];
                    otherWeight = visit[0][1];
                    topVal = visited[value-1][2];
                }
                
                //Find
                int rows = 0;
                int column = 0;
                for(int i = 0; i < numVertices + 1; i++){
                    if(graph.adjacencyMatrix[value][i] != 0){
                        
                        //value if value has already been visited
                        if(i == visited[i-1][0]){
                        } else {
                            column = 0;
                            find[i-1][column] = i;
                            column++;
                            overallWeight = otherWeight + graph.adjacencyMatrix[value][i];
                            find[i-1][column] = overallWeight;
                            rows++;
                        }
                    }
                }
                
                for(int i = 0; i < numVertices + 1; i++){
                    if(graph.adjacencyMatrix[value][i] != 0){
                        if(visited[i-1][0] != 0){
                        } else {
                            if(queue[i-1][1] != 0){
                                if(find[i-1][1] < queue[i-1][1]){
                                    queue[i-1][1] = find[i-1][1];
                                    queue[i-1][2] = value;
                                }
                            } else {
                                column = 0;
                                queue[i-1][column] = i;
                                column++;
                                overallWeight = graph.adjacencyMatrix[sourceVertex][value] + graph.adjacencyMatrix[value][i];
                                queue[i-1][column] = overallWeight;
                                column++;
                                if( j == 0){
                                    queue[i-1][column] = sourceVertex;
                                } else {
                                    queue[i-1][column] = value;
                                }
                            }
                        }
                        //value if value has already been visited
                        
                    }
                }
                // Check Queue
                int temp = 0;
                min = 10000000;
                for (int i = 0; i < numVertices; i++) {
                    if(queue[i][1] > 0){
                        if(queue[i][1] < min){
                            value = queue[i][0];
                            min = queue[i][1];
                            topVal = queue[i][2];
                            temp = i;
                        }
                    }
                }
                
                if(min == 10000000){
                    min = queue[value-1][1];
                }
                
                visited[value - 1][0] = value;
                visited[value - 1][1] = min;
                visited[value - 1][2] = queue[value-1][2];
                
                queue[value-1][0] = 0;
                queue[value-1][1] = 0;
                queue[value-1][2] = 0;
                
                visit[0][0] = value;
                visit[0][1] = min;
                if(j == 0){
                    visit[0][2] = sourceVertex;
                } else {
                    visit[0][2] = queue[temp][2];
                }
            }
            for (int i = 0; i < numVertices; i++) {
                for (int j = 0; j < 3; j++) {
                    fileWrite.write(new Integer(visited[i][j]).toString());
                    fileWrite.write(" ");
                }
                fileWrite.write("\n");
            }
            fileWrite.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
