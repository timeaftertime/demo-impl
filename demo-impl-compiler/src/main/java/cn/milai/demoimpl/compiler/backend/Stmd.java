package cn.milai.demoimpl.compiler.backend;

import java.util.List;

import cn.milai.common.base.BytesBuilder;
import cn.milai.demoimpl.compiler.ActType;
import cn.milai.demoimpl.compiler.CompileContext;
import cn.milai.demoimpl.compiler.CompilerException;
import cn.milai.demoimpl.compiler.frontend.parsing.Node;

/**
 * Stmd 语法树
 * 
 * @author milai
 * @date 2020.04.16
 */
public class Stmd {

	public static byte[] toBytes(CompileContext ctx, Node stmdNode) {
		List<Node> children = stmdNode.getChildren();
		switch (children.get(0).getToken().getType()) {
		// Stmd -> IDENTIFIER = Expr
		case IDENTIFIER:
			return parseStoreStmd(ctx, children.get(0), children.get(2));
		// Stmd -> IF ( Expr ) { Stmds }
		case IF:
			return parseIfStmd(ctx, children.get(2), children.get(5));
		// Stmd -> OUTPUTINT ( Expr )
		case OUTPUTINT:
			return parseOutputStmd(ctx, children.get(2));
		// Stmd -> INPUTINT ( IDENTIFIER )
		case INPUTINT:
			return parseInputStmd(ctx, children.get(2));
		default:
			throw new CompilerException("Stmd 结点出现未知子节点：" + children);
		}
	}

	private static byte[] parseStoreStmd(CompileContext ctx, Node idNode, Node exprNode) {
		BytesBuilder bb = new BytesBuilder();
		bb.append(Expr.toBytes(ctx, exprNode));
		bb.appendInt8(ActType.ISTORE.getCode()).appendInt16(ctx.getLocalVars().getLocalVar(idNode.getOrigin()));
		return bb.toBytes();
	}

	private static byte[] parseIfStmd(CompileContext ctx, Node exprNode, Node StmdsNode) {
		BytesBuilder bb = new BytesBuilder();
		byte[] conditionBytes = Expr.toBytes(ctx, exprNode);
		byte[] stmdsBytes = Stmds.toBytes(ctx, StmdsNode);
		// 先执行 condition 的代码
		bb.append(conditionBytes);
		// IF_ICMPGT 如果 Expr 设置的栈顶 2 个元素不相等，则设置 pc = pc + stmdsBytes.length
		bb.appendInt8(ActType.IF_ICMPNEQ.getCode()).appendInt16(stmdsBytes.length);
		// 最后写入 stmdsBytes 的代码
		bb.append(stmdsBytes);
		return bb.toBytes();
	}

	private static byte[] parseOutputStmd(CompileContext ctx, Node exprNode) {
		BytesBuilder bb = new BytesBuilder();
		bb.append(Expr.toBytes(ctx, exprNode));
		bb.appendInt8(ActType.OUT_I.getCode());
		return bb.toBytes();
	}

	private static byte[] parseInputStmd(CompileContext ctx, Node idNode) {
		BytesBuilder bb = new BytesBuilder();
		bb.appendInt8(ActType.IN_I.getCode());
		bb.appendInt8(ActType.ISTORE.getCode()).appendInt16(ctx.getLocalVars().getLocalVar(idNode.getOrigin()));
		return bb.toBytes();
	}

}
