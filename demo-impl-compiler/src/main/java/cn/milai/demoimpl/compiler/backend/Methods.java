package cn.milai.demoimpl.compiler.backend;

import java.util.ArrayList;
import java.util.List;

import cn.milai.demoimpl.compiler.frontend.parsing.Node;

/**
 * Methods 语法树
 * 
 * @author milai
 * @date 2020.04.16
 */
public class Methods {

	private Methods() {
	}

	public static List<Method> parse(Node methodsNode) {
		List<Method> methods = new ArrayList<>();
		parseMethods(methods, methodsNode);
		return methods;
	}

	private static void parseMethods(List<Method> methods, Node methodsNode) {
		// Methods -> ϵ
		if (methodsNode.getChildren().isEmpty()) {
			return;
		}
		List<Node> children = methodsNode.getChildren();
		// Methods -> TYPE_VOID IDENTIFIER ( ) { Stmds } Methods
		methods.add(new Method(children.get(1).getOrigin(), children.get(5)));
		parseMethods(methods, children.get(7));
	}

}
