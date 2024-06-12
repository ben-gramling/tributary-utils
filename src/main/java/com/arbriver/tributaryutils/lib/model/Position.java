package com.arbriver.tributaryutils.lib.model;

import lombok.Data;

@Data
public abstract class Position {
    protected Bookmaker bookmaker;
    protected String value;
    protected Double odds;
    protected String matchRefId;
}