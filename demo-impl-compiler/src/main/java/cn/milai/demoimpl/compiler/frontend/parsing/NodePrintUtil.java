package cn.milai.demoimpl.compiler.frontend.parsing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.milai.common.uniform.serialize.JSON;

public class NodePrintUtil {

	static class PrintNode {
		private String value;
		private List<PrintNode> children;

		public PrintNode(String value, List<PrintNode> children) {
			this.value = value;
			this.children = children;
		}

		public String getValue() {
			return value;
		}

		public List<PrintNode> getChildren() {
			return children;
		}
	}

	private static Map<String, List<Map<String, ?>>> newPrintNode(Node node) {
		if (node == null) {
			return null;
		}
		List<Map<String, ?>> children = new ArrayList<>();
		if (node.getSymbol().isNonTerminal()) {
			for (Node c : node.getChildren()) {
				children.add(newPrintNode(c));
			}
		}
		Map<String, List<Map<String, ?>>> res = new HashMap<>();
		res.put(getNodeValue(node), children);
		return res;
	}

	public static String parseNodeTree(Node node) {
		return JSON.write(newPrintNode(node));
	}

	private static String getNodeValue(Node node) {
		if (node == null || node.getSymbol() == null) {
			return null;
		}

		return node.getSymbol().getCode() + " -> " + node.getOrigin();
	}
}
