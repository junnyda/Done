package com.jun.app.modules.settings.controller;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TagForm {
    private String tagTitle;
}