package model;

import java.io.File;
import java.util.*;

public class KdTree {
    private static String firstCriteriaName = "";
    private static String secondCriteriaName = "";
    private int size;
    public enum Line {HORIZONTAL, VERTICAL}
    private Line line;
    private float median;
    private Point point;
    private KdTree left;
    private KdTree right;
    private float minX;
    private float maxX;
    private float minY;
    private float maxY;

    public KdTree(File file){
        ArrayList<Point> points = parseFile(file);
        findMinsAndMaxs(points);
        line = Line.VERTICAL;
        points.sort(new SortingUsingXCoordinate());
        int medianIndex = getMedianIndex(size);
        median = points.get(medianIndex).getX();
        left = new KdTree(new ArrayList<>(points.subList(0,medianIndex + 1)),1);
        right = new KdTree(new ArrayList<>(points.subList(medianIndex + 1,size)),1);
    }

    private KdTree(ArrayList<Point> points, int depth){
        findMinsAndMaxs(points);
        size = points.size();
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
            e.printStackTrace();
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
        Comparator<Point> comparingX = new SortingUsingXCoordinate();
        Comparator<Point> comparingY = new SortingUsingYCoordinate();
        this.minX = Collections.min(points,comparingX).getX();
        this.maxX = Collections.max(points,comparingX).getX();
        this.minY = Collections.min(points,comparingY).getY();;
        this.maxY = Collections.max(points,comparingY).getY();
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
