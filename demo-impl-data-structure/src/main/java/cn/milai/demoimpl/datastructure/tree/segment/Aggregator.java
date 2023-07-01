package cn.milai.demoimpl.datastructure.tree.segment;

/**
 * 线段树值聚合器
 * @author milai
 * @date 2023.07.01
 */
public interface Aggregator<T> {

	/**
	 * 返回聚合后的结果
	 * @param v1 可为 null
	 * @param v2 可为 null
	 * @return
	 */
	default T aggregateNullable(T v1, T v2) {
		if (v1 == null) {
			return v2;
		}
		if (v2 == null) {
			return v1;
		}
		return aggregate(v1, v2);
	}

	/**
	 * 返回聚合后的结果
	 * @param v1 不为 null
	 * @param v2 不为 null
	 * @return
	 */
	T aggregate(T v1, T v2);

}
