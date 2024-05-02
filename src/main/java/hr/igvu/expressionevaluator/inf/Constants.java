package hr.igvu.expressionevaluator.inf;

import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.time.format.DateTimeFormatter;

public class Constants {

    public static final String DEFAULT_DATETIME_FORMAT = "dd,MM,yyyy HH:mm.ss";

    public static LocalDateTimeSerializer LOCAL_DATETIME_SERIALIZER = new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATETIME_FORMAT));

}
