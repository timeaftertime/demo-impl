package cn.milai.demoimpl.compiler.backend;

import java.util.List;

import cn.milai.demoimpl.compiler.frontend.parsing.Node;

/**
 * 语法树根节点
 * 
 * @author milai
 * @date 2020.04.16
 */
public class CFG {

	private CFG() {
	}

	public static CompilerData parse(Node cfgNode) {
		CompilerData data = new CompilerData();
		// CFG -> Methods
		List<Node> children = cfgNode.getChildren();
		data.setMethods(Methods.parse(children.get(0)));
		return data;
	}

}
