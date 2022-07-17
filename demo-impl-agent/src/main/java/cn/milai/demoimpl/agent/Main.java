package cn.milai.demoimpl.agent;

import java.io.IOException;
import java.util.Arrays;

import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;

/**
 * 开启 Attach 并加载 Agent
 * @author milai
 * @date 2022.07.17
 */
public class Main {

	public static void main(String[] args) throws IOException, AttachNotSupportedException, AgentLoadException,
		AgentInitializationException {
		String jarFileName = args[0];
		String processId = args[1];
		System.out.println("Start to attach process: " + processId);
		VirtualMachine virtualMachine = VirtualMachine.attach(processId);
		try {
			virtualMachine.loadAgent(jarFileName, args[2]);
		} finally {
			virtualMachine.detach();
		}

		System.out.println("AgentAttach done: " + Arrays.toString(args));
	}

}
