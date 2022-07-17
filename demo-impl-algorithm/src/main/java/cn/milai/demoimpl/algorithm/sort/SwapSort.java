package cn.milai.demoimpl.algorithm.sort;

public abstract class SwapSort implements Sort {

	protected static void swap(Object[] es, int i, int j) {
		Object temp = es[i];
		es[i] = es[j];
		es[j] = temp;
	}

}
