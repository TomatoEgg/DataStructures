

public class Value implements Comparable<Value>
{
  public int count;
  public int id;
  public Value(int id)
  {
    this.id = id;
    this.count = 1;
  }
  
  public String toString()
  {
    return "<id,count>=" + "<"+id+","+count+">";
  }
  
  public int compareTo(Value v)
  {
    int returnValue = 0;
    if (this.count < v.count)
    {
      returnValue = -1;
    }
    else if (this.count > v.count)
    {
      returnValue = 1;
    }
    else //same count: their id must be different. Here we assume the object with smaller id (found earlier) is bigger
    {
      returnValue = (v.id - id);
    }
    
    return returnValue;
  }
}

