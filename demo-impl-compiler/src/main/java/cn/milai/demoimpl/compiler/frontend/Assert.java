package cn.milai.demoimpl.compiler.frontend;

import java.util.Collection;

public class Assert {

	public static void notEmpty(Collection<?> c, String msg) {
		if (c == null || c.isEmpty()) {
			throw new IllegalStateException(msg);
		}
	}
	
	public static void notNull(Object o, String msg) {
		if (o == null) {
			throw new IllegalStateException(msg); 
		}
	}

}
