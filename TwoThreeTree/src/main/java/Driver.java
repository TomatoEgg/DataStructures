import edu.princeton.cs.introcs.In;

import java.net.URL;
import java.util.Collections;
import java.util.Stack;
/**
*
*/
public class Driver 
{
   public static void main(String[] args) 
   {
     int index = 0;
     URL url = Driver.class.getResource("kap1.txt");
     In input = new In(url);
     TwoThreeTree<String, Value> tree = new TwoThreeTree<String, Value>();
     
     Value v = null;
     while (!input.isEmpty()) 
     {
       String line = input.readLine().trim();
       String[] words = line.split(" ");
       for (String word : words) 
       {
         if (word.equals(""))
         {
           continue;
         }
         v = tree.get(word);
         if(v != null)
         {
           v.count++;
         }
         else
         {
           v = new Value(++index);
         }
         word = word.replaceAll("[,.;:'-()]", ""); //remove some special characters
         tree.put(word.toLowerCase(), v); //make it case insensitive
         System.out.println(word);
         //Run the program and scan the words
         //First strip away extra characters from each word
         //Then add the word into the search tree
       }
     }
     System.out.println(" ");
     System.out.println("size=" + tree.size());
     System.out.println("density=" + tree.density());
     System.out.println("depth=" + tree.depth());
     System.out.println("howMuchMore=" + tree.howMuchMore());
     java.util.Iterator<String> it = tree.new Iterator().iterator();
     
     System.out.println(" ");
     System.out.println("Printing the 10 words with most occurances using iterator");
     Stack<Value> stack = new Stack<Value>();
     while (it.hasNext())
     {
       String k = it.next();
//       System.out.println(k + ":" + tree.get(k));
       stack.push(tree.get(k));
     }
     //Since the values (<id, occurrence>) is not sorted, so we need to sort them before popping
     Collections.sort(stack);
     
     for (int i = 0; i < 10; ++i)
     {
       System.out.println("[" + i + "]" + ":" + stack.pop());
     }
     
     TwoThreeTree<Value, String> inverseTree = new TwoThreeTree<Value, String>();
     it = tree.new Iterator().iterator();
     //Loop all keys and reverse the key-value pair
     while (it.hasNext())
     {
       String key = it.next();
       Value value = tree.get(key);
       inverseTree.put(value, key);
     }
     //create an iterator for the inverseTree
     java.util.Iterator<Value> inverseIt = inverseTree.new Iterator().iterator();
     Stack<String> stackForInverseTree = new Stack<String>();
     while (inverseIt.hasNext())
     {
       Value reversedKey = inverseIt.next();
       stackForInverseTree.push(inverseTree.get(reversedKey)); //Push the words in the stack. It is ordered by the number of occurences
//       System.out.println(reversedKey + ":" + inverseTree.get(reversedKey));
     }
     
     System.out.println("Printing the 10 words with most occurances reversed tree");
     for (int i = 0; i < 10; ++i)
     {
       System.out.println("[" + i + "]" + ":" + stackForInverseTree.pop());
     }
   }
}