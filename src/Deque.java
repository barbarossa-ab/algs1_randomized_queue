
import java.util.Iterator;
import java.util.NoSuchElementException;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author barbarossa
 */
public class Deque<Item> implements Iterable<Item> {

    private int N;               // number of elements on queue
    private Deque.Node first;    // beginning of queue
    private Deque.Node last;     // end of queue

    // helper linked list class
    private class Node {
        private Item item;
        private Deque.Node next;
        private Deque.Node prev;
    }

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        N = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return first == null;
    }

    // return the number of items on the deque
    public int size() {
        return N;
    }

    // insert the item at the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new java.lang.NullPointerException();
        }

        Node newFirst = new Node();
        newFirst.item = item;
        newFirst.next = first;

        if (isEmpty()) {
            last = newFirst;
        } else {
            first.prev = newFirst;
        }

        first = newFirst;
        N++;
    }

    // insert the item at the end
    public void addLast(Item item) {
        if (item == null) {
            throw new java.lang.NullPointerException();
        }

        Node newLast = new Node();
        newLast.item = item;
        newLast.prev = last;

        if (isEmpty()) {
            first = newLast;
        } else {
            last.next = newLast;
        }

        last = newLast;
        N++;
    }

    // delete and return the item at the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }

        Node oldFirst = first;
        first = first.next;
        N--;

        if (isEmpty()) {
            last = null;
        } else {
            first.prev = null;
        }
        return oldFirst.item;
    }

    // delete and return the item at the end
    public Item removeLast() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }

        Node oldLast = last;
        last = last.prev;
        N--;

        if (last == null) {
            first = null;
        } else {
            last.next = null;
        }

        return oldLast.item;
    }

    // return an iterator over items in order from front to end
    @Override
    public Iterator<Item> iterator() {
        return new Deque.ListIterator();
    }

    // an iterator, doesn't implement remove() since it's optional
    private class ListIterator implements Iterator<Item> {

        private Deque.Node current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Node i = current;
            current = current.next;
            return i.item;
        }
    }

    /*
     public static void main(String[] args) {
     Deque<Integer> d = new Deque<Integer>();
     d.addFirst(3);
     d.addFirst(2);
     d.addFirst(1);
     d.addLast(4);
     d.addLast(5);
     d.addLast(6);

     for (Iterator it = d.iterator(); it.hasNext();) {
     System.out.println(it.next() + " ");
     }
        
     d.removeFirst();
     d.removeLast();
     d.removeLast();
     d.removeLast();
        
     System.out.println("After removing 1 4 5 6....");
        
     for (Iterator it = d.iterator(); it.hasNext();) {
     System.out.println(it.next() + " ");
     }
     }
     */
}
