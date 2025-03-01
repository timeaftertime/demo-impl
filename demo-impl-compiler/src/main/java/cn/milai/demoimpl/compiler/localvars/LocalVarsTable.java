package cn.milai.demoimpl.compiler.localvars;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 局部变量表
 * 
 * @author milai
 *
 */
public class LocalVarsTable {

	private AtomicInteger varCnt = new AtomicInteger();
	private Map<String, Integer> varName2Index = new HashMap<>();

	public int getLocalVar(String varName) {
		return varName2Index.computeIfAbsent(varName.strip(), name -> varCnt.getAndIncrement());
	}
}
