package cn.teampancake.theaurorian.client.rune_game;

public record RuneGameRectangle(int x, int y, int width, int height) {

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

            // 计算两个矩形的边界
            int txRight = tx + tw;
            int tyBottom = ty + th;
            int rxRight = rx + rw;
            int ryBottom = ry + rh;

            // 考虑水平方向的1像素偏移，当水平方向的重叠小于等于1像素时，不认为是交叉
            // 垂直方向保持原有的判断逻辑
            boolean horizontalOverlap = !(txRight <= rx + 1 || rxRight <= tx + 1);
            boolean verticalOverlap = !(tyBottom <= ry || ryBottom <= ty);

            return horizontalOverlap && verticalOverlap;
        } else {
            return false;
        }
    }

}