package cn.milai.demoimpl.agent;

/**
 * 不断调用 {@link Hello#sayHello()} 的 main 方法
 * @author milai
 * @date 2022.07.17
 */
public class HelloMain {

	public static void main(String[] args) throws InterruptedException {
		Hello hello = new Hello();
		while (true) {
			hello.sayHello();
			Thread.sleep(1500);
		}
	}

}
