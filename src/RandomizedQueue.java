import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] a;
    private int N;

    public RandomizedQueue()           // construct an empty randomized queue
    {
        a = (Item[]) new Object [2];
        N = 0;
    }
    public boolean isEmpty()           // is the queue empty?
    {
        return N == 0;
    }
    public int size()                  // return the number of items on the queue
    {
        return N;
    }
    public void enqueue(Item item) throws NullPointerException     // add the item
    {
        if (item == null) throw new NullPointerException();
        if (N == a.length) resize(2*N);
        a[N] = item;
        if (N > 0) {
            int randomIndex = StdRandom.uniform (N + 1);
            Item tmpItem = a[randomIndex];
            a[randomIndex] = a[N];
            a[N] = tmpItem;
        }
        N++;

    }
    public Item dequeue() throws NoSuchElementException             // delete and return a random item
    {
        if (isEmpty()) throw new NoSuchElementException();

        Item item = a[--N];

        a[N] = null;
        if (N > 0 && N == (a.length/4)) resize(a.length/2);
        return item;
    }

    public Item sample() throws NoSuchElementException              // return (but do not delete) a random item
    {
        if (isEmpty()) throw new NoSuchElementException();
        return a[StdRandom.uniform(N)];
    }
    public Iterator<Item> iterator()   // return an independent iterator over items in random order
    {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        int it;
        Item temp;
        int defaultNumOfVar;

        public ListIterator () {
            defaultNumOfVar = N;
            for (int i = 0; i < defaultNumOfVar; i++) {
                int r = i + StdRandom.uniform(defaultNumOfVar-i);     // between i and hi
                temp = a[i];
                a[i] = a[r];
                a[r] = temp;
            }
            it = 0;
        }
        public boolean hasNext() {
            if (it < (defaultNumOfVar-1)) return true;
            return false;
        }
        public Item next () throws NoSuchElementException {
            if (it >= defaultNumOfVar) throw new NoSuchElementException();
            return a[it++];
        }

        public void remove() throws UnsupportedOperationException{
            throw new UnsupportedOperationException();
        }

    }
    private void resize(int len) {
        Item[] temp = (Item [])new Object[len];
        for (int i = 0; i < N; ++i) {
            temp[i] = a[i];
        }
        a = temp;
    }
}