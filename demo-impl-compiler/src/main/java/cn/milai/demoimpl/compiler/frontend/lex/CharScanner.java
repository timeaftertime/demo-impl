package cn.milai.demoimpl.compiler.frontend.lex;

import java.util.Arrays;

import cn.milai.demoimpl.compiler.frontend.Scanner;
import cn.milai.demoimpl.compiler.frontend.StringUtil;

/**
 * 处理 {@link Character} 类型的 {@link Scanner}
 * @author milai
 * @date 2020.02.04
 */
public class CharScanner extends Scanner<Character> {

	public CharScanner(String str) {
		super(Arrays.asList(StringUtil.toCharacterArray(str)));
	}

}
