package cn.milai.demoimpl.compiler;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;

import cn.milai.common.collection.Mapping;
import cn.milai.common.ex.unchecked.Uncheckeds;
import cn.milai.common.io.InputStreams;
import cn.milai.demoimpl.compiler.backend.CFG;
import cn.milai.demoimpl.compiler.backend.CompilerData;
import cn.milai.demoimpl.compiler.backend.Method;
import cn.milai.demoimpl.compiler.constant.Constant;
import cn.milai.demoimpl.compiler.frontend.lex.CharScanner;
import cn.milai.demoimpl.compiler.frontend.lex.Lexer;
import cn.milai.demoimpl.compiler.frontend.lex.TokenDefinition;
import cn.milai.demoimpl.compiler.frontend.lex.TokenType;
import cn.milai.demoimpl.compiler.frontend.parsing.GrammerReader;
import cn.milai.demoimpl.compiler.frontend.parsing.Parser;

/**
 * 编译器
 */
public class Compiler {

	private static final String GRAMMER_FILE = "/grammer.txt";

	public static byte[] compile(InputStream in) {
		CharScanner input = new CharScanner(String.join("", InputStreams.readLines(in)));
		return Uncheckeds.rethrow(() -> build(CFG.parse(newParser().parse(newLexer().lex(input)))));
	}

	private static Parser newParser() {
		return new Parser(GrammerReader.parseGrammer(Compiler.class.getResourceAsStream(GRAMMER_FILE)));
	}

	private static Lexer newLexer() {
		return new Lexer(Mapping.set(TokenType.values(), t -> new TokenDefinition(t.getRE(), t.getCode())));
	}

	private static byte[] build(CompilerData data) throws IOException {
		ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(byteOutput);
		ConstantTable table = new ConstantTable();
		byte[] methodsBytes = buildMethodsBytes(data, table);
		// 常量池
		writeConstantTable(table, out);
		// 方法
		out.write(methodsBytes);
		return byteOutput.toByteArray();
	}

	private static byte[] buildMethodsBytes(CompilerData data, ConstantTable table) throws IOException {
		ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(byteOutput);
		// 方法个数
		out.writeShort(data.getMethods().size());
		CompileContext ctx = new CompileContext(table);
		for (Method method : data.getMethods()) {
			out.write(method.toBytes(ctx));
		}
		return byteOutput.toByteArray();
	}

	private static void writeConstantTable(ConstantTable table, DataOutputStream out) throws IOException {
		Constant<?>[] constants = table.getConstants();
		// 常量表最前面为 null ，所以实际大小为数组长度 -1
		out.writeShort(constants.length - 1);
		for (Constant<?> c : constants) {
			if (c == null) {
				continue;
			}
			out.writeByte(c.getType().getCode());
			out.write(c.getBytes());
		}
	}
}
