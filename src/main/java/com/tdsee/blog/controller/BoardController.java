package com.tdsee.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.tdsee.blog.config.auth.PrincipalDetail;

@Controller
public class BoardController {
	
	// @AuthenticationPrincipal PrincipalDetail principal
	@GetMapping({"", "/"})
	public String index(@AuthenticationPrincipal PrincipalDetail principal) {
		// /WEB-INF/views/index.jsp
		System.out.println("User id: "  + principal.getUsername());
		return "index";
	}
}
