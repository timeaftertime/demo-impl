package cn.milai.demoimpl.compiler.backend;

import java.util.List;

import cn.milai.common.base.BytesBuilder;
import cn.milai.demoimpl.compiler.ActType;
import cn.milai.demoimpl.compiler.CompileContext;
import cn.milai.demoimpl.compiler.CompilerException;
import cn.milai.demoimpl.compiler.frontend.parsing.Node;

public class Expr {

	public static byte[][] toSplitBytes(CompileContext ctx, Node node) {
		List<Node> children = node.getChildren();
		BytesBuilder bb = new BytesBuilder();
		// Expr -> Factor Expr'
		parseExprSBytes(ctx, bb, children.get(1));
		return new byte[][] { Factor.toBytes(ctx, children.get(0)), bb.toBytes() };
	}

	public static byte[] toBytes(CompileContext cxt, Node node) {
		BytesBuilder bb = new BytesBuilder();
		for (byte[] bytes : toSplitBytes(cxt, node)) {
			bb.append(bytes);
		}
		return bb.toBytes();
	}

	private static void parseExprSBytes(CompileContext ctx, BytesBuilder bb, Node node) {
		List<Node> children = node.getChildren();
		// Expr' -> ϵ
		if (children.isEmpty()) {
			return;
		}
		Node firstNode = children.get(0);
		ActType act = null;
		// 这里都只处理了 int 类型，实际上需要进行类型转换和类型判断
		switch (firstNode.getToken().getType()) {
		// Expr' -> > Expr Expr'
		case CMP_GT:
			act = ActType.ICMP;
			break;
		// Expr' -> * Expr Expr'
		case TIMES:
			act = ActType.IMUL;
			break;
		// Expr' -> + Expr Expr'
		case PLUS:
			act = ActType.IADD;
			break;
		// Expr' -> - Expr Expr'
		case MINUS:
			act = ActType.ISUB;
			break;
		default:
			throw new CompilerException("未知 Expr' 子节点: " + firstNode);
		}
		byte[][] exprBytes = Expr.toSplitBytes(ctx, children.get(1));
		byte[] factorBytes = exprBytes[0];
		byte[] otherBytes = exprBytes[1];
		bb.append(factorBytes);
		bb.appendInt8(act.getCode());
		bb.append(otherBytes);
		parseExprSBytes(ctx, bb, children.get(2));
	}
}
