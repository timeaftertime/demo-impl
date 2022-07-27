package cn.milai.demoimpl.datastructure;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * {@link RedBlackTree} 测试类
 * @author milai
 * @date 2022.07.27
 */
public class RedBlackTreeTest {

	private final RedBlackTree<Integer, String> tree = new RedBlackTree<Integer, String>();
	private final RedBlackTree<ReverseInt, String> reverseIntTree = new RedBlackTree<ReverseInt, String>(
		(a, b) -> a.v - b.v
	);

	private static class ReverseInt {
		private int v;

		public ReverseInt(int v) {
			this.v = -v;
		}
	}

	@Before
	public void setUp() {
		tree.clear();
		reverseIntTree.clear();
	}

	@Test
	public void testPutAndRemove() {
		String[] values = { "hihihi", "heyheyhey", "yoyoyo", "hahaha", "hello", "goodbye" };
		for (int i = 0; i < values.length; i++) {
			tree.put(i, values[i]);
			for (int j = 0; j <= i; j++) {
				assertSame(values[j], tree.get(j));
			}
		}
		for (int i = 0; i < values.length; i++) {
			tree.remove(i);
			assertNull(tree.get(i));
			for (int j = i + 1; j < values.length; j++) {
				assertSame(values[j], tree.get(j));
			}
		}
	}

	@Test
	public void testModify() {
		String v1 = "this is v1";
		String v2 = "that is v2";
		int key = 99;
		tree.put(key, v1);
		assertSame(v1, tree.get(key));
		tree.put(key, v2);
		assertSame(v2, tree.get(key));
	}

	@Test
	public void testRemoveNotExists() {
		String v = "vvvvvv";
		int key = 99;
		tree.put(key, v);
		assertSame(v, tree.get(key));
		tree.remove(key + 1);
		assertSame(v, tree.get(key));
		tree.remove(key - 1);
		assertSame(v, tree.get(key));
		tree.remove(key);
		assertNull(tree.get(key));
	}

	@Test
	public void testSizeAndIsEmpty() {
		String[] values = { "111", "22", "3", "44", "555", "6666" };
		assertTrue(tree.isEmpty());
		for (int i = 0; i < values.length; i++) {
			tree.put(i, values[i]);
			assertEquals(i + 1, tree.size());
			assertFalse(tree.isEmpty());
		}
		for (int i = 0; i < values.length; i++) {
			assertFalse(tree.isEmpty());
			tree.remove(i);
			assertEquals(values.length - 1 - i, tree.size());
		}
		assertTrue(tree.isEmpty());
	}

	@Test
	public void testComparator() {
		String[] values = { "aaa", "bbb", "ccc", "qwe", "asd", "zxc" };
		for (int i = 0; i < values.length; i++) {
			reverseIntTree.put(new ReverseInt(i), values[i]);
			for (int j = 0; j <= i; j++) {
				assertSame(values[j], reverseIntTree.get(new ReverseInt(j)));
			}
		}
		for (int i = 0; i < values.length; i++) {
			reverseIntTree.remove(new ReverseInt(i));
			assertNull(tree.get(i));
			for (int j = i + 1; j < values.length; j++) {
				assertSame(values[j], reverseIntTree.get(new ReverseInt(j)));
			}
		}
	}

}
