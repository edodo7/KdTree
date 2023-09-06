package model;

import java.io.File;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static model.TreePrinter.*;

public class KdTree implements PrintableNode {
    private static String firstCriteriaName = "";
    private static String secondCriteriaName = "";
    private static int size;
    public enum Line {HORIZONTAL, VERTICAL}
    private Line line;
    private float median;
    private Point point;
    private KdTree left;
    private KdTree right;
    private static float minX;
    private static float maxX;
    private static float minY;
    private static float maxY;

    public KdTree(File file){
        ArrayList<Point> points = parseFile(file);
        int size = points.size();
        findMinsAndMaxs(points);
        line = Line.VERTICAL;
        points.sort(new SortingUsingXCoordinate());
        int medianIndex = getMedianIndex(size);
        median = points.get(medianIndex).getX();
        left = new KdTree(new ArrayList<>(points.subList(0,medianIndex + 1)),1);
        right = new KdTree(new ArrayList<>(points.subList(medianIndex + 1,size)),1);
    }

    private KdTree(ArrayList<Point> points, int depth){
        int size = points.size();
        int medianIndex;
        if (depth % 2 == 0){
            line = Line.VERTICAL;
            points.sort(new SortingUsingXCoordinate());
            medianIndex = getMedianIndex(size);
            median = points.get(medianIndex).getX();
        }
        else{
            line = Line.HORIZONTAL;
            points.sort(new SortingUsingYCoordinate());
            medianIndex = getMedianIndex(size);
            median = points.get(medianIndex).getY();
        }
        if(size != 1) {
            left = new KdTree(new ArrayList<>(points.subList(0,medianIndex + 1)),depth + 1);
            right = new KdTree(new ArrayList<>(points.subList(medianIndex + 1,size)),depth + 1);
        }
        else{
            point = points.get(0);
        }
    }

    private KdTree(Point point,int depth){
        this.point = point;
        line = (depth % 2 == 0) ? Line.VERTICAL : Line.HORIZONTAL;
    }

    public void add(Point element){
        if(isLeaf()){
            maxX = Math.max(maxX,element.getX());
            minX = Math.min(minX,element.getX());
            maxY = Math.max(maxY, element.getY());
            minY = Math.min(minY, element.getY());
            size++;
            Point minimum;
            Point maximum;
            if (line == Line.VERTICAL){
                minimum = element.getX() < point.getX() ? element : point;
                maximum = element.getX() > point.getX() ? element : point;
                median = minimum.getX();
                left = new KdTree(minimum,1);
                right = new KdTree(maximum,1);
            }
            else{
                minimum = element.getY() < point.getY() ? element : point;
                maximum = element.getY() > point.getY() ? element : point;
                median = minimum.getY();
                left = new KdTree(minimum,2);
                right = new KdTree(maximum,2);
            }
            point = null;
        }
        else{
            if(line == Line.VERTICAL){
                if(element.getX() > median)
                    right.add(element);
                else
                    left.add(element);
            }
            else {
                if(element.getY() > median)
                    right.add(element);
                else
                    left.add(element);
            }
        }
    }

    private ArrayList<Point> parseFile(File file){
        ArrayList<Point> points = new ArrayList<>();
        float x, y;
        try {
            Scanner scan = new Scanner(file);
            scan.useLocale(Locale.US);
            firstCriteriaName = scan.next();
            secondCriteriaName = scan.next();
            size = scan.nextInt();
            int i = 0;
            while (i < size){
                x = scan.nextFloat();
                y = scan.nextFloat();
                Point point = new Point(x,y);
                points.add(point);
                i++;
            }
            scan.close();
        }
        catch(Exception e){
            Logger logger = Logger.getAnonymousLogger();
            logger.log(Level.WARNING,e.getMessage(),e);
        }
        return points;
    }
    private int getMedianIndex(int range){
        int index = range / 2;
        if (range % 2 == 0)
            index -= 1;
        return index;
    }
    public boolean isLeaf() { return left == null && right == null; }

    private void findMinsAndMaxs(ArrayList<Point> points){
        minX = minY = Float.POSITIVE_INFINITY;
        maxX = maxY = Float.NEGATIVE_INFINITY;
        for(Point point : points){
            minX = Math.min(minX,point.getX());
            maxX = Math.max(maxX,point.getX());
            minY = Math.min(minY,point.getY());
            maxY = Math.max(maxY,point.getY());
        }
    }
    public static String getFirstCriteriaName() { return firstCriteriaName; }

    public static String getSecondCriteriaName() { return secondCriteriaName; }

    public float getMaxX() { return maxX; }

    public float getMinX() { return minX; }

    public float getMaxY() { return maxY; }

    public float getMinY() { return minY; }

    public int getSize() { return size; }

    public KdTree getLeft() { return left; }

    public KdTree getRight() { return right; }

    public float getMedian() { return median; }

    public Line getLine() { return line; }

    public Point getPoint() { return point; }

    @Override
    public String getText(){
        if(isLeaf()){
            return point.toString();
        }
        else {
            String res;
            if (line == Line.VERTICAL)
                res = "mid x : " + median;
            else
                res = "mid y : " + median;
            return res;
        }
    }

    public static void setFirstCriteriaName(String firstCriteriaName) {
        KdTree.firstCriteriaName = firstCriteriaName;
    }

    public static void setSecondCriteriaName(String secondCriteriaName) {
        KdTree.secondCriteriaName = secondCriteriaName;
    }

    private static class SortingUsingYCoordinate implements Comparator<Point> {
        @Override
        public int compare(Point firstPoint, Point secondPoint){
            return Float.compare(firstPoint.getY(),secondPoint.getY());
        }
    }

    private static class SortingUsingXCoordinate implements Comparator<Point> {
        @Override
        public int compare(Point firstPoint, Point secondPoint){
            return Float.compare(firstPoint.getX(),secondPoint.getX());
        }
    }
}
