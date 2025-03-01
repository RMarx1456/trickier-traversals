import java.util.*;

public class Traversals {

  /**
   * Returns the sum of the values of all leaf nodes in the given tree of integers.
   * A leaf node is defined as a node with no children.
   * If node is null, this method returns 0.
   *
   * @param node the node of the tree
   * @return the sum of leaf node values, or 0 if the tree is null
   */
  public static int sumLeafNodes(TreeNode<Integer> node) {
    if(node == null) {
      return 0;
    }


    int ctr = 0;

    Queue<TreeNode<Integer>> BFSqueue = new LinkedList<>();

    BFSqueue.add(node);

    TreeNode<Integer> current;
    while(!BFSqueue.isEmpty()) {
      current = BFSqueue.poll();

      if(current.left == null && current.right == null) {
        ctr += current.value;
      }
      else if(current.left != null && current.right == null) {
        BFSqueue.add(current.left);
      }
      else if(current.right != null && current.left == null) {
        BFSqueue.add(current.right);
      }
      else {
        BFSqueue.add(current.left);
        BFSqueue.add(current.right);
      }
    }



    return ctr;
  }

  /**
   * Counts the number of internal nodes (non-leaf nodes) in the given tree of integers.
   * An internal node has at least one child.
   * If node is null, this method returns 0.
   *
   * @param node the node of the tree
   * @return the count of internal nodes, or 0 if the tree is null
   */
  public static int countInternalNodes(TreeNode<Integer> node) {
    if(node == null) {
      return 0;
    }


    int ctr = 0;

    Queue<TreeNode<Integer>> BFSqueue = new LinkedList<>();

    BFSqueue.add(node);

    TreeNode<Integer> current;
    while(!BFSqueue.isEmpty()) {
      current = BFSqueue.poll();

      if(current.left == null && current.right == null) {
      }
      else if(current.left != null && current.right == null) {
        ctr++;
        BFSqueue.add(current.left);
      }
      else if(current.right != null && current.left == null) {
        ctr++;
        BFSqueue.add(current.right);
      }
      else {
        ctr++;
        BFSqueue.add(current.left);
        BFSqueue.add(current.right);
      }
    }



    return ctr;
  }

  /**
   * Creates a string by concatenating the string representation of each node's value
   * in a post-order traversal of the tree. For example, if the post-order visitation
   * encounters values "a", "b", and "c" in that order, the result is "abc".
   * If node is null, returns an empty string.
   *
   * @param node the node of the tree
   * @param <T>  the type of values stored in the tree
   * @return a post-order traversal string, or an empty string if the tree is null
   */
  public static <T> String buildPostOrderString(TreeNode<T> node) {
    String ret = "";

    if(node == null) {
      return ret;
    }


    Stack<TreeNode<T>> nodeStack = new Stack<>();

    nodeStack.add(node);
    TreeNode<T> current;
    while(!nodeStack.isEmpty()) {
      current = nodeStack.pop();
      //I'm extremely iffy on this line below. It originally was ret += current.value,
      //but the ordering was backwards. It "works" because the test case is passed regardless,
      //but if there's any polymorphism between the nodes beyond its value, say, 
      //a set of results from traversing this tree depends on a method call that has its
      //functionality rely on the value in the node, but the side effects must happen in a specific
      //order, I see a huge problem there.
      ret = current.value + ret;

      if(current.left != null) {
        nodeStack.push(current.left);
      }
      if(current.right != null) {
        nodeStack.push(current.right);
      }
    }

    return ret;
  }

  /**
   * Collects the values of all nodes in the tree level by level, from top to bottom.
   * If node is null, returns an empty list.
   *
   * @param node the node of the tree
   * @param <T>  the type of values stored in the tree
   * @return a list of node values in a top-to-bottom order, or an empty list if the tree is null
   */
  public static <T> List<T> collectLevelOrderValues(TreeNode<T> node) {
    if(node == null) {
      List<T> ret = Collections.emptyList();
      return ret;
    }
    List<T> ret = new ArrayList<>();
    Queue<TreeNode<T>> que = new LinkedList<>();

    TreeNode<T> ptr = node;

    que.add(ptr);

    while(!que.isEmpty()) {
      ptr = que.remove();

      ret.add(ptr.value);

      if(ptr.left != null) {
        que.add(ptr.left);
      }
      if(ptr.right != null) {
        que.add(ptr.right);
      } 
    }
    return ret;
  }

  /**
   * Counts the distinct values in the given tree.
   * If node is null, returns 0.
   *
   * @param node the node of the tree
   * @return the number of unique values in the tree, or 0 if the tree is null
   */
  public static int countDistinctValues(TreeNode<Integer> node) {
    if(node == null) {
      return 0;
    }
    int ret = 0;
    Set<Integer> memoize = new HashSet<Integer>();

    Queue<TreeNode<Integer>> que = new LinkedList<>();

    TreeNode<Integer> ptr = node;

    que.add(ptr);

    while(!que.isEmpty()) {
      ptr = que.remove();

      if(!memoize.contains(ptr.value)) {
        ret++;
        memoize.add(ptr.value);
      }
      

      if(ptr.left != null) {
        que.add(ptr.left);
      }
      if(ptr.right != null) {
        que.add(ptr.right);
      } 
    }
    return ret;
  }

  /**
   * Determines whether there is at least one root-to-leaf path in the tree
   * where each successive node's value is strictly greater than the previous node's value.
   * If node is null, returns false.
   *
   * @param node the node of the tree
   * @return true if there exists a strictly increasing root-to-leaf path, false otherwise
   */
  public static boolean hasStrictlyIncreasingPath(TreeNode<Integer> node) {
    if(node == null) {
      return false;
    }
    if(node.left != null && node.right != null) {
      return true;
    }
    boolean ret = false;

    Queue<Integer> comparisonValue = new LinkedList<>(); //Stores the value of the parent.
    Queue<TreeNode<Integer>> que = new LinkedList<>(); //Stores the actual nodes for the search.
    
    TreeNode<Integer> ptr = node;
    que.add(ptr);

    comparisonValue.add(ptr.value);
    while(!que.isEmpty() && !ret) {
      ptr = que.remove();
      int num = comparisonValue.remove();

      if(ptr.left.value < ptr.value && ptr.right.value < ptr.value) {
        if(que.isEmpty()) {
          break;
        }
        continue;
      }

      if(ptr.left == null && ptr.right == null) {
        ret = true;
        break;
      }


      if(ptr.left.value > num) {
        comparisonValue.add(ptr.left.value);
        que.add(ptr.left);
      }
      if(ptr.right.value > num) {
        comparisonValue.add(ptr.right.value);
        que.add(ptr.right);
      }
    }


    return ret;
  }

  // OPTIONAL CHALLENGE
  /**
   * Checks if two trees have the same shape. Two trees have the same shape
   * if they have exactly the same arrangement of nodes, irrespective of the node values.
   * If both trees are null, returns true. If one is null and the other is not, returns false.
   *
   * @param nodeA the node of the first tree
   * @param nodeB the node of the second tree
   * @param <T>   the type of values stored in the trees
   * @return true if the trees have the same shape, false otherwise
   */
  public static <T> boolean haveSameShape(TreeNode<T> nodeA, TreeNode<T> nodeB) {
    return false;
  }


  // OPTIONAL CHALLENGE
  // Very challenging!
  // Hints:
  // List.copyOf may be helpful
  // Consider adding a helper method
  // Consider keeping the current path in a separate variable
  // Consider removing the current node from the current path after the node's subtrees have been traversed.
  /**
   * Finds all paths from the root to every leaf in the given tree.
   * Each path is represented as a list of node values from root to leaf.
   * The paths should be added pre-order.
   * If node is null, returns an empty list.
   * 
   * Example:
   *
   *         1
   *        / \
   *       2   3
   *      / \    \
   *     4   5    6
   * 
   * Expected output:
   *   [[1, 2, 4], [1, 2, 5], [1, 3, 6]]
   * 
   * @param node the root node of the tree
   * @return a list of lists, where each inner list represents a root-to-leaf path in pre-order
   */
  public static <T> List<List<T>> findAllRootToLeafPaths(TreeNode<T> node) {
    return null;
  }
}
