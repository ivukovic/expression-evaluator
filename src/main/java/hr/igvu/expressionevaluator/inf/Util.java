package hr.igvu.expressionevaluator.inf;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME;

public final class Util {

    private Util() {
    }

    public static String formatISO8601(@NotNull LocalDateTime dtf) {
        return dtf.format(ISO_LOCAL_DATE_TIME);
    }

    public static String formatLocalDatetimeISO8601() {
        return formatISO8601(LocalDateTime.now());
    }
}
