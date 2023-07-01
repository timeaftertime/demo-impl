package cn.milai.demoimpl.datastructure.tree.segment;

/**
 * {@link TreeBase}
 * @author milai
 * @date 2023.07.01
 */
public abstract class AbstractTreeBase<T> implements TreeBase<T> {

	protected SegmentTree<T> tree;
	private Aggregator<T> reqAgg;
	private Aggregator<T> childAgg;
	private ValueMapper<T> map;

	public AbstractTreeBase(SegmentTree<T> tree, Aggregator<T> reqAgg, Aggregator<T> childAgg, ValueMapper<T> map) {
		this.tree = tree;
		this.reqAgg = reqAgg;
		this.childAgg = childAgg;
		this.map = map;
	}

	/**
	 * 设置下标 i 的 buffer 为 v
	 * @param i
	 * @param v 可以为 null
	 */
	protected abstract void setBuffer(int i, T v);

	protected abstract T getBuffer(int i);

	/**
	 * 设置下标 i 的 bufferReset 为 v
	 * @param i
	 * @param v 可以为 null
	 */
	protected abstract void setBufferReset(int i, T v);

	protected abstract T getBufferReset(int i);

	@Override
	public SegmentTree<T> getTree() { return tree; }

	@Override
	public void refresh(int i, int lefChild, int rigChild) {
		set(i, aggregateChild(get(lefChild), get(rigChild)));
	}

	@Override
	public void addNode(int i, T v) {
		set(i, reqAgg.aggregateNullable(get(i), map.toNodeValue(getTree(), i, v)));
		setBuffer(i, reqAgg.aggregateNullable(getBuffer(i), v));
	}

	@Override
	public void setNode(int i, T v) {
		set(i, map.toNodeValue(getTree(), i, v));
		setBuffer(i, null);
		setBufferReset(i, v);
	}

	@Override
	public T aggregateChild(T lefChildV, T rigChildV) {
		return childAgg.aggregateNullable(lefChildV, rigChildV);
	}

	@Override
	public void pushDown(int i, int lefChild, int rigChild) {
		T bufReset = getBufferReset(i);
		if (bufReset != null) {
			setBufferReset(i, null);
			setNode(lefChild, bufReset);
			setNode(rigChild, bufReset);
		}

		T buf = getBuffer(i);
		if (buf == null) {
			return;
		}
		setBuffer(i, null);
		addNode(lefChild, buf);
		addNode(rigChild, buf);
	}
}
