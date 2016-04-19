package cn.accessbright.blade.core.region;

/**
 * 叶子区域，可以存放内容，但是不能有子区域
 * 
 * @author ll
 * 
 */
public class LeafRegion extends AbstractRegion {
	Object content;

	public LeafRegion(CompositeRegion parent, Point relative, int width, int height, Object content) {
		super(parent, relative, width, height);
		this.content = content;
	}

	public LeafRegion(CompositeRegion parent, Point relative, Object content) {
		this(parent, relative, 1, 1, content);
	}

	public void accept(RegionVisitor visitor) {
		visitor.visit(this);
	}

	public Object content() {
		return content;
	}
}
