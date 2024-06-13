package com.arbriver.tributaryutils.lib.model.constants;

import com.arbriver.tributaryutils.lib.model.BetType;

public enum PropBetType implements BetType {
    POINTS;

    @Override
    public String getId() {
        return String.format("%02d", ordinal());
    }
}
