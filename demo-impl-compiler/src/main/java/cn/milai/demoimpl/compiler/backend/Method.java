package cn.milai.demoimpl.compiler.backend;

import cn.milai.common.base.BytesBuilder;
import cn.milai.demoimpl.compiler.CompileContext;
import cn.milai.demoimpl.compiler.frontend.parsing.Node;

/**
 * 方法
 * 
 * @author milai
 * @date 2020.02.28
 */
public class Method {

	private String name;

	private Node stmdsNode;

	public Method(String name, Node stmdsNode) {
		this.name = name;
		this.stmdsNode = stmdsNode;
	}

	public byte[] toBytes(CompileContext ctx) {
		ctx.refreshLocalVars();

		BytesBuilder bytes = new BytesBuilder();
		bytes.appendInt16(ctx.getContantTable().utf8Index(name));
		// 这里忽略了局部变量表大小，实际上需要写入 method 属性中

		BytesBuilder codeBytes = new BytesBuilder();
		codeBytes.append(Stmds.toBytes(ctx, stmdsNode));
		bytes.appendInt16(codeBytes.toBytes().length);
		bytes.append(codeBytes);

		return bytes.toBytes();
	}

}
