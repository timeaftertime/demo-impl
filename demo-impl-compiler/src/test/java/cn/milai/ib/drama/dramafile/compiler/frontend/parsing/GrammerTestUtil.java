package cn.milai.ib.drama.dramafile.compiler.frontend.parsing;

import java.io.IOException;
import java.io.InputStream;

import cn.milai.demoimpl.compiler.frontend.parsing.GrammerConvertor;

/**
 * 语法测试工具
 * 
 * @author milai
 * @date 2021.09.20
 */
public class GrammerTestUtil {

	// @Test
	public void printConverted() throws IOException {
		InputStream input = GrammerTestUtil.class.getResourceAsStream("/parsing/testParseProductions.txt");
		System.out.println(String.join("", GrammerConvertor.convert(input)));
	}
}
