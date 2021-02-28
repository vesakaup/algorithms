import java.util.Iterator;
import java.util.NoSuchElementException;
import java.lang.NullPointerException;



public class Deque<Item> implements Iterable<Item> {
    
    private Node first;
    private  Node last;
    private int size;
    
    private class Node
    {
        Item item;
        Node next;
        Node previous;
    }
       
    public Deque() // create empty deque
    {
        first = null;
        last = first;
        size = 0;
    }
    
    public boolean isEmpty() // is the deque empty?
    {
        return (size == 0);
    }
    
    public int size() // return the number of items on the deque
    {
        return size;
    }
    
    public void addFirst(Item item) // add the item to the front
    {
        if(item == null)
        {
            throw new java.lang.IllegalArgumentException();
        }
        Node old = first;
        first = new Node();
        first.item = item;
        first.next = old;
        first.previous = null;
       
        if(isEmpty())
        {
            last = first;
        }
        else
        {
            old.previous = first;
        }
        size++;
    }
    
    public void addLast(Item item) // add the item to the back
    {
        if(item == null)
        {
            throw new java.lang.IllegalArgumentException();
        }
        Node old = last;
        last = new Node();
        last.item = item;
        last.previous = old;
        last.next = null;
        
        if(isEmpty())
        {
            first = last;
        }
        else
        {
            old.next = last;
        }
        size++;
    }
    
    public Item removeFirst() // remove and return the item from the front
    {
        if(isEmpty())
        {
            throw new NoSuchElementException();
        }
        
        Item value = first.item; 
        
        if(size() == 1)
        {
            first = null;
            last = first;
            size--;
            return value;
        }
        
        first = first.next;
        first.previous = null;
        size--;
        if(isEmpty())
        {
            last = null;
        }
        return value;
    }
    
    public Item removeLast() // remove and return the item from the back
    {
        if(isEmpty())
        {
            throw new NoSuchElementException();
        }
        
        Item value = last.item;
        
        if(size() == 1)
        {
            last = null;
            first = last;
            size--;
            return value;
        }
        last = last.previous;
        last.next = null;
        size--;
        if(isEmpty())
        {
            first = null;
        }
        return value;
        

    }
    
    public Iterator<Item> iterator() // return an iterator over items in order from front to back
    {
        return new DequeIterator();
    }
    
    private class DequeIterator implements Iterator<Item>
    {
        private Node current = first;
        
        public boolean hasNext()
        {
            return current != null;
        }
        
        public void remove()
        {
            throw new UnsupportedOperationException();
        }
        
        public Item next()
        {
            try
            {
                Node attempt = current.next;
            }
            catch(NullPointerException e)
            {
                throw new NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            return item;
        }
    }
    
    public static void main(String[] args)    // unit testing (required)
    {

        Deque<Integer> test1 = new Deque<Integer>();
        test1.addFirst(1);
        test1.removeFirst();
        test1.addLast(2);
        System.out.print(test1.removeLast());


        
        for(Integer s: test1)
        {
            System.out.print(s);
        }
    }
}