package com.group16.stardewvalley.model.map;

public class Pos {
    private int x;
    private int y;

    public Pos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    //To check whether Pos1 is in the 8 neighboring tiles of Pos2.
    public static boolean isNear(Pos pos1, Pos pos2) {
        for (int dy = -1; dy <= 1; dy++) {
            for (int dx = -1; dx <= 1; dx++) {
                if (dx == 0 && dy == 0) continue; // skip the center (pos2 itself)
                int newX = pos2.x + dx;
                int newY = pos2.y + dy;
                if (pos1.x == newX && pos1.y == newY) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isEqual(Pos pos) {
        return pos.getY() == this.y && pos.getX() == this.x;
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}