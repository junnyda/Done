package com.jun.app.modules.main.endpoint.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.jun.app.modules.account.domain.entity.Account;
import com.jun.app.modules.account.support.CurrentUser;

@Controller
public class MainController {
    @GetMapping("/")
    public String home(@CurrentUser Account account, Model model) { // (1)
        if (account != null) {
            model.addAttribute(account);
        }
        return "index";
    }
  @GetMapping("/login") // 로그인 페이지만들e
  public String login() {
	  return "login";
  }
}