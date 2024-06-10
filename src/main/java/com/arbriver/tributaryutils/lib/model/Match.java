package com.arbriver.tributaryutils.lib.model;

import com.arbriver.tributaryutils.lib.model.constants.Sport;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "Match")
public class Match {
    private String matchID;
    private Sport sport;
    private String homeName;
    private String awayName;
}