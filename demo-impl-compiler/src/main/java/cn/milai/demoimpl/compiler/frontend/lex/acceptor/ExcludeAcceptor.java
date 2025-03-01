package cn.milai.demoimpl.compiler.frontend.lex.acceptor;

import java.util.Collection;
import java.util.Set;

import cn.milai.common.collection.Filter;

/**
 * 接受不在 {@link Set} 的字符的 {@link CharAcceptor}
 * @author milai
 * @date 2020.03.19
 */
public class ExcludeAcceptor extends CharSetAcceptor {

	public ExcludeAcceptor(char... excluded) {
		super(excluded);
	}

	public ExcludeAcceptor(Collection<Character> excluded) {
		super(excluded);
	}

	@Override
	protected boolean accept(Set<Character> chs, char c) {
		return !chs.contains(c);
	}

	@Override
	public Set<Character> accepts(Set<Character> alphabet) {
		return Filter.nset(alphabet, c -> getChs().contains(c));
	}

	@Override
	public String toString() {
		return String.format("exclude(%s)", charsString());
	}

}
