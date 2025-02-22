package cn.milai.demoimpl.compiler.frontend;

import java.util.Collection;

/**
 * {@link Token} 的 {@link Scanner}
 * @author milai
 */
public class TokenScanner extends Scanner<Token> {

	public TokenScanner(Collection<Token> tokens) {
		super(tokens);
	}

}
