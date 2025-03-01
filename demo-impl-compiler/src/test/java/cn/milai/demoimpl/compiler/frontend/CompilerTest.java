package cn.milai.demoimpl.compiler.frontend;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

import cn.milai.common.base.BytesBuilder;
import cn.milai.demoimpl.compiler.Compiler;

public class CompilerTest {

	public byte[] getExpectedData() {
		byte[] beforeMethodData = {
				// ConstantPool Size = 6 ("main", 1, 2, 3, 100, 10)
				0x0, 0x6,
				// [1] UTF8(4) main
				0x4, 0x0, 0x4, 0x6d, 0x61, 0x69, 0x6e,
				// [2] Int(1) 1
				0x1, 0x0, 0x0, 0x0, 0x1,
				// [3] Int(1) 2
				0x1, 0x0, 0x0, 0x0, 0x2,
				// [4] Int(1) 3
				0x1, 0x0, 0x0, 0x0, 0x3,
				// [5] Int(1) 100
				0x1, 0x0, 0x0, 0x0, 0x64,
				// [6] Int(1) 10
				0x1, 0x0, 0x0, 0x0, 0xa, };
		byte[] methodsMetaBytes = {
				// method name -> main(1)
				0x0, 0x1,
		};
		byte[] methodCodeBytes = {
				// inputint(0x1)
				0x1,
				// istore(0x8) v
				0x8, 0x0, 0x0,
				// iload(0x7) v
				0x7, 0x0, 0x0,
				// ldc(0x6) 1
				0x6, 0x0, 0x2,
				// iadd(0x3) -> v + 1
				0x3,
				// ldc(0x6) 2
				0x6, 0x0, 0x3,
				// iadd(0x3) -> v + 1 + 2
				0x3,
				// ldc(0x6) 3
				0x6, 0x0, 0x4,
				// iadd(0x3) -> v + 1 + 2 + 3
				0x3,
				// ldc(0x6) 3
				0x6, 0x0, 0x4,
				// ldc(0x6) 2
				0x6, 0x0, 0x3,
				// iadd(0x3) -> (3 + 2)
				0x3,
				// isub(0x4) -> v + 1 + 2 + 3 - (3 + 2)
				0x4,
				// ldc(0x6) 1
				0x6, 0x0, 0x2,
				// iadd(0x3) -> v + 1 + 2 + 3 - (3 + 2) + 1
				0x3,
				// istore(0x8) v
				0x8, 0x0, 0x0,
				// iload(0x7) v
				0x7, 0x0, 0x0,
				// ldc(0x6) 100
				0x6, 0x0, 0x5,
				// icmp(0x9) -> v > 100
				0x9,
				// if_icmpneq(0xa) 4(要跳转的字节偏移) -> if
				0xa, 0x0, 0x4,
				// iload(0x7) v
				0x7, 0x0, 0x0,
				// outputint(0x2)
				0x2,
				// iload(0x7) v
				0x7, 0x0, 0x0,
				// ldc(0x6) 10
				0x6, 0x0, 0x6,
				// imul(0x5)
				0x5,
				// outputint(0x2)
				0x2,
		};
		BytesBuilder bb = new BytesBuilder();
		return bb.append(beforeMethodData)
				.append(new byte[] {
						// Method Count = 1
						0x0, 0x1,
				})
				.append(methodsMetaBytes)
				.appendInt16(methodCodeBytes.length).append(methodCodeBytes)
				.toBytes();
	}

	@Test
	public void testCompile() {
		byte[] data = Compiler.compile(CompilerTest.class.getResourceAsStream("/code.txt"));
		printBytesString(getExpectedData());
		printBytesString(data);
		assertArrayEquals(getExpectedData(), data);
	}

	public void printBytesString(byte[] data) {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (byte b : data) {
			sb.append("0x" + Integer.toHexString(b) + ", ");
		}
		sb.append("]");
		System.out.println(sb.toString());
	}
}
