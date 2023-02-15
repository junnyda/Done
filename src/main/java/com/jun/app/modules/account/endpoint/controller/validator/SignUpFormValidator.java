package com.jun.app.modules.account.endpoint.controller.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.jun.app.account.infra.repository.AccountRepository;
import com.jun.app.modules.account.endpoint.controller.SignUpForm;

@Component // (2)
@RequiredArgsConstructor // (2)
public class SignUpFormValidator implements Validator { // (1)

    private final AccountRepository accountRepository; // (2)

    @Override
    public boolean supports(Class<?> clazz) { // (3)
        return clazz.isAssignableFrom(SignUpForm.class);
    }

    @Override
    public void validate(Object target, Errors errors) { // (4)
        SignUpForm signUpForm = (SignUpForm) target;
        if (accountRepository.existsByEmail(signUpForm.getEmail())) {
            errors.rejectValue("email", "invalid.email", new Object[]{signUpForm.getEmail()},
                    "이미 사용중인 이메일입니다.");
        }
        if (accountRepository.existsByNickname(signUpForm.getNickname())) {
            errors.rejectValue("nickname", "invalid.nickname", new Object[]{signUpForm.getEmail()},
                    "이미 사용중인 닉네임입니다.");
        }
    }
}