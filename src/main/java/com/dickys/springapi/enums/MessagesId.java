package com.dickys.springapi.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MessagesId {
    SUCCESS_MESSAGE("success.success"),
    ;

    private final String id;
}
