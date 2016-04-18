package cn.accessbright.blade.core;


/**
 * 一个矩形区域的内部表示，用布尔值表示坐标是否被占用
 *
 * @author ll
 */
public class BitMap {

    private boolean[][] map;

    // 搜索的起始位置，先从0,0开始，随着标记的增加，搜索起始位置也会相应的增加
    private int searchTop = 0;
    private int searchLeft = 0;

    /**
     * 初始化一个比特矩形
     *
     * @param width  矩形的宽度
     * @param height 矩形的高度
     */
    public BitMap(int width, int height) {
        map = new boolean[height][width];
    }

    public boolean[][] getMap() {
        return map;
    }

    /**
     * 原点作为查找的起始坐标，查找并放置指定宽高的区域
     *
     * @param width
     * @param height
     * @return
     */
    public Point place(int width, int height) {
        return place(searchTop, searchLeft, width, height);
    }

    /**
     * 查找并放置区域
     *
     * @param top    查找的起始坐标
     * @param left   查找的起始坐标
     * @param width  放置矩形的宽度
     * @param height 放置矩形的高度
     * @return
     */
    public Point place(int top, int left, int width, int height) {
        Point point = findFirstPoint(top, left, width, height);
        if (point != null) {
            markRectangleTaken(point.top(), point.left(), width, height);
        }
        return point;
    }

    /**
     * 查找第一个可用区域
     *
     * @return
     */
    public Point findFirstPoint() {
        return findFirstPoint(searchTop, searchLeft, 1, 1);
    }

    /**
     * 从指定坐标开始查找第一个可用区域
     *
     * @param top
     * @param left
     * @return
     */
    public Point findFirstPoint(int top, int left) {
        return findFirstPoint(top, left, 1, 1);
    }

    /**
     * 从指定坐标开始，查询区域合适的放置位置
     *
     * @param top    查找的起始坐标
     * @param left   查找的起始坐标
     * @param width  查找空白区域的宽度
     * @param height 查找空白区域的高度
     * @return
     */
    public Point findFirstPoint(int top, int left, int width, int height) {
        int searchHeight = map.length - height;
        for (int h = top; h <= searchHeight; h++) {
            int searchWidth = map[h].length - width;
            for (int w = left; w <= searchWidth; w++) {
                if (!map[h][w] && isRectangleEmpty(h, w, width, height))
                    return new Point(w, h);
            }
        }
        return null;
    }

    /**
     * 判断此区域内是否未被占用
     *
     * @param top    区域的左上角坐标
     * @param left   区域的左上角坐标
     * @param width  区域的宽度
     * @param height 区域的高度
     * @return
     */
    private boolean isRectangleEmpty(int top, int left, int width, int height) {
        for (int h = top; h < height + top; h++) {
            for (int w = left; w < width + left; w++) {
                if (map[h][w])
                    return false;
            }
        }
        return true;
    }

    /**
     * 将此区域标记为已占用
     *
     * @param top    区域的左上角坐标
     * @param left   区域的左上角坐标
     * @param width  区域的宽度
     * @param height 区域的高度
     */
    private void markRectangleTaken(int top, int left, int width, int height) {
        boolean isStartFromRow = top == searchTop;
        boolean isStartFromCol = left == searchLeft;

        for (int h = top; h < height + top; h++) {
            boolean isLastRow = (h == height + top - 1);
            for (int w = left; w < width + left; w++) {
                map[h][w] = true;
                boolean isLastColumn = (w == width + left - 1);

                // 如果是区域的最后一行，则去判断区域的每一列，是否整列已经被占用，如果整列被占用，则设置搜索的起始为该列+1
                if (isStartFromCol && isLastRow && isWholeColumnHasBeanTaken(w)) {
                    searchLeft = w + 1;
                }

                // 如果是区域的最后一列，则去判断区域的每一行，是否整行已经被占用，如果整行被占用，则设置搜索的起始为该列+1
                if (isStartFromRow && isLastColumn && isWholeRowHasBeanTaken(h)) {
                    searchTop = h + 1;
                }
            }
        }
    }

    /**
     * 是否整行已经被占用
     *
     * @param top
     * @return
     */
    private boolean isWholeRowHasBeanTaken(int top) {
        boolean row[] = map[top];
        for (int i = searchTop; i < row.length; i++) {
            if (!row[i])
                return false;
        }
        return true;
    }

    /**
     * 是否整列已经被占用
     *
     * @param left
     * @return
     */
    private boolean isWholeColumnHasBeanTaken(int left) {
        for (int i = searchLeft; i < map.length; i++) {
            if (!map[i][left])
                return false;
        }
        return true;
    }

    /**
     * 查询指定宽度的区域，纵向可伸展的最大高度
     *
     * @param top   区域的左上角坐标
     * @param left  区域的左上角坐标
     * @param width 区域的宽度
     * @return
     */
    public int findMaxHeight(int top, int left, int width) {
        int height = 0;
        int cusur = top;
        while (cusur < map.length && isRectangleEmpty(cusur, left, width, 1)) {
            height++;
            cusur++;
        }
        return height;
    }

    /**
     * 查询指定高度度的区域，横向可伸展的最大宽度
     *
     * @param top    区域的左上角坐标
     * @param left   区域的左上角坐标
     * @param height 区域的高度
     * @return
     */
    public int findMaxWidth(int top, int left, int height) {
        int width = 0;
        int cusur = left;
        while (cusur < map[top].length && isRectangleEmpty(top, cusur, 1, height)) {
            width++;
            cusur++;
        }
        return width;
    }

    /**
     * 该坐标点是否为空
     *
     * @param left
     * @param top
     * @return
     */
    public boolean isPointEmpty(int left, int top) {
        return !map[top][left];
    }
}
