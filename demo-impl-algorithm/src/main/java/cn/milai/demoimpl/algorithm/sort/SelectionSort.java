package cn.milai.demoimpl.algorithm.sort;

/**
 * 
 * 选择排序
 * 时间复杂度 n^2
 * 空间复杂度 1
 * 不稳定
 */
public class SelectionSort extends SwapSort {

	SelectionSort() {
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void sort(Object[] es) {
		int len = es.length;
		for (int i = 0; i < len; i++) {
			int min = i;
			for (int j = i + 1; j < len; j++) {
				if (((Comparable) es[min]).compareTo(es[j]) > 0)
					min = j;
			}
			swap(es, min, i);
		}
	}

}
