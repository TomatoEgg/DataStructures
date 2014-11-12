import java.util.Stack;

public class TwoThreeTree<Key extends Comparable<Key>, Value>
{
    private Node<Key,Value> root;
    public TwoThreeTree()
    {
      root = new Node<Key, Value>();
    }
    
    public static class Node<K, V>
    {
      private K key1;
      private K key2;
      private V val1;
      private V val2;
      private Node<K, V> left, right, middle;
      private boolean isKey1Visited;
     
      public Node() //empty Node for root
      {
        this.key1 = null;
        this.key2 = null;
        this.val1 = null;
        this.val2 = null;
        left = right = middle = null;
        isKey1Visited = false;
      }
      public Node(K key1, V val1)    // 2-Node
      {
        this.key1 = key1;
        this.key2 = null;
        this.val1 = val1;
        this.val2 = null;
        left = right = middle = null;
        isKey1Visited = false;
      }
      
      private void reset() // reset a node when we don't need it anymore.
      {
        this.key1 = this.key2 = null;
        this.left = this.middle = this.right = null;
      }
      
    }
    
    public void put(Key key, Value val) 
    { 
      if(root.key1 == null)
      {
        root.key1 = key;
        root.val1 = val;
      }
      else
      {
        Node<Key, Value> N = put(root, key, val); 
        if(N != null)
        {
          root = N;
        }
      }
    }
    
    private Node<Key, Value> put(Node<Key, Value> x, Key key, Value val)
    {
      Node<Key, Value> N = null;
      int cmp = key.compareTo(x.key1);
      if (cmp < 0) //Smaller than key1
      {
        if (x.left != null)  // have left subtree, means that it will go into subtree
        {
          N = put(x.left, key, val);  // search in left subtree
          if (N != null) 
          {
            if(x.key2 == null) //the parent is 2-node, we need to insert the return key into the node
            {
              x.key2 = x.key1;  // key2 replaced by key1
              x.val2 = x.val1;
              x.key1 = N.key1;
              x.val1 = N.val1;
              x.left = N.left;  // connect to N's children
              x.middle = N.right;
              N = null;// do not need be promoted further
            }
            else //the parent is 3-node
            {
              Node<Key, Value> rightNode = new Node<Key, Value>(x.key2, x.val2);//split the old 3-node
              rightNode.left = x.middle;
              rightNode.right = x.right;
              x.key2 = null;
              x.val2 = null;
              x.left = N;
              x.right = rightNode;
              x.middle = null;
              N = x;
            }
          }
        }
        else //no left subtree the node is found
        {
          if(x.key2 == null) //2-node, insert into Node
          {
            x.key2 = x.key1;
            x.val2 = x.val1;
            x.key1 = key;
            x.val1 = val;
          }
          else //3-node
          {
            Node<Key, Value> rightNode = new Node<Key, Value>(x.key2,x.val2);
            rightNode.left = x.middle;
            rightNode.right = x.right;
            x.key2 = null;
            x.val2 = null;
            x.left = new Node<Key, Value>(key, val);
            x.right = rightNode;
            x.middle = null;
            N = x;
          }
        }
      }
      
      else if(cmp > 0) // bigger than key1
      {
        if(x.key2 == null) // 2-node
        { 
          if(x.right != null) // have right subtree
          {
            N = put(x.right, key, val); // search in the right subtree
            if (N != null)
            {
              x.key2 = N.key1;
              x.val2 = N.val1;
              x.middle = N.left;
              x.right = N.right;
              N.reset();
              N = null; //N is inserted into this 2-node, nothing should be propagate upwards
            }
          }
          else //no right subtree
          {
            x.key2 = key;
            x.val2 = val;
          }
        }
        else // 3-node
        {
          int cmp2 = key.compareTo(x.key2);
          if(cmp2 < 0)// smaller than key2 
          {
            if(x.middle != null) // have middle subtree
            {
              N = put(x.middle, key, val);
              if(N != null)
              {
                Node<Key, Value> rightNode = new Node<Key, Value>(x.key2, x.val2);//split the old 3-node, creat a 2 node at right
                rightNode.left = N.right;
                rightNode.right = x.right;
                Node<Key, Value> leftNode = new Node<Key, Value>(x.key1, x.val1);//split the old 3-node, creat a 2 node at left
                leftNode.left = x.left;
                leftNode.right = N.left;
                N.left = leftNode;
                N.right = rightNode;
                x.left = x.middle = x.right =null;
                x = null;
              }
            }
            else // no middle subtree return the new Node
            {
              N = new Node<Key, Value>(key, val);
              
              Node<Key, Value> leftNode = new Node<Key, Value>(x.key1,x.val1);  //split the old 3-node
              leftNode.left = x.left;
              leftNode.right = N.right;
              Node<Key, Value> rightNode = new Node<Key, Value>(x.key2,x.val2);
              rightNode.left = N.left;
              rightNode.right = x.right;
              N.left = leftNode;
              N.right = rightNode;
              x.left = x.middle = x.right =null;
            }
          }
          else if (cmp2 > 0) //bigger than key2
          {
            if(x.right != null) // have right subtree
            {
              N = put(x.right, key, val);
              if(N != null)
              {
                Node<Key, Value> leftNode = new Node<Key, Value>(x.key1, x.val1);//split the old 3-node
                leftNode.left = x.left;
                leftNode.right = x.middle;
                x.key1 = x.key2;
                x.val1 = x.val2;
                x.key2 = null;
                x.val2 = null;
                x.right = N;
                x.left = leftNode;
                x.middle = null;
                N = x;
              }
            }
            else // no right subtree
            {
              Node<Key, Value> leftNode = new Node<Key, Value>(x.key1,x.val1);
              leftNode.left = x.left;
              leftNode.right = x.middle;
              x.key1 = x.key2;
              x.val1 = x.val2;
              x.key2 = null;
              x.val2 = null;
              x.left = leftNode;
              x.right = new Node<Key, Value>(key, val);
              x.middle = null;
              N = x;
            }
          }
          else // key == key2
          {
            x.key2 = key; 
            x.val2 = val;
          }
        }
      }
      
      else // the key == key1
      {
        x.key1 = key;  
        x.val1 = val;
      }
      return N;
  }
    public Value get(Key key1)
    {
      if (size() > 0)
      {
        Node<Key, Value> x = root;
        while(x != null)
        {
          int cmp = key1.compareTo(x.key1);   //compare with the left key of Node
          if (cmp < 0)
          {
            x = x.left;
          }
          else if (cmp > 0)  //bigger than left key and this Node is 3-Node
          {
            if (x.key2 != null) //has key2
            {
              cmp = key1.compareTo(x.key2);
              if (cmp < 0)      //bigger than left key and smaller than right key
              {
                x = x.middle;
              }
              else if (cmp > 0)   // bigger than right key
              {
                x = x.right;
              }
              else //equal to the right key
              {
                return x.val2;
              }
            }
            else //no key2
            {
              x = x.right;
            }
          }
          else // equal to the left key
          {
            return x.val1;
          }
        }
      }
      return null;
    }
    
    
    public int size()
    {
      return size(root);
    }
    
    //function size return the number of keys in the tree
    private int size(Node<Key, Value> x)
    {
      int count = 0;
      if (x != null)
      {
        if(x.key1 != null)
        {
          count++;
        }
        if(x.key2 != null)
        {
          count++;
        }
        count += size(x.left) + size(x.middle) + size(x.right);
      }
      return count;
    }
    
   
    //function depth return the distance between the root and leaves
    public int depth()
    {
      return depth(root);
    }
    private int depth(Node<Key, Value> x)
    {
      int depth = 0;
      int depthRight = 0;
      int depthMiddle = 0;
      int depthLeft = 0;
      if(x != null)
      {
        depth++;
        depthRight = depth(x.right);
        depthMiddle = depth(x.middle);
        depthLeft = depth(x.left);
        int max = depthRight > depthMiddle? depthRight:depthMiddle;
        max = max > depthLeft? max:depthLeft;
        depth += max;
      }
      return depth;
    }
    
// function howMuchMore return the number of additional entries that can be added before the tree must grow in depth
    public int howMuchMore() //find how many 2-nodes the tree have
    {
      return howMuchMore(root);
    }
    
    private int howMuchMore(Node<Key, Value> x)
    {
      int more = 0;
      if(x != null)
      {
        if(x.left != null && x.left.key2 != null)
        {
          more++;
        }
        if(x.right != null && x.right.key2 != null)
        {
          more++;
        }
        if(x.middle != null && x.middle.key2 != null)
        {
          more++;
        }
        more += howMuchMore(x.left) + howMuchMore(x.middle) + howMuchMore(x.right);
      }
      return more;
    }
    
    // returns the average number of entries per node (which varies between 2 and 3)
    public float density()
    {
      int up = size();
      int down = nodes(root);
      float density = ((float)(up + down))/down;
      return density;
    }
    
    //help function, to count the number of nodes in the tree
    private int nodes(Node<Key, Value> x)
    {
      int count = 0;
      if (x != null)
      {
        count++;
        count += nodes(x.left) + nodes(x.middle) + nodes(x.right);
      }
      return count;
    }
    
    public class TwoThreeTreeIterator implements java.util.Iterator<Key>
    {
      private Node<Key, Value> currentNode;
      //Use a stack to save all the visited (but skipped) keys along the way 
      //during a left-middle-right traversal so that when we pop them, they
      //will be in ascending order
      private Stack<Node<Key, Value>> nodeStack;

      private int count;
      
      //Help function that moves currentNode to the node containing Key k
      private Node<Key, Value> moveTo(Key k)
      {
        //TBI
        Node<Key, Value> nodeFound = root;
        return nodeFound;
      }
      
      public TwoThreeTreeIterator(Key lo, Key hi)
      {
        nodeStack = new Stack<Node<Key, Value>>();
        currentNode = root;
        count = 0;
        if (lo != null && hi != null)
        {
          currentNode = moveTo(lo);
        }
        //else: ignore lo and hi, we create a iterator with ascending order from the smallest
      }
      
      public boolean hasNext()
      {
        return count < TwoThreeTree.this.size();
      }

      public Key next()
      {
        count++;
        Key keyToReturn = null;
        while (currentNode != null && !currentNode.isKey1Visited)
        {
          nodeStack.push(currentNode);
          currentNode = currentNode.left; //We continue going left to reach the smallest key
        }
        
        //Now we cannot go any further on the left, we found the smallest key which was the last pushed element
        currentNode = nodeStack.pop();
        
        if (!currentNode.isKey1Visited)
        {
          keyToReturn = currentNode.key1;
          if (currentNode.key2 != null)
          {
            currentNode.isKey1Visited = true;
            //push this node back, so that next time, we take its key2
            nodeStack.push(currentNode);
          }
          
          if (currentNode.middle != null)
          {
            currentNode = currentNode.middle;
          }
          else if (currentNode.right != null)
          {
            currentNode = currentNode.right;
          }
          //else: we don't have middle and right nodes (and we don't have left also because of the while loop), basically this is a leaf.
          //we don't need to push it back!
          else
          {
            currentNode = null; //This will pop the parent next time
          }
        }
        else
        {
          currentNode.isKey1Visited = false;
          keyToReturn = currentNode.key2;
          currentNode = currentNode.right;
        }
        
        return keyToReturn;
      }
    }
    
    public class Iterator implements Iterable<Key>
    {
      private Key lo;
      private Key hi;
      
      public Iterator()
      {
        lo = hi = null;
      }
      
      public Iterator(Key lo, Key hi)
      {
        this.lo = lo;
        this.hi = hi;
      }
      
      public java.util.Iterator<Key> iterator()
      {
        return new TwoThreeTreeIterator(lo, hi);
      }
    }
    
    
    public Iterable<Key> keys()
    {
      return new Iterator();
    }
    
    public Iterable<Key> keys(Key lo, Key hi)
    {
      return new Iterator(lo, hi);
    }
 }


