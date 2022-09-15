//Ali Sbeih

/**
 * An implementation of the SimpleMap interface that represents
 * a map (or function) from keys of type K to values of type V. A
 * SimpleMap represents a set of key-value pairs,(k, v).
 */
public class HashSimpleMap<K, V> implements SimpleMap<K, V> {
    HashUSet table = new HashUSet();

    /**
     * Get the size of the SimpleMap
     */
    public int size() {
        return table.size();
    }

    /**
     * Determine if the map is empty
     */
    public boolean isEmpty() {
        return table.isEmpty();
    }

    /**
     * Get the (unique) value associated with key key , or
     * null if key is not present in the map.
     */
    public V get(K key) {
        //create a pair with key
        Pair pair = new Pair();
        pair.key = key;
        //find a pair equal to the pair we created
        Pair newPair = (Pair) table.find(pair);
        if (null != newPair) return (V) newPair.value;
        return null;
    }

    /**
     * Set the value associated to key key to be value and return the
     * previous value associated with key, or null if key
     * was not previously present.
     */
    public V put(K key, V value) {
        //value to be returned
        V val = get(key);
//create a pair with key
        Pair pair = new Pair();
        pair.key = key;
        // find a pair equal to the created pair
        Pair newPair = (Pair) table.find(pair);
        //update the value of the pair found
        if (newPair != null) {
            newPair.value = value;
        }
        //create a new pair with right value and key and add it to the set
        else {
            Pair addPair = new Pair();
            addPair.value = value;
            addPair.key = key;
            table.add(addPair);
        }
        return val;
    }

    /**
     * Remove a key and its associated value from the map, and return
     * the associated value null if the key was not present.
     */
    public V remove(K key) {
        //create a pair with key
        Pair pair = new Pair();
        pair.key = key;
        //remove a pair equal to the created pair
        Pair newPair = (Pair) (table.remove(pair));
        //return correct value
        if (newPair != null) return (V) newPair.value;
        return null;
    }

    /**
     * Determine if a key is contained in the map.
     */
    public boolean contains(K key) {
        //create pair with key
        Pair pair = new Pair();
        pair.key = key;
        //check if the pair is found in the table
        if (table.find(pair) != null) return true;
        return false;
    }

    //A class that represents a pair consisting of a key (of type K) and a value (of type V).


    public class Pair<K, V> {
        K key;
        V value;

        // if two Pairs have the same (semantically equivalent) keys, then they should be hashed to the same index.
        @Override
        public int hashCode() {
            return key.hashCode();
        }

        //the HashUSet should treat two Pairs as equal if they have the same key.
        @Override
        public boolean equals(Object obj) {
            if (key.equals(((Pair) obj).key)) return true;
            return false;
        }

    }
}
