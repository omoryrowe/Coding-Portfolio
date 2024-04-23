//An adjacency matrix method using a Set for vert and a two-dimensional array of Booleans to store the edges.

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


public class HW3 {
    private Set<Integer> vert;
    private int[][] adjMatrix;
    
    public HW3() {
        vert = new HashSet<>();
        adjMatrix = new int[0][0];
    }
    
    public void addVertex(int vertex) {
        vert.add(vertex);
        resizeMatrix();
    }
    
    public void addEdge(int source, int destination, int weight) {
        if (!vert.contains(source) || !vert.contains(destination))
        throw new IllegalArgumentException("One or more vert do not exist.");
        
        if(weight <= -1){
            throw new IllegalArgumentException("Negative Weight is not ok");
        }
        
        adjMatrix[source][destination] = weight;
        adjMatrix[destination][source] = weight;
    }
    
    public void resizeMatrix() {
        int size = vert.size() + 1;
        int[][] newMatrix = new int[size][size];
        
        for (int i = 0; i < adjMatrix.length; i++) {
            for (int j = 0; j < adjMatrix[i].length; j++) {
                newMatrix[i][j] = adjMatrix[i][j];
            }
        }
        
        adjMatrix = newMatrix;
    }
    
    public void printGraph() {
        for (int vertex : vert) {
            //System.out.print("Vertex " + vertex + " is connected to: ");
            for (int i = 0; i < adjMatrix.length; i++) {
                if (adjMatrix[vertex][i] != 0) {
                    //System.out.print("[" + i + ", " + adjMatrix[vertex][i] + "]");
                }
            }
            //System.out.println();
        }
    }
    
    public static void main(String[] args) throws FileNotFoundException {
        File text = new File("cop3503-asn3-input.txt");
        Scanner scanner = new Scanner(text);
        
        HW3 graph = new HW3();
        
        
        int vert = scanner.nextInt();
        for(int i = 0; i < vert; i++){
            graph.addVertex(i + 1);
        }
        
        int source = scanner.nextInt();
        
        //lines 76 - 88 for FW
        int n = vert;
        int[][] d = new int[n][n];
        for(int r = 0; r < n; r++){
            for(int c = 0; c < n; c++){
                if(r == c){
                    d[r][c] = 0; 
                } else {
                    d[r][c] = 999999999;
                }
            }
        }
        
        int edges = scanner.nextInt();
        for(int j = 0; j < edges; j++){
            int s = scanner.nextInt();
            int des = scanner.nextInt();
            int w = scanner.nextInt();
            
            //FW 3 next lines
            d[s-1][des-1] = w;
            d[des-1][s-1] = w;
            
            graph.addEdge(s, des , w);
        }
        
        //lines 104 - 119 for FW
        for(int k = 0; k < n ; k++){
            for(int a = 0; a < n ; a++){
                for(int b = 0; b < n ; b++){
                    if (d[a][k] + d[k][b] < d[a][b]){
                        d[a][b] = d[a][k] + d[k][b];
                        d[b][a] = d[a][k] + d[k][b];
                    }
                }
            }
        }
        
        try{
            FileWriter writer = new FileWriter(" cop3503-asn3-output-Rowe-Omory-bf.txt");
            FileWriter writer2 = new FileWriter(" cop3503-asn3-output-Rowe-Omory-fw.txt");
            writer.write(new Integer(vert).toString());
            writer.write("\n");
            
            writer2.write(new Integer(vert).toString());
            writer2.write("\n");
            
            int[][] visit = new int[1][3];
            int[][] find = new int [vert][2];
            int[][] visited = new int[vert][3];
            int[][] queue = new int[vert][3];
            
            //Algorithm            
            int check;
            int parent;
            int overallWeight;
            int minimum = 10000000;
            int currentWeight = 0;
            for(int a = 0; a < vert-1; a++){
                if(a == 0){
                    check = source;
                    visit[0][0] = source;
                    
                    //lines 138 - 139 for BF
                    visit[0][1] = 0;
                    visit[0][2] = 0;
                    
                    visited[check - 1][0] = source;
                    visited[check - 1][1] = 0;
                    visited[check - 1][2] = 0;
                } else {
                    check = visit[0][0];
                    currentWeight = visit[0][1];
                    parent = visited[check-1][2];
                }
                
                //find
                int rows = 0;
                int columns = 0;
                for(int i = 0; i < vert + 1; i++){
                    if(graph.adjMatrix[check][i] != 0){
                        if(i == visited[i-1][0]){
                            //DO nothing
                        } else {
                            columns = 0;
                            find[i-1][columns] = i;
                            columns++;
                            overallWeight = currentWeight + graph.adjMatrix[check][i];
                            find[i-1][columns] = overallWeight;
                            rows++;
                        }
                    }
                }
                
                
                //queue
                for(int i = 0; i < vert + 1; i++){
                    if(graph.adjMatrix[check][i] != 0){
                        if(visited[i-1][0] != 0){
                            //DOES NOTHING, which is the point
                        } else {
                            if(queue[i-1][1] != 0){                      
                                if(find[i-1][1] < queue[i-1][1]){
                                    queue[i-1][1] = find[i-1][1];
                                    queue[i-1][2] = check;
                                }
                            } else {
                                columns = 0;
                                queue[i-1][columns] = i;
                                columns++;
                                overallWeight = graph.adjMatrix[source][check] + graph.adjMatrix[check][i];
                                queue[i-1][columns] = overallWeight;
                                columns++;
                                if( a == 0){
                                    queue[i-1][columns] = source;
                                } else {
                                    queue[i-1][columns] = check;
                                }
                            }
                        }                                
                    }
                }
                
                //relooping
                int temp = 0;
                minimum = 10000000;
                for (int i = 0; i < vert; i++) {
                    if(queue[i][1] > 0){
                        if(queue[i][1] < minimum){
                            check = queue[i][0];
                            minimum = queue[i][1];
                            parent = queue[i][2];
                            temp = i;
                        }
                    }
                }
                
                
                if(minimum == 10000000){
                    minimum = queue[check-1][1];
                }
                
                visited[check - 1][0] = check;
                visited[check - 1][1] = minimum;
                visited[check - 1][2] = queue[check-1][2];
                
                queue[check-1][0] = 0;
                queue[check-1][1] = 0;
                queue[check-1][2] = 0;
                
                visit[0][0] = check;
                visit[0][1] = minimum;
                if(a == 0){
                    visit[0][2] = source;
                } else {
                    visit[0][2] = queue[temp][2];
                }
            }
            
            //lines 242 - 250 for BF
            for (int i = 0; i < vert; i++) {
                for (int j = 0; j < 3; j++) {
                    writer.write(new Integer(visited[i][j]).toString());
                    writer.write(" ");
                }
                writer.write("\n");
            }         
            writer.close();  
            
            //lines 252 - 261 for FW
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    //System.out.println(d[0][0]);
                    writer2.write(new Integer(d[i][j]).toString());
                    writer2.write(" ");
                }
                writer2.write("\n");
            }   
            writer2.close();
            
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
