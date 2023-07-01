package cn.milai.demoimpl.datastructure.tree.segment;

/**
 * 计算 int 和的 {@link TreeBase}
 * @author milai
 * @date 2023.07.01
 */
public class LongTreeBase extends AbstractTreeBase<Long> {

	private long[] value;
	private long[] buffer;
	private long[] bufferReset;

	public LongTreeBase(SegmentTree<Long> tree, Aggregator<Long> reqAgg, Aggregator<Long> childAgg,
		ValueMapper<Long> map) {
		super(tree, reqAgg, childAgg, map);
		this.value = new long[tree.getNodeLen()];
		this.buffer = new long[tree.getNodeLen()];
		this.bufferReset = new long[tree.getNodeLen()];
	}

	public LongTreeBase(SegmentTree<Long> tree, Aggregator<Long> agg, ValueMapper<Long> map) {
		this(tree, agg, agg, map);
	}

	@Override
	public Long get(int i) {
		return value[i];
	}

	@Override
	public void set(int i, Long v) {
		this.value[i] = v;
	}

	@Override
	protected Long getBuffer(int i) {
		return buffer[i] == 0 ? null : buffer[i];
	}

	@Override
	protected void setBuffer(int i, Long v) {
		this.buffer[i] = v == null ? 0 : v;
	}

	@Override
	protected Long getBufferReset(int i) {
		return bufferReset[i] == 0 ? null : bufferReset[i];
	}

	@Override
	protected void setBufferReset(int i, Long v) {
		this.bufferReset[i] = v == null ? 0 : v;
	}

	@Override
	public Long aggregateChild(Long lefChildV, Long rigChildV) {
		Long v = super.aggregateChild(lefChildV, rigChildV);
		return v == null ? 0 : v;
	}

}