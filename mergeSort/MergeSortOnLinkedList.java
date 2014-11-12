package com.adt.junxie;
import java.util.Random;

/*
 * MergeSortOnLinkedList implements uniformly shuffling a linked list.
 * The time complexity is O(nlogn) for MergeSortOnLinkedList.
 * The memory cost is constant, because the algorithm is in-place merge sort. 
 */
public class MergeSortOnLinkedList 
{
  public static class Node
  {
    public Node(int index)
    {
      this.next = null;
      this.index = index;
    }
    public Node next;
    public int data; //Data will be set by linked list
    public int index; //Used for displaying the order
  }
  
  public static class myLinkedList
  {
    public Node head;
    public int size;
    public Random r;
    
    public myLinkedList()
    {
      head = new Node(-1);
      head.next = null;
      head.data = 0; //it does not matter
      size = 0;
      r  = new Random();
    }
    
    public Node getMiddleNode()
    {
      Node middleNode = head;
      for (int i = 0; i < size/2; ++i)
      {
        middleNode = middleNode.next;
      }
      
      return middleNode;
    }
    
    public void append(Node n)
    {
      n.data = r.nextInt();
      Node lastNode = head;
      while (lastNode.next != null)
      {
        lastNode = lastNode.next;
      }
      
      lastNode.next = n;
      n.next = null;
      
      size++;
    }
    
    //Generate new random numbers for each node
    public void randomize()
    {
      Node n = head;
      while (n.next != null)
      {
        n.data = r.nextInt();
        n = n.next;
      }
    }
  }
  
  public static void mergeSort(myLinkedList list)
  {
    if (list.head.next != null && list.head.next.next != null) //more than 1 element
    {
      //Split it into two halves
      int totalSize = list.size; //Use a copy, otherwise updating the left size will update the list size because they point to the same object
      myLinkedList left = list;
      Node middleNode = list.getMiddleNode();
      left.size = totalSize/2;
      myLinkedList right = new myLinkedList();
      right.head.next = middleNode.next; //This set the new start of the right half
      right.size = totalSize - totalSize/2;
      middleNode.next = null; //this terminates the left half
      mergeSort(left);
      mergeSort(right);
      merge(left, right);
    }
    //else: only 1 element, considered sorted
  }
  
  //Merge two sorted linked list into one, merge the right one into the left one
  private static void merge(myLinkedList left, myLinkedList right)
  {
    Node currentLeft = left.head; //The first possible insertion position
    Node currentRight = right.head.next; //First node
    
    //Merge the right list into the left list (in place)
    while (currentLeft.next != null && currentRight != null) //both are not null, we can compare them
    {
      if (currentLeft.next.data < currentRight.data)
      {
        currentLeft = currentLeft.next; //move it forward to get the next insertion position
      }
      else //insert the currentRight into the left
      {
        Node nextRight = currentRight.next;
        currentRight.next = currentLeft.next;
        currentLeft.next = currentRight;
        currentLeft = currentRight;
        currentRight = nextRight;
      }
    }
    
    //we quite the while loop, so at least one of them is null.
    if (currentLeft.next == null && currentRight != null)
    {
      currentLeft.next = currentRight;
    }
    
    //Update the size after the merge
    left.size += right.size;
    right.head = null; //"discard" the right one, so that it does not points to any Node in order to avoid memory leakage
    right.size = 0;
  }
  
  private static void printList(myLinkedList list)
  {
    Node n = list.head;
    while (n.next != null)
    {
      System.out.println("[" + n.next.index + "]" + ":" + n.next.data);
      n = n.next;
    }
  }
  
  public static void main(String[] args)
  {
    myLinkedList myList = new myLinkedList();
    for (int i = 0; i < 10; ++i)
    {
      myList.append(new Node(i));
    }
    System.out.println("Original list");
    printList(myList);
    
    System.out.println("\nShuffled list");
    mergeSort(myList);
    printList(myList);
    
//    System.out.println("\nShuffled list");
//    myList.randomize();
//    mergeSort(myList);
//    printList(myList);
  }
}
