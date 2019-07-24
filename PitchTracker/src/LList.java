import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Linked list implementation.
 *
 * @author sjw
 */
public class LList<T> implements List<T>, Iterable<T>{

    protected int size;
    protected Node first;
    protected Node last;

    //adds to end of the list
    @Override
    public boolean add(T o) {
        verifyIntegrity();
        Node n = new Node(o);
        if (size == 0) {
            first = n;
        } else {
            link(last, n);
        }
        last = n;
        size++;
        return true;
    }//ends boolean add(T o)

    @Override
    public boolean add(int index, T o) {
        verifyIntegrity();
        if (index >= size) {
            return add(o);
        } 
        Node n = new Node(o);
        if (index == 0) {
            link(n, first);
            first = n;
            size++;
            return true;
        } else {
            Node next = getNodeAt(index);
            insertBefore(n, next);
            size++;
            return true;
        }
    }//ends boolean add(int index, T o)

    @Override
    public void clear() {
        verifyIntegrity();
        first = last = null;
        size = 0;
    }

    @Override
    public boolean contains(T o) {
        verifyIntegrity();
        return indexOf(o) >= 0;
    }//ends boolean contains

    @Override
    public T get(int index) {
        verifyIntegrity();
        if (index < 0 || index >= size) {
            throw new NoSuchElementException();
        }
        return getNodeAt(index).getElement();
    }//ends T get

    @Override
    public T set(int index, T o) {
        verifyIntegrity();
        if (index < 0 || index >= size) {
            throw new NoSuchElementException();
        }
        Node n = getNodeAt(index);
        T old = n.getElement();
        n.setElement(o);
        return old;
    }//ends T set
    
    @Override
    public boolean removeLast(){
      verifyIntegrity();
      if(size <= 0)
         return false;
      last = last.getPrevious();
      last.next = null;     
      
      return true;
    
    }//ends removeLast()

    @Override
    public boolean remove(T o) {
        verifyIntegrity();
        int index = indexOf(o);
        if (index >= 0) {
            remove(index);
            return true;
        }
        return false;
    }//ends remove

    @Override
    public T remove(int index) {
        verifyIntegrity();
        Node n = getNodeAt(index);
        T value = n.getElement();
        link(n.getPrevious(), n.getNext());
        
        if (index == 0) {
            first = n.getNext();
            first.previous = null;
        } else if (index == size-1) {
            last = n.getPrevious();
            last.next = null;
        }        
        size--;
        return value;
    }//ends T remove

    @Override
    public int indexOf(T o) {
        verifyIntegrity();
        Node current = first;
        for (int i = 0; i < size; i++) {
            if (current.getElement().equals(o)) {
                return i;
            }
            current = current.getNext();
        }
        return -1;
    }//ends indexOf

    @Override
    public int lastIndexOf(T o) {
        verifyIntegrity();
        Node current = last;
        for (int i = size - 1; i >= 0; i--) {
            if (current.getElement().equals(o)) {
                return i;
            }
            current = current.getPrevious();
        }
        return -1;
    }//ends lastIndexOf

    @Override
    public boolean isEmpty() {
        verifyIntegrity();
        return size == 0;
    }

    @Override
    public int size() {
        verifyIntegrity();
        return size;
    }

    @Override
    public Object[] toArray() {
        verifyIntegrity();
        Object[] array = new Object[size];
        Node current = first;
        for (int i = 0; i < size; i++) {
            array[i] = current.getElement();
            current = current.getNext();
        }
        return array;
    }//toArray

    @Override
    public Iterator<T> iterator() {
        return new Iterator() {
            private Node current = first;
            private Node lastReturned = null;
            private boolean nextCalled = false;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public Object next() {
                if (hasNext()) {
                    T value = current.getElement();
                    lastReturned = current;
                    current = current.getNext();
                    nextCalled = true;
                    return value;
                }
                throw new NoSuchElementException();
            }

            @Override
            public void remove() {
                if (!nextCalled) {
                    throw new IllegalStateException("remove without next");
                }
                nextCalled = false;
                if (lastReturned == first) {
                    first = current;
                    size--;
                } else {
                    link(lastReturned.getPrevious(), current);
                    size--;
                }
            }
        };
    }//ends iterator

    /**
     * Use this method to link two nodes.
     *
     * @param previous
     * @param next
     */
    protected void link(Node previous, Node next) {
        if (previous != null) {
            previous.setNext(next);
        }
        if (next != null) {
            next.setPrevious(previous);
        }
    }//ends link

    /**
     * Use this method to insert a new node into a list after a node.
     *
     * @param previous
     * @param newNode
     */
    protected void insertAfter(Node previous, Node newNode) {
        Node next = previous.getNext();
        link(previous, newNode);
        link(newNode, next);
    }//insertAfter

    /**
     * Use this method to insert a new node into a list before a node.
     *
     * @param newNode
     * @param next
     */
    protected void insertBefore(Node newNode, Node next) {
        Node previous = next.getPrevious();
        link(previous, newNode);
        link(newNode, next);
    }//ends insertBefore

    protected Node getNodeAt(int index) {
        if (index < 0 || index >= size) {
            throw new NoSuchElementException();
        }
        Node current = first;
        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }
        return current;
    }//ends getNodeAt

    private void verifyIntegrity() {
        Node c = first;
        Object[] nodes;
        nodes = new Object[size];
        int i = 0;
        while (c != null) {
            for (int j = 0; j < i; j++) {
                if (nodes[j] == c) {
                    throw new IllegalStateException("Circular reference");
                }
            }
            if (i == size) {
                throw new IllegalStateException("More nodes than objects in chain");
            } else if (i == size - 1 && c != last) {
                throw new IllegalStateException("last node isn't last");
            }
            nodes[i] = c;
            c = c.getNext();
            i++;
        }

        c = last;
        i = size - 1;
        while (c != null) {
            if (nodes[i] != c) {
                throw new IllegalStateException("Previous reference broken");
            }
            c = c.getPrevious();
            i--;
        }

        if (i != -1) {
            throw new IllegalStateException("Previous reference broken");
        }
    }//ends verifyIntegrety

    protected class Node {

        private T element;
        private Node previous;
        private Node next;

        public Node(T element) {
            this.element = element;
        }

        public T getElement() {
            return element;
        }

        public void setElement(T element) {
            this.element = element;
        }

        public Node getPrevious() {
            return previous;
        }

        public void setPrevious(Node previous) {
            this.previous = previous;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }
    }//ends node class
}//ends class