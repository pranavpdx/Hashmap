// --== CS400 Project One File Header ==--
// Name: Pranav Sharma
// CSL Username: psharma
// Email: pnsharma@wisc.edu
// Lecture #: 003 @2:25pm
// Notes to Grader:
import java.util.NoSuchElementException;

@SuppressWarnings("unchecked")
public class HashtableMap<KeyType, ValueType> implements MapADT<KeyType, ValueType> {

  // helper class that defines an element in the hashtable array
  protected static class HashtableItem<KeyType, ValueType> {
    // private fields for the helper class
    private KeyType key;
    private ValueType value;
    private HashtableItem<KeyType, ValueType> next;

    /**
     * Constructor that takes in a key and value and assigns them
     * 
     * @param key   the key for the item
     * @param value the value of the item
     */
    HashtableItem(KeyType key, ValueType value) {
      this.key = key;
      this.value = value;
    }

    /**
     * getter method for key
     * 
     * @return the key
     */
    public KeyType getKey() {
      return this.key;
    }

    /**
     * getter method for value
     * 
     * @return the value
     */
    public ValueType getValue() {
      return this.value;
    }

    /**
     * setter method for next item
     * 
     * @param next item
     */
    public void setNext(HashtableItem<KeyType, ValueType> next) {
      this.next = next;
    }

    /**
     * getter method for next item
     * 
     * @return the next item
     */
    public HashtableItem<KeyType, ValueType> getNext() {
      return this.next;
    }

  }

  // private & protected fields for HashtableMap
  private int capacity;
  private int size;
  private double loadFactor = 0.7;
  protected HashtableItem[] hashTable;

  /**
   * Constructor for class that assigns capacity of HashMap
   * 
   * @param capacity initial capacity of the HashMap
   */
  public HashtableMap(int capacity) {
    this.capacity = capacity;
    hashTable = new HashtableItem[capacity];
  }

  /**
   * Constructor for class that auto-assigns capacity as 15
   */
  public HashtableMap() {
    this.capacity = 15;
    hashTable = new HashtableItem[capacity];
  }

  /**
   * Sets the size of the array
   * 
   * @param size the new size
   */
  private void setSize(int size) {
    this.size = size;
  }

  /**
   * gets the capacity of the array
   * 
   * @return the capacity of the array
   */
  private int getCapacity() {
    return capacity;
  }

  /**
   * Sets the capacity of the hashMap array
   * 
   * @param capacity the new capacity
   */
  private void setCapacity(int capacity) {
    this.capacity = capacity;
  }

  /**
   * returns the Load Factor of the array
   * 
   * @return the load factor
   */
  private double getLoadFactor() {
    return loadFactor;
  }

  /**
   * Applies the hash function to the key
   * 
   * @param key the key the function will be performed on
   * @return the integer that results from the function being performed
   */
  private int hashFunction(KeyType key) {
    return Math.abs(key.hashCode() % getCapacity());
  }

  /**
   * rehashes the array if the load factor requirement is met
   */
  private void reHash() {
    if ((double) (size()) / (double) (getCapacity()) >= getLoadFactor()) {
      HashtableItem[] newTable = new HashtableItem[getCapacity()];

      for (int i = 0; i < getCapacity(); i++) {
        newTable[i] = hashTable[i];
      }
      setCapacity(getCapacity() * 2);
      hashTable = new HashtableItem[getCapacity()];
      setSize(0);

      for (int i = 0; i < getCapacity() / 2; i++) {
        if (newTable[i] != null) {
          put((KeyType) newTable[i].getKey(), (ValueType) newTable[i].getValue());

          HashtableItem head = newTable[i];
          while (head.getNext() != null) {
            put((KeyType) head.getKey(), (ValueType) head.getValue());
            head = head.getNext();
          }
        }
      }
    }
  }

  /**
   * Inserts a new (key, value) pair into the map if the map does not contain a value mapped to key
   * yet.
   * 
   * @param key   the key of the (key, value) pair to store
   * @param value the value that the key will map to
   * @return true if the (key, value) pair was inserted into the map, false if a mapping for key
   *         already exists and the new (key, value) pair could not be inserted
   */
  public boolean put(KeyType key, ValueType value) {
    if (key == null || containsKey(key) || value == null) {
      return false;
    }
    int index = hashFunction(key);
    if (hashTable[index] == null) {
      hashTable[index] = new HashtableItem(key, value);
    } else {
      HashtableItem head = hashTable[index];
      while (head.getNext() != null) {
        head = head.getNext();
      }
      head.setNext(new HashtableItem(key, value));
    }
    setSize(size() + 1);
    reHash();
    return true;
  }

  /**
   * Returns the value mapped to a key if the map contains such a mapping.
   * 
   * @param key the key for which to look up the value
   * @return the value mapped to the key
   * @throws NoSuchElementException if the map does not contain a mapping for the key
   */
  public ValueType get(KeyType key) throws NoSuchElementException {
    if (key == null) {
      throw new NoSuchElementException();
    }
    int index = hashFunction(key);

    if (hashTable[index] == null) {
      throw new NoSuchElementException();
    } else {
      HashtableItem head = hashTable[index];
      if (head.getKey().equals(key)) {
        return (ValueType) head.getValue();
      }
      while (head != null) {
        if (head.getKey().equals(key)) {
          return (ValueType) head.getValue();
        }
        head = head.getNext();
      }
    }
    throw new NoSuchElementException();
  }

  /**
   * Removes a key and its value from the map.
   * 
   * @param key the key for the (key, value) pair to remove
   * @return the value for the (key, value) pair that was removed, or null if the map did not
   *         contain a mapping for key
   */
  public ValueType remove(KeyType key) {
    if (key == null || !containsKey(key)) {
      return null;
    }

    ValueType result;
    int index = hashFunction(key);
    HashtableItem head = hashTable[index];
    if (head.getKey().equals(key)) {
      hashTable[index] = head.getNext();
      setSize(size() - 1);
      return (ValueType) head.getValue();
    }
    while (head.getNext() != null) {
      if (head.getNext().getKey().equals(key)) {
        result = (ValueType) head.getNext().getValue();
        head.setNext(head.getNext().getNext());
        setSize(size() - 1);
        return result;
      }
      head = head.getNext();
    }
    return null;
  }

  /**
   * Checks if a key is stored in the map.
   * 
   * @param key the key to check for
   * @return true if the key is stored (mapped to a value) by the map and false otherwise
   */
  public boolean containsKey(KeyType key) {
    int index = hashFunction(key);
    if (hashTable[index] == null) {
      return false;
    } else {
      HashtableItem head = hashTable[index];
      while (head != null) {
        if (head.getKey().equals(key)) {
          return true;
        }
        head = head.getNext(); // could be errors
      }
    }
    return false;
  }


  /**
   * Returns the number of (key, value) pairs stored in the map.
   * 
   * @return the number of (key, value) pairs stored in the map
   */
  public int size() {
    return size;
  }

  /**
   * Removes all (key, value) pairs from the map.
   */
  public void clear() {
    for (int i = 0; i < capacity; i++) {
      hashTable[i] = null;
    }
    size = 0;
  }


}
