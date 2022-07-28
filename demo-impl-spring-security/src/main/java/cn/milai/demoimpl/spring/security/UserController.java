package cn.milai.demoimpl.spring.security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

	@RequestMapping(value = "/hello")
	@ResponseBody
	public String hello() {
		return "Hello Spring Security!";
	}

	@GetMapping(value = "/myLogin")
	@ResponseBody
	public String myLogin() {
		return "This my Login Page!";
	}

}
