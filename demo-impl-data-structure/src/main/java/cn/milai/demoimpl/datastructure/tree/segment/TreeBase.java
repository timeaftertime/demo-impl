package cn.milai.demoimpl.datastructure.tree.segment;

/**
 * 线段树底层数组
 * @author milai
 * @date 2023.07.01
 */
public interface TreeBase<T> {

	/**
	 * 获取指定下标的值
	 * @param i
	 * @return
	 */
	T get(int i);

	/**
	 * 设置下标的值
	 * @param i
	 * @param v
	 */
	void set(int i, T v);

	/**
	 * 增加指定下标节点的值
	 * @param
	 * @param i
	 * @param v
	 */
	void addNode(int i, T v);

	/**
	 * 设置指定下标节点的值
	 * @param i
	 * @param v
	 */
	void setNode(int i, T v);

	/**
	 * 根据左右子节更新当前节点
	 * @param i
	 * @param lefChild
	 * @param rigChild
	 */
	void refresh(int i, int lefChild, int rigChild);

	/**
	 * 将 buffer 的值往下推
	 * @param i
	 * @param lefChild
	 * @param rigChild
	 */
	void pushDown(int i, int lefChild, int rigChild);

	/**
	 * 聚合左右子节点的值，返回聚合后的结果
	 * @param lefChildV 可为 null
	 * @param rigChildV 可为 null
	 * @return
	 */
	T aggregateChild(T lefChildV, T rigChildV);

	/**
	 * 获取关联的 {@link SegmentTree}
	 * @return
	 */
	SegmentTree<T> getTree();

}