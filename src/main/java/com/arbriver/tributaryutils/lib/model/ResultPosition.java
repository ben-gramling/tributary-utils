package com.arbriver.tributaryutils.lib.model;

import com.arbriver.tributaryutils.lib.model.constants.ResultBetType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "ResultPosition")
public class ResultPosition {
    private ResultBetType betType;
    private String value;
    private Double odds;
    private String matchRefId;
}
