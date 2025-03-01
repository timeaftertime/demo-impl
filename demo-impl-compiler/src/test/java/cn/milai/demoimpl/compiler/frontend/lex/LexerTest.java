package cn.milai.demoimpl.compiler.frontend.lex;

import static org.junit.Assert.assertArrayEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import cn.milai.demoimpl.compiler.frontend.Scanner;
import cn.milai.demoimpl.compiler.frontend.Token;
import cn.milai.demoimpl.compiler.frontend.TokenScanner;

public class LexerTest {

	@Test
	public void testIfStatement() {
		TokenScanner tokens = LexTestUtils.LEXER.lex(new CharScanner("if(playerLife == 0) { gameOver(); }"));
		assertArrayEquals(new Token[] { new Token("if", TokenType.IF), new Token("(", TokenType.BRACKET_LEFT),
				new Token("playerLife", TokenType.IDENTIFIER), new Token(" ", TokenType.BLANK),
				new Token("==", TokenType.EQUALS), new Token(" ", TokenType.BLANK), new Token("0", TokenType.INT),
				new Token(")", TokenType.BRACKET_RIGHT), new Token(" ", TokenType.BLANK),
				new Token("{", TokenType.BLOCK_LEFT), new Token(" ", TokenType.BLANK),
				new Token("gameOver", TokenType.IDENTIFIER), new Token("(", TokenType.BRACKET_LEFT),
				new Token(")", TokenType.BRACKET_RIGHT), new Token(";", TokenType.STMD_END),
				new Token(" ", TokenType.BLANK), new Token("}", TokenType.BLOCK_RIGHT), }, leftTokens(tokens));
	}

	@Test
	public void testWhileAndNew() {
		TokenScanner tokens = LexTestUtils.LEXER.lex(
				new CharScanner("while(bossIsAlive) { outputint(\"cn.milai.ib.character.plane.WelcomePlane\"); }"));
		assertArrayEquals(new Token[] { new Token("while", TokenType.WHILE), new Token("(", TokenType.BRACKET_LEFT),
				new Token("bossIsAlive", TokenType.IDENTIFIER), new Token(")", TokenType.BRACKET_RIGHT),
				new Token(" ", TokenType.BLANK), new Token("{", TokenType.BLOCK_LEFT), new Token(" ", TokenType.BLANK),
				new Token("outputint", TokenType.OUTPUTINT), new Token("(", TokenType.BRACKET_LEFT),
				new Token("\"cn.milai.ib.character.plane.WelcomePlane\"", TokenType.STR),
				new Token(")", TokenType.BRACKET_RIGHT), new Token(";", TokenType.STMD_END),
				new Token(" ", TokenType.BLANK), new Token("}", TokenType.BLOCK_RIGHT), }, leftTokens(tokens));
	}

	private Token[] leftTokens(Scanner<Token> tokens) {
		List<Token> list = new ArrayList<>();
		while (tokens.hasMore()) {
			list.add(tokens.next());
		}
		return list.toArray(new Token[0]);
	}

}
