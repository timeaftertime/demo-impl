package cn.milai.demoimpl.compiler;

import java.util.List;
import java.util.stream.Collectors;

import cn.milai.demoimpl.compiler.frontend.parsing.Node;

/**
 * 出现不符合预期的子节点列表的异常
 * @author milai
 * @date 2020.02.29
 */
public class UnknownChildrenException extends CompilerException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UnknownChildrenException(String node, List<Node> children) {
		super(
			String.format(
				"非终结符节点 %s 出现不符合预期的子节点列表 %s",
				node, children.stream().map(n -> n.getSymbol().getCode()).collect(Collectors.toList())
			)
		);
	}
}
