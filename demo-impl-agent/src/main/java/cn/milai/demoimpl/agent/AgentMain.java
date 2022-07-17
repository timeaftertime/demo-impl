package cn.milai.demoimpl.agent;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

/**
 * 当前 Agent 被加载时的执行入口
 * @author milai
 * @date 2022.07.17
 */
public class AgentMain {

	public static void premain(String agentArgs, Instrumentation inst) throws ClassNotFoundException,
		UnmodifiableClassException, IOException {
		for (Class<?> c : inst.getAllLoadedClasses()) {
			if (c.getName().equals(agentArgs)) {
				inst.redefineClasses(new ClassDefinition(c, loadHello()));
				System.out.println("成功替换: " + Hello.class.getName());
			}
		}
	}

	public static void agentmain(String agentArgs, Instrumentation inst) throws ClassNotFoundException,
		UnmodifiableClassException, IOException {
		premain(agentArgs, inst);
	}

	private static byte[] loadHello() throws IOException {
		InputStream in = AgentMain.class.getResourceAsStream("/Hello.class");
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = -1;
		while ((len = in.read(buffer)) > 0) {
			out.write(buffer, 0, len);
		}
		System.out.println("load Hello.class: " + out.size());
		return out.toByteArray();
	}

}
