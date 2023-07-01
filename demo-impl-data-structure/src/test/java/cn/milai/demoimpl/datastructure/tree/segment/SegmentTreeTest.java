package cn.milai.demoimpl.datastructure.tree.segment;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * SegmentTree 测试类
 * @author milai
 * @date 2023.06.26
 */
public class SegmentTreeTest {

	@Test
	public void testAddAndSum() {
		SegmentTree<Integer> tree = SegmentTree.newFromRange(
			1, 100, t -> new IntTreeBase(t, Integer::sum, (tr, i, v) -> v * tr.getNode(i).getLen())
		);
		tree.add(5, 20, 10);
		for (int i = 1; i < 5; i++) {
			assertEquals(0, (int) tree.range(1, i));
		}
		for (int i = 5; i <= 20; i++) {
			assertEquals((i - 5 + 1) * 10, (int) tree.range(5, i));
		}
		for (int i = 21; i <= 100; i++) {
			assertEquals(0, (int) tree.range(i, 100));
		}

		tree.add(15, 50, -20);
		for (int i = 1; i < 5; i++) {
			assertEquals(0, (int) tree.range(1, i));
		}
		for (int i = 5; i < 15; i++) {
			assertEquals((i - 5 + 1) * 10, (int) tree.range(5, i));
		}
		for (int i = 15; i <= 20; i++) {
			assertEquals((i - 15 + 1) * (-10), (int) tree.range(15, i));
		}
		for (int i = 21; i <= 50; i++) {
			assertEquals((i - 21 + 1) * (-20), (int) tree.range(21, i));
		}
		for (int i = 51; i <= 100; i++) {
			assertEquals(0, (int) tree.range(i, 100));
		}
	}

	@Test
	public void testMaxLong() {
		SegmentTree<Long> tree = SegmentTree.newFromRange(
			1, 100, t -> new LongTreeBase(t, Long::sum, Long::max, ValueMapper.self())
		);
		tree.add(5, 10, 1L);
		tree.add(8, 50, 20L);
		tree.add(30, 50, -10L);
		tree.add(60, 90, 100L);
		tree.add(100, 100, 1000L);
		for (int i = 1; i < 5; i++) {
			for (int j = 1; j <= i; j++) {
				assertEquals(String.format("[%d, %d]", j, i), 0, (long) tree.range(j, i));
			}
		}
		for (int i = 5; i < 8; i++) {
			for (int j = 1; j <= i; j++) {
				assertEquals(String.format("[%d, %d]", j, i), 1, (long) tree.range(j, i));
			}
		}
		for (int i = 11; i < 60; i++) {
			for (int j = 1; j < Math.min(i, 11); j++) {
				assertEquals(String.format("[%d, %d]", j, i), j <= 10 ? 21 : 20, (long) tree.range(j, i));
			}
		}
		for (int i = 30; i <= 50; i++) {
			for (int j = 30; j <= i; j++) {
				assertEquals(String.format("[%d, %d]", j, i), 10, (long) tree.range(j, i));
			}
		}
		for (int i = 60; i <= 90; i++) {
			for (int j = 1; j <= i; j++) {
				assertEquals(String.format("[%d, %d]", j, i), 100, (long) tree.range(j, i));
			}
		}
		assertEquals(1000, (long) tree.range(1, 100));
	}

	@Test
	public void testSetRange() {
		SegmentTree<Integer> tree = SegmentTree.newFromRange(
			1, 100, t -> new IntTreeBase(t, Integer::sum, Integer::min, ValueMapper.self())
		);
		tree.set(1, 100, 100);
		tree.set(10, 30, 10);
		tree.set(20, 30, 20);
		tree.set(50, 80, 40);
		assertEquals(10, (int) tree.range(1, 100));
		assertEquals(10, (int) tree.range(1, 30));
		assertEquals(20, (int) tree.range(20, 100));
		assertEquals(20, (int) tree.range(30, 100));
		assertEquals(40, (int) tree.range(50, 100));
		assertEquals(100, (int) tree.range(81, 100));
	}

	@Test
	public void testNewFromEnum() {
		SegmentTree<Long> tree = SegmentTree.newFromEnum(
			new long[] { 123456789123456789L, 0, 100, 10, 99, 200, 999999999L, 999999999L, 0, 100, 99, 99, 99, 100 },
			t -> new LongTreeBase(t, Long::sum, Long::max, ValueMapper.self())
		);
		tree.add(99, 200, 10L);
		tree.set(100, 999999999L, 1L);
		tree.add(999999999L, 123456789123456789L, 1000L);
		for (long i : new long[] { 0, 10, 99, 100, 200, 999999999L, 123456789123456789L }) {
			System.out.println(i + "->" + tree.range(i, i));
		}
		assertEquals(0, (long) tree.range(0, 0));
		assertEquals(0, (long) tree.range(0, 10));
		assertEquals(10, (long) tree.range(0, 100));
		assertEquals(10, (long) tree.range(99, 100));
		assertEquals(1, (long) tree.range(100, 100));
		assertEquals(1, (long) tree.range(100, 200));
		assertEquals(1001, (long) tree.range(100, 999999999L));
		assertEquals(1001, (long) tree.range(10, 999999999L));
		assertEquals(1001, (long) tree.range(0, 999999999L));
		assertEquals(1001, (long) tree.range(999999999L, 999999999L));
		assertEquals(1000, (long) tree.range(123456789123456789L, 123456789123456789L));
	}

}
