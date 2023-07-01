package cn.milai.demoimpl.datastructure.tree.segment;

/**
 * 线段树值映射器
 * @author milai
 * @date 2023.07.01
 */
public interface ValueMapper<T> {

	/**
	 * 将线段树操作参数的值 v 转换为线段树 i 节点对应的值
	 * @param tree
	 * @param i
	 * @param v
	 * @return
	 */
	T toNodeValue(SegmentTree<T> tree, int i, T v);

	static ValueMapper<?> SELF = (tree, i, v) -> v;

	/**
	 * 获取一个返回自身的值映射器
	 * @param <T>
	 * @return
	 */
	@SuppressWarnings("unchecked")
	static <T> ValueMapper<T> self() {
		return (ValueMapper<T>) SELF;
	}

}
