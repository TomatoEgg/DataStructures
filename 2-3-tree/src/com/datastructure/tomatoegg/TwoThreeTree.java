package com.datastructure.tomatoegg;

public class TwoThreeTree<K, V> //K == key, V == value
{
  //Create an empty tree
  public TwoThreeTree()
  {
    root = new Node<K, V>();
  }
  
  public void put(K k, V v)
  {
    
  }
  
  public V get(K k)
  {
    return null;
  }
  
  public int size()
  {
    //TBI
    return 0;
  }
  
  public int depth()
  {
    //TBI
    return 0;
  }
  
  public int howMuchMore()
  {
    //TBI
    return 0;
  }
  
  public class Node<KK, VV>
  {
    public Node()
    {
      //Create an empty node
      leftKey = rightKey = null;
      leftValue = rightValue = null;
      leftChild = middleChild = rightChild = null;
    }
    
    public Node(KK k, VV v)
    {
      leftKey = k;
      leftValue = v;
      
      //Init right keys to empty
      rightKey = null;
      rightValue = null;
    }
    
    public boolean hasTwoKeys()
    {
      return leftKey != null && rightKey != null;
    }
    
    public void add(KK k, VV v)
    {
      if (leftKey == null)
      {
        leftKey = k;
        leftValue = v;
      }
      else if (rightKey == null)
      {
        rightKey = k;
        rightValue = v;
      }
      else
      {
        //Error! TBI
      }
    }
    
    private KK leftKey;
    private VV leftValue;
    private KK rightKey;
    private VV rightValue;
    
    //public for now, to be refactored later
    public Node leftChild;
    public Node middleChild;
    public Node rightChild;
  }
  
  private Node root;
}
