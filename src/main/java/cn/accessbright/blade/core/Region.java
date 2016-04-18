package cn.accessbright.blade.core;
/**
 * 表示一个区域
 *
 * @author ll
 *
 */
public interface Region {
	// 相对于根区域的坐标(左上角)
	Point absolute();

	// 相对于父区域的坐标(左上角)
	Point relative();

	// 区域宽度
	int width();

	// 区域高度
	int height();

	// 获取父区域
	Region parent();

	// 将区域放置到指定父节点的指定位置
	void place(CompositeRegion parent, Point relative);

	// 访问者模式
	void accept(RegionVisitor visitor);
}