package com.jun.app.modules.account.support;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

@Retention(RetentionPolicy.RUNTIME) // (1)
@Target(ElementType.PARAMETER) // (2)
@AuthenticationPrincipal(expression = "#this == 'anonymousUser' ? null : account") // (3)
public @interface CurrentUser {

}
