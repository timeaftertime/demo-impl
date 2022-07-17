package cn.milai.demoimpl.algorithm.sort;

/**
 * 
 * 快速排序
 * 时间复杂度 nlog(n)
 * 空间复杂度 nlog(n)
 * 不稳定
 */
public class QuickSort extends SwapSort {

	QuickSort() {
	}

	@Override
	public void sort(Object[] es) {
		sort(es, 0, es.length - 1);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void sort(Object[] es, int start, int end) {
		if (start >= end)
			return;
		int l = start + 1;
		int r = end;
		while (l <= r) {
			while (l <= r && l <= end && ((Comparable) es[l]).compareTo(es[start]) <= 0) {
				l++;
			}
			while (l <= r && r >= start && ((Comparable) es[r]).compareTo(es[start]) >= 0) {
				r--;
			}
			if(l < r)
				swap(es, l, r);
		}
		swap(es, start, r);
		sort(es, start, r - 1);
		sort(es, l, end);
	}

}
