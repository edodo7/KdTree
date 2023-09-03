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

    @Override
    public boolean equals(Object other){
        if(!(other instanceof Point otherPoint)){
            return false;
        }
        else{
            float EPSILON = 0.0001f;
            return Math.abs(x - otherPoint.getX()) <= EPSILON && Math.abs(y - otherPoint.getY()) <= EPSILON;
        }
    }

    @Override
    public String toString(){
        return "(" + x + " , " + y + ")";
    }

}
