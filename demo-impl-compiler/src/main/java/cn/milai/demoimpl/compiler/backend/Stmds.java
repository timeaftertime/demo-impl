package cn.milai.demoimpl.compiler.backend;

import java.util.List;

import cn.milai.common.base.BytesBuilder;
import cn.milai.demoimpl.compiler.CompileContext;
import cn.milai.demoimpl.compiler.frontend.parsing.Node;

/**
 * Stmds 语法树
 * 
 * @author milai
 * @date 2020.04.16
 */
public class Stmds {

	public static byte[] toBytes(CompileContext ctx, Node stmdsNode) {
		BytesBuilder bytes = new BytesBuilder();
		parseStmds(ctx, bytes, stmdsNode);
		return bytes.toBytes();
	}

	private static void parseStmds(CompileContext ctx, BytesBuilder bytes, Node stmdsNode) {
		List<Node> children = stmdsNode.getChildren();
		// Stmds -> ϵ
		if (children.isEmpty()) {
			return;
		}
		// Stmds -> Stmd Stmds
		bytes.append(Stmd.toBytes(ctx, children.get(0)));
		parseStmds(ctx, bytes, children.get(1));
	}
}
