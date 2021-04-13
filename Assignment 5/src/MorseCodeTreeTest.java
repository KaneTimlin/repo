import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MorseCodeTreeTest {

    MorseCodeTree<String> tree;

    @BeforeEach
    void setUp() { // using the test constructor that will not run buildTree
        tree = new MorseCodeTree<>("Testing");
    }

    @AfterEach
    void tearDown() {
        tree = null;
    }

    @Test
    void insertTest() {
        String[] letters = {"", "e", "t", "i", "a", "n", "m", "s", "u", "r", "w", "d", "k", "g", "o",
                "h", "v", "f"};
        String[] codes = {"", ".", "-", "..", ".-", "-.", "--", "...", "..-", ".-.", ".--", "-..", "-.-",
                "--.", "---", "....", "...-", "..-."};
        for (int i = 0; i < letters.length; i++) {
            tree.insert(codes[i], letters[i]);
        }
        TreeNode<String> root = tree.getRoot();
        assertEquals("", root.getData());
        assertEquals("i", root.leftNode.leftNode.getData());
        assertEquals("m", root.rightNode.rightNode.getData());
    }

    @Test
    void fetchTest() {
        tree.buildTree();
        assertEquals("v", tree.fetch("...-"));
        assertEquals("e", tree.fetch("."));
        assertEquals("m", tree.fetch("--"));
        assertEquals("y", tree.fetch("-.--"));
    }

    @Test
    void deleteTest() {
        tree.buildTree();
        try {
            tree.delete("t");
            fail("The method should have thrown an exception");
        } catch (UnsupportedOperationException e) {
            assertTrue(true);
        }
    }

    @Test
    void updateTest() {
        tree.buildTree();
        try {
            tree.update();
            fail("The method should have thrown an exception");
        } catch (UnsupportedOperationException e) {
            assertTrue(true);
        }
    }

    @Test
    void buildTreeTest() {
        TreeNode<String> root;
        tree.buildTree();
        root = tree.getRoot();
        assertEquals("", root.getData());
        assertEquals("i", root.leftNode.leftNode.getData());
    }
}