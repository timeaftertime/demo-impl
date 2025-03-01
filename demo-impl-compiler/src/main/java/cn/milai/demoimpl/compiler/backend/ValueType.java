package cn.milai.demoimpl.compiler.backend;

import java.util.HashMap;
import java.util.Map;

import cn.milai.demoimpl.compiler.frontend.lex.TokenType;

/**
 * 值（表达式、常量、变量等）的类型
 * @author milai
 * @date 2020.03.03
 */
public class ValueType {

	private static final String TYPE_VOID = "void";
	private static final String TYPE_INT = "int";
	private static final String TYPE_FLOAT = "float";
	private static final String TYPE_STR = "str";

	private String name;
	private String canonical;

	private static Map<String, ValueType> types = new HashMap<>();

	public static final ValueType VOID = of(TYPE_VOID);
	public static final ValueType INT = of(TYPE_INT);
	public static final ValueType FLOAT = of(TYPE_FLOAT);
	public static final ValueType STR = of(TYPE_STR);

	private ValueType() {
	}

	/**
	 * 获取类型名
	 * @return
	 */
	public String getName() { return name; }

	/**
	/**
	 * 获取最简表示的字符串
	 * @return
	 */
	public String getCanonical() { return canonical; }

	/**
	 * 获取指定 Token 类型对应的值类型
	 * @param type
	 * @return
	 */
	public static ValueType ofToken(TokenType type) {
		switch (type) {
			case INT :
			case FLOAT :
			case STR :
				return of(type.getCode());
			default:
				throw new IllegalArgumentException("不支持的 Token 类型：" + type);
		}
	}

	public static ValueType of(String typeName) {
		if (types.containsKey(typeName)) {
			return types.get(typeName);
		}
		return types.computeIfAbsent(typeName, name -> {
			String canonical;
			switch (name) {
				case TYPE_VOID : {
					canonical = "V";
					break;
				}
				case TYPE_INT : {
					canonical = "I";
					break;
				}
				case TYPE_FLOAT : {
					canonical = "F";
					break;
				}
				case TYPE_STR : {
					canonical = "S";
					break;
				}
				default: {
					throw new IllegalArgumentException("暂不支持的类型：" + name);
				}
			}
			ValueType valueType = new ValueType();
			valueType.name = name;
			valueType.canonical = canonical;
			return valueType;
		});
	}
}
