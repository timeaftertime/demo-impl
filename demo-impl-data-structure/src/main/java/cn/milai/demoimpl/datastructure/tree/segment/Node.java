package cn.milai.demoimpl.datastructure.tree.segment;

/**
 * 线段树节点
 * @author milai
 * @date 2023.07.01
 */
public class Node {
	private int lef;
	private int rig;

	public Node(int lef, int rig) {
		this.lef = lef;
		this.rig = rig;
	}

	public int getLef() { return lef; }

	public int getRig() { return rig; }

	public int getMid() { return (int) ((1L * lef + rig) >> 1); }

	public int getLen() { return rig - lef + 1; }

}