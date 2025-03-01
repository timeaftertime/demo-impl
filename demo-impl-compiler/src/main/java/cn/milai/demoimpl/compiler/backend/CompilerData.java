package cn.milai.demoimpl.compiler.backend;

import java.util.List;

/**
 * 编译器后端用到的中间数据结构
 * 
 * @author milai
 * @date 2020.02.28
 */
public class CompilerData {

	/**
	 * 方法列表
	 */
	private List<Method> methods;

	public List<Method> getMethods() {
		return methods;
	}

	public void setMethods(List<Method> methods) {
		this.methods = methods;
	}

}
