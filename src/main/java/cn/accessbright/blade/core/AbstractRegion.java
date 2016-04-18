package cn.accessbright.blade.core;

/**
 * 区域的抽象表示
 *
 * @author ll
 *
 */
public abstract class AbstractRegion implements Region {
	protected CompositeRegion parent;// 父区域对象
	protected Point relative;// 相对于父区域的坐标位置

	private int width = 1;
	private int height = 1;

	public AbstractRegion(Point coordinate) {
		this(null, coordinate);
	}

	public AbstractRegion(CompositeRegion parent, Point relative) {
		this.parent = parent;
		this.relative = relative;
	}

	public AbstractRegion(Point coordinate, int width, int height) {
		this(null, coordinate, width, height);
	}

	public AbstractRegion(CompositeRegion parent, Point relative, int width, int height) {
		this(parent, relative);
		this.width = width;
		this.height = height;
	}

	public Point absolute() {
		if (parent == null)
			return relative;
		Point absolute = parent.absolute();
		return new Point(absolute.left() + relative.left(), absolute.top() + relative.top());
	}

	public int width() {
		return width;
	}

	public int height() {
		return height;
	}

	public Region parent() {
		return parent;
	}

	public Point relative() {
		return relative;
	}

	public void place(CompositeRegion parent, Point relative) {
		this.parent = parent;
		this.relative = relative;
	}
	
	public boolean isMinimize(){
		return width==1&&height==1;
	}

	public abstract void accept(RegionVisitor visitor) ;
}