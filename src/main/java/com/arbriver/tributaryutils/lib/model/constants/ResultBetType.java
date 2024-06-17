package com.arbriver.tributaryutils.lib.model.constants;

import com.arbriver.tributaryutils.lib.model.BetType;

public enum ResultBetType implements BetType {
    MATCH_WINNER,
    ASIAN_HANDICAP,
    GOALS_OVER_UNDER,
    BOTH_TEAMS_SCORE,
    EXACT_SCORE,
    DOUBLE_CHANCE,
    TEAM_OVER_UNDER,
    ODD_EVEN,
    TEAM_ODD_EVEN,
    RESULT_BOTH_TEAMS_SCORE,
    RESULT_TOTAL_GOALS,
    TEAM_CLEAN_SHEET,
    TEAM_WIN_TO_NIL,
    EXACT_GOALS,
    TEAM_EXACT_GOALS,
    TEAM_TO_SCORE,
    DRAW_NO_BET,
    TEAM_GOALS_RANGE,
    BOTH_TO_SCORE_OVER_UNDER;

    @Override
    public String getId() {
        return String.format("%02d", ordinal());
    }
}
