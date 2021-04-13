import java.util.ArrayList;

public class MorseCodeTree<T extends String> implements LinkedConverterTreeInterface<T> {

    private TreeNode<T> root;

    public MorseCodeTree() {
        buildTree();
    }

    public MorseCodeTree(String test) {

    }

    public MorseCodeTree(TreeNode<T> root) {
        this.root = root;
    }

    /**
     * Returns a reference to the root
     * @return reference to root
     */
    @Override
    public TreeNode<T> getRoot() {
        return root;
    }

    /**
     * sets the root of the Tree
     * @param newNode a TreeNode that will be the new root
     */
    @Override
    public void setRoot(TreeNode<T> newNode) {
        root = newNode;
    }

    /**
     * Adds result to the correct position in the tree based on the code
     * This method will call the recursive method addNode
     * @param code the code for the new node to be added
     * @param data the data to be inserted
     * @return the linkedConverterTree with the new node added
     */
    @Override
    public LinkedConverterTreeInterface<T> insert(T code, T data) {
        if (root != null) {
            addNode(root, code, data);
            return new MorseCodeTree<>(root);
        } else {
            root = new TreeNode<>(data);
            return new MorseCodeTree<>(root);
        }
    }

    /**
     * This is a recursive method that adds element to the correct position
     * in the tree based on the code.
     * @param root   the root of the tree for this particular recursive instance of addNode
     * @param code   the code for this particular recursive instance of addNode
     * @param data the data of the new TreeNode to be added
     */
    @Override
    public void addNode(TreeNode<T> root, T code, T data) {
        if (code.length() == 1) {
            if (code.charAt(0) == '.') {
                root.leftNode = new TreeNode<>(data);
            } else {
                root.rightNode = new TreeNode<>(data);
            }
        } else {
            if (code.charAt(0) == '.') {
                root = root.leftNode;
                StringBuilder sb = new StringBuilder(code);
                sb.deleteCharAt(0);
                code = (T) sb.toString();
                addNode(root, code, data);
            } else if (code.charAt(0) == '-') {
                root = root.rightNode;
                StringBuilder sb = new StringBuilder(code);
                sb.deleteCharAt(0);
                code = (T) sb.toString();
                addNode(root, code, data);
            }
        }
    }

    /**
     * Fetch the data in the tree based on the code
     * This method will call the recursive method fetchNode
     * @param code the code that describes the traversals within the tree
     * @return the result that corresponds to the code
     */
    @Override
    public T fetch(T code) {
        return fetchNode(root, code);
    }

    /**
     * This is the recursive method that fetches the data of the TreeNode
     * that corresponds with the code
     * @param root the root of the tree for this particular recursive instance of addNode
     * @param code the code for this particular recursive instance of fetchNode
     * @return the data corresponding to the code
     */
    @Override
    public T fetchNode(TreeNode<T> root, T code) {
        if (code.isEmpty()) {
            return root.data;
        } else {
            if (code.charAt(0) == '.') {
                StringBuilder sb = new StringBuilder(code);
                sb.deleteCharAt(0);
                code = (T) sb.toString();
                return fetchNode(root.leftNode, code);
            } else {
                StringBuilder sb = new StringBuilder(code);
                sb.deleteCharAt(0);
                code = (T) sb.toString();
                return fetchNode(root.rightNode, code);
            }
        }
    }

    /**
     * This operation is not supported for a LinkedConverterTree
     * @param data data of node to be deleted
     * @return reference to the current tree
     * @throws UnsupportedOperationException this operation is not supported
     */
    @Override
    public LinkedConverterTreeInterface<T> delete(T data) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("This operation is not supported for a LinkedConverterTree");
    }

    /**
     * This operation is not supported for a LinkedConverterTree
     * @return reference to the current tree
     * @throws UnsupportedOperationException this operation is not supported
     */
    @Override
    public LinkedConverterTreeInterface<T> update() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("This operation is not supported for a LinkedConverterTree");
    }

    /**
     * This method builds the LinkedConverterTree by inserting TreeNodes
     * into their proper locations
     */
    @Override
    public void buildTree() {
        String[] letters = {"", "e", "t", "i", "a", "n", "m", "s", "u", "r", "w", "d", "k", "g", "o",
                            "h", "v", "f", "l", "p", "j", "b", "x", "c", "y", "z", "q"};
        String[] codes = {"", ".", "-", "..", ".-", "-.", "--", "...", "..-", ".-.", ".--", "-..", "-.-",
                        "--.", "---", "....", "...-", "..-.", ".-..", ".--.", ".---", "-...", "-..-", "-.-.",
                        "-.--", "--..", "--.-"};
        for (int i = 0; i < letters.length; i++) {
            insert((T) codes[i], (T) letters[i]);
        }
    }

    /**
     * Returns an ArrayList of the items in the linked converter Tree in LNR (Inorder) Traversal order
     * Used for testing to make sure tree is built correctly
     *
     * @return an ArrayList of the items in the linked Tree
     */
    @Override
    public ArrayList<T> toArrayList() {
        ArrayList<T> list = new ArrayList<>();
        LNRoutputTraversal(root, list);
        return list;
    }

    /**
     * The recursive method to put the contents of the linked converter tree in an ArrayList
     * LNR (Inorder)
     *
     * @param root the root of the tree for this particular recursive instance
     * @param list the ArrayList that will hold the contents of the tree in LNR order
     */
    @Override
    public void LNRoutputTraversal(TreeNode<T> root, ArrayList<T> list) {
        if (root == null) {
            return;
        }
        LNRoutputTraversal(root.leftNode, list);
        list.add(root.data);
        LNRoutputTraversal(root.rightNode, list);

    }

}
