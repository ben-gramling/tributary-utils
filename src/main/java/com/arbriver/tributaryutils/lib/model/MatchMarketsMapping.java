package com.arbriver.tributaryutils.lib.model;

import com.fasterxml.jackson.databind.JsonNode;

public record MatchMarketsMapping(Match match, JsonNode markets, Participants denormalizedParticipants) { }
