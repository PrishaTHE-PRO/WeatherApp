public class WeatherList {
    private class Node {
        WeatherData data;
        Node next;

        Node(WeatherData data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node head;
    private Node tail;
    private int size;

    /**
     * Constructor - initializes an empty list
     */
    public WeatherList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    /**
     * Adds a WeatherData object to the end of the list
     */
    public void add(WeatherData data) {
        Node newNode = new Node(data);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    /**
     * Gets WeatherData at specified index
     */
    public WeatherData get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        Node current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.data;
    }

    /**
     * Returns the size of the list
     */
    public int size() {
        return size;
    }

    /**
     * Checks if the list is empty
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears the list
     */
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Converts list to array
     */
    public WeatherData[] toArray() {
        WeatherData[] array = new WeatherData[size];
        Node current = head;
        int index = 0;
        while (current != null) {
            array[index++] = current.data;
            current = current.next;
        }
        return array;
    }

    /**
     * Removes element at specified index
     */
    public WeatherData remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        
        WeatherData removedData;
        
        //removing first element
        if (index == 0) {
            removedData = head.data;
            head = head.next;
            
            if (head == null) {
                tail = null;
            }
        } else {
            Node current = head;
            for (int i = 0; i < index - 1; i++) {
                current = current.next;
            }
            
            removedData = current.next.data;
            current.next = current.next.next;
            
            if (current.next == null) {
                tail = current;
            }
        }
        
        size--;
        return removedData;
    }

    /**
     * Finds the index of a city in the list
     */
    public int indexOf(String cityName) {
        Node current = head;
        int index = 0;
        
        while (current != null) {
            if (current.data.getCityName().equalsIgnoreCase(cityName)) {
                return index;
            }
            current = current.next;
            index++;
        }
        
        return -1; 
    }

    /**
     * Checks if a city exists in the list
     */
    public boolean contains(String cityName) {
        return indexOf(cityName) != -1;
    }
}