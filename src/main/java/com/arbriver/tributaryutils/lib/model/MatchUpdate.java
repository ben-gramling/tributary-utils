package com.arbriver.tributaryutils.lib.model;

import lombok.Data;

import java.util.List;

@Data
public class MatchUpdate {
    private Match match;
    private List<Position<?>> positions;
}