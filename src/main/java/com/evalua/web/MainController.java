package com.evalua.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {

	@RequestMapping("/")
	public ModelAndView startup(){
		ModelAndView mv=new ModelAndView("index");
		return mv;
	}
}
