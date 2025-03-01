package cn.milai.demoimpl.compiler.frontend.parsing;

import java.io.InputStream;

import org.junit.Test;

import cn.milai.common.collection.Mapping;
import cn.milai.common.io.InputStreams;
import cn.milai.demoimpl.compiler.Compiler;
import cn.milai.demoimpl.compiler.frontend.CompilerTest;
import cn.milai.demoimpl.compiler.frontend.lex.CharScanner;
import cn.milai.demoimpl.compiler.frontend.lex.Lexer;
import cn.milai.demoimpl.compiler.frontend.lex.TokenDefinition;
import cn.milai.demoimpl.compiler.frontend.lex.TokenType;

public class NodePrintUtilTest {

	private static final String GRAMMER_FILE = "/grammer.txt";

	@Test
	public void testPrintNode() {
		InputStream in = CompilerTest.class.getResourceAsStream("/code.txt");
		CharScanner input = new CharScanner(String.join("", InputStreams.readLines(in)));
		Node rootNode = newParser().parse(newLexer().lex(input));
		System.out.println(NodePrintUtil.parseNodeTree(rootNode));
	}

	private static Parser newParser() {
		return new Parser(GrammerReader.parseGrammer(Compiler.class.getResourceAsStream(GRAMMER_FILE)));
	}

	private static Lexer newLexer() {
		return new Lexer(Mapping.set(TokenType.values(), t -> new TokenDefinition(t.getRE(), t.getCode())));
	}
}
