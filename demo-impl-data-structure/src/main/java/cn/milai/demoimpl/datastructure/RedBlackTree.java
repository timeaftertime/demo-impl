package cn.milai.demoimpl.datastructure;

import java.util.Comparator;

/**
 * 红黑树。
 * 1. 结点是黑色或红色。
 * 2. 根结点必须为黑色。
 * 3. 所有叶子(这里是指 null，而不是传统二叉树的叶子结点)看做黑色。
 * 4. 每个红色结点的 2 个子节点必须是黑色。即从所有叶子到根结点的路径中不能出现 2 个连续红色结点。
 * 5. 任何一个结点到(其子树中的)每个叶子的路径中包含的黑色结点数量一样。
 * 关于 2-、3-、4-
 * 1. 一个单独的黑色结点有 left、right 2 个引用，因此是 2- 结点
 * 2. 红色结点可以看作是和其父结点合并到一起的结点，这样一个红色结点的 left、right 及其父结点的 right 就一共有 3 个引用，因此这两个节点称为 3- 结点
 * 3. 两个连续的红色结点及其父节点(黑色)一共有 4 个引用，因此这 3 个结点一起称为 4- 结点。这种情况只会是插入、删除时的临时结构。
 * @author milai
 * @date 2022.07.24
 */
public class RedBlackTree<K, V> {

	private Node root;
	private int size;
	private Comparator<K> keyComparator;

	public RedBlackTree() {
	}

	public RedBlackTree(Comparator<K> keyComparator) {
		this.keyComparator = keyComparator;
	}

	private class Node {
		K key;
		V value;
		Node left;
		Node right;
		boolean red;

		private Node(K key, V value, boolean red) {
			this.key = key;
			this.value = value;
			this.red = red;
		}

		@Override
		public String toString() {
			return String.format("(%s=%s, %s)", key, value, red ? "R" : "B");
		}

		@SuppressWarnings("unchecked")
		private int compareTo(K key) {
			if (keyComparator != null) {
				return keyComparator.compare(this.key, key);
			}
			return ((Comparable<K>) this.key).compareTo(key);
		}

		public boolean isLessThan(K key) {
			return compareTo(key) < 0;
		}

		public boolean isMoreThan(K key) {
			return compareTo(key) > 0;
		}

		public boolean isEquals(K key) {
			return compareTo(key) == 0;
		}

	}

	/**
	 * 获取指定 key 对应的元素
	 * @param key
	 * @return
	 */
	public V get(K key) {
		return get(root, key);
	}

	private V get(Node now, K key) {
		if (now == null) {
			return null;
		}
		if (now.isEquals(key)) {
			return now.value;
		}
		return now.isLessThan(key) ? get(now.right, key) : get(now.left, key);
	}

	/**
	 * 获取元素个数
	 * @return
	 */
	public int size() {
		return size;
	}

	/**
	 * 是否为空
	 * @return
	 */
	public boolean isEmpty() { return size() == 0; }

	/**
	 * 添加一个元素
	 * @param key
	 * @param value
	 */
	public void put(K key, V value) {
		root = merge(root, key, value);
		ensureRootBlack();
	}

	/**
	 * 将指定元素合并指定树中，并返回新的 root 结点
	 * @param now
	 * @param key
	 * @param value
	 * @return
	 */
	private Node merge(Node now, K key, V value) {
		if (now == null) {
			size++;
			// 创建的新结点始终是红色，这样各路径的黑色结点数量不变，只需旋转避免连续红色结点
			return new Node(key, value, true);
		}

		if (now.isEquals(key)) {
			now.value = value;
		} else if (now.isMoreThan(key)) {
			now.left = merge(now.left, key, value);
		} else {
			now.right = merge(now.right, key, value);
		}
		return balance(now);
	}

	/**
	 * 清空元素
	 */
	public void clear() {
		root = null;
		size = 0;
	}

	/**
	 * 删除一个 key 对应的结点。
	 * 若直接移除一个黑色结点，会导致其所在子树的结点到叶子的路径 -1，从而不符合规则，
	 * 因此删除时需要在向下遍历的过程中，始终保持当前结点是一个非 2- 结点。
	 * @param key
	 */
	public void remove(K key) {
		if (root == null) {
			return;
		}
		// 调用 delete 方法之前需要确保 now 是非 2- 结点
		if (!isRed(root.left)) {
			root.red = true;
		}
		root = remove(root, key);
		ensureRootBlack();
	}

	private Node remove(Node now, K key) {
		if (now == null) {
			return null;
		}
		// 要删除的节点就是当前节点
		if (now.isEquals(key)) {
			// 没有右子树：返回左子树
			// 只有这里真正删除结点
			if (now.right == null) {
				size--;
				return now.left;
			}
			// 有右子树：找到右子树中最小的结点 target，将当前节点赋值上 target 的 key-value，然后从右子树删除 target
			Node target = minNode(now.right);
			now.key = target.key;
			now.value = target.value;
			now.right = deleteMin(now.right);
		}
		// 要删除的节点比当前节点小：确保左子节点非 2- 结点后递归删除左子树指定节点
		else if (now.isMoreThan(key)) {
			now = ensureLeftRemovable(now);
			now.left = remove(now.left, key);
		}
		// 要删除的节点比当前节点大：确保右子节点非 2- 结点后递归删除右子树指定结点
		else {
			// 如果当前节点的左节点是红色节点，而当前节点正好是要删除的节点，
			// 这样直接删除当前节点，则左边的红色节点会连接到当前节点的父节点，
			// 使得当前路径上的黑色边少 1 ，从而破坏数的平衡。
			// 为避免以上情况，如果左边子节点是红色，直接右旋转节点
			if (isRed(now.left)) {
				now = rightRot(now);
			}
			// 要删除的节点比当前节点大，确保右子节点为红色后递归删除右子树中指定节点
			if (now.isLessThan(key)) {
				if (now.right == null) {
					return now;
				}
				now = ensureRightRemovable(now);
				now.right = remove(now.right, key);
			}
		}
		// 向上回溯，去掉生成的临时 4- 节点，并确保所有红色边为向左
		return balance(now);
	}

	private Node deleteMin(Node now) {
		// 当前结点就是最小结点了，返回 null
		if (now.left == null) {
			// 这里本应该返回 now.right，但其实 now.right 一定是 null
			// 插入元素时，若最小结点添加一个红右子节点，该最小结点一定会被 blance 成没有右结点
			return null;
		}
		now = ensureLeftRemovable(now);
		now.left = deleteMin(now.left);
		return balance(now);
	}

	/**
	 * 确保左子节点可直接删除，即是 <code>null</code> 或非 2- 节点
	 * @param now
	 * @return
	 */
	private Node ensureLeftRemovable(Node now) {
		if (now.left == null) {
			return now;
		}
		// 已经是非 2- 结点：直接返回
		if (isRed(now.left) || isRed(now.left.left)) {
			return now;
		}
		// 删除结点时，now 一定是非 2- 结点，因为 !isRed(now.left)，则 now 一定是红结点，因此会变为黑结点
		flipColors(now);
		// 若右结点本来就是 3- 结点，将其红色结点旋转上来，以免这条边在 blance() 时被忽略
		if (now.right != null && isRed(now.right.left)) {
			now.right = rightRot(now.right);
			now = leftRot(now);
			// 已经从右子树借来了一条红色边，因此需要再 flip 取消第一次的 flip 操作，以免旋转后的红色的右子节点被 balance() 忽略
			flipColors(now);
		}
		return now;
	}

	/**
	 * 确保右子节点可直接删除，即是 <code>null</code> 或非 2- 节点
	 * @param now
	 * @return
	 */
	private Node ensureRightRemovable(Node now) {
		if (now.right == null) {
			return now;
		}
		// 已经是非 2- 节点：直接返回
		if (isRed(now.right) || isRed(now.right.left)) {
			return now;
		}
		// 删除结点时，now 一定是非 2- 结点，因为 !isRed(now.left)，则 now 一定是红结点，因此会变为黑结点
		flipColors(now);
		if (now.left != null && isRed(now.left.left)) {
			now = rightRot(now);
			// 已经从左子树借来了一条红色边，因此需要再 flip 取消第一次的 flip 操作，以免旋转后的红色的左子节点被 balance() 忽略
			flipColors(now);
		}
		return now;
	}

	/**
	 * 重新平衡红黑树。
	 * 主要目的包括：
	 *    1. 确保红结点始终为左子节点，方便操作。如果出现红色结点为非左结点，使用左旋。
	 *    2. 确保不会出现 2 个连续的红色结点(红色树平衡时没有连续的红色结点，因此插入一个红结点后最多只能有 2 个连续红色结点)。
	 *        因为 1. 已经确保没有右子节点为红色，因此只有 now.left 和 now.left.left 都是红色结点。
	 *        此时右旋，变成一个结点的左右子结点为红结点。
	 *    3. 一个结点左右子结点都是红色结点时，直接将左右子节点变为黑色，当前结点变为红色
	 * @param now
	 * @return
	 */
	private Node balance(Node now) {
		// 右子结点为红色且左子结点不为红： 左旋，将右红链转换为左红链
		if (isRed(now.right) && !isRed(now.left)) {
			now = leftRot(now);
		}
		// 连续 2 个左子节点为红色：右旋，转换为一个结点的两个子节点都是红色
		if (isRed(now.left) && isRed(now.left.left)) {
			now = rightRot(now);
		}
		// 左右子节点为红色：左右结点变成黑色，当前结点变为红色
		if (isRed(now.left) && isRed(now.right)) {
			// 插入元素时会临时出现左右结点都为红色，此时不可能出现连续 3 个红结点，因此 now 原本一定是黑色，会变为红色结点
			flipColors(now);
		}
		return now;
	}

	/**
	 * 获取当前子树中最小(即最左)的结点
	 * @param now
	 * @return
	 */
	private Node minNode(Node now) {
		return now.left == null ? now : minNode(now.left);
	}

	/**
	 * 左旋
	 * @param now
	 * @return
	 */
	private Node leftRot(Node now) {
		Node n1 = now;
		Node n2 = now.right;
		n2.red = n1.red;
		n1.red = true;
		n1.right = n2.left;
		n2.left = n1;
		return n2;
	}

	/**
	 * 右旋
	 * @param now
	 * @return
	 */
	private Node rightRot(Node now) {
		Node n1 = now;
		Node n2 = now.left;
		n2.red = n1.red;
		n1.red = true;
		n1.left = n2.right;
		n2.right = n1;
		return n2;
	}

	private boolean isRed(Node node) {
		return node != null && node.red;
	}

	private void flipColors(Node node) {
		node.red = !node.red;
		if (node.left != null) {
			node.left.red = !node.left.red;
		}
		if (node.right != null) {
			node.right.red = !node.right.red;
		}
	}

	/**
	 * 红黑树规定 root 不为 <code>null</code> 时必须为黑结点
	 */
	private void ensureRootBlack() {
		if (root != null) {
			root.red = false;
		}
	}

}
