package cn.milai.demoimpl.compiler.backend;

import java.util.List;

import cn.milai.common.base.BytesBuilder;
import cn.milai.demoimpl.compiler.ActType;
import cn.milai.demoimpl.compiler.CompileContext;
import cn.milai.demoimpl.compiler.CompilerException;
import cn.milai.demoimpl.compiler.frontend.parsing.Node;

public class Factor {

	public static byte[] toBytes(CompileContext ctx, Node node) {
		List<Node> children = node.getChildren();
		BytesBuilder bb = new BytesBuilder();
		Node firstNode = children.get(0);
		switch (firstNode.getToken().getType()) {
		// Factor -> INT
		case INT:
			int v = Integer.parseInt(firstNode.getOrigin());
			bb.appendInt8(ActType.LDC.getCode()).appendInt16(ctx.getContantTable().int32Index(v));
			break;
		// Factor -> IDENTIFIER
		case IDENTIFIER:
			// 这里只处理整数类型，实际编程语言应该分类型处理
			bb.appendInt8(ActType.ILOAD.getCode()).appendInt16(ctx.getLocalVars().getLocalVar(firstNode.getOrigin()));
			break;
		// Factor -> ( Expr )
		case BRACKET_LEFT:
			bb.append(Expr.toBytes(ctx, children.get(1)));
			break;
		default:
			throw new CompilerException("未知 Factor 子节点" + firstNode.getToken());
		}
		return bb.toBytes();
	}
}
