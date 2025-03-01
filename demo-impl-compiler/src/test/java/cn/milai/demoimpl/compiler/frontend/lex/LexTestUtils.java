package cn.milai.demoimpl.compiler.frontend.lex;

import cn.milai.common.collection.Mapping;

/**
 * {@link Lexer} 测试工具类
 * 
 * @author milai
 * @date 2021.09.16
 */
public class LexTestUtils {

	public static final Lexer LEXER = new Lexer(
			Mapping.set(TokenType.values(), t -> new TokenDefinition(t.getRE(), t.getCode())));

}
