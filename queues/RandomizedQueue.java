import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.lang.UnsupportedOperationException;

public class RandomizedQueue<Item> implements Iterable<Item>
{
    private Item[] q;
    private int size;
   

        
    public RandomizedQueue()  // construct an empty randomized queue
    {
        q = (Item[]) new Object[2];
        size = 0;
        
    }
    
    public boolean isEmpty()  // is the randomized queue empty?
    {
        return size == 0;
    }
    
    public int size() // return the number of items on the randomized queue
    {
        return size;
    }
    
    private void resize(int capacity) 
    {
        Item[] copy = (Item[]) new Object[capacity];
        for(int i = 0; i < size; i++)
        {
            copy[i] = q[i];
        }
        q = copy;
    }
    
    public void enqueue(Item item) // add the item
    {
        if(item == null)
        {
            throw new IllegalArgumentException();
        }
        if( size == q.length)
        {
            resize(2*q.length);
        }
        q[size++] = item;
     
    }
    
    public Item dequeue() // remove and return a random item
    {
        if(isEmpty())
        {
            throw new NoSuchElementException();
        }
        int rand = StdRandom.uniform(size);
        Item value = q[rand];
     
        if(rand != size-1) {
        	q[rand] = q[size-1];
        	}
        q[size-1] = null;
        size--;
        if(size > 0 && size <= q.length/4)
        {
            resize(q.length/2);
        }
        return value;
    }
    
    public Item sample() // return a random item (but do not remove it)
    {
        if(isEmpty())
        {
            throw new NoSuchElementException();
        }
        int rand = StdRandom.uniform(size);
        Item value = q[rand];
        if(size > 0 && size == q.length/4)
        {
            resize(q.length/2);
        }
        return value;
    }
    

    public Iterator<Item> iterator()   // return an independent iterator over items in random order
    {
        return new RandomIterator();
    }
    
    private class RandomIterator implements Iterator<Item>
    {
        private int randLoc = 0;
        
        private int copySize = size;
        private Item[] copy = (Item[]) new Object[copySize];
        
        private RandomIterator()
        {
            for(int i = 0; i<copySize;i++)
            {
                copy[i] = q[i];
            }
        }
        
        public boolean hasNext()
        {
            return copySize > 0;
        }
        
        public void remove()
        {
            throw new UnsupportedOperationException();
        }
        
        public Item next()
        {
            if(copySize == 0)
            {
                throw new NoSuchElementException();
            }
            randLoc = StdRandom.uniform(copySize);
            Item currentItem = copy[randLoc];
            if(randLoc != copySize-1)
            {
                copy[randLoc] = copy[copySize-1];
            }
            copy[copySize-1] = null;
            copySize--;
            return currentItem;
        }
    }
    
    public static void main(String[] args)   // unit testing (required)
    {

        
         RandomizedQueue<String> test2 = new RandomizedQueue<String>();
         test2.enqueue("to");
         test2.enqueue("be");
         test2.enqueue("or");
         test2.enqueue("not");
  
         for(String s: test2)
         {
             for(String s2: test2)
             {
                 System.out.print(s2 += " ");
             }
             System.out.print(s += " ");
         }
         
    }
}