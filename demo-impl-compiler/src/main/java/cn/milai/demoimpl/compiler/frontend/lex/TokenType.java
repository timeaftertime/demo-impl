package cn.milai.demoimpl.compiler.frontend.lex;

import java.util.Arrays;

/**
 * 词法分析 Token 类型枚举
 * 
 * @author milai
 * @date 2020.02.10
 */
public enum TokenType {

	// 空白间隔（包括空格、\n 等）
	BLANK("\\s+", 0),
	// 标点符号
	BLOCK_LEFT("\\{", 1), BLOCK_RIGHT("\\}", 1), STMD_END(";", 1), ASSIGN("=", 1), EQUALS("==", 1), NOT_EQUALS("!=", 1),
	BRACKET_LEFT("\\(", 1), BRACKET_RIGHT("\\)", 1), PLUS("\\+", 1), MINUS("\\-", 1), TIMES("\\*", 1),
	DIVISION("\\\\", 1), COMMA(",", 1), CMP_GT(">", 1),
	// 关键字
	IF("if", 2), WHILE("while", 2), IMPORT("import", 2), INPUTINT("inputint", 2), OUTPUTINT("outputint", 2),
	TYPE_VOID("void", 2),
	// 标识符
	IDENTIFIER("[_a-zA-Z]\\w*", 3),
	// 常量
	INT("[+\\-]?0|([1-9][0-9]*)", 4), FLOAT("[+\\-]?(0|([1-9][0-9]*))\\.[0-9]+", 4), STR("\"[^\"]*\"", 4),;

	/**
	 * 正则表达式
	 */
	private String re;

	/**
	 * 匹配顺序，越靠前，越优先匹配
	 */
	private int order;

	TokenType(String re, int order) {
		this.re = re;
		this.order = order;
	}

	/**
	 * 获取正则表达式
	 * 
	 * @return
	 */
	public String getRE() {
		return re;
	}

	public int getOrder() {
		return order;
	}

	/**
	 * 获取唯一标识
	 * 
	 * @return
	 */
	public String getCode() {
		return name();
	}

	/**
	 * 获取 code 对应的 TokenType ，若不存在，返回 null
	 * 
	 * @param code
	 */
	public static TokenType findByCode(String code) {
		TokenType[] values = TokenType.values();
		Arrays.sort(values, (o1, o2) -> o1.order - o2.order);
		for (TokenType type : values) {
			if (type.getCode().equals(code)) {
				return type;
			}
		}
		return null;
	}

	/**
	 * 获取 code 对应的 TokenType ，若不存在，抛出 IllegalArgumentException 异常
	 * 
	 * @param code
	 * @return
	 * @throws IllegalArgumentException
	 */
	public static TokenType of(String code) throws IllegalArgumentException {
		TokenType type = findByCode(code);
		if (type == null) {
			throw new IllegalArgumentException(String.format("%s 对应的 TokenType 不存在", code));
		}
		return type;
	}

}
