package com.es.phoneshop.util;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomParser {
    public static final Pattern nonNegativeInt = Pattern.compile("^\\d+$");
    public static final Pattern nonNegativeDouble = Pattern.compile("^\\d+\\.?\\d*$");

    private CustomParser() {
    }

    public static Optional<Double> parseNonNegativeDouble(String potentialDouble) {
        if (potentialDouble == null || potentialDouble.isEmpty()) {
            return Optional.empty();
        }
        Matcher matcher = nonNegativeDouble.matcher(potentialDouble);
        if (matcher.find()) {
            return Optional.of(Double.parseDouble(matcher.group()));
        }
        return Optional.empty();
    }

    public static Optional<Integer> parseNonNegativeInt(String potentialInteger) {
        if (potentialInteger == null || potentialInteger.isEmpty()) {
            return Optional.empty();
        }
        Matcher matcher = nonNegativeInt.matcher(potentialInteger);
        if (matcher.find()) {
            return Optional.of(Integer.parseInt(matcher.group()));
        }
        return Optional.empty();
    }
}
