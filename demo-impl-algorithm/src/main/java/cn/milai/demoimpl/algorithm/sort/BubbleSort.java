package cn.milai.demoimpl.algorithm.sort;

/**
 * 冒泡排序
 * 时间复杂度 n^2
 * 空间复杂度 1
 * 稳定
 */
public class BubbleSort extends SwapSort {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void sort(Object[] es) {
		int len = es.length;
		for (int i = 0; i < len; i++) {
			boolean hasSwap = false;
			for (int j = 1; j < len - i; j++) {
				if (((Comparable) es[j - 1]).compareTo(es[j]) > 0) {
					swap(es, j - 1, j);
					hasSwap = true;
				}
			}
			if (!hasSwap)
				return;
		}
	}

}
