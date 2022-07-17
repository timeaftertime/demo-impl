package cn.milai.demoimpl.algorithm.sort;

public class ShellSort extends SwapSort {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void sort(Object[] es) {
		int len = 1;
		while (len < es.length / 3) {
			len = len * 3 + 1;
		}
		for (; len >= 1; len /= 3) {
			for (int i = len - 1; i < es.length; i++) {
				for (int j = i; j - len >= 0; j -= len) {
					if (((Comparable) es[j]).compareTo(es[j - len]) >= 0) {
						break;
					}
					swap(es, j, j - len);
				}
			}
		}
	}

}
