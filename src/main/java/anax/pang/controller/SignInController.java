package anax.pang.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SignInController {

	@RequestMapping("/signin")
	public String signIn(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("NAME:  "+auth.getName());
		return "redirect:http://localhost:8080/pangfront/servicechecker.html";
	}
}
