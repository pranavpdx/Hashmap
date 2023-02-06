// --== CS400 Project One File Header ==--
// Name: Pranav Sharma
// CSL Username: psharma
// Email: pnsharma@wisc.edu
// Lecture #: 003 @2:25pm
// Notes to Grader:
import java.util.NoSuchElementException;

public class HashtableMapTests {

  public static void main(String[] args) {
    // TODO Auto-generated method stub
    if (test1() && test2() && test3() && test4() && test5()) {
      System.out.println("True");
    }else {
      System.out.println("False");
    }
  }

  /**
   * Tests the put() and containsKey() methods of HashtableMap
   * 
   * @return true if test passes, false otherwise
   */
  public static boolean test1() {
    try {
    HashtableMap<Integer, String> map = new HashtableMap<>();
    // tests if put return true to adding an entry
    if (!map.put(1, "firstValue")) {
      System.out.println(
          "The map.put() function returned false indicating adding this element wasn't possible");
      return false;
    }

    // tests if containsKey find the key added
    if (!map.containsKey(1)) {
      System.out.println("map.containKey() did not find a known element");
      return false;
    }

    // tests if containsKey() finds an index that does not exist
    if (map.containsKey(2)) {
      System.out.println("map.containKey() found unknown element");
      return false;
    }

    // makes sure false is returned when duplicate keys are entered
    if (map.put(1, "secondValue")) {
      System.out.println("map.containKey() returned true to adding a duplicate");
      return false;
    }

    // makes sure size remains at 1`
    if (map.size() != 1) {
      System.out.println("map.put() added a duplicate");
      return false;
    }

    return true;
    }catch(Exception e) {
      System.out.println("Error was thrown in test1()");
      return false;
    }
  }

  /**
   * Tests the get() and remove() methods of HashtableMap
   * 
   * @return true if test passes, false otherwise
   */
  public static boolean test2() {
    try {
    HashtableMap<Integer, String> map = new HashtableMap<>();
    // tests if the correct exception is thrown
    try {
      map.get(null);
      System.out.println("get() did not throw an error");
      return false;
    } catch (NoSuchElementException e) {

    } catch (Exception e) {
      System.out.println("get() threw the wrong exception");
      return false;
    }
    // adds duplicate entries
    map.put(1, "firstValue");
    map.put(1, "secondValue");

    // makes sure only first value stored
    if (!map.get(1).equals("firstValue")) {
      System.out.println("map.get() returned the wrong value");
      return false;
    }

    // removes the key and adds it back with a new value
    map.remove(1);
    map.put(1, "secondValue");
    if (!map.get(1).equals("secondValue")) {
      System.out.println("map.remove() did nto remove a value");
      return false;
    }

    // creates a new map to check remove() function
    HashtableMap<Integer, String> map1 = new HashtableMap<>();
    map1.put(1, "test1");
    map1.put(2, "test2");

    map1.remove(1);
    // checks the size after remove
    if (map1.size() != 1) {
      System.out.println("Incorrect size after remove");
      return false;
    }

    try {
      // tries to get an index that has been removed, should throw NoSuchElementException
      map1.get(1);
      System.out.println("get() did not throw an error");
      return false;
    } catch (NoSuchElementException e) {

    } catch (Exception e) {
      System.out.println("get() threw the wrong exception");
      return false;
    }
   
    HashtableMap<String, String> map2 = new HashtableMap<>();
    // both of these keys have the same hashcode value, therefore must chain
    map2.put("Aa", "value1");
    map2.put("BB", "value2");
    
    String key = "Aa";
    // chekcks if chaining actually occurs
    int index = Math.abs(key.hashCode() % map2.hashTable.length);
   if(!map2.hashTable[index].getNext().getValue().equals("value2")){
     return false;
   }
    
    return true;
    }catch(Exception e) {
      System.out.println("Error was thrown in test2()" );
      return false;
    }
  }

  /**
   * Tests the reHash functionality of the HashtableMap array
   * 
   * @return true if test passes, false otherwise
   */
  public static boolean test3() {
    try {
    HashtableMap<Integer, String> map = new HashtableMap<>(2);
    map.put(1, "test1");
    // should rehash after adding next variable
    map.put(2, "test2");
    // checks capacity doubled
    if(map.hashTable.length != 4) {
      System.out.println("Rehashing did not occur correctly");
      return false;
    }
    map.put(3, "test3");
    map.put(4, "test4");

    if (!map.get(1).equals("test1")) {
      System.out.println("HashTable did not rehash correctly");
      return false;
    }
    if (!map.get(2).equals("test2")) {
      System.out.println("HashTable did not rehash correctly");
      return false;
    }
    if (!map.get(3).equals("test3")) {
      System.out.println("HashTable did not rehash correctly");
      return false;
    }
    if (!map.get(4).equals("test4")) {
      System.out.println("HashTable did not rehash correctly");
      return false;
    }
    // checks size
    if (map.size() != 4) {
      System.out.println("Incorrect size when rehashing");
      return false;
    }
    return true;
    }catch(Exception e) {
      System.out.println("Error was thrown in test3()");
      return false;
    }

  }

  /**
   * Tests the if the clear() function works
   * 
   * @return true if test passes, false otherwise
   */
  public static boolean test4() {
    try {
    HashtableMap<Integer, Integer> map = new HashtableMap<>();
    map.put(1, 2);
    map.put(2, 4);
    map.put(3, 6);
    if (map.size() != 3) {
      System.out.println("Incorrect map size before clear");
      return false;
    }

    // clears map and checks to see if its reset
    map.clear();
    if (map.size() != 0) {
      System.out.println("Incorrect map size after clear");
      return false;
    }
    try {
      map.get(1);
      System.out.println("get() did not throw an error");
      return false;
    } catch (NoSuchElementException e) {

    } catch (Exception e) {
      System.out.println("get() threw the wrong exception");
      return false;
    }
    return true;
    }catch(Exception e) {
      System.out.println("Error was thrown in test4()");
      return false;
    }
  }

  /**
   * Checks that chaining occurs correctly and the constructors work
   * 
   * @return true if test passes, false otherwise
   */
  public static boolean test5() {
    try {
    // checks if constructor worked with no args
    HashtableMap<Integer, Integer> map = new HashtableMap<>();

    if (map.size() != 0 || map.hashTable.length != 15) {
      System.out.println(
          "Hashtable had the wrong size or capacity after a constructor with no arguments was "
          + "passed");
      return false;
    }
    
    // checks if constructor worked with args
    HashtableMap<Integer, Integer> map1 = new HashtableMap<>(38);

    if (map1.size() != 0 || map1.hashTable.length != 38) {
      System.out.println(
          "Hashtable had the wrong size or capacity after a constructor with arguments was "
          + "passed");
      return false;
    }
    
    return true;
    }catch(Exception e) {
      System.out.println("Error was thrown in test5()");
      return false;
    }

  }



}
