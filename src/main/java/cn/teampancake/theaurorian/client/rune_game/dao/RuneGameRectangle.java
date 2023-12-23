package cn.teampancake.theaurorian.client.rune_game.dao;

public class RuneGameRectangle {
    private int x;
    private int y;
    private int width;
    private int height;

    public RuneGameRectangle(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean intersects(RuneGameRectangle r) {
        int tw = this.width;
        int th = this.height;
        int rw = r.width;
        int rh = r.height;
        if (rw > 0 && rh > 0 && tw > 0 && th > 0) {
            int tx = this.x;
            int ty = this.y;
            int rx = r.x;
            int ry = r.y;
            rw += rx;
            rh += ry;
            tw += tx;
            th += ty;
            return (rw < rx || rw > tx) && (rh < ry || rh > ty) && (tw < tx || tw > rx) && (th < ty || th > ry);
        } else {
            return false;
        }
    }
}