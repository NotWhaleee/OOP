import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import org.junit.jupiter.api.Test;
import ru.nsu.kozorez.Tree;

/**
 * Tests tree class.
 */
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
    void checkNotEquals() {
        var tree1 = new Tree<>("R");
        tree1.addChild("A");
        var tree2 = new Tree<>("R");
        tree2.addChild("B");

        assertNotEquals(tree1, tree2);
    }
    @Test
    void simpleTree(){
        var tree1 = new Tree<>("R");
        tree1.addChild("A");
        tree1.addChild("B");

        var tree2 = new Tree<>("R");
        tree2.addChild("B");
        tree2.addChild("A");

        assertEquals(tree1, tree2);
    }


    @Test
    void checkNotEqualsComplex() {
        var tree1 = new Tree<>("R");
        tree1.addChild("A");
        var subtree1 = new Tree<>("R1");
        subtree1.addChild("B");
        subtree1.addChild("C");
        tree1.addChild("D");
        tree1.addChild(subtree1);
        //System.out.println(tree1);

        var tree22 = new Tree<>("R");
        tree22.addChild("A");
        var subtree22 = new Tree<>("R1");
        subtree22.addChild("C");
        subtree22.addChild("B");
        tree22.addChild("D");
        tree22.addChild(subtree22);

//        var tree2 = new Tree<>("R");
//        tree2.addChild("A");
//        tree2.addChild("D");
//        var subtree2 = new Tree<>("R1");
//        subtree2.addChild("C");
//        subtree2.addChild("B");
//        tree2.addChild(subtree2);

        assertEquals(tree1, tree22);
    }
    @Test
    public void testEquals_differentTrees_differAtThorthLevel() {
        Tree<String> tree1 = new Tree<>("root");
        Tree<String> child1 = tree1.addChild("child1");
        child1.addChild("grandchild1");
        var grandchild1 = child1.addChild("grandchild2");
        grandchild1.addChild("supergrandchild1");
        grandchild1.addChild("supergrandchild2");

        tree1.addChild("child2");

        Tree<String> tree2 = new Tree<>("root");
        Tree<String> child2 = tree2.addChild("child1");
        child2.addChild("grandchild1");
        var grandchild2 = child2.addChild("grandchild2");
        grandchild2.addChild("supergrandchild1");
        grandchild2.addChild("supergrandchild3");


        tree2.addChild("child2");

        assertNotEquals(tree1, tree2);
    }
    @Test
    public void testEquals_sameTrees_differAtThorthLevel() {
        Tree<String> tree1 = new Tree<>("root");
        Tree<String> child1 = tree1.addChild("child1");
        child1.addChild("grandchild1");
        var grandchild1 = child1.addChild("grandchild2");
        grandchild1.addChild("supergrandchild1");
        grandchild1.addChild("supergrandchild2");

        tree1.addChild("child2");

        Tree<String> tree2 = new Tree<>("root");
        Tree<String> child2 = tree2.addChild("child1");
        child2.addChild("grandchild1");
        var grandchild2 = child2.addChild("grandchild2");
        grandchild2.addChild("supergrandchild1");
        grandchild2.addChild("supergrandchild2");


        tree2.addChild("child2");

        assertEquals(tree1, tree2);
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
