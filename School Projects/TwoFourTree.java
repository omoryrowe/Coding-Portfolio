//Omory Rowe
public class TwoFourTree {
  private class TwoFourTreeItem {
    int values = 1;
    int value1 = 0; // always exists.
    int value2 = 0; // exists iff the node is a 3-node or 4-node.
    int value3 = 0; // exists iff the node is a 4-node.
    boolean isLeaf = true; // code for this // during the 4 node split for insert
    
    TwoFourTreeItem parent = null; // parent exists iff the node is not root.
    TwoFourTreeItem leftChild = null; // left and right child exist iff the note is a non-leaf.
    TwoFourTreeItem rightChild = null;
    TwoFourTreeItem centerChild = null; // center child exists iff the node is a non-leaf 3-node.
    TwoFourTreeItem centerLeftChild = null; // center-left and center-right children exist iff the node is a non-leaf 4-node.
    TwoFourTreeItem centerRightChild = null;
    
    public boolean isTwoNode() {
      return values == 1;
    }
    
    public boolean isThreeNode() {
      return values == 2;
    }
    
    public boolean isFourNode() {
      return values == 3;
    }
    
    public boolean isRoot() {
      return (parent == null);
    }
    
    public TwoFourTreeItem(int value1) {
      this.values = 1;
      this.value1 = value1;
    }
    
    public TwoFourTreeItem(int value1, int value2) {
      this.values = 2;
      this.value1 = value1;
      this.value2 = value2;
    }
    
    public TwoFourTreeItem(int value1, int value2, int value3) {
      this.values = 3;
      this.value1 = value1;
      this.value2 = value2;
      this.value3 = value3;
    }
    
    private void printIndents(int indent) {
      for (int i = 0; i < indent; i++)
      System.out.printf("  ");
    }
    
    public void printInOrder(int indent) {
      if (!isLeaf)
      leftChild.printInOrder(indent + 1);
      printIndents(indent);
      System.out.printf("%d\n", value1);
      if (isThreeNode()) {
        if (!isLeaf)
        centerChild.printInOrder(indent + 1);
        printIndents(indent);
        System.out.printf("%d\n", value2);
      } else if (isFourNode()) {
        if (!isLeaf)
        centerLeftChild.printInOrder(indent + 1);
        printIndents(indent);
        System.out.printf("%d\n", value2);
        if (!isLeaf)
        centerRightChild.printInOrder(indent + 1);
        printIndents(indent);
        System.out.printf("%d\n", value3);
      }
      if (!isLeaf)
      rightChild.printInOrder(indent + 1);
    }
    
    //what it does: this function checks whether the current node is a leaf or not.
    //if it is not a leaf, it recurses the add function to the next level in the
    //tree based on the value being inserted. if it is a leaf, it checks what kind
    //of node it is and inserts based on that. If it encounters a 4 node along the way, it gets split.
    //what it accepts: it takes in the value being inserted into the tree.
    //what it returns: this returns false once the value is successfully inserted.
    //what can go wrong: the large amount of if statements may lead to a slower run time.
    public boolean add(int value) {
      if (isLeaf) {
        if (isTwoNode()) { // 2-node
          if (value == value1) {
            return false; // Value already exists in the tree
          } else if (value < value1) {
            value2 = value1;
            value1 = value;
          } else {
            value2 = value;
          }
          values = 2;
          return false;
        } else if (isThreeNode()) { // 3-node
          if (value == value1 || value == value2) {
            return false; // Value already exists in the tree
          } else if (value < value1) {
            value3 = value2;
            value2 = value1;
            value1 = value;
          } else if (value > value1 && value < value2) {
            value3 = value2;
            value2 = value;
          } else {
            value3 = value;
          }
          values = 3;
          return false;
        } else if (isFourNode()) { // 4-node
          return splitFourNode(value); // Split the node and insert value
        }
      } else { // Non-leaf node
        if (values == 1) { // 2-node
          if (value < value1) {
            return leftChild.add(value);
          } else {
            return rightChild.add(value);
          }
        } else if (values == 2) { // 3-node
          if (value < value1) {
            return leftChild.add(value);
          } else if (value > value1 && value < value2) {
            return centerChild.add(value);
          } else {
            return rightChild.add(value);
          }
        } else if (values == 3) { // 4-node
          return splitFourNode(value);
        }
      }
      return false; // Value added successfully
    }
    
    //what it does: this function splits the current 4 node to rebalance the tree for insertion.
    //what it accepts: it takes in the value being inserted into the tree.
    //what it returns: this returns the add function after the tree is rebalanced to continue insertion.
    //what can go wrong: too many calls of this function may result in a value being lost if there is a plethora of 3 nodes in the tree.
    private boolean splitFourNode(int value) {
      if (this.isRoot()) { //Root node
        if(root.isLeaf){
          TwoFourTreeItem newRoot = new TwoFourTreeItem(value2);
          TwoFourTreeItem left = new TwoFourTreeItem(value1);
          TwoFourTreeItem right = new TwoFourTreeItem(value3);
          newRoot.leftChild = left;
          newRoot.rightChild = right;
          left.parent = newRoot;
          right.parent = newRoot;
          root.value2 = 0;
          root.value3 = 0;
          newRoot.isLeaf = false;
          root = newRoot;
          newRoot.values = 1;
          return newRoot.add(value);
        } else {
          TwoFourTreeItem newRoot = new TwoFourTreeItem(value2);
          TwoFourTreeItem left = new TwoFourTreeItem(value1);
          TwoFourTreeItem right = new TwoFourTreeItem(value3);
          
          newRoot.values = 1;
          newRoot.value2 = 0;
          newRoot.value3 = 0;
          newRoot.isLeaf = false;
          
          left.values = 1;
          left.value2 = 0;
          left.value3 = 0;
          left.isLeaf = false;
          
          right.values = 1;
          right.value2 = 0;
          right.value3 = 0;
          right.isLeaf = false;
          
          newRoot.leftChild = left;
          newRoot.rightChild = right;
          left.parent = newRoot;
          right.parent = newRoot;
          
          newRoot.leftChild.leftChild = root.leftChild;
          root.leftChild.parent = newRoot.leftChild;
          newRoot.leftChild.rightChild = root.centerLeftChild;
          root.centerLeftChild.parent = newRoot.leftChild;
          
          newRoot.rightChild.leftChild = root.centerRightChild;
          root.centerRightChild.parent = newRoot.rightChild;
          newRoot.rightChild.rightChild = root.rightChild;
          root.rightChild.parent = newRoot.rightChild;
          
          root = newRoot;
          return root.add(value);
        }
      } else {
        TwoFourTreeItem newNode = new TwoFourTreeItem(value2);
        TwoFourTreeItem left = new TwoFourTreeItem(value1);
        TwoFourTreeItem right = new TwoFourTreeItem(value3);
        
        if (parent.isTwoNode()) {
          if(this.isLeaf){
            parent.values = 2;
            parent.value2 = value2;
            parent.centerChild = left;
            parent.centerChild.parent = parent;
            parent.rightChild = right;
            parent.rightChild.parent = parent;
          } else {
            parent.values = 2;
            TwoFourTreeItem newCenter = new TwoFourTreeItem(value1);
            newCenter.rightChild = this.centerLeftChild;
            this.centerLeftChild.parent = newCenter;
            newCenter.leftChild = this.leftChild;
            this.leftChild.parent = newCenter;
            this.isLeaf = false;
            newCenter.isLeaf = false;
            
            value1 = 0;
            value1 = value3;
            parent.value2 = value2;
            value2 = 0;
            value3 = 0;
            this.isLeaf = false;
            this.values = 1;
            
            this.leftChild = this.centerRightChild;
            this.centerRightChild.parent = this.leftChild;
            
            parent.centerChild = newCenter;
            parent.rightChild = this;
          }
        } else if (parent.isThreeNode()) {
          if(isLeaf){
            parent.values = 3;
            parent.value3 = value2;
            parent.centerLeftChild = parent.centerChild;
            parent.centerLeftChild.parent = parent;
            parent.centerRightChild = left;
            parent.centerRightChild.parent = parent;
            parent.rightChild = right;
            parent.rightChild.parent = parent;
            this.isLeaf = false;
          }	else {
            TwoFourTreeItem moveNewCL = parent.centerChild;
            parent.values = 3;
            parent.centerLeftChild = moveNewCL;
            moveNewCL.parent = parent;
            parent.value3 = value2;
            value2 = 0;
            
            parent.centerRightChild = left;
            parent.centerRightChild.values = 1;
            parent.centerRightChild.isLeaf = false;
            parent.centerRightChild.leftChild = this.leftChild;
            this.leftChild.parent = parent.centerRightChild;
            parent.centerRightChild.rightChild =this.centerLeftChild;
            this.centerLeftChild.parent = parent.centerRightChild;
            
            value1 = value3;
            value3 = 0;
            TwoFourTreeItem moveNewRL = this.centerRightChild;
            this.values = 1;
            
            this.leftChild = moveNewRL;
            moveNewRL.parent = this;            
          }
          
        }	else if (parent.isFourNode()){
          //Start
          TwoFourTreeItem newerNode = new TwoFourTreeItem(parent.value2);
          TwoFourTreeItem newLeft = new TwoFourTreeItem(parent.value1);
          TwoFourTreeItem newRight = new TwoFourTreeItem(parent.value3);
          newerNode.values = 1;
          newerNode.value2 = 0;
          newerNode.value3 = 0;
          newerNode.isLeaf = false;
          
          newLeft.values = 1;
          newLeft.value2 = 0;
          newLeft.value3 = 0;
          newLeft.isLeaf = false;
          
          newRight.values = 1;
          newRight.value2 = 0;
          newRight.value3 = 0;
          newRight.isLeaf = false;
          
          //Setup
          newerNode.leftChild = newLeft;
          newLeft.parent = newerNode;
          newerNode.rightChild = newRight;
          newRight.parent = newerNode;
          
          //Left
          newLeft.leftChild = parent.leftChild;
          parent.leftChild.parent = newLeft;
          
          newLeft.rightChild = parent.centerLeftChild;
          parent.centerLeftChild.parent = newLeft;
          
          //Right
          newRight.leftChild = parent.centerRightChild;
          parent.centerRightChild.parent = newRight;
          
          newRight.rightChild = parent.rightChild;
          parent.rightChild.parent = newRight;
          
          //Complete
          root = newerNode;
          return root.add(value);
        }
        if (value < value1) {
          return leftChild.add(value);
        } else if (value > value1 && value < value2) {
          return centerLeftChild.add(value);
        } else if (value > value2 && value < value3) {
          return centerRightChild.add(value);
        } else {
          return parent.rightChild.add(value);
        }
      }
    }
    
    //what it does: this function checks to see if a value is inside of the tree.
    //what it accepts: it takes an int value.
    //what it returns: it returns true if the value is in the tree or false if it isn't.
    //what can go wrong: not much. if the value isn't found, false is returned.
    public boolean checkValue(int value) {
      if (isTwoNode()) {
        if (value == value1) {
          return true;
        } else if (value < value1) {
          if (leftChild != null) {
            return leftChild.checkValue(value);
          }
        } else {
          if (rightChild != null) {
            return rightChild.checkValue(value);
          }
        }
      } else if (isThreeNode()) {
        if (value == value1 || value == value2) {
          return true;
        } else if (value < value1) {
          if (leftChild != null) {
            return leftChild.checkValue(value);
          }
        } else if (value > value1 && value < value2) {
          if (centerChild != null) {
            return centerChild.checkValue(value);
          }
        } else {
          if (rightChild != null) {
            return rightChild.checkValue(value);
          }
        }
      } else if (isFourNode()) {
        if (value == value1 || value == value2 || value == value3) {
          return true;
        } else if (value < value1) {
          if (leftChild != null) {
            return leftChild.checkValue(value);
          }
        } else if (value > value1 && value < value2) {
          if (centerLeftChild != null) {
            return centerLeftChild.checkValue(value);
          }
        } else if (value > value2 && value < value3) {
          if (centerRightChild != null) {
            return centerRightChild.checkValue(value);
          }
        } else {
          if (rightChild != null) {
            return rightChild.checkValue(value);
          }
        }
      }
      
      return false;
    }
    
    
    private boolean delete(int value) {
      return false;
    }
  }
  
  TwoFourTreeItem root = null;
  
  //what it does: this function calls the add function if the root isn't null. If the root is null, it directly inserts the value into the tree as a 2 node.
  //what it accepts: it takes an int value.
  //what it returns: this returns false once the value is inserted successfully.
  //what can go wrong: not much. a nother function is called if the root isn't null.
  public boolean addValue(int value) {
    if (root == null) {
      root = new TwoFourTreeItem(value);
      return true;
    } else {
      return root.add(value);
    }
  }
  
  //what it does: this function checks to see if a value is inside of the tree if the root isn't null.
  //what it accepts: it takes an int value.
  //what it returns: it returns true if the value is in the tree or false if it isn't.
  //what can go wrong: the function simply returns true or false after traversing the tree recursively to the needed value.
  public boolean hasValue(int value) {
    if(root == null)
    return false;
    if(root.checkValue(value))
    return true;
    return false;
  }
  
  public boolean deleteValue(int value) {
    if (root == null){
      return false;
    } else {
      return root.delete(value);
    }
  }
  
  public void printInOrder() {
    if (root != null)
    root.printInOrder(0);
  }
  
  public TwoFourTree() {
    root = null;
  }
}