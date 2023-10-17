import org.junit.jupiter.api.Test;
import ru.nsu.kozorez.Main;
import ru.nsu.kozorez.Tree;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

public class TreeTest {
    @Test
    void checkMain() {
        Main.main(new String[]{});
        assertTrue(true);
    }

    @Test
    public void testAddNode() {
        Tree<String> tree = new Tree<>("R");
        Tree<String> child = tree.addChild("A");
        assertEquals("A", child.getData());
        assertEquals(1, tree.getChildren().size());
    }

    @Test
    public void testAddTree() {
        Tree<String> tree = new Tree<>("R1");
        var a = tree.addChild("A");
        var b = a.addChild("B");
        Tree<String> subtree = new Tree<>("R2");
        subtree.addChild("C");
        subtree.addChild("D");
        tree.addChild(subtree);
        b.remove();

        Tree<String> expectedTree = new Tree<>("R1");
        expectedTree.addChild("A");
        expectedTree.addChild("R2");

        expectedTree.getChildren().get(1).addChild("C");
        expectedTree.getChildren().get(1).addChild("D");
        assertEquals(expectedTree.toString(), tree.toString());
    }

    @Test
    public void testRemove() {
        Tree<String> tree = new Tree<>("R");
        Tree<String> child = tree.addChild("A");
        child.remove();
        assertEquals(0, tree.getChildren().size());
    }

    @Test
    public void testIterator() {
        Tree<String> tree = new Tree<>("R");
        tree.addChild("A");
        tree.addChild("B");
        Iterator<Tree<String>> iterator = tree.iterator();
        assertTrue(iterator.hasNext());
        assertEquals("R", iterator.next().getData());
        assertEquals("A", iterator.next().getData());
        assertEquals("B", iterator.next().getData());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testEquals() {
        Tree<String> tree = new Tree<>("R");
        tree.addChild("A");

        Tree<String> expectedTree = new Tree<>("R");
        expectedTree.addChild("A");

        assertEquals(expectedTree, tree);
    }

    @Test
    public void testToString() {
        Tree<String> tree = new Tree<>("R1");
        tree.addChild("A");
        Tree<String> subtree = new Tree<>("R2");
        subtree.addChild("C");
        subtree.addChild("D");
        tree.addChild(subtree);
        String expected = "R1 [A, R2 [C, D]]";
        assertEquals(expected, tree.toString());
    }
}
