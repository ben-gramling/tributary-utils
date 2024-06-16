package com.arbriver.tributaryutils.lib.converters;

import com.arbriver.tributaryutils.lib.model.BetType;
import com.arbriver.tributaryutils.lib.model.constants.ResultBetType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
public class BetTypeConverter implements Converter<String, BetType> {
    @Override
    public BetType convert(String s) {
        return ResultBetType.valueOf(s);
    }
}
