package com.arbriver.tributaryutils.lib.utils;

import com.arbriver.tributaryutils.lib.model.BetType;
import com.arbriver.tributaryutils.lib.model.Bookmaker;
import com.arbriver.tributaryutils.lib.model.constants.CommonConstants;
import com.arbriver.tributaryutils.lib.model.constants.Sport;

public class UpdateHelper {
    final static int HASH_LENGTH = 11;

    public static String generateMatchID(Sport sport, String homeTeam, String awayTeam) {
        StringBuilder result = new StringBuilder();
        result.append(sport.getId());
        result.append("-");
        String teamSegment = String.valueOf(homeTeam.compareToIgnoreCase(awayTeam) > 0 ?
                Math.abs((awayTeam.toLowerCase() + homeTeam.toLowerCase()).hashCode()) :
                Math.abs((homeTeam.toLowerCase() + awayTeam.toLowerCase()).hashCode()));

        teamSegment = teamSegment.length() < HASH_LENGTH ?
                "0".repeat(HASH_LENGTH - teamSegment.length()).concat(teamSegment) :
                teamSegment.substring(0, HASH_LENGTH);

        return result.append(teamSegment).toString();
    }

    public static String generateBetId(String matchID, String value, Bookmaker book, BetType betType) {
        StringBuilder result = new StringBuilder();
        String valueSegment = String.valueOf(Math.abs((matchID + value.toLowerCase()).hashCode()));
        valueSegment = valueSegment.length() < HASH_LENGTH ?
                "0".repeat(HASH_LENGTH - valueSegment.length()).concat(valueSegment) :
                valueSegment.substring(0, HASH_LENGTH);

        result.append(book.getId()).append(betType.getId()).append("-").append(valueSegment);
        return result.toString();
    };

    public static String cleanName(String input) {
        StringBuilder result = new StringBuilder();
        String[] tokens = input
                .toLowerCase()
                .replaceAll("[^A-Za-z0-9\\s]","")
                .trim()
                .split(" ");
        for (String token : tokens) {
            if(!CommonConstants.badWords.contains(token)) result.append(token).append("_");
        }
        return result.substring(0, result.length()-1);
    }
}
