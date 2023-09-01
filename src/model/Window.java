package model;

import java.io.File;
import java.util.ArrayList;

public class Window {
    private KdTree tree;
    private float[] region;
    private float[] range;

    public Window(File file, float[] range){
        tree = new KdTree(file);
        region = new float[]{Float.MIN_VALUE,Float.MAX_VALUE,Float.MIN_VALUE,Float.MAX_VALUE};
        this.range = range;
    }

    private ArrayList<Point> reportSubtree(KdTree tree){
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

    private boolean isFullyContained(float[] region, float[] range){
        return region[0] >= range[0] && region[1] <= range[1] &&
               region[2] >= range[2] && region[3] <= range[3];
    }

    private boolean intersects(float[] region, float[] range){
        if (region[1] < range[0] || region[0] > range[1])
            return false;
        else if(region[3] < range[2] || region[2] > range[3])
            return false;
        else
            return true;
    }
}
