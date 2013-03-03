import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private class Node {
        private Item item;
        private Node next;
        private Node previous;

        Node() {
            this(null, null, null);
        }

        Node(Item item, Node previous, Node next) {
            this.item = item;
            this.previous = previous;
            this.next = next;
        }
    }

    private Node head;
    private Node tail;
    private int N;

    public Deque()                     // construct an empty deque
    {
        head = new Node();
        tail = new Node();
        head.next = tail;
        tail.previous = head;
        N = 0;
    }

    public boolean isEmpty()           // is the deque empty?
    {
        return N == 0;
    }

    public int size()                  // return the number of items on the deque
    {
        return N;
    }

    public void addFirst(Item item) throws NullPointerException // insert the item at the front
    {
        if (item == null) throw new NullPointerException();
        Node second = head.next;
        Node newNode = new Node(item, head, second);
        second.previous = newNode;
        head.next = newNode;
        ++N;
    }

    public void addLast(Item item) throws NullPointerException    // insert the item at the end
    {
        if (item == null) throw new NullPointerException();
        Node secondLast = tail.previous;
        Node newNode = new Node(item, secondLast, tail);
        secondLast.next = newNode;
        tail.previous = newNode;
        ++N;
    }

    public Item removeFirst() throws NoSuchElementException         // delete and return the item at the front
    {
        Node tmp = head.next;
        if (N == 0) throw new NoSuchElementException();
        Item val = tmp.item;
        (tmp.next).previous = head;
        head.next = tmp.next;

        --N;
        return val;
    }

    public Item removeLast() throws NoSuchElementException         // delete and return the item at the end
    {
        Node tmp = tail.previous;
        if (N == 0) throw new NoSuchElementException();
        Item val = tmp.item;
        (tmp.previous).next = tail;
        tail.previous = tmp.previous;
        --N;
        return val;
    }

    public Iterator<Item> iterator()   // return an iterator over items in order from front to end
    {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node firstNode;
        private Node lastNode;

        public ListIterator() {
            firstNode = head;
            lastNode = tail;
        }

        public boolean hasNext() {
            return (firstNode.next.next != null);
        }

        public boolean hasPrevious() {
            return (lastNode.previous.previous != null);
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            firstNode = firstNode.next;
            Item item = firstNode.item;

            return item;
        }

        public Item previous() {
            if (!hasPrevious()) throw new NoSuchElementException();
            lastNode = lastNode.previous;
            Item item = lastNode.item;

            return item;
        }
    }
}
