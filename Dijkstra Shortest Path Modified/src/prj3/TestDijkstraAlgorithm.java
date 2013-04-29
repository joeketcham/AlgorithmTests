package prj3;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import prj3.DijkstraAlgorithm;
import prj3.Edge;
import prj3.Graph;
import prj3.Vertex;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class TestDijkstraAlgorithm {

  private List<Vertex> nodes;
  private List<Edge> edges;
  private List<Fuel> fuelStations;
  
  
  
  @Test
  public void testExcuteModifiedTest2() {
    nodes = new ArrayList<Vertex>();
    edges = new ArrayList<Edge>();
    fuelStations = new ArrayList<Fuel>();
    

    //addFuel("Node_0");
    addFuel("Node_1");
    addFuel("Node_4");
    
    
    for (int i = 0; i < 6; i++) {
        Vertex location = new Vertex("Node_" + i, "Node_" + i);
        nodes.add(location);
      }
    

    addLane("Edge_0", 0, 1, 4);
    addLane("Edge_1", 1, 3, 12);
    addLane("Edge_2", 1, 2, 7);
    addLane("Edge_3", 2, 4, 2);
    addLane("Edge_4", 4, 3, 7);
    addLane("Edge_5", 4, 5, 15);
    addLane("Edge_6", 3, 4, 7);
    addLane("Edge_7", 3, 5, 3);
    
    
    // Lets check from location Loc_1 to Loc_10
    Graph graph = new Graph(nodes, edges, fuelStations);
    DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);
    dijkstra.execute(nodes.get(0), 5, 12);
    LinkedList<Vertex> path = dijkstra.getPath(nodes.get(5));
    
    assertNotNull(path);
    assertTrue(path.size() > 0);
    
    for (Vertex vertex : path) {
      System.out.println("Test 2: " + vertex);
    }
    
  }
  
  private void addLane(String laneId, int sourceLocNo, int destLocNo,
      int duration) {
    Edge lane = new Edge(laneId,nodes.get(sourceLocNo), nodes.get(destLocNo), duration);
    edges.add(lane);
  }
  
  private void addFuel(String stationId) {
	    Fuel fuel = new Fuel(stationId); 
	    
	    fuelStations.add(fuel);
	  }
} 
