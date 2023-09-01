package test;

import model.KdTree;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class KdTreeTest {

    private KdTree tree;

    @BeforeEach
    void setUp() {
        tree = new KdTree(new File("src/data/test.txt"));
    }

    @Test
    void getFirstCriteriaName() {
        assertEquals("age",KdTree.getFirstCriteriaName());
    }

    @Test
    void getSecondCriteriaName() {
        assertEquals("revenus",KdTree.getSecondCriteriaName());
    }

    @Test
    void getMaxX() {
        assertEquals(45.0,tree.getMaxX(),0.0001);
        KdTree left = tree.getLeft();
        KdTree right = tree.getRight();
        assertEquals(25.0,left.getMaxX());
        assertEquals(45.0,right.getMaxX());
    }

    @Test
    void getMinX() {
        assertEquals(15.0,tree.getMinX(),0.0001);
        assertEquals(27.0,tree.getRight().getMinX(),0.0001);
        assertEquals(15.0,tree.getLeft().getMinX(),0.0001);
    }

    @Test
    void getMaxY() {
        assertEquals(2456.35,tree.getMaxY(),0.0001);
        assertEquals(1491.63,tree.getLeft().getMaxY(),0.0001);
        assertEquals(1850.00,tree.getRight().getLeft().getMaxY(),0.0001);
    }

    @Test
    void getMinY() {
        assertEquals(0.0,tree.getMinY(),0.0001);
        assertEquals(1212.45,tree.getRight().getMinY(),0.0001);
    }

    @Test
    void getSize() {
        assertEquals(6,tree.getSize());
        KdTree left = tree.getLeft();
        KdTree right = tree.getRight();
        assertEquals(3,left.getSize());
        assertEquals(3,right.getSize());
        assertEquals(2,left.getLeft().getSize());
        assertEquals(2,right.getLeft().getSize());
    }

    @Test
    void getMedian(){
        assertEquals(25.0,tree.getMedian(),0.0001);
        assertEquals(1000.0,tree.getLeft().getMedian(),0.0001);
        assertEquals(1850.0,tree.getRight().getMedian(),0.0001);
        assertEquals(15.0,tree.getLeft().getLeft().getMedian(),0.0001);
        assertEquals(27.0,tree.getRight().getLeft().getMedian(),0.0001);
    }

    @Test
    void getLine(){
        assertSame(tree.getLine(), KdTree.Line.VERTICAL);
        assertSame(tree.getLeft().getLine(), KdTree.Line.HORIZONTAL);
        assertSame(tree.getRight().getLine(), KdTree.Line.HORIZONTAL);
        assertSame(tree.getLeft().getLeft().getLine(), KdTree.Line.VERTICAL);
        assertSame(tree.getRight().getLeft().getLine(), KdTree.Line.VERTICAL);
    }

    @Test
    void isLeaf() {
        assertTrue(tree.getLeft().getRight().isLeaf());
        assertFalse(tree.getLeft().getLeft().isLeaf());
        assertTrue(tree.getLeft().getLeft().getLeft().isLeaf());
        assertTrue(tree.getLeft().getLeft().getRight().isLeaf());
    }

    @Test
    void setFirstCriteriaName() {
        KdTree.setFirstCriteriaName("x");
        assertEquals("x",KdTree.getFirstCriteriaName());
    }

    @Test
    void setSecondCriteriaName() {
        KdTree.setSecondCriteriaName("y");
        assertEquals("y",KdTree.getSecondCriteriaName());
    }
}