package test;

import model.KdTree;
import model.Point;
import model.Window;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;

class WindowTest {

    private float minInf;
    private float plusInf;

    @BeforeEach
    void setUp(){
        minInf = Float.NEGATIVE_INFINITY;
        plusInf = Float.POSITIVE_INFINITY;
    }

    @Test
    void getPeopleEarning1500Max() {
        KdTree tree = new KdTree(new File("src/data/test.txt"));
        ArrayList<Point> expected = new ArrayList<>();
        expected.add(new Point(25.f ,1491.63f));
        expected.add(new Point(15.f ,0.00f));
        expected.add(new Point(27.f ,1212.45f));
        expected.add(new Point(25.f ,1000.00f));
        ArrayList<Point> actual = Window.requestTree(tree,new float[]{minInf,plusInf,minInf,1500f});
        assertTrue(expected.size() == actual.size() && expected.containsAll(actual));
    }

    @Test
    void getPeopleEarningMoreThan1500(){
        KdTree tree = new KdTree(new File("src/data/test.txt"));
        ArrayList<Point> expected = new ArrayList<>();
        expected.add(new Point(45.f ,1850.00f));
        expected.add(new Point(32.f, 2456.35f));
        ArrayList<Point> actual = Window.requestTree(tree,new float[]{25f,45f,1500f,plusInf});
        assertTrue(expected.size() == actual.size() && expected.containsAll(actual));
    }

    @Test
    void getPeopleEarningMoreThan3000(){
        KdTree tree = new KdTree(new File("src/data/test.txt"));
        ArrayList<Point> actual = Window.requestTree(tree,new float[]{minInf,plusInf,3000f,plusInf});
        assertTrue(actual.isEmpty());
    }

    @Test
    void get25to30EarningMoreThan2000(){
        KdTree tree = new KdTree(new File("src/data/test.txt"));
        ArrayList<Point> actual = Window.requestTree(tree,new float[]{25f,30f,2000f,plusInf});
        assertTrue(actual.isEmpty());
    }
}