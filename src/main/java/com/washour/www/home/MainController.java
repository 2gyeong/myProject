package com.washour.www.home;


import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.washour.www.member.Member;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {
	
	@GetMapping("/")
	public String index() {

		
		return "index"; 
	}
/*
	@GetMapping("/")
	public String index(Model model, @AuthenticationPrincipal Member member) {

		if(member == null) {
			model.addAttribute("message", "로그인 정보 없음");
		} else {
			model.addAttribute("message", "로그인 정보" + member.getUsername() + member.getEmail() + member.getAddress());
		}
		return "index"; 
	}
	
*/
	@GetMapping("/category_1")
	public String category() {
		
		return "category/category_1";
	}
	
	@GetMapping("/product_1")
	public String product() {
		return "product_1";
	}
	


}