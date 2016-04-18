package cn.accessbright.blade.core;

/**
 * 区域访问者接口
 *
 * @author ll
 *
 */
public interface RegionVisitor {

	/**
	 * 访问复合区域
	 *
	 * @param composit
	 */
	void visit(CompositeRegion composit);

	/**
	 * 访问叶子区域
	 *
	 * @param leaf
	 */
	void visit(LeafRegion leaf);
}