package com.arbriver.tributaryutils.lib.model;

import com.arbriver.tributaryutils.lib.model.constants.Sport;
import com.arbriver.tributaryutils.lib.utils.UpdateHelper;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Map;

@Data
@Document(collection = "matches")
public class Match {
    @Id
    private String matchID;
    private Sport sport;
    private String homeName;
    private String awayName;
    private String league;
    private Map<Bookmaker, String> links;
    private Instant startTime;
    private int numBooks;

    public void generateMatchID() {
        assert sport != null;
        assert homeName != null;
        assert awayName != null;
        this.matchID = UpdateHelper.generateMatchID(sport, homeName, awayName);
    }
}