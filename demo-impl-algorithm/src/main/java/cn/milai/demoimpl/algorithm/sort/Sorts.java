package cn.milai.demoimpl.algorithm.sort;

public class Sorts {

	private static Sort selection;
	private static Sort bubble;
	private static Sort quick;
	private static Sort shell;

	public static void selectSort(Object[] es) {
		if (selection == null) {
			selection = new SelectionSort();
		}
		selection.sort(es);
	}

	public static void bubbleSort(Object[] es) {
		if (bubble == null) {
			bubble = new BubbleSort();
		}
		bubble.sort(es);
	}

	public static void quickSort(Object[] es) {
		if (quick == null) {
			quick = new QuickSort();
		}
		quick.sort(es);
	}

	public static void shellSort(Object[] es) {
		if (shell == null) {
			shell = new ShellSort();
		}
		shell.sort(es);
	}

}
