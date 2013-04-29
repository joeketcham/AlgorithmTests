package prj3;

import java.util.List;

public class Graph {
  private final List<Vertex> vertexes;
  private final List<Edge> edges;
  private final List<Fuel> fuel;
  
  public Graph(List<Vertex> vertexes, List<Edge> edges, List<Fuel> fuels) {
    this.vertexes = vertexes;
    this.edges = edges;
    this.fuel = fuels;
  }

  public List<Vertex> getVertexes() {
    return vertexes;
  }

  public List<Edge> getEdges() {
    return edges;
  }
  
  public List<Fuel> getFuel() {
	  return fuel;
  }
  
} 