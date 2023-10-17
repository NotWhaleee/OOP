import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;


import java.util.ConcurrentModificationException;
import java.util.Iterator;

import org.junit.jupiter.api.Test;
import ru.nsu.kozorez.Tree;


public class TreeTest {
    @Test
    public void testAddNodeString() {
        var tree = new Tree<>("R");
        var child = tree.addChild("A");
        assertEquals("A", child.getData());
        assertEquals(1, tree.getChildren().size());
    }

    @Test
    public void testAddNodeInt() {
        Tree<Integer> tree = new Tree<>(0);
        Tree<Integer> child = tree.addChild(10);
        assertEquals(10, child.getData());
        assertEquals(1, tree.getChildren().size());
    }

    @Test
    public void testAddTree() {
        var tree = new Tree<>("R1");
        tree.addChild("A");
        var subtree = new Tree<>("R2");
        subtree.addChild("C");
        subtree.addChild("D");
        tree.addChild(subtree);

        var expectedTree = new Tree<>("R1");
        expectedTree.addChild("A");
        expectedTree.addChild("R2");

        expectedTree.getChildren().get(1).addChild("C");
        expectedTree.getChildren().get(1).addChild("D");
        assertEquals(expectedTree.toString(), tree.toString());
    }


    @Test
    public void testRemoveNode() {
        var tree = new Tree<>("R");
        var child = tree.addChild("A");
        child.remove();
        assertEquals(0, tree.getChildren().size());
    }

    @Test
    public void testRemoveConnectedNode() {
        var tree = new Tree<>("R");
        var child = tree.addChild("A");
        child.addChild("B");
        child.remove();
        assertEquals("B", tree.getChildren().get(0).getData());
        assertEquals(1, tree.getChildren().size());
    }

    @Test
    public void testIterator() {
        var tree = new Tree<>("R");
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
    public void testForEach() {
        var tree = new Tree<>("R");
        tree.addChild("A");
        tree.addChild("B");
        tree.addChild("C");
        String expectedAnswer = "ABC";
        String result = "";
        for (Tree<String> each : tree.getChildren()) {
            result = result.concat(each.getData());
        }
        assertEquals(expectedAnswer, result);
    }

    @Test
    public void testConcurrentModification() {
        var tree = new Tree<>("R");
        tree.addChild("A");
        tree.addChild("B");

        Iterator<Tree<String>> iterator = tree.iterator();
        tree.addChild("C");
        assertThrows(ConcurrentModificationException.class, iterator::next);
    }

    @Test
    public void testEquals() {
        var tree = new Tree<>("R");
        tree.addChild("A");

        var expectedTree = new Tree<>("R");
        expectedTree.addChild("A");

        assertEquals(expectedTree, tree);
        assertEquals(tree, expectedTree);

    }

    @Test
    public void testToString() {
        var tree = new Tree<>("R1");
        tree.addChild("A");
        var subtree = new Tree<>("R2");
        subtree.addChild("C");
        subtree.addChild("D");
        tree.addChild(subtree);
        String expected = "R1 [A, R2 [C, D]]";
        assertEquals(expected, tree.toString());
    }
}
