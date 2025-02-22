package cn.milai.ib.drama.dramafile.compiler.frontend.lex;

import cn.milai.common.collection.Mapping;
import cn.milai.demoimpl.compiler.frontend.lex.Lexer;
import cn.milai.demoimpl.compiler.frontend.lex.TokenDefinition;
import cn.milai.demoimpl.compiler.frontend.lex.TokenType;

/**
 * {@link Lexer} 测试工具类
 * @author milai
 * @date 2021.09.16
 */
public class LexTestUtils {

	public static final Lexer LEXER = new Lexer(
		Mapping.set(TokenType.values(), t -> new TokenDefinition(t.getRE(), t.getCode()))
	);

}
