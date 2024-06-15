package com.arbriver.tributaryutils.lib.model;

import com.arbriver.tributaryutils.lib.model.constants.BetClass;
import com.arbriver.tributaryutils.lib.model.constants.PositionStatus;
import com.arbriver.tributaryutils.lib.utils.UpdateHelper;
import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Objects;

@Data
@Document(collection = "positions")
public class Position {
    @Id
    private String betId;
    private BetType betType;
    private BetClass betClass;
    private Bookmaker bookmaker;
    private String value;
    private Double odds;
    private String matchRefId;
    private PositionStatus status;
    private Instant modifiedAt;
    private String participant;

    public void generateBetId() {
        this.betId = UpdateHelper.generateBetId(Objects.requireNonNull(matchRefId),
                Objects.requireNonNull(value),
                Objects.requireNonNull(bookmaker),
                Objects.requireNonNull(betType));
    }
}