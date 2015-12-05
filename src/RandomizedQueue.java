
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
public class RandomizedQueue<Item> implements Iterable<Item> {

    private static final int DEFAULT_ALLOC_LEN = 2;
    private Item[] items;
    private int N;         // size of queue

    public RandomizedQueue() {
        items = (Item[]) new Object[DEFAULT_ALLOC_LEN];
        N = 0;
    }

    public boolean isEmpty() {
        return (N == 0);
    }

    public int size() {
        return N;
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new java.lang.NullPointerException();
        }

        int index = StdRandom.uniform(items.length);
        while (items[index] != null) {
            index = StdRandom.uniform(items.length);
        }
        items[index] = item;
        N++;

        if (N >= items.length / 2) {
            Item[] newItems = (Item[]) new Object[items.length * 2];

            for(int i = 0; i < items.length; i++) {
                newItems[i] = items[i];
            }
            items = newItems;
        }
    }

    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        int index = StdRandom.uniform(items.length);

        while (items[index] == null) {
            index = StdRandom.uniform(items.length);
        }

        Item selected = items[index];
        items[index] = null;
        N--;

        if (N <= items.length / 4) {
            Item[] newItems = (Item[]) new Object[items.length / 2];
            int newIndex = 0;

            for (int i = 0; i < items.length; i++) {
                if (items[i] != null) {
                    newItems[newIndex++] = items[i];
                }
            }
            items = newItems;
        }

        return selected;
    }

    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        int index = StdRandom.uniform(items.length);

        while (items[index] == null) {
            index = StdRandom.uniform(items.length);
        }

        return items[index];
    }

    @Override
    public Iterator<Item> iterator() {
        return new RandomizedQueue.ShuffleIterator();
    }

    // an iterator, doesn't implement remove() since it's optional
    private class ShuffleIterator implements Iterator<Item> {

        private int index;
        private Item[] itItems;

        public ShuffleIterator() {
            index = 0;
            itItems = (Item[]) new Object[N];

            // copy the items from the queue
            int j = 0;
            for (int i = 0; (i < items.length) && (j < N); i++) {
                if (items[i] != null) {
                    itItems[j++] = items[i];
                }
            }

            // shuffle them
            for (int i = 0; i < N; i++) {
                // int from remainder of deck
                int r = i + StdRandom.uniform(N - i);
                Item swap = itItems[r];
                itItems[r] = itItems[i];
                itItems[i] = swap;
            }
        }

        @Override
        public boolean hasNext() {
            return (index < N);
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
            return itItems[index++];
        }
    }

    /*    
     public static void main(String[] args) {
     RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();

     for(int i = 0 ; i < 5000000; i++) {
     rq.enqueue(i);
     }
     for(int i = 0 ; i < 4999990; i++) {
     rq.dequeue();
     }

     for (Iterator it = rq.iterator(); it.hasNext();) {
     System.out.println(it.next() + " ");
     }
     }
     */
}
