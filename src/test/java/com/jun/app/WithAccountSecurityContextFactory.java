package com.jun.app;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import com.jun.app.modules.account.application.AccountService;
import com.jun.app.modules.account.endpoint.controller.SignUpForm;

public class WithAccountSecurityContextFactory implements WithSecurityContextFactory<WithAccount> { // (1)

    private final AccountService accountService;

    public WithAccountSecurityContextFactory(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public SecurityContext createSecurityContext(WithAccount annotation) { // (2)
        String nickname = annotation.value(); 

        SignUpForm signUpForm = new SignUpForm(); 
        signUpForm.setNickname(nickname);
        signUpForm.setEmail(nickname + "@gmail.com");
        signUpForm.setPassword("1234asdf");
        accountService.signUp(signUpForm);

        UserDetails principal = accountService.loadUserByUsername(nickname); // (5)
        Authentication authentication = new UsernamePasswordAuthenticationToken(principal, principal.getPassword(), principal.getAuthorities()); // (6)
        SecurityContext context = SecurityContextHolder.createEmptyContext(); // (7)
        context.setAuthentication(authentication);
        return context;
    }
}