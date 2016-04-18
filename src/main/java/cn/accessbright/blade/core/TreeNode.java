package cn.accessbright.blade.core;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 树节点
 * 
 * @author ll
 *
 */
public class TreeNode {
	private TreeNode parent;
	private String name;
	private int index;
	private LinkedHashMap children = new LinkedHashMap();

	private TreeNode(String name) {
		this.name = name;
	}

	public TreeNode getParent() {
		return parent;
	}

	public void setParent(TreeNode parent) {
		this.parent = parent;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public Map getChildren() {
		return Collections.unmodifiableMap(this.children);
	}

	public TreeNode addChild(TreeNode node) {
		node.setIndex(this.children.size());
		node.setParent(this);
		this.children.put(node.getName(), node);
		return node;
	}

	public TreeNode addChild(String name) {
		TreeNode node = TreeNode.valueOf(name);
		addChild(node);
		return node;
	}

	public boolean isRoot() {
		return parent == null;
	}

	public boolean isLeaf() {
		return children.isEmpty();
	}

	/**
	 * ����������tree�ڵ�
	 * 
	 * @param visitor
	 */
	public void accept(TreeNodeVisitor visitor) {
		visitor.visit(this);
		if (!isLeaf()) {
			Iterator iter = children.values().iterator();
			while (iter.hasNext()) {
				TreeNode node = (TreeNode) iter.next();
				node.accept(visitor);
			}
		}
	}

	public String toString() {
		return name;
	}

	public static TreeNode valueOf(String name) {
		return new TreeNode(name);
	}
}