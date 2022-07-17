package cn.milai.demoimpl.algorithm.sort;

import static org.junit.Assert.assertArrayEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * 排序测试
 * @author milai
 * @date 2022.07.17
 */
public class SortTest {

	private List<ArrayPair> arrayPairs;

	private static class ArrayPair {
		private Object[] disordered;
		private Object[] ordered;

		public ArrayPair(Object[] disordered, Object[] ordered) {
			this.disordered = disordered;
			this.ordered = ordered;
		}
	}

	@Before
	public void setUp() {
		arrayPairs = new ArrayList<>();
		arrayPairs.add(
			new ArrayPair(
				new Integer[] { 2, 3, 4, -21, 7, 8, 11, 89, 0, -1, 6 },
				new Integer[] { -21, -1, 0, 2, 3, 4, 6, 7, 8, 11, 89 }
			)
		);
		arrayPairs.add(
			new ArrayPair(
				new String[] { "中文", "abc", "a", "xyz", "000", "22" },
				new String[] { "000", "22", "a", "abc", "xyz", "中文" }
			)
		);

	}

	private void sortTest(Sort sorter) {
		for (ArrayPair pair : arrayPairs) {
			sorter.sort(pair.disordered);
			assertArrayEquals(pair.ordered, pair.disordered);
		}
	}

	@Test
	public void selectionSort() {
		sortTest(new SelectionSort());
	}

	@Test
	public void bubbleSort() {
		sortTest(new BubbleSort());
	}

	@Test
	public void quickSort() {
		sortTest(new QuickSort());
	}

	@Test
	public void shellSort() {
		sortTest(new ShellSort());
	}

}
