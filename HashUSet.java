//Ali Sbeih
/**
 * An implementation of the SimpleUSet interface that represents
 * a set as a hash table with chaining.
 */

import java.util.Random;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class HashUSet<E> implements SimpleUSet<E> {
    public static final int DEFAULT_LOG_CAPACITY = 4;

    protected int logCapacity = DEFAULT_LOG_CAPACITY; // value d from lecture
    protected int capacity = 1 << logCapacity;        // n = 2^d
    protected int size = 0;
    protected Object[] table;                         // array of heads of linked lists

    // final = can't be changed after initial assignment!
    protected final int z;

    public HashUSet() {
        // set capacity to 2^logCapacity
        int capacity = 1 << logCapacity;

        table = new Object[capacity];

        // add a sentinel node to each list in the table
        for (int i = 0; i < capacity; ++i) {
            table[i] = new Node<E>(null);
        }


        // fix a random odd integer
        Random r = new Random();
        z = (r.nextInt() << 1) + 1;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }


    /**
     * Add a new element to the set. This method first checks if
     * the set already contains an element y that is equal to the
     * element x being added. If no such element is found then x
     * is added to the set, and the value true is returned. If such
     * an element y is found, then the set is not modified and the method returns false
     */
    @Override
    @SuppressWarnings("unchecked")
    public boolean add(E x) {
        //check if the array is full and if so increase its capacity
        if (size == capacity) increaseCapacity();
        //get the index of x
        int i = getIndex(x);
        Node nd = (Node) table[i];
        //check if the list at the index i is not empty
        if (nd.next == null) {
            //add new node
            nd.next = new Node(x);
            size++;
            return true;
        } else {
            while (true) {
                //check if there is an element equal to x in the list
                if (nd.next.value.equals(x)) return false;
                if (nd.next.next != null) nd = nd.next;
                    //if no such element exists append a new node to the end of the list
                else {
                    nd.next.next = new Node(x);
                    size++;
                    return true;
                }
            }
        }
    }

    /**
     * Remove an element equal to x from the
     * set and return it. Otherwise, return null
     */
    @Override
    @SuppressWarnings("unchecked")
    public E remove(E x) {
//index of element x
        int i = getIndex(x);
        Node nd = (Node) table[i];
        //check if the list is empty
        if (nd.next == null) {
            return null;
        } else {
            while (true) {
                //if an element equal to x is in the set remove it and return it
                if (nd.next.value.equals(x)) {
                    E value = (E) nd.next.value;
                    if (nd.next.next == null) nd.next = null;
                    else nd.next = nd.next.next;
                    size--;
                    return value;
                }
                if (nd.next.next != null) nd = nd.next;
                else {
                    return null;
                }
            }
        }
    }

    /**
     * Find an element equal to x in the set. If such
     * an element y is found, return it. Otherwise return null.
     * This method does not change the state of the set.
     */
    @Override
    @SuppressWarnings("unchecked")
    public E find(E x) {
        //index of x
        int i = getIndex(x);
        Node nd = (Node) table[i];
        //check if the list at index i is empty
        if (nd.next == null) {
            return null;
        } else {
            while (true) {
                //if there is an element equal to x return
                if (nd.next.value.equals(x)) {
                    E value = (E) nd.next.value;
                    return value;
                }
                if (nd.next.next != null) nd = nd.next;
                else {
                    return null;
                }
            }
        }
    }

    protected int getIndex(E x) {
        // get the first logCapacity bits of z * x.hashCode()
        return ((z * x.hashCode()) >>> 32 - logCapacity);
    }

    // @SuppressWarnings("unchecked")
    // protected void increaseCapacity() {

    // 	logCapacity += 1;
    // 	capacity = capacity << 1;

    // 	// store the old hash table
    // 	Object[] oldTable = table;

    // 	// make a new new has table and initialize it
    // 	table = new Object[capacity];

    // 	// add old lists to new table
    // 	for (int i = 0; i < oldTable.length; ++i) {
    // 	    table[i] = oldTable[i];	    
    // 	}

    // 	// add sentinel nodes to new table
    // 	for (int i = oldTable.length; i < table.length; ++i) {
    // 	    table[i] = new Node<E>(null);
    // 	}
    // }

    @SuppressWarnings("unchecked")
    protected void increaseCapacity() {
        logCapacity += 1;
        capacity = capacity << 1;

        // store the old hash table
        Object[] oldTable = table;

        // make a new new has table and initialize it
        table = new Object[capacity];

        for (int i = 0; i < table.length; ++i) {
            table[i] = new Node<E>(null);
        }

        // reset the size to 0 since it will get incremented when we
        // add elements to the new table
        size = 0;

        // iterate over lists in oldTable and add elements to new table
        for (int i = 0; i < oldTable.length; ++i) {
            Node<E> nd = ((Node<E>) oldTable[i]).next;
            while (nd != null) {
                this.add(nd.value);
                nd = nd.next;
            }
        }
    }

    protected class Node<E> {
        protected Node<E> next = null;
        protected E value;

        public Node(E value) {
            this.value = value;
        }
    }
}
