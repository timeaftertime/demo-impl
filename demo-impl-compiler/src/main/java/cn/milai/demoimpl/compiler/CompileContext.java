package cn.milai.demoimpl.compiler;

import cn.milai.demoimpl.compiler.localvars.LocalVarsTable;

public class CompileContext {

	private ConstantTable constantTable;
	private LocalVarsTable localVars;

	public CompileContext(ConstantTable constantTable) {
		super();
		this.constantTable = constantTable;
	}

	public ConstantTable getContantTable() {
		return constantTable;
	}

	public LocalVarsTable getLocalVars() {
		return localVars;
	}

	public void refreshLocalVars() {
		this.localVars = new LocalVarsTable();
	}

}
