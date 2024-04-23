import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.random.RandomGenerator;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Random;
import java.lang.Math;

public class SkipListSet<T extends Comparable<T>> implements SortedSet<T> {
  private int maxHeight = 4;
  ArrayList<LinkedList<T>> list;
  
  public int getMaxHeight(){
    return maxHeight;
  }
  
  public void setMaxHeight(int height){
    this.maxHeight = height;
  }
  
  public class Node<T extends Comparable<T>> {
    
    private T data;
    private Node<T> next;
    
    public Node(T data){
      this.data = data;
    }
    
    public T getData() {
      return data;
    }
    
    public void setData(T data) {
      this.data = data;
    }
    
    public Node<T> getNextNode() {
      return next;
    }
    
    public void setNextNode(Node<T> next) {
      this.next = next;
    }
  }
  
  public class LinkedList<T extends Comparable<T>> {
    
    private Node<T> head;
    private int listHeight;
    
    public LinkedList(){
      head = new Node<T>(null);
    }
    
    public Node<T> getHead() {
      return head;
    }
    
    public void setListHeight(int height){
      listHeight = height;
    }
    
    public int getListHeight(){
      return listHeight;
    }
    
    public void insert(T data){
      Node<T> newNode = new Node<T>(data);
      Node<T> currentNode = head;
      while(currentNode.getNextNode() != null){
        currentNode = currentNode.getNextNode();
      }
      currentNode.setNextNode(newNode);
    }
    
    public void insertAt(int index, T data){
      Node<T> nodeToBeInserted = new Node<T>(data);
      Node<T> node = head;
      for(int i = 0; i < index - 1; i++){
        node = node.getNextNode();
      }
      //System.out.println("NODEEE: " + node.getData());
      nodeToBeInserted.setNextNode(node.getNextNode());
      node.setNextNode(nodeToBeInserted);
    }
    
    public void deleteNodeAt(int index){
      Node<T> node = head;
      for(int i = 0; i< index -1; i++){
        node = node.getNextNode();
      }
      node.setNextNode(node.getNextNode().getNextNode());
    }
    
    public void display(){
      Node<T> currentNode = head.getNextNode();
      if(currentNode.getData() != null)
      System.out.print("[");
      while(currentNode.getNextNode() != null){
        System.out.print(currentNode.getData() + ",");
        if(currentNode.getData() != null)
        currentNode = currentNode.getNextNode();
      }
      if(currentNode.getData() != null)
      System.out.print(currentNode.getData() + "]");
    }
  }
  
  public SkipListSet() {
    list = new ArrayList<LinkedList<T>>();
    for(int i = 0; i < maxHeight; i++){
      list.add(new LinkedList());
    }
  }
  
  private static int getRandomHeight() {//fix the height probability
    Random random = new Random();
    int height = 1; // Minimum height is always 1
    double rand = random.nextDouble();
    
    for (int h = 2; h <= 8; h++) { //.getMaxHeight()
      double probability = Math.pow(0.5, h - 1);
      if (rand < probability) {
        height = h;
        break;
      }
    }
    
    return height;
  }
  
  class SkipListSetIterator<T extends Comparable<T>> implements Iterator<T> {
    
    @Override
    public boolean hasNext() {
      
      return false;
    }
    
    @Override
    public T next() {
      
      return null;
    }
    
  }
  
  @Override
  public int size() {//DONE
    //count all the nodes using a traversal
    //if node == null, don't increment the size
    int count = 0;
    //loops through the levels
    for(int i = 0; i < maxHeight; i++){
      //loops through each linked list
      while(list.get(i).getHead().getNextNode() != null)
      count++;
    }
    return count;
  }
  
  @Override
  public boolean isEmpty() {//DONE
    if(list.get(0).getHead().getNextNode() == null){
      return true;
    }
    return false;
  }
  
  @Override
  public boolean contains(Object o) {//DONE
    Node<T> temp;
    for(int i = 0; i < maxHeight; i++){
      temp = list.get(i).getHead();
      while(temp.getNextNode() != null)
      if(temp.getNextNode().getData() == o){
        return true;
      }else{
        temp = temp.getNextNode();
      }
    }
    return false;
  }
  
  @Override
  public Iterator<T> iterator() { //THIS IS THE SEARCH
    
    return null;
  }
  
  @Override
  public Object[] toArray() {
    
    return null;
  }
  
  @Override
  public <T> T[] toArray(T[] a) {
    
    return null;
  }
  
  @Override
  public boolean add(T e) {
    Node<T> temp = list.get(0).getHead().getNextNode();
    //if skip list is empty
    if(isEmpty()){
      list.get(0).insert(e);
      //Height Randomization
      return true;
    }
    //find the spot the node should go
    int index = 1;
    while(temp.getNextNode() != null){
      if(temp.getData().compareTo(e) <= 0 && ((temp.getNextNode().getData().compareTo(e) >= 0 || temp.getNextNode() == null))){
        list.get(0).insertAt(index + 1, e);
        //Height Randomization
        // this.setListHeight(getRandomHeight());
        // System.out.println("Height of " + data + ": " + this.getListHeight());
        return true;
      } else {
        temp = temp.getNextNode();
        index++;
      }
    }
    //index++;
    if(temp.getData().compareTo(e) <= 0){
      list.get(0).insertAt(index + 1, e);
      //Height Randomization
      return true;
    }else{
      list.get(0).insertAt(index, e);
      //Height Randomization
      return true;
    }
  }
  
  @Override
  public boolean remove(Object o) {
    return false;
  }
  
  @Override
  public boolean containsAll(Collection<?> c) {//DONE
    for(Object item : c){
      if(this.contains(item) == false)
      return false;
    }
    return true;
  }
  
  @Override
  public boolean addAll(Collection<? extends T> c) {//DONE
    for(T obj : c){
      this.add(obj);
    }
    return false;
  }
  
  @Override
  public boolean retainAll(Collection<?> c) {
    ArrayList<T> retain = new ArrayList<T>();
    //Stores items from the collection into the array
    for(Object obj : c){
      retain.add(obj);
    }
    //removes items that aren't in the retain list
    //cannot complete until remove is finished
    return false;
  }
  
  @Override
  public boolean removeAll(Collection<?> c) {
    //cannot complete until remove is finished
    return false;
  }
  
  @Override
  public void clear() {
    //cannot complete until remove is finished
  }
  
  @Override
  public Comparator<? super T> comparator() {
    // @Override
    // public int compare(T t1, T t2){
      //   return t1.compareTo(t2);
      // }
      return null;
    }
    
    @Override
    public SortedSet<T> subSet(T fromElement, T toElement) {
      
      return null;
    }
    
    @Override
    public SortedSet<T> headSet(T toElement) {
      
      return null;
    }
    
    @Override
    public SortedSet<T> tailSet(T fromElement) {
      
      return null;
    }
    
    @Override
    public T first() {//DONE
      return list.get(0).getHead().getNextNode().getData();
    }
    
    @Override
    public T last() {//DONE
      Node<T> temp = list.get(0).getHead();
      while(temp.getNextNode() != null){
        temp = temp.getNextNode();
      }
      return temp.getData();
    }
    
    public void print() {//DONE
      for(int i = 0; i < maxHeight; i++){
        if(list.get(i).getHead().getNextNode() != null)
        list.get(i).display();
      }
    }
    
    public void reBalance() {
      //setMaxHeight(Math.log(list.size()) / Math.log(2));
      //ensureCapacity(int minCapacity) to grow height
    }
    
  }