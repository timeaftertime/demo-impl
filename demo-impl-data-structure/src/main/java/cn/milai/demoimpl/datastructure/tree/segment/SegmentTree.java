package cn.milai.demoimpl.datastructure.tree.segment;

import java.util.Arrays;
import java.util.function.Function;

/**
 * 线段树
 * @author milai
 * @date 2023.06.26
 */
public class SegmentTree<T> {

	private static final int ROOT = 0;

	private Node[] nodes;
	private TreeBase<T> base;

	// 用于离散化
	private final long[] MAPPER;

	public static <T> SegmentTree<T> newFromRange(int lef, int rig,
		Function<SegmentTree<T>, TreeBase<T>> treeBaseFactory) {
		if (lef > rig) {
			throw new IllegalArgumentException(String.format("min > max: min=%d, max=%d", lef, rig));
		}

		Node[] nodes = new Node[requiredLength(rig - lef + 1)];
		return new SegmentTree<>(null, 0, nodes, lef, rig, treeBaseFactory);
	}

	public static <T> SegmentTree<T> newFromEnum(long[] points, Function<SegmentTree<T>, TreeBase<T>> treeBaseFactory) {
		if (points == null || points.length == 0) {
			throw new IllegalArgumentException("map is empty");
		}

		points = Arrays.copyOf(points, points.length);
		Arrays.sort(points);
		int i = 1;
		for (int j = i; j < points.length; j++) {
			if (points[i - 1] == points[j]) {
				continue;
			}
			points[i] = points[j];
			i++;
		}

		Node[] nodes = new Node[requiredLength(i)];
		return new SegmentTree<>(points, i, nodes, 0, i - 1, treeBaseFactory);
	}

	private SegmentTree(long[] mapper, int mapperLen, Node[] nodes, int lef, int rig,
		Function<SegmentTree<T>, TreeBase<T>> treeBaseFactory) {
		this.MAPPER = mapper;
		this.nodes = nodes;
		this.base = treeBaseFactory.apply(this);
		build(lef, rig, ROOT);
	}

	public Node getNode(int i) {
		return nodes[i];
	}

	public int getNodeLen() { return nodes.length; }

	private static int requiredLength(int n) {
		return 4 * n;
	}

	public int left(int i) {
		return i * 2 + 1;
	}

	public int right(int i) {
		return i * 2 + 2;
	}

	private void build(int min, int max, int i) {
		nodes[i] = new Node(min, max);
		if (min == max) {
			return;
		}

		int mid = nodes[i].getMid();
		build(min, mid, left(i));
		build(mid + 1, max, right(i));
	}

	public void add(long lef, long rig, T v) {
		checkLefRig(lef, rig);
		update(ROOT, lef, rig, v, base::addNode);
	}

	public void set(long lef, long rig, T v) {
		checkLefRig(lef, rig);
		update(ROOT, lef, rig, v, base::setNode);
	}

	private static interface OP<T> {
		void op(int i, T v);
	}

	private void update(int i, long lef, long rig, T v, OP<T> op) {
		Node now = nodes[i];
		if (getReal(now.getLef()) == lef && getReal(now.getRig()) == rig) {
			op.op(i, v);
			return;
		}

		int lefChild = left(i);
		int rigChild = right(i);
		base.pushDown(i, lefChild, rigChild);

		int mid = now.getMid();
		if (lef <= getReal(mid)) {
			update(left(i), lef, Math.min(getReal(mid), rig), v, op);
		}
		if (getReal(mid) < rig) {
			update(right(i), Math.max(getReal(mid + 1), lef), rig, v, op);
		}
		base.refresh(i, lefChild, rigChild);
		return;
	}

	private long getReal(int i) {
		if (MAPPER == null) {
			return i;
		}
		return MAPPER[i];
	}

	private void checkLefRig(long lef, long rig) {
		if (lef > rig) {
			throw new IllegalArgumentException(String.format("lef <= rig required: lef=%d rig=%d", lef, rig));
		}
	}

	public T range(long lef, long rig) {
		checkLefRig(lef, rig);
		return range(ROOT, lef, rig);
	}

	private T range(int i, long lef, long rig) {
		Node now = nodes[i];
		if (lef == getReal(now.getLef()) && rig == getReal(now.getRig())) {
			return base.get(i);
		}

		int lefChild = left(i);
		int rigChild = right(i);
		base.pushDown(i, lefChild, rigChild);

		T lefChildV = null;
		T rigChildV = null;
		int mid = now.getMid();
		if (lef <= getReal(mid)) {
			lefChildV = range(left(i), lef, Math.min(getReal(mid), rig));
		}
		if (rig > getReal(mid)) {
			rigChildV = range(right(i), Math.max(getReal(mid + 1), lef), rig);
		}
		return base.aggregateChild(lefChildV, rigChildV);
	}

}
