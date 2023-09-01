package model;

public class Point {
    private final float x;
    private final float y;

    public Point(float x, float y){
        this.x = x;
        this.y = y;
    }

    public float getX() { return x; }
    public float getY() { return y; }
    public boolean isInsideRange(float[] range){
        return x >= range[0] && x <= range[1] &&
               y >= range[2] && y <= range[3];
    }

}
