package model;

import java.io.File;
import java.util.ArrayList;

public class Window {

    public static ArrayList<Point> requestTree(KdTree tree,float[] range){
        return requestTree(tree,range,new float[]{Float.NEGATIVE_INFINITY,Float.POSITIVE_INFINITY,Float.NEGATIVE_INFINITY,Float.POSITIVE_INFINITY});
    }

    private static ArrayList<Point> requestTree(KdTree tree ,float[] range, float[] region){
        ArrayList<Point> points = new ArrayList<>();
        if(tree.isLeaf()){
            Point point = tree.getPoint();
            if(point.isInsideRange(range))
                points.add(point);
        }
        else{
            float median = tree.getMedian();
            float[] regionLc;
            float[] regionRc;
            if(tree.getLine() == KdTree.Line.VERTICAL){
                regionLc = new float[]{region[0],median,region[2],region[3]};
                regionRc = new float[]{median,region[1],region[2],region[3]};
            }
            else{
                regionLc = new float[]{region[0],region[1],region[2],median};
                regionRc = new float[]{region[0],region[1],median,region[3]};
            }
            if(isFullyContained(regionLc,range)){
                points.addAll(reportSubtree(tree.getLeft()));
            }
            else if(intersects(regionLc,range)){
                points.addAll(requestTree(tree.getLeft(),range,regionLc));
            }
            if (isFullyContained(regionRc,range)){
                points.addAll(reportSubtree(tree.getRight()));
            }
            else if (intersects(regionRc,range)){
                points.addAll(requestTree(tree.getRight(),range,regionRc));
            }
        }
        return points;
    }

    private static ArrayList<Point> reportSubtree(KdTree tree){
        ArrayList<Point> points = new ArrayList<>();
        if (tree.isLeaf()){
            points.add(tree.getPoint());
        }
        else{
            points.addAll(reportSubtree(tree.getLeft()));
            points.addAll(reportSubtree(tree.getRight()));
        }
        return points;
    }

    private static boolean isFullyContained(float[] region, float[] range){
        return region[0] >= range[0] && region[1] <= range[1] &&
               region[2] >= range[2] && region[3] <= range[3];
    }

    private static boolean intersects(float[] region, float[] range){
        if (region[1] < range[0] || region[0] > range[1])
            return false;
        else if(region[3] < range[2] || region[2] > range[3])
            return false;
        else
            return true;
    }

    public static void main(String[] args) {
        KdTree tree = new KdTree(new File("src/data/addTest.txt"));
        System.out.println(tree.getMaxX());
        tree.add(new Point(10,4));
        System.out.println(tree.getMaxX());
        TreePrinter.print(tree);
    /*    for(Point point : Window.requestTree(tree,new float[]{15f,20f,Float.NEGATIVE_INFINITY,Float.POSITIVE_INFINITY})){
            System.out.println("age : " + point.getX() + " revenus : " + point.getY());
        } **/
    }
}
