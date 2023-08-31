import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Locale;
import java.util.Scanner;

public class KdTree {
    private static String firstCriteriaName = "";
    private static String secondCriteriaName = "";
    private int size;
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
        findMinsandMaxs(points);
        line = Line.VERTICAL;
        points.sort(new SortingUsingXCoordinate());
        int medianIndex = getMedianIndex(size);
        median = points.get(medianIndex).getX();
        left = new KdTree(new ArrayList<>(points.subList(0,medianIndex + 1)),1);
        right = new KdTree(new ArrayList<>(points.subList(medianIndex + 1,size)),1);
    }

    private KdTree(ArrayList<Point> points,int depth){
        findMinsandMaxs(points);
        size = points.size();
        if (size == 1){
            this.point = points.get(0);
        }
        else{
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
            left = new KdTree(new ArrayList<>(points.subList(0,medianIndex + 1)),depth + 1);
            right = new KdTree(new ArrayList<>(points.subList(medianIndex + 1,size)),depth + 1);
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
    public boolean isLeaf(){
        return left == null && right == null;
    }

    private void findMinsandMaxs(ArrayList<Point> points){
        float minX = points.get(0).getX();
        float maxX = points.get(0).getX();
        float minY = points.get(0).getY();
        float maxY = points.get(0).getY();
        for(Point point : points){
            if(point.getX() < minX)
                minX = point.getX();
            if (point.getX() > maxX)
                maxX = point.getX();
            if(point.getY() < minY)
                minY = point.getY();
            if (point.getY() > maxY)
                maxY = point.getY();
        }
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
    }
    public static String getFirstCriteriaName() { return firstCriteriaName; }

    public static String getSecondCriteriaName() { return secondCriteriaName; }

    public static void setFirstCriteriaName(String firstCriteriaName) {
        KdTree.firstCriteriaName = firstCriteriaName;
    }

    public static void setSecondCriteriaName(String secondCriteriaName) {
        KdTree.secondCriteriaName = secondCriteriaName;
    }

    private enum Line {HORIZONTAL, VERTICAL};

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
