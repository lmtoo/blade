package cn.accessbright.blade.core;


import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * 表示一个复合区域，一个存放区域的容器，只能存放其他的区域对象。
 * 并且有一个内部占用大小的对象表示BitMap，用来表示该复核区域已经被占用的位置，和未被占用的位置<br>
 * 具体用法见{@link cn.accessbright.blade.core.excel.ExcelHeader}
 * {@link }
 *
 * @author ll
 */
public class CompositeRegion extends AbstractRegion {
    private BitMap map;

    private List subRegions = new LinkedList();

    public CompositeRegion(CompositeRegion parent, Point relative, int width, int height) {
        super(parent, relative, width, height);
        map = new BitMap(width, height);
    }

    public CompositeRegion(Point coordinate, int width, int height) {
        this(null, coordinate, width, height);
    }

    public CompositeRegion(int width, int height) {
        this(new Point(0, 0), width, height);
    }

    /**
     * 获取该复合区域的一个指定宽高的子复合区域
     *
     * @param width
     * @param height
     * @return
     */
    public CompositeRegion sub(int width, int height) {
        Point location = map.place(width, height);
        if (location != null) {
            CompositeRegion added = new CompositeRegion(this, location, width, height);
            subRegions.add(added);
            return added;
        }
        return null;
    }

    /**
     * 获取该复合区域的一个子复合区域，高度为本区域的高度
     *
     * @param width
     * @return
     */
    public CompositeRegion sub(int width) {
        return sub(width, height());
    }

    /**
     * 设置一个叶子内容区域，并按照指定叶子内容区域的高度，默认宽度为1
     *
     * @param height
     * @param content
     * @return
     */
    public CompositeRegion height(int height, Object content) {
        return content(1, height, content);
    }

    /**
     * 复数形式
     *
     * @param height
     * @param content
     * @return
     */
    public CompositeRegion height(int height, Object[] content) {
        return content(1, height, content);
    }

    /**
     * 设置一个叶子内容区域，并按照指定叶子内容区域的宽度，默认高度为1
     *
     * @param width
     * @param content
     * @return
     */
    public CompositeRegion width(int width, Object content) {
        return content(width, 1, content);
    }

    /**
     * 复数形式
     *
     * @param width
     * @param content
     * @return
     */
    public CompositeRegion width(int width, Object[] content) {
        return content(width, 1, content);
    }

    /**
     * 设置一个叶子内容区域，并以最小化单元格存储
     *
     * @param content
     * @return
     */
    public CompositeRegion minimize(Object content) {
        return content(1, 1, content);
    }

    /**
     * 复数形式
     *
     * @param content
     * @return
     */
    public CompositeRegion minimize(Object[] content) {
        return content(1, 1, content);
    }

    /**
     * 设置一个叶子内容区域，并按照指定叶子内容区域的宽高
     *
     * @param width
     * @param height
     * @param content
     * @return
     */
    public CompositeRegion content(int width, int height, Object content) {
        Point location = map.place(width, height);
        if (location != null) {
            subRegions.add(new LeafRegion(this, location, width, height, content));
        }
        return this;
    }

    /**
     * 复数形式
     *
     * @param width
     * @param height
     * @param content
     * @return
     */
    public CompositeRegion content(int width, int height, Object[] content) {
        for (int i = 0; i < content.length; i++) {
            content(width, height, content[i]);
        }
        return this;
    }

    /**
     * 设置叶子内容区域，并按照本复合区域的可用最大宽度设置，叶子内容区域高度默认为1
     *
     * @param content 要放置的内容
     * @return
     */
    public CompositeRegion maxWidth(Object content) {
        return maxWidth(new Object[]{content});
    }

    /**
     * 复数形式
     *
     * @param content
     * @return
     */
    public CompositeRegion maxWidth(Object[] content) {
        int height = 1;
        for (int i = 0; i < content.length; i++) {
            Point start = map.findFirstPoint();
            if (start != null) {
                int width = map.findMaxWidth(start.top(), start.left(), height);
                content(width, height, content[i]);
            }
        }
        return this;
    }

    /**
     * 设置叶子内容区域，并按照本复合区域的可用最大高度设置，叶子内容区域宽度默认为1
     *
     * @param content 要放置的内容
     * @return
     */
    public CompositeRegion maxHeight(Object content) {
        return maxHeight(new Object[]{content});
    }

    /**
     * 复数形式
     *
     * @param content
     * @return
     */
    public CompositeRegion maxHeight(Object[] content) {
        int width = 1;
        for (int i = 0; i < content.length; i++) {
            Point start = map.findFirstPoint();
            if (start != null) {
                int height = map.findMaxHeight(start.top(), start.left(), width);
                content(width, height, content[i]);
            }
        }
        return this;
    }

    /**
     * 结束本对象的访问，将父对象的引用返回
     *
     * @return
     */
    public CompositeRegion end() {
        return parent;
    }

    /**
     * 访问者模式，方便遍历复合区域和子区域
     */
    public void accept(RegionVisitor visitor) {
        Iterator iter = subRegions.iterator();
        while (iter.hasNext()) {
            Region subregion = (Region) iter.next();
            subregion.accept(visitor);
        }
        visitor.visit(this);
    }
}