package cn.milai.demoimpl.compiler;

/**
 * Act  的枚举
 * 2019.12.16
 * @author milai
 */
public enum ActType {

	/**
	 * 从标准准入读取一个整数并压入栈顶
	 */
	IN_I(0x1, "in_i"),
	
	/**
	 * 将栈顶一个整数弹出并输出到标准输出
	 */
	OUT_I(0x2, "out_i"),
	
	/**
	 * 弹出栈顶 2 个整数，将 op2+op1 压入栈顶
	 */
	IADD(0x3, "iadd"),
	
	/**
	 * 弹出栈顶元素 op1, 弹出栈顶元素 op2，将 op2-op1 压入栈顶
	 */
	ISUB(0x4, "isub"),
	
	/**
	 * 弹出栈顶 2 个整数，将 op2*op1 压入栈顶
	 */
	IMUL(0x5, "imul"),
	
	/**
	 * 把常量表第 op 个常量压入栈顶
	 */
	LDC(0x6, "ldc"),
	
	/**
	 * 把局部变量表第 op 个元素压入栈顶
	 */
	ILOAD(0x7, "iload"),
	
	/**
	 * 把栈顶元素赋值到局部变量表第 op 个元素
	 */
	ISTORE(0x8, "istore"),
	
	/**
	 * 弹出栈顶第一个元素 n1,弹出栈顶第二个元素 n2，如果 n2>n1，压入 1，如果 n2<n1，压入 -1，压入 0
	 */
	ICMP(0x9, "icmp"),
	
	/**
	 * 弹出栈顶第一个元素 n1,弹出栈顶第二个元素 n2，如果 n2 == n1，跳转 pc = pc + op
	 */
	IF_ICMPNEQ(0xa, "if_icmpneq"),
	;

	/**
	 * 唯一标识
	 */
	private int code;

	/**
	 * 可读名称
	 */
	private String name;

	ActType(int code, String name) {
		this.code = code;
		this.name = name;
	}

	public int getCode() { return code; }

	public String getName() { return name; }

	public static ActType findByCode(int code) {
		for (ActType act : ActType.values()) {
			if (act.code == code) {
				return act;
			}
		}
		return null;
	}

	public static ActType findByName(String name) {
		for (ActType act : ActType.values()) {
			if (act.name.equals(name)) {
				return act;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return getName();
	}

}
