package com.arbriver.tributaryutils.lib.model.constants;

import java.util.HashSet;
import java.util.Set;

public class CommonConstants {
    public static final String RETRIEVER_CLASS = "retriever.class";
    public static final String PROCESSOR_CLASS = "processor.class";
    public static final String PARSER_CLASS = "retriever.parser.class";
    public static final String REST_SERVICE_CLASS = "retriever.restservice.class";
    public static final String EVENT_PAGE_TEMPLATE = "retriever.eventPage.template";

    public static Set<String> badWords = Set.of(
            "fc","atletico","ca"
    );
}
