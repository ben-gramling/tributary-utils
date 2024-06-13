package com.arbriver.tributaryutils.lib.model;

import com.arbriver.tributaryutils.lib.model.constants.PositionStatus;
import com.arbriver.tributaryutils.lib.utils.UpdateHelper;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.Instant;

@Data
public class Position<M extends BetType> {
    @Id
    protected String betId;
    protected M betType;
    protected Bookmaker bookmaker;
    protected String value;
    protected Double odds;
    protected String matchRefId;
    protected PositionStatus status;
    protected Instant modifiedAt;

    public void generateBetId() {
        assert matchRefId != null;
        assert betId != null;
        assert betType != null;
        assert bookmaker != null;
        this.betId = UpdateHelper.generateBetId(matchRefId, value, bookmaker, betType);
    }
}