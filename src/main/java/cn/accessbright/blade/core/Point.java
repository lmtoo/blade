package cn.accessbright.blade.core;

/**
 * 表示一个坐标点
 * 
 * @author ll
 * 
 */
public class Point {

	private int left;
	private int top;

	public Point(int left, int top) {
		this.left = left;
		this.top = top;
	}

	public int left() {
		return left;
	}

	public int top() {
		return top;
	}

	public int X() {
		return left;
	}

	public int Y() {
		return top;
	}

	public int getHorizontal() {
		return left;
	}

	public int getVertical() {
		return top;
	}
}
