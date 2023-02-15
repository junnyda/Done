package com.jun.app;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME) // (1)
@WithSecurityContext(factory = WithAccountSecurityContextFactory.class) // (2) 
public @interface WithAccount {
    String value(); 
}
