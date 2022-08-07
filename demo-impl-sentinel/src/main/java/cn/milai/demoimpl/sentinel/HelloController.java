package cn.milai.demoimpl.sentinel;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;

@RestController
public class HelloController {

	@SentinelResource(value = "sayHello", blockHandler = "blockHello")
	@RequestMapping("/hello")
	public String sayHello() {
		return "Hello!";
	}

	public String blockHello(BlockException e) {
		return "Blocking!" + e;
	}
}
