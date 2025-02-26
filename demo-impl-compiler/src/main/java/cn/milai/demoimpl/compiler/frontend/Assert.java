package cn.milai.demoimpl.compiler.frontend;

import java.util.Collection;

import cn.milai.demoimpl.compiler.AssertException;

public class Assert {

	public static void notEmpty(Collection<?> c, String msg) {
		if (c == null || c.isEmpty()) {
			throw new AssertException(msg);
		}
	}

	public static void notNull(Object o, String msg) {
		if (o == null) {
			throw new AssertException(msg);
		}
	}

	public static void isTrue(boolean v, String msg) {
		if (!v) {
			throw new AssertException(msg);
		}
	}

	public static void hasLength(String v, String msg) {
		if (v == null || v.isEmpty()) {
			throw new AssertException(msg);
		}
	}

}
