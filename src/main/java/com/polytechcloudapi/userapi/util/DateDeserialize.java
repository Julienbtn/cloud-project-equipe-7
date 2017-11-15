package com.polytechcloudapi.userapi.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by JOYMANGUL Jensen Selwyn
 * on 14-Nov-17.
 */
public class DateDeserialize extends JsonDeserializer<Date> {
    private SimpleDateFormat dateFormat = new SimpleDateFormat(
            "MM/dd/yyyy");

    @Override
    public Date deserialize(JsonParser paramJsonParser,
                            DeserializationContext paramDeserializationContext)
            throws IOException {
        String str = paramJsonParser.getText().trim();
        try {
            TimeZone timeZone;
            timeZone = TimeZone.getTimeZone("GMT+0:00");
            TimeZone.setDefault(timeZone);
            dateFormat.setTimeZone(timeZone);
            return dateFormat.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return paramDeserializationContext.parseDate(str);
    }
}
