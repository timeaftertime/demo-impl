package cn.milai.demoimpl.compiler.frontend.parsing;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

/**
 * 语法测试工具
 * 
 * @author milai
 * @date 2021.09.20
 */
public class GrammerPrintUtilTest {

	@Test
	public void printConverted() throws IOException {
		InputStream input = Grammer.class.getResourceAsStream("/grammer.txt");
		System.out.println(String.join("", GrammerPrintUtil.convert(input)));
	}
}
