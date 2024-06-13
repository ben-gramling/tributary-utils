package com.arbriver.tributaryutils.lib.model.constants;

import lombok.Getter;

@Getter
public enum Sport {
    TENNIS("01"),
    SOCCER("02"),
    FOOTBALL("03");

    private final String id;

    Sport(String i) {
        this.id = i;
    }
}
