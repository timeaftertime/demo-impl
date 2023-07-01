package cn.milai.demoimpl.datastructure.tree.segment;

/**
 * 计算 int 和的 {@link TreeBase}
 * @author milai
 * @date 2023.07.01
 */
public class IntTreeBase extends AbstractTreeBase<Integer> {

	private int[] value;
	private int[] buffer;
	private int[] bufferReset;

	public IntTreeBase(SegmentTree<Integer> tree, Aggregator<Integer> reqAgg, Aggregator<Integer> childAgg,
		ValueMapper<Integer> map) {
		super(tree, reqAgg, childAgg, map);
		this.value = new int[tree.getNodeLen()];
		this.buffer = new int[tree.getNodeLen()];
		this.bufferReset = new int[tree.getNodeLen()];
	}

	public IntTreeBase(SegmentTree<Integer> tree, Aggregator<Integer> agg, ValueMapper<Integer> map) {
		this(tree, agg, agg, map);
	}

	@Override
	public Integer get(int i) {
		return value[i];
	}

	@Override
	public void set(int i, Integer v) {
		this.value[i] = v;
	}

	@Override
	protected Integer getBuffer(int i) {
		return buffer[i] == 0 ? null : buffer[i];
	}

	@Override
	protected void setBuffer(int i, Integer v) {
		this.buffer[i] = v == null ? 0 : v;
	}

	@Override
	protected Integer getBufferReset(int i) {
		return bufferReset[i] == 0 ? null : bufferReset[i];
	}

	@Override
	protected void setBufferReset(int i, Integer v) {
		this.bufferReset[i] = v == null ? 0 : v;
	}

	@Override
	public Integer aggregateChild(Integer lefChildV, Integer rigChildV) {
		Integer v = super.aggregateChild(lefChildV, rigChildV);
		return v == null ? 0 : v;
	}

}