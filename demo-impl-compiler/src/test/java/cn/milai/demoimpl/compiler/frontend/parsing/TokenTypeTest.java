package cn.milai.demoimpl.compiler.frontend.parsing;

import static org.junit.Assert.assertTrue;

import java.util.regex.Pattern;

import org.junit.Test;

import cn.milai.demoimpl.compiler.frontend.lex.TokenType;

/**
 * {@link TokenType} 测试类
 * 
 * @author milai
 * @date 2021.01.31
 */
public class TokenTypeTest {

	@Test
	public void testFloat() {
		assertTrue(Pattern.matches(TokenType.FLOAT.getRE(), "0.34"));
		assertTrue(Pattern.matches(TokenType.FLOAT.getRE(), "-0.34"));
		assertTrue(Pattern.matches(TokenType.FLOAT.getRE(), "+0.34"));
		assertTrue(Pattern.matches(TokenType.FLOAT.getRE(), "0.0"));
		assertTrue(Pattern.matches(TokenType.FLOAT.getRE(), "1.34"));
		assertTrue(Pattern.matches(TokenType.FLOAT.getRE(), "0.99"));
	}
}
