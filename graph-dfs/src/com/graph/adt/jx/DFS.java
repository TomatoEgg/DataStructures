package com.graph.adt.jx;

import java.util.ArrayList;
import java.util.Stack;

public class DFS
{
  private boolean vis[];
  private Digraph G;  
  private int s; 
  public DFS(Digraph G, int s)
  {
    vis = new boolean[G.sizeOfV()];
    for(boolean u : vis)
    {
      u = false;
    }
    
    this.G = G;
    this.s = s;
  }
  
  public void reset()
  {
    for(int i = 0; i < vis.length; i++)
    {
      vis[i] = false;
    }
  }
  
  public void visitByRecursion(int u)
  {
    vis[u] = true; //mark the visited vertices as true
    System.out.print(u + " ");
    for(int v : G.adj[u])
    {
      if(vis[v] == false)
      {
        visitByRecursion(v);
      }
    }
  }
  
  public void visitByStack(int u)
  {
    Stack<Integer> vertices = new Stack<Integer>();
    vertices.push(u);
    while(!vertices.empty())
    {
      u = vertices.pop();
      if(vis[u] == false) //the u is not visited
      {
        System.out.print(u + " ");
        vis[u] = true; //mark as true
      }
      
      for(int i = 0; i < G.adj[u].size(); i++)
      {
        int v = G.adj[u].get(i);//get all the neighbor of u
        if(vis[v] == false)
        {
          vis[v] = true;  //mark the vertices
          vertices.push(u); //if find a neighbor which has not been visited, push u back
          vertices.push(v); //push the neighbor
          System.out.print(v + " ");
          break;
        }
      }
    }
  }
  
  //this function is changed a little to help the function minU
  public void visitByRecursion(int startindex, int min, int[] minU, Digraph G1)
  {
    for(int v : G1.adj[startindex])
    {
      if(vis[v] == false) //find a neighbor of u which is not visited
      {
        minU[v] = min; // the minimum index of R(v) is start-index;
        visitByRecursion(v, min, minU, G1);
        vis[v] = true; //mark the v as "the minimum index of v has been found"
        
      }
    }
  }

  //the basic principle of how to find minU is: reverse the original graph. 
  //start from j = 0, find all the vertices which j has direct path to. 
  //that means these found vertices has direct path to j at original graph, and j is the minimum index of R(u).
  //the time complexity of minU is O(V+E)for reverse + O(V+E) for recursion. therefore the time complexity is still O(V+E).
  public int[] minU(Digraph G)
  {
    int[] minU = new int[G.sizeOfV()];
    for(int j = 0; j < G.sizeOfV(); j++)
    {
      minU[j] = -1; //initialize each element of U as "have no neighbor"----(-1)
    }
    Digraph revG = G.reverse();
   
    for(int i = 0; i < revG.sizeOfV(); i++)
    {
      visitByRecursion(i, i, minU, revG);
    }
    return minU;
  }
  
  public static class Digraph
  {
    private final int V;
    private int E;
    private ArrayList<Integer>[] adj;
    public Digraph(int V)
    {
      this.V = V;
      this.E = 0;
      adj = (ArrayList<Integer>[])new ArrayList[V];
      for (int v = 0; v < V; v++)
      {
        adj[v] = new ArrayList<Integer>();
      }
    }
     public int sizeOfV() { return V; }
     public int sizeOfE() { return E; }
     
     public void addEdge(int v, int w)
     {
       adj[v].add(w);
       E++;
     }
     
     public Iterable<Integer> adj(int v)
     { 
       return adj[v]; 
     }
     
     public Digraph reverse()
     {
       Digraph R = new Digraph(V);
       for (int v = 0; v < V; v++)
       {
         for (int w : adj(v))
         {
           R.addEdge(w, v);
         }
       }
       return R;
     }
   }
  
  public static void main(String[] args)
  {
    Digraph G = new Digraph(8);
    G.addEdge(0, 1);
    G.addEdge(0, 7);
    G.addEdge(0, 4);
    G.addEdge(1, 3);
    G.addEdge(1, 5);
    G.addEdge(7, 6);
    G.addEdge(5, 4);
    G.addEdge(3, 2);
    DFS newDfs = new DFS(G, 0);
    long startTime = System.nanoTime();
    newDfs.visitByRecursion(0);
    long endTime = System.nanoTime();
    long duration = (endTime - startTime);
    System.out.println("visitByRecursion:" + duration);
    
    newDfs.reset();
    startTime = System.nanoTime();
    newDfs.visitByStack(0);
    endTime = System.nanoTime();
    duration = (endTime - startTime);
    System.out.println("visitByStack:" + duration);
    
    newDfs.reset();
    int[] print = newDfs.minU(G);
    System.out.println("minU");
    for(int i = 0; i < print.length; i++)
    {
      System.out.println("[" + i + "]" + " " + print[i]);
    }
  }
}
