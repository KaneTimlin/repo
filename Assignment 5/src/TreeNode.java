public class TreeNode<T> {
    TreeNode<T> leftNode;
    TreeNode<T> rightNode;
    T data;

    public TreeNode(T data) {
        this.data = data;
    }

    public TreeNode(TreeNode<T> leftNode, TreeNode<T> rightNode, T data) {
        this.leftNode = leftNode;
        this.rightNode = rightNode;
        this.data = data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setLeftNode(TreeNode<T> leftNode) {
        this.leftNode = leftNode;
    }

    public void setRightNode(TreeNode<T> rightNode) {
        this.rightNode = rightNode;
    }

    public T getData() {
        return data;
    }

    public TreeNode<T> getLeftNode() {
        return leftNode;
    }

    public TreeNode<T> getRightNode() {
        return rightNode;
    }
}

